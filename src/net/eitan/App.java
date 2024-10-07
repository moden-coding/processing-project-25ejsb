package net.eitan;
import processing.core.*;
import java.util.*;

public class App extends PApplet{
    public static void main(String[] args)  {
        PApplet.main("App");
    }

    public static String inputText = "";  // To store user input
    public static boolean isFocused = false;  // To track if the text box is focused
    private TextBox textBoxFocused;

    private int windowWidth = 1200;
    private int windowHeight = 800;

    public static int gridX = 10;
    public static int gridY = 10;
    
    private double pixelSizeX;
    private double pixelSizeY;

    private ArrayList<Pixel> pixels = new ArrayList<>();
    private ArrayList<TextBox> textboxes = new ArrayList<>();

    private TextBox color = new TextBox(0, 0, 150, 40, this);

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
            pixel.sizeX((float) pixelSizeX);
            pixel.sizeY((float) pixelSizeY);
        }
    }

    private void updateGrid() {
        for (int y = 1; y <= gridY; y++) {
            for (int i = 1; i <= gridX; i++) {
                boolean found = false;
                for (Pixel pixel: pixels) {
                    if (i != pixel.gridX() && y != pixel.gridY()) {
                        found = true;
                    }
                }
                if (!found) {
                    System.out.println("Yes");
                    Pixel pixel = new Pixel(((float) ((pixelSizeX*i)-pixelSizeX)), ((float) ((pixelSizeY*y)-pixelSizeY)), (float)pixelSizeX, (float)pixelSizeY, i, y, 255, 255, 255, this);
                    pixels.add(pixel);
                }
            }
        }
    }

    public void setup(){
        textboxes.add(color);
        background(200);
        updateGridSize();
        for (int y = 1; y <= gridY; y++) {
            for (int i = 1; i <= gridX; i++) {
                Pixel pixel = new Pixel(((float) ((pixelSizeX*i)-pixelSizeX)), ((float) ((pixelSizeY*y)-pixelSizeY)), (float)pixelSizeX, (float)pixelSizeY, i, y, 255, 255, 255, this);
                pixels.add(pixel);
            }
        }
        textSize(16);
    }

    public void settings() {
        size(windowWidth, windowHeight);
    }

    public void draw() {
        updateGridSize();
        updateGrid();
        for (Pixel pixel: pixels) {
            pixel.drawPixel();
        }
        for (TextBox textBox: textboxes) {
            textBox.drawTextBox();
        }
    }

    @Override
    public void mouseDragged() {
        if (!isFocused) {
            for (Pixel pixel: pixels) {
                if (mouseX >= pixel.x() && mouseY >= pixel.y() && mouseX <= pixel.x()+pixel.sizeX() && mouseY <= pixel.y()+pixel.sizeY()) {
                    if (inputText.length() > 0 && inputText.split(",").length >= 3) {
                        pixel.r = Integer.valueOf(inputText.split(",")[0]);
                        pixel.g = Integer.valueOf(inputText.split(",")[1]);
                        pixel.b = Integer.valueOf(inputText.split(",")[2]);
                    } else {
                        pixel.r = 255;
                        pixel.g = 0;
                        pixel.b = 0;
                    }
                }
            }
        }
    }

    @Override
    public void mousePressed() {
        for (TextBox textBox: textboxes) {
            if (textBox.checkIfClicked()) {
                isFocused = true;  // Focus the text box
                textBoxFocused = textBox;
            } else {
                isFocused = false;  // Unfocus if clicked outside
                textBoxFocused = null;
                for (Pixel pixel: pixels) {
                    if (mouseX >= pixel.x() && mouseY >= pixel.y() && mouseX <= pixel.x()+pixel.sizeX() && mouseY <= pixel.y()+pixel.sizeY()) {
                        if (inputText.length() > 0 && inputText.split(",").length >= 3) {
                            pixel.r = Integer.valueOf(inputText.split(",")[0]);
                            pixel.g = Integer.valueOf(inputText.split(",")[1]);
                            pixel.b = Integer.valueOf(inputText.split(",")[2]);
                        } else {
                            pixel.r = 255;
                            pixel.g = 0;
                            pixel.b = 0;
                        }
                    }
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
