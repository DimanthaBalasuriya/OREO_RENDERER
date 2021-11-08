import Component.Model.MeshBuilder;
import Component.Model.Object;
import Component.Model.Translation;
import Component.Render.Renderer;
import Texture.Color;
import Texture.CubeMapTexture;
import Texture.Material;
import Texture.Texture;
import Tool.Window;
import input.Keyboard;
import input.Mouse;
import org.joml.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class Main {

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

    private static final float[] skybox_Vertices = {
            -1.0f, 1.0f, -1.0f,
            -1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,

            -1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, -1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,

            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,

            -1.0f, -1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, -1.0f, 1.0f,
            -1.0f, -1.0f, 1.0f,

            -1.0f, 1.0f, -1.0f,
            1.0f, 1.0f, -1.0f,
            1.0f, 1.0f, 1.0f,
            1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, 1.0f,
            -1.0f, 1.0f, -1.0f,

            -1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, -1.0f,
            1.0f, -1.0f, -1.0f,
            -1.0f, -1.0f, 1.0f,
            1.0f, -1.0f, 1.0f
    };

    public static void main(String[] args) {

        Window window = Window.getInstance();
        window.init();

        glfwSetFramebufferSizeCallback(window.getWindow(), Window::framebuffer_call_back);
        glfwSetCursorPosCallback(window.getWindow(), Mouse::cursor_position_callback);
        glfwSetKeyCallback(window.getWindow(), Keyboard::key_callback);

        glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        ArrayList<Object> ar = new ArrayList<>();

        ArrayList<String> cubeMapTextures = new ArrayList<>();
        cubeMapTextures.add("assets/textures/skybox/right.jpg");
        cubeMapTextures.add("assets/textures/skybox/left.jpg");
        cubeMapTextures.add("assets/textures/skybox/top.jpg");
        cubeMapTextures.add("assets/textures/skybox/bottom.jpg");
        cubeMapTextures.add("assets/textures/skybox/front.jpg");
        cubeMapTextures.add("assets/textures/skybox/back.jpg");

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                MeshBuilder meshBuilder1 = new MeshBuilder(vertices, texture, indices);
                Material material1 = new Material(new Texture("assets/textures/image.png", "norm"), new Color(0, 0, 0, 0));
                Translation translation1 = new Translation(new Vector3f(i, 0, j), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
                Object object1 = new Object(meshBuilder1, material1, translation1);
                ar.add(object1);
            }
        }

        CubeMapTexture cmt = new CubeMapTexture(cubeMapTextures);
        Material material = new Material(null, cmt, null);
        Translation skyTransform = new Translation(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1));
        Object skybox = new Object(new MeshBuilder(skybox_Vertices, null, null), material, skyTransform);

        Renderer renderer = new Renderer();
        renderer.init();

        while (!glfwWindowShouldClose(window.getWindow())) {
            renderer.prepare();
            renderer.inputProcess();
            renderer.renderEntity(ar);
            renderer.renderSkybox(skybox);
            window.update();
        }

        renderer.destroy();
        window.close();
    }

}
