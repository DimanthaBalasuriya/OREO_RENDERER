package input;

import org.joml.Vector2f;

public class Mouse {

    private static Mouse instance;

    private float x, y, lastX, lastY;
    private Vector2f delta;

    public Mouse() {
        this.x = 0;
        this.y = 0;
        this.lastX = -1;
        this.lastY = -1;
        this.delta = new Vector2f();
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

    public void setLastX(float lastX) {
        this.lastX = lastX;
    }

    public void setLastY(float lastY) {
        this.lastY = lastY;
    }

    public Vector2f getDelta() {
        return delta;
    }

    public float getDX() {
        return getX() - getLastX();
    }

    public float getDY() {
        return getY() - getLastY();
    }

    public void setNewPos() {
        setLastX(getX());
        setLastY(getY());
    }

    public void calculateDelta() {
        delta.x = 0;
        delta.y = 0;
        if (getLastX() > 0 && getLastY() > 0) {
            double deltax = getX() - getLastX();
            double deltay = getY() - getLastY();
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
                delta.y = (float) deltax;
            }
            if (rotateY) {
                delta.x = (float) deltay;
            }
        }
        setLastX(getX());
        setLastY(getY());
    }

    public static void cursor_position_callback(long window, double xpos, double ypos) {
        getInstance().x = (float) xpos;
        getInstance().y = (float) ypos;
    }

    public void reset() {
        x = 0;
        y = 0;
    }

}
