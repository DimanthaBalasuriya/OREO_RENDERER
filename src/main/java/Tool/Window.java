package Tool;

import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;

public class Window {

    private int width;
    private int height;
    private final String title;
    private boolean vSync = false;

    private long window;

    private static Window instance;

    private Window() {
        this.width = 1280;
        this.height = 720;
        this.title = "Oreo";
    }

    public static Window getInstance() {
        return instance == null ? instance = new Window() : instance;
    }

    public void init() {
        glfwInit();
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);

        this.window = glfwCreateWindow(this.width, this.height, this.title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            System.out.println("Tool.Window not created...");
            glfwTerminate();
        }

        glfwMakeContextCurrent(window);
        enableOpengl();
    }

    public void update() {
        glfwSwapInterval(!vSync ? 0 : 1);
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    public void close() {
        glfwTerminate();
    }

    private void enableOpengl() {
        GL.createCapabilities();
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setvSync(boolean vSync) {
        this.vSync = vSync;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getWindow() {
        return window;
    }

    public boolean isvSync() {
        return vSync;
    }

    public static void framebuffer_call_back(long window, int width, int height) {
        getInstance().width = width;
        getInstance().height = height;
        GL11.glViewport(0, 0, width, height);
    }

}
