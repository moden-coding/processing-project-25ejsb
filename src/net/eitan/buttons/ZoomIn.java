package net.eitan.buttons;

import net.eitan.App;
import net.eitan.Button;
import processing.core.PApplet;

public class ZoomIn extends Button {

    public ZoomIn(float sizeX, float sizeY, int posX, int posY, int r, int g, int b, String text, PApplet applet) {
        super(sizeX, sizeY, posX, posY, r, g, b, text, applet);
    }
    
    @Override
    protected void clicked() {
        App.gridX -= 1;
        App.gridY -= 1;
    }
}
