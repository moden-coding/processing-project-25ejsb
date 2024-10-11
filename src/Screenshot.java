import processing.core.PApplet;

public class Screenshot extends Button {

    public Screenshot(float sizeX, float sizeY, int posX, int posY, int r, int g, int b, String text, PApplet applet) {
        super(sizeX, sizeY, posX, posY, r, g, b, text, applet);
        //TODO Auto-generated constructor stub
    }

    public void clicked() {
        App.takingScreenshot = true;
    }    
}
