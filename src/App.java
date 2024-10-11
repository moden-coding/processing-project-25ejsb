import processing.core.*;
import processing.event.MouseEvent;

import java.io.*;
import java.util.*;

public class App extends PApplet {
    public static void main(String[] args) {
        PApplet.main("App");
    }

    public static String inputText = "";  // To store user input
    public static boolean isFocused = false;  // To track if the text box is focused
    private TextBox textBoxFocused;

    private int windowWidth = 1200;
    private int windowHeight = 800;

    public static int gridX = 25;
    public static int gridY = 25;

    public static int backgroundr = 255;
    public static int backgroundg = 255;
    public static int backgroundb = 255;
    
    private float pixelSizeX;
    private float pixelSizeY;

    private ArrayList<Pixel> pixels = new ArrayList<>();
    private ArrayList<TextBox> textboxes = new ArrayList<>();
    private ArrayList<Button> buttons = new ArrayList<>();

    private TextBox color = new TextBox(0, 50, 150, 40, this);
    private ZoomIn zoomIn = new ZoomIn(50, 50, 1, 200, 70, 116, 224, "🔎", this);
    private ZoomOut zoomOut = new ZoomOut(50, 50, 75, 200, 70, 116, 224, "🔍", this);

    private Pixel hoveredPixel;

    private ArrayList<Action> undo = new ArrayList<>();

    private ArrayList<Action> actions = new ArrayList<>();

    public int width() {
        return windowWidth;
    }

    public int height() {
        return windowHeight;
    }

    private void undo() {
        if (actions.size() > 0) {
            Action action = actions.get(actions.size()-1);
            undo.add(action);
            action.revertAction();
            actions.remove(action);
        }
    }

    private void redo() {
        if (undo.size() > 0) {
            Action action = undo.get(undo.size()-1);
            actions.add(action);
            action.undoAction();
            undo.remove(action);
        }
    }

    private void updateGridSize() {
        pixelSizeX = (float) windowWidth/gridX;
        pixelSizeY = (float) windowHeight/gridY;
        for (Pixel pixel: pixels) {
            pixel.sizeX(pixelSizeX);
            pixel.sizeY(pixelSizeY);
            pixel.x(((float) ((pixelSizeX*pixel.gridX())-pixelSizeX)));
            pixel.y(((float) ((pixelSizeY*pixel.gridY())-pixelSizeY)));
        }
    }

    private void updateGrid() {
        for (int y = 1; y <= gridY; y++) {
            for (int i = 1; i <= gridX; i++) {
                ArrayList<Pixel> working = new ArrayList<>();
                for (Pixel pixel: pixels) {
                    if (pixel.gridX() == i && pixel.gridY() == y) {
                        working.add(pixel);
                    }
                }
                if (working.size() == 0) {
                    Pixel pixel = new Pixel(((float) ((pixelSizeX*i)-pixelSizeX)), ((float) ((pixelSizeY*y)-pixelSizeY)), (float) pixelSizeX, (float) pixelSizeY, i, y, backgroundr, backgroundg, backgroundb, this);
                    pixels.add(pixel);
                }
            }
        }
    }

    public void drawPixel(Pixel pixel) {
        if (mouseX >= pixel.x() && mouseY >= pixel.y() && mouseX <= pixel.x()+pixel.sizeX() && mouseY <= pixel.y()+pixel.sizeY()) {
            if (inputText.length() > 0 && inputText.split(",").length >= 3) {
                try {
                    int newR = Integer.valueOf(inputText.split(",")[0].strip());
                    int newG = Integer.valueOf(inputText.split(",")[1].strip());
                    int newB = Integer.valueOf(inputText.split(",")[2].strip());
                    if (!(newR == pixel.r && newG == pixel.g && newB == pixel.b)) {
                        Action action = new Action(pixel.r, pixel.g, pixel.b, newR, newB, newG, pixel, this);
                        actions.add(action);
                        pixel.r = newR;
                        pixel.g = newG;
                        pixel.b = newB;      
                    }
                } catch (Exception e) {
                   // Action action = new Action(pixel.r, pixel.g, pixel.b, pixel, this);
                   // actions.add(action);
                   Action action = new Action(pixel.r, pixel.g, pixel.b, 0, 0, 0, pixel, this);
                   actions.add(action);
                   pixel.r = 0;
                   pixel.b = 0;
                   pixel.g = 0;
                }
            } else {
                Action action = new Action(pixel.r, pixel.g, pixel.b, 255, 0, 0, pixel, this);
                actions.add(action);
                pixel.r = 255;
                pixel.g = 0;
                pixel.b = 0;
            }
        }
    }

