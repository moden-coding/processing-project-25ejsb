
import processing.core.PApplet;

public class TextBox {
    private float x;
    private float y;
    private float sizeX;
    private float sizeY;
    private PApplet applet;

    public TextBox(float x, float y, float sizeX, float sizeY, PApplet applet) {
        this.x = x;
        this.y = y;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.applet = applet;
    }

    public void drawTextBox() {
        // Draw the text box (a rectangle)
        applet.stroke(0);  // Black border
        applet.fill(255);  // White fill
        applet.rect(x, y, sizeX, sizeY);
                
        // Highlight the text box when it's focused
        if (App.isFocused) {
            applet.stroke(0, 0, 255);  // Blue border if focused
            applet.noFill();
            applet.rect(x, y, sizeX, sizeY);
        }
                
        // Display the entered text inside the text box
        applet.fill(0);  // Black text
        applet.text(App.inputText, applet.CENTER, ((y+(sizeY/2))+applet.CENTER));  // Adjust x and y for the text position
    }

    public boolean checkIfClicked() {
        if (applet.mouseX >= this.x && applet.mouseY >= this.y && applet.mouseX <= this.x+this.sizeX && applet.mouseY <= this.y+this.sizeY) {
            return true;
        } else {
            return false;
        }
    }

    public void type() {
        if (App.isFocused) {
            if (applet.key == applet.BACKSPACE && App.inputText.length() > 0) {
                App.inputText = App.inputText.substring(0, App.inputText.length() - 1);
            } else if (applet.key != applet.BACKSPACE && applet.key != applet.DELETE && applet.key != applet.ENTER && applet.key != applet.RETURN) {
                App.inputText += applet.key;
            }
        }
    }
}
