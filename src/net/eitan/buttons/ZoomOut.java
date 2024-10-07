package net.eitan.buttons;

import net.eitan.App;
import net.eitan.Button;
import processing.core.PApplet;

public class ZoomOut extends Button {

    public ZoomOut(float sizeX, float sizeY, int posX, int posY, int r, int g, int b, String text, PApplet applet) {
        super(sizeX, sizeY, posX, posY, r, g, b, text, applet);
    }
    
    @Override
    protected void clicked() {
        App.gridX += 1;
        App.gridY += 1;
    }
}
