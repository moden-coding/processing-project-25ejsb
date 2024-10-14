import processing.core.PApplet;

public class Undo extends Button {

    public Undo(float sizeX, float sizeY, int posX, int posY, int r, int g, int b, String text, PApplet applet) {
        super(sizeX, sizeY, posX, posY, r, g, b, text, applet);
    }
    
    @Override
    protected void clicked() {
        App.undo();
    }
}
