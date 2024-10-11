import processing.core.PApplet;

public class Action {
    public int oldr;
    public int oldg;
    public int oldb;
    
    public int newr;
    public int newb;
    public int newg;

    public Pixel pixel;
    private PApplet applet;

    public Action(int oldr, int oldg, int oldb, int newr, int newb, int newg, Pixel pixel, PApplet applet) {
        this.oldr = oldr;
        this.oldg = oldg;
        this.oldb = oldb;
        this.newr = newr;
        this.newg = newg;
        this.newb = newb;
        this.pixel = pixel;
        this.applet = applet;
    }

    public void revertAction() {
        pixel.r = oldr;
        pixel.b = oldb;
        pixel.g = oldg;
    }

    public void undoAction() {
        pixel.r = newr;
        pixel.g = newg;
        pixel.b = newb;
    }
}
