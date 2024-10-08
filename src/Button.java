import processing.core.PApplet;

public class Button {

    private float sizeX;
    private float sizeY;
    private float posX;
    private float posY;
    public int r;
    public int g;
    public int b;
    private String text;
    private PApplet applet;

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

    public boolean mouseClicked() {
        if (applet.mouseX >= posX && applet.mouseX <= posX+sizeX && applet.mouseY >= posY && applet.mouseY <= posY+sizeY) {
            clicked();
            return true;
        }
        return false;
    }

    protected void clicked() {
        System.out.println("Clicked!");
    }

    public void drawButton() {
        applet.fill(r, g, b);
        applet.rect(posX, posY, sizeX, sizeY);

        applet.fill(0);
        applet.text(text, posX+(sizeX/applet.CENTER), ((posY+(sizeY/2))+applet.CENTER));
    }
}