

import processing.core.PApplet;

public class Redo extends Button {

    public Redo(float sizeX, float sizeY, int posX, int posY, int r, int g, int b, String text, PApplet applet) {
        super(sizeX, sizeY, posX, posY, r, g, b, text, applet);
    }

    @Override
    protected void clicked() {
        App.redo();
    }
    
}
