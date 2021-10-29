package input;

import org.joml.Vector2f;

public class Mouse {

    private static Mouse instance;

    private float x, y, lastX, lastY;

    public Mouse() {
        this.x = 0;
        this.y = 0;
        this.lastX = 0;
        this.lastY = 0;
    }

    public static Mouse getInstance() {
        return instance == null ? instance = new Mouse() : instance;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getLastX() {
        return lastX;
    }

    public float getLastY() {
        return lastY;
    }

    public static void cursor_position_callback(long window, double xpos, double ypos) {
        getInstance().x = (float) xpos;
        getInstance().y = (float) ypos;
    }

}
