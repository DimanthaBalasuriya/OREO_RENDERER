package Render;

import Shader.DefaultShader;
import Tool.Mathematics;
import org.joml.Math;
import org.lwjgl.opengl.GL11;

public class Renderer {

    private final int WIDTH = 1280;
    private final int HEIGHT = 720;
    private final float FOV = Math.toRadians(60.0f);
    private final float Z_NEAR = 0.6f;
    private final float Z_FAR = 1000.0f;

    private final float RED = 0.12f;
    private final float GREEN = 0.12f;
    private final float BLUE = 0.12f;
    private final float ALPHA = 1.0f;

    DefaultShader defaultShader = new DefaultShader();

    public void prepare() {
        GL11.glClearColor(RED, GREEN, BLUE, ALPHA);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void loadProjectionMatrix() {
        defaultShader.start();
        defaultShader.loadProjectionMatrix(Mathematics.getProjectionMatrix(FOV, Z_NEAR, Z_FAR, WIDTH, HEIGHT));
    }

    public void close() {
        defaultShader.cleanUp();
    }

}