    public void resetPixel(Pixel pixel) {
        if (mouseX >= pixel.x() && mouseY >= pixel.y() && mouseX <= pixel.x()+pixel.sizeX() && mouseY <= pixel.y()+pixel.sizeY()) {
            Action action = new Action(pixel.r, pixel.g, pixel.b, backgroundr, backgroundb, backgroundg, pixel, this);
            actions.add(action);
            pixel.r = backgroundr;
            pixel.g = backgroundg;
            pixel.b = backgroundb;
        }
    }

    public void setup(){
        buttons.add(zoomIn);
        buttons.add(zoomOut);
        textboxes.add(color);
        background(backgroundr, backgroundg, backgroundb);
        updateGridSize();
        try {
            File file = new File("save.draw");
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String[] data = reader.nextLine().split(", ");
                Pixel pixel = new Pixel(Float.valueOf(data[0]), Float.valueOf(data[1]), Float.valueOf(data[2]), Float.valueOf(data[3]), Integer.valueOf(data[7]), Integer.valueOf(data[8]), Integer.valueOf(data[4]), Integer.valueOf(data[5]), Integer.valueOf(data[6]), this);
                pixels.add(pixel);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            for (int y = 1; y <= gridY; y++) {
                for (int i = 1; i <= gridX; i++) {
                    Pixel pixel = new Pixel(((float) ((pixelSizeX*i)-pixelSizeX)), ((float) ((pixelSizeY*y)-pixelSizeY)), pixelSizeX, pixelSizeY, i, y, backgroundr, backgroundb, backgroundg, this);
                    pixels.add(pixel);
                }
            }
        }  
        PFont f = createFont("AppleSDGothicNeo-ExtraBold",16,true);
        textFont(f,16);
        frameRate(240);
        surface.setTitle("Drawing App!");   
    }

    public void settings() {
        size(windowWidth, windowHeight);
    }

    public void draw() {
        updateGridSize();
        updateGrid();
        for (Pixel pixel: pixels) {
            if (mouseX >= pixel.x() && mouseY >= pixel.y() && mouseX <= pixel.x()+pixel.sizeX() && mouseY <= pixel.y()+pixel.sizeY()) {
                hoveredPixel = pixel;
            }
            if (pixel.gridX() <= gridX && pixel.gridY() <= gridY) {
                pixel.drawPixel();
            }
        }
        for (TextBox textBox: textboxes) {
            textBox.drawTextBox();
        }
        for (Button button: buttons) {
            button.drawButton();
        }

        textSize(32);
        text("Color (R,G,B):", 1, 40);
        text("Zoom", 20, 170);
        textSize(16);
    }

    @Override
    public void mouseDragged() {
        if (!isFocused) {
            for (Pixel pixel: pixels) {
                if (mouseButton == RIGHT) {
                    resetPixel(pixel);
                } else {
                    drawPixel(pixel);
                }
            }
        }
    }

    @Override
    public void mousePressed() {
        boolean successfulInput = false;   
        // textboxes
        for (TextBox textBox: textboxes) {
            if (textBox.checkIfClicked()) {
                isFocused = true;  // Focus the text box
                textBoxFocused = textBox;
                successfulInput = true;
            } else {
                isFocused = false;  // Unfocus if clicked outside
                textBoxFocused = null;
            }
        }
        for (Button button: buttons) {
            if (button.mouseClicked()) {
                System.out.println(button + " Click successfully worked!");
                successfulInput = true;
            }
        }
        if (!successfulInput) {
            for (Pixel pixel: pixels) {
                if (mouseButton == RIGHT) {
                    resetPixel(pixel);
                } else {
                    drawPixel(pixel);
                }

            }
        }
    }

    @Override
    public void keyTyped() {
        if (key == 'c') {
            undo();
        }
    
        if (key == 'v') {
            redo();
        }

        if (textBoxFocused != null) {
            textBoxFocused.type();
        }
    }

    @Override
    public void mouseWheel(MouseEvent event) {
        if (event.getCount() > 0 && gridX <= 70) {
            gridX++;
            gridY++;
        }
        if (event.getCount() < 0 && gridX > 1) {
            gridX--;
            gridY--;
        }
    }

    @Override
    public void exit() {
        try {
            FileWriter fileWriter = new FileWriter("save.draw");
            String writerText = "";
            for (Pixel pixel: pixels) {
                writerText += pixel.x() + ", " + pixel.y() + ", " + pixel.sizeX() + ", " + pixel.sizeY() + ", " + pixel.r + ", " + pixel.g + ", " + pixel.b + ", " + pixel.gridX() + ", " + pixel.gridY() + "\n";
            }
            fileWriter.write(writerText);
            fileWriter.close();
            System.out.println("Wrote the file!");
        } catch (IOException e) {
            System.out.println("An error occured");
            e.printStackTrace();
        }
    }
}
