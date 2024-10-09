import processing.core.*;
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

    public int width() {
        return windowWidth;
    }

    public int height() {
        return windowHeight;
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
                    pixel.r = Integer.valueOf(inputText.split(",")[0].strip());
                    pixel.g = Integer.valueOf(inputText.split(",")[1].strip());
                    pixel.b = Integer.valueOf(inputText.split(",")[2].strip());
                } catch (Exception e) {
                   pixel.r = 0;
                   pixel.b = 0;
                   pixel.g = 0; 
                }
            } else {
                pixel.r = 255;
                pixel.g = 0;
                pixel.b = 0;
            }
        }
    }

    public void resetPixel(Pixel pixel) {
        if (mouseX >= pixel.x() && mouseY >= pixel.y() && mouseX <= pixel.x()+pixel.sizeX() && mouseY <= pixel.y()+pixel.sizeY()) {
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
        for (int y = 1; y <= gridY; y++) {
            for (int i = 1; i <= gridX; i++) {
                Pixel pixel = new Pixel(((float) ((pixelSizeX*i)-pixelSizeX)), ((float) ((pixelSizeY*y)-pixelSizeY)), pixelSizeX, pixelSizeY, i, y, backgroundr, backgroundb, backgroundg, this);
                pixels.add(pixel);
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
        if (textBoxFocused != null) {
            textBoxFocused.type();
        }
    }
}
