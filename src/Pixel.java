
import processing.core.PApplet;

public class Pixel {
    private float pixelX;
    private float pixelY;
    private float sizeX;
    private float sizeY;
    public int r;
    public int g;
    public int b;
    private int gridX;
    private int gridY;
    private PApplet applet;

    public float x() {
        return pixelX;
    }

    public void x(float x) {
        this.pixelX = x;
    }

    public float y() {
        return pixelY;
    }

    public void y(float y) {
        this.pixelY = y;
    }

    public float sizeX() {
        return sizeX;
    }

    public void sizeX(float size) {
        this.sizeX = size;
    }

    public int gridX() {
        return gridX;
    }

    public int gridY() {
        return gridY;
    }

    public float sizeY() {
        return sizeY;
    }
    
    public void sizeY(float size) {
        this.sizeY = size;
    }

    public Pixel(float x, float y, float sizeX, float sizeY, int gridX, int gridY, int r, int g, int b, PApplet applet) {
        this.pixelX = x;
        this.pixelY = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.r = r;
        this.g = g;
        this.b = b;
        this.gridX = gridX;
        this.gridY = gridY;
        this.applet = applet;
    }

    public void drawPixel() {
        applet.fill(r, g, b);
        applet.stroke(r, g, b);
        applet.rect(pixelX, pixelY, sizeX, sizeY);
    }
}
