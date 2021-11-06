import Component.Model.MeshBuilder;
import Component.Model.Object;
import Component.Model.Translation;
import Math.Transformation;
import Shader.DefaultShader;
import Texture.Color;
import Texture.Material;
import Texture.Texture;
import Tool.Camera;
import input.Keyboard;
import input.Mouse;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Main {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;

    private static final float RED = 0.7f;
    private static final float GREEN = 0.8f;
    private static final float BLUE = 0.9f;
    private static final float ALPHA = 1.0f;

    private static final float CAM_SPEED = 0.06f;
    private static final float ROT_SPEED = 0.6f;

    private static final float radius = 1.0f;

    private static final float[] vertices = {
            -0.5f, 0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            0.5f, 0.5f, 0.0f,
    };

    private static final int[] indices = {
            0, 1, 3, 3, 1, 2,
    };

    private static final float[] texture = {
            1.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 0.0f,
            0.0f, 1.0f
    };

    public static void main(String[] args) {

        Window window = Window.getInstance();
        window.init();

        glfwSetFramebufferSizeCallback(window.getWindow(), Window::framebuffer_call_back);
        glfwSetCursorPosCallback(window.getWindow(), Mouse::cursor_position_callback);
        glfwSetKeyCallback(window.getWindow(), Keyboard::key_callback);

        glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        MeshBuilder meshBuilder1 = new MeshBuilder(vertices, texture, indices);
        Material material1 = new Material(new Texture("assets/textures/image.png"), new Color(0, 0, 0, 0));
        Translation translation1 = new Translation(new Vector3f(-1, 0, -2), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        Object object1 = new Object(meshBuilder1, material1, translation1);

        MeshBuilder meshBuilder2 = new MeshBuilder(vertices, texture, indices);
        Material material2 = new Material(new Texture("assets/textures/image.png"), new Color(0, 0, 0, 0));
        Translation translation2 = new Translation(new Vector3f(1, 0, -2), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        Object object2 = new Object(meshBuilder2, material2, translation2);

        MeshBuilder meshBuilder3 = new MeshBuilder(vertices, texture, indices);
        Material material3 = new Material(new Texture("assets/textures/image.png"), new Color(0, 0, 0, 0));
        Translation translation3 = new Translation(new Vector3f(-1, 0, -4), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        Object object3 = new Object(meshBuilder3, material3, translation3);

        MeshBuilder meshBuilder4 = new MeshBuilder(vertices, texture, indices);
        Material material4 = new Material(new Texture("assets/textures/image.png"), new Color(0, 0, 0, 0));
        Translation translation4 = new Translation(new Vector3f(1, 0, -4), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        Object object4 = new Object(meshBuilder4, material4, translation4);

        MeshBuilder meshBuilder5 = new MeshBuilder(vertices, texture, indices);
        Material material5 = new Material(new Texture("assets/textures/image.png"), new Color(0, 0, 0, 0));
        Translation translation5 = new Translation(new Vector3f(-1, 0, -6), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        Object object5 = new Object(meshBuilder5, material5, translation5);

        MeshBuilder meshBuilder6 = new MeshBuilder(vertices, texture, indices);
        Material material6 = new Material(new Texture("assets/textures/image.png"), new Color(0, 0, 0, 0));
        Translation translation6 = new Translation(new Vector3f(1, 0, -6), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        Object object6 = new Object(meshBuilder6, material6, translation6);

        ArrayList<Object> ar = new ArrayList<Object>();
        ar.add(object1);
        ar.add(object2);
        ar.add(object3);
        ar.add(object4);
        ar.add(object5);
        ar.add(object6);

        Transformation transformation = new Transformation();
        Camera camera = new Camera();
        DefaultShader defaultShader = new DefaultShader();
        defaultShader.startProgram();


        defaultShader.loadProjectionMatrix(transformation.getProjectionMatrix(60, Window.getInstance().getWidth(), Window.getInstance().getHeight(), 0.01f, 1000));

        while (!glfwWindowShouldClose(window.getWindow())) {
            GL11.glClearColor(RED, GREEN, BLUE, ALPHA);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

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

            float x = (2.0f * Mouse.getInstance().getX()) / Window.getInstance().getWidth() - 1.0f;
            float y = 1.0f - (2.0f * Mouse.getInstance().getY()) / Window.getInstance().getHeight();
            float z = 1.0f;

            Vector3f ray_nds = new Vector3f(x, y, z);
            Vector4f ray_clip = new Vector4f(ray_nds.x, ray_nds.y, -1.0f, 1.0f);
            Vector4f ray_eye = transformation.getProjectionMatrix(60, Window.getInstance().getWidth(), Window.getInstance().getHeight(), 0.01f, 1000).invertPerspective().transform(ray_clip);
            ray_eye = new Vector4f(ray_eye.x, ray_eye.y, -1.0f, 0.0f);
            Vector4f invRayWor = transformation.getViewMatrix(camera).invert().transform(ray_eye);
            Vector3f ray_wor = new Vector3f(invRayWor.x, invRayWor.y, invRayWor.z).normalize();
            //System.out.println(ray_wor);

            defaultShader.loadViewMatrix(transformation.getViewMatrix(camera));
/*            int hitColour = GL20.glGetUniformLocation(shaderProgram, "col");
            if (intersectSphere(camera.getPosition(), ray_wor, 1, 1, 1)) {
                GL20C.glUniform3f(hitColour, 0, 1, 0);
            } else if (!intersectSphere(camera.getPosition(), ray_wor, 1, 1, 1)) {
                GL20C.glUniform3f(hitColour, 0, 0, 1);
            }*/

            for (Object object : ar) {
                defaultShader.loadTranslationMatrix(transformation.getTranslationMatrix(object.getTranslation().getPosition(), object.getTranslation().getRotation(), object.getTranslation().getScale()));
                GL13.glActiveTexture(object.getMaterial().getDiffuse().getTextureID());
                GL11.glDrawElements(GL11.GL_TRIANGLES, object.getMeshBuilder().getIndicesCount(), GL11.GL_UNSIGNED_INT, 0);
            }

            window.update();
        }

        defaultShader.cleanShader();
        window.close();
    }

    public static boolean intersectSphere(Vector3f p, Vector3f d, float r, float t1, float t2) {
        float A = d.dot(d);
        float B = 2.0f * d.dot(p);
        float C = p.dot(p) - r * r;
        float dis = B * B - 4.0f * A * C;

        if (dis < 0.0f) {
            return false;
        }

        float S = (float) Math.sqrt(dis);
        t1 = (-B - S) / (2.0f * A);
        t2 = (-B + S) / (2.0f * A);

        return true;
    }

}
