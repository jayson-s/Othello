package sample;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Piece {
    protected boolean isPlaced = false;
    protected int isWhite = 0;
    private Circle circle = new Circle(20, 20, 30);

    public void toggle() {
        if (isWhite == 0) {
            isWhite = 1;
            circle.setFill(Color.WHITE);
        }
        else if (isWhite == 1) {
            isWhite = 0;
            circle.setFill(Color.BLACK);
        }
    }

    public void place(int player) {
        if (player == 1) {
            toggle();
        }
        isPlaced = true;
    }

    public Circle getCircle() {
        return circle;
    }
}