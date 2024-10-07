package net.eitan;
import processing.core.PApplet;

public class Button {

    private float sizeX;
    private float sizeY;
    private float posX;
    private float posY;
    private PApplet applet;
    public int r;
    public int g;
    public int b;
    private String text;

    public Button(float sizeX, float sizeY, int posX, int posY, int r, int g, int b, String text, PApplet applet) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.posX = posX;
        this.posY = posY;
        this.r = r;
        this.g = g;
        this.b = b;
        this.text = text;
        this.applet = applet;
    }

    public void mouseClicked() {
        if (applet.mouseX >= posX && applet.mouseX <= posX+sizeX && applet.mouseY >= posY && applet.mouseY <= posY+sizeY) {
            clicked();
        }
    }

    protected void clicked() {
        System.out.println("Clicked!");
    }

    public void drawButton() {
        applet.fill(r, g, b);
        applet.rect(posX, posY, sizeX, sizeY);

        applet.fill(0);
        applet.text(text, applet.CENTER, (sizeY/2)+applet.CENTER);
    }
}