package input;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Keyboard {

    private static Keyboard instance;
    private boolean keyPressed[] = new boolean[350];

    private Keyboard() {
    }

    public static Keyboard getInstance() {
        return instance == null ? instance = new Keyboard() : instance;
    }

    public static void key_callback(long window, int key, int scancode, int action, int mod) {
        if (action == GLFW_PRESS) {
            getInstance().keyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            getInstance().keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int keycode) {
        return getInstance().keyPressed[keycode];
    }

}
