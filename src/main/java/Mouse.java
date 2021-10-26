import org.joml.Vector2f;

public class Mouse {

    private static Mouse instance;

    private double xPos, yPos, lastX, lastY;

    private Vector2f display;

    public Mouse() {
        xPos = 0;
        yPos = 0;
        display = new Vector2f(0, 0);
    }

    public static Mouse getInstance() {
        return instance == null ? instance = new Mouse() : instance;
    }

    public static void cursor_position_callback(long window, double xPos, double yPos) {
        getInstance().xPos = xPos;
        getInstance().yPos = yPos;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public double getLastX() {
        return lastX;
    }

    public double getLastY() {
        return lastY;
    }

    public Vector2f getDisplay() {
        return display;
    }

    public void setLastX(double lastX) {
        this.lastX = lastX;
    }

    public void setLastY(double lastY) {
        this.lastY = lastY;
    }

    public void mouseDisplay() {
        display.x = 0;
        display.y = 0;
        if (getInstance().getLastX() > 0 && getInstance().getLastY() > 0) {
            double deltax = getInstance().getxPos() - getInstance().getLastX();
            double deltay = getInstance().getyPos() - getInstance().getLastY();
            boolean rotateX = deltax != 0;
            boolean rotateY = deltay != 0;
            if (rotateX) {
                display.y = (float) deltax;
            }
            if (rotateY) {
                display.x = (float) deltay;
            }
        }
        getInstance().setLastX(getInstance().getxPos());
        getInstance().setLastY(getInstance().getyPos());

    }

}
