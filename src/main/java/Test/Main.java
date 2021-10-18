package Test;

import Component.Transformation;
import EntityItem.Entity;
import Material.Material;
import Material.Texture;
import Render.EntityRenderer;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;

public class Main {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final String TITLE = "oreo";

    private static final float[] lightCubePosition = {
            -0.5f, 0.5f, 0.5f,
            -0.5f, -0.5f, 0.5f,
            0.5f, -0.5f, 0.5f,
            0.5f, 0.5f, 0.5f,
            -0.5f, 0.5f, -0.5f,
            0.5f, 0.5f, -0.5f,
            -0.5f, -0.5f, -0.5f,
            0.5f, -0.5f, -0.5f,
    };

    private static final int[] lightCubeIndices = {
            0, 1, 3, 3, 1, 2,
            4, 0, 3, 5, 4, 3,
            3, 2, 7, 5, 3, 7,
            6, 1, 0, 6, 0, 4,
            2, 1, 6, 2, 6, 7,
            7, 6, 4, 7, 4, 5,
    };

    private static long window;

    public static void main(String[] args) {

        init();

        //Renderer renderer = new Renderer();
        Entity backPackEntity = new Entity(
                new Transformation(
                        new Vector3f(0, -0.14f, -30),
                        new Vector3f(0, 0, 0), 0.06f),
                new Material(new Texture("assets/textures/human/scifiFemale.png")),
                "assets/models/scifiFemale.obj");

        EntityRenderer entityRenderer = new EntityRenderer();
        entityRenderer.loadProjectionMatrix();

        while (!glfwWindowShouldClose(window)) {

            entityRenderer.prepare();
            entityRenderer.render(backPackEntity);
            entityRenderer.close();
            update();

        }
        destroy();

    }

    private static void init() {

        glfwInit();
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);

        window = glfwCreateWindow(WIDTH, HEIGHT, TITLE, MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            System.out.println("Window creation process is Failed!");
            glfwTerminate();
        }

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        GL11.glViewport(0, 0, WIDTH, HEIGHT);

    }

    private static void update() {
        glfwSwapInterval(1);
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    private static void destroy() {
        glfwTerminate();
    }

}
