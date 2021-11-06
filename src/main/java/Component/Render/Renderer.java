package Component.Render;

import Component.Model.Object;
import Math.Transformation;
import Shader.DefaultShader;
import Tool.Camera;
import Tool.Window;
import input.Keyboard;
import input.Mouse;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Renderer {

    private final float RED = 0.7f;
    private final float GREEN = 0.8f;
    private final float BLUE = 0.9f;
    private final float ALPHA = 1.0f;

    private final float fov = 60;
    private final float near = 0.01f;
    private final float far = 1000;

    private static final float CAM_SPEED = 0.06f;
    private static final float ROT_SPEED = 0.6f;

    DefaultShader defaultShader = new DefaultShader();
    Transformation transformation = new Transformation();
    Camera camera = new Camera(new Vector3f(4, 6, 20), new Vector3f(30, 0, 0));

    public void init() {
        defaultShader.startProgram();
        defaultShader.loadProjectionMatrix(transformation.getProjectionMatrix(fov, Window.getInstance().getWidth(), Window.getInstance().getHeight(), near, far));
    }

    public void prepare() {
        GL11.glClearColor(RED, GREEN, BLUE, ALPHA);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void render(ArrayList<Object> objects) {
        defaultShader.loadViewMatrix(transformation.getViewMatrix(camera));
        for (Object object : objects) {
            defaultShader.loadTranslationMatrix(transformation.getTranslationMatrix(object.getTranslation().getPosition(), object.getTranslation().getRotation(), object.getTranslation().getScale()));
            GL13.glActiveTexture(object.getMaterial().getDiffuse().getTextureID());
            GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMeshBuilder().getIndicesCount(), GL11.GL_UNSIGNED_INT, 0);
        }
    }

    public void destroy() {
        defaultShader.cleanShader();
    }

    public void inputProcess() {
        if (Keyboard.isKeyPressed(GLFW_KEY_W)) {
            camera.setPosition(0, 0, -CAM_SPEED);
        } else if (Keyboard.isKeyPressed(GLFW_KEY_S)) {
            camera.setPosition(0, 0, CAM_SPEED);
        } else if (Keyboard.isKeyPressed(GLFW_KEY_A)) {
            camera.setPosition(-CAM_SPEED, 0, 0);
        } else if (Keyboard.isKeyPressed(GLFW_KEY_D)) {
            camera.setPosition(CAM_SPEED, 0, 0);
        } else if (Keyboard.isKeyPressed(GLFW_KEY_UP)) {
            camera.setRotation(-ROT_SPEED, 0, 0);
        } else if (Keyboard.isKeyPressed(GLFW_KEY_DOWN)) {
            camera.setRotation(ROT_SPEED, 0, 0);
        } else if (Keyboard.isKeyPressed(GLFW_KEY_LEFT)) {
            camera.setRotation(0, -ROT_SPEED, 0);
        } else if (Keyboard.isKeyPressed(GLFW_KEY_RIGHT)) {
            camera.setRotation(0, ROT_SPEED, 0);
        }

        Mouse.getInstance().calculateDelta();
        camera.setRotation(Mouse.getInstance().getDelta().x * CAM_SPEED, Mouse.getInstance().getDelta().y * CAM_SPEED, 0);
        Mouse.getInstance().setNewPos();
    }

}
