import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;

public class Test {

    public static void main(String[] args) {

        glfwInit();
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_OPENGL_CORE_PROFILE, GL11.GL_TRUE);

        long window = glfwCreateWindow(720, 640, "Hello", MemoryUtil.NULL, MemoryUtil.NULL);

        glfwMakeContextCurrent(window);

        GL.createCapabilities();

        while (!glfwWindowShouldClose(window)) {

            GL11.glClearColor(0, 0.12f, 0, 1);
            GL11.glEnable(GL11C.GL_COLOR_BUFFER_BIT);

            glfwSetCursorPosCallback(window, Test::cursor_position_callback);
            glfwSetMouseButtonCallback(window, Test::mouse_button_callback);
            glfwSetScrollCallback(window, Test::scroll_callback);

            glfwSwapBuffers(window);
            glfwPollEvents();

        }

        glfwTerminate();

    }

    public static void cursor_position_callback(long window, double xpos, double ypos) {
        //System.out.println(xpos + " " + ypos);
    }

    public static void mouse_button_callback(long window, int button, int action, int mods) {
        if (button == GLFW_MOUSE_BUTTON_RIGHT && action == GLFW_PRESS) {
            System.out.println("RIGHT");
        }
        if (button == GLFW_MOUSE_BUTTON_LEFT && action == GLFW_PRESS) {
            System.out.println("LEFT");
        }
    }

    public static void scroll_callback(long window, double xoffset, double yoffset) {
        System.out.println(xoffset + " " + yoffset);
    }

}
