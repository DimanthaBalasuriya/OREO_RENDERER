package Test;

import Component.Transformation;
import EntityItem.Entity;
import Material.Material;
import Material.Texture;
import Shader.DefaultShader;
import Tool.Mathematics;
import org.joml.Math;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;

import static org.lwjgl.glfw.GLFW.*;

public class Main {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final String TITLE = "oreo";

    private static final float RED = 0.12f;
    private static final float GREEN = 0.12f;
    private static final float BLUE = 0.12f;
    private static final float ALPHA = 1.0f;

    private static final float FOV = Math.toRadians(60.0f);
    private static final float Z_NEAR = 0.1f;
    private static final float Z_FAR = 1000.0f;

    private static long window;

    public static void main(String[] args) {

        init();
        GL.createCapabilities();

        Entity backPackEntity = new Entity(
                new Transformation(
                        new Vector3f(0, -0.05f, -10),
                        new Vector3f(0, 0, 0), 0.02f),
                new Material(new Texture("assets/textures/human/scifiFemale.png"))
                , "assets/models/scifiFemale.obj");

        DefaultShader defaultShader = new DefaultShader();

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        Matrix4f matrix = Mathematics.getProjectionMatrix(FOV, Z_NEAR, Z_FAR, WIDTH, HEIGHT);

        defaultShader.start();
        defaultShader.loadProjectionMatrix(matrix);

        while (!glfwWindowShouldClose(window)) {

            GL11.glClearColor(RED, GREEN, BLUE, ALPHA);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            backPackEntity.getTransformation().setRotation(new Vector3f(0, (float) glfwGetTime() * 10, 0));
            defaultShader.loadTransformationMatrix(Mathematics.getTransformationMatrix(backPackEntity));

            for (int i = 0; i < backPackEntity.getRawModel().length; i++) {
                GL30.glBindVertexArray(backPackEntity.getRawModel()[i].getVaoID());
                GL20.glEnableVertexAttribArray(0);
                GL20.glEnableVertexAttribArray(1);
                GL20.glEnableVertexAttribArray(2);
                GL13.glActiveTexture(GL13.GL_TEXTURE0);
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, backPackEntity.getMaterial().getDiffuse().getTextureID());
                GL11.glDrawElements(GL11.GL_TRIANGLES, backPackEntity.getRawModel()[i].getIndices(), GL11.GL_UNSIGNED_INT, 0);
                GL20.glDisableVertexAttribArray(0);
                GL20.glDisableVertexAttribArray(1);
                GL20.glDisableVertexAttribArray(2);
                GL30.glBindVertexArray(0);
            }

            update();

        }
        defaultShader.cleanUp();
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
