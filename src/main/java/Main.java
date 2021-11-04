import Texture.Color;
import Texture.Material;
import Texture.Texture;
import Tool.Camera;
import input.Keyboard;
import input.Mouse;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;

public class Main {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final String TITLE = "HELLO";

    private static final float RED = 0.12f;
    private static final float GREEN = 0.12f;
    private static final float BLUE = 0.12f;
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

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        glfwSetInputMode(window.getWindow(), GLFW_CURSOR, GLFW_CURSOR_DISABLED);

/*        if (glfwRawMouseMotionSupported()) {
            glfwSetInputMode(window, GLFW_RAW_MOUSE_MOTION, GLFW_TRUE);
        }*/

        //Store data in buffers for the store data
        FloatBuffer vBuffer = BufferUtils.createFloatBuffer(vertices.length);
        vBuffer.put(vertices).flip();

        IntBuffer eBuffer = BufferUtils.createIntBuffer(indices.length);
        eBuffer.put(indices).flip();

        FloatBuffer tBuffer = BufferUtils.createFloatBuffer(texture.length);
        tBuffer.put(texture).flip();

        //Create VAO id for store buffers
        int vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);

        //Create VBO for store position data of polygons
        int vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(0);

        //Create VBO for store duplicated polygonal locations
        int ebo = GL15C.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, ebo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, eBuffer, GL15.GL_STATIC_DRAW);

        int tbo = GL15C.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, tbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, tBuffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
        GL20.glEnableVertexAttribArray(1);

        //All about shader
        int vertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        GL20.glShaderSource(vertexShader, getShader("assets/shaders/vertex_shader.glsl"));
        GL20.glCompileShader(vertexShader);

        if (GL20.glGetShaderi(vertexShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(vertexShader, 2400));
            System.err.println("Could not compile shader...");
            System.exit(-1);
        }

        int fragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        GL20.glShaderSource(fragmentShader, getShader("assets/shaders/fragment_shader.glsl"));
        GL20.glCompileShader(fragmentShader);

        if (GL20.glGetShaderi(fragmentShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(fragmentShader, 2400));
            System.err.println("Could not compile shader...");
            System.exit(-1);
        }

        int shaderProgram = GL20.glCreateProgram();
        GL20.glAttachShader(shaderProgram, vertexShader);
        GL20.glAttachShader(shaderProgram, fragmentShader);
        GL20.glLinkProgram(shaderProgram);

        GL20.glUseProgram(shaderProgram);

        //Texture
        String fileName1 = "assets/textures/image.png";

        Material material1 = new Material(new Texture(fileName1), new Color(0, 0, 0, 0));



        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);

        Camera camera = new Camera();

        glfwSetCursorPosCallback(window.getWindow(), Mouse::cursor_position_callback);
        glfwSetKeyCallback(window.getWindow(), Keyboard::key_callback);

        int projection = GL20.glGetUniformLocation(shaderProgram, "projection");
        GL20.glUniformMatrix4fv(projection, false, projection().get(buffer));

        while (!glfwWindowShouldClose(window.getWindow())) {
            GL11.glClearColor(RED, GREEN, BLUE, ALPHA);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

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

            int transformation = GL20.glGetUniformLocation(shaderProgram, "transform");
            GL20.glUniformMatrix4fv(transformation, false, transform(new Vector3f(0, 0, -1f), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1)).get(buffer));

            int view = GL20.glGetUniformLocation(shaderProgram, "view");
            GL20.glUniformMatrix4fv(view, false, view(camera).get(buffer));

            float x = (2.0f * Mouse.getInstance().getX()) / WIDTH - 1.0f;
            float y = 1.0f - (2.0f * Mouse.getInstance().getY()) / HEIGHT;
            float z = 1.0f;

            Vector3f ray_nds = new Vector3f(x, y, z);
            Vector4f ray_clip = new Vector4f(ray_nds.x, ray_nds.y, -1.0f, 1.0f);
            Vector4f ray_eye = projection().invertPerspective().transform(ray_clip);
            ray_eye = new Vector4f(ray_eye.x, ray_eye.y, -1.0f, 0.0f);
            Vector4f invRayWor = view(camera).invert().transform(ray_eye);
            Vector3f ray_wor = new Vector3f(invRayWor.x, invRayWor.y, invRayWor.z).normalize();

            int hitColour = GL20.glGetUniformLocation(shaderProgram, "col");
            if (intersectSphere(camera.getPosition(), ray_wor, 1, 1, 1)) {
                GL20C.glUniform3f(hitColour, 0, 1, 0);
            } else if (!intersectSphere(camera.getPosition(), ray_wor, 1, 1, 1)) {
                GL20C.glUniform3f(hitColour, 0, 0, 1);
            }

            try {
                GL13.glActiveTexture(material1.getDiffuse().getTextureID());
            } catch (Exception e) {
                e.printStackTrace();
            }
            GL11.glDrawElements(GL11.GL_TRIANGLES, indices.length, GL11.GL_UNSIGNED_INT, 0);
            window.update();
        }
        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);

        window.close();
    }

    private static String getShader(String file) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public static Matrix4f transform(Vector3f position, Vector3f rotation, Vector3f scale) {
        Matrix4f transformMatrix = new Matrix4f();
        transformMatrix.identity().
                translate(position).
                rotateX((float) Math.toRadians(rotation.x)).
                rotateY((float) Math.toRadians(rotation.y)).
                rotateZ((float) Math.toRadians(rotation.z)).
                scale(scale);
        return transformMatrix;
    }

    public static Matrix4f projection() {
        float aspectRatio = 1.77f;
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(60 / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = 1000 - 0.01f;

        Matrix4f projectionMatrix = new Matrix4f();
        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((1000 + 0.01f) / frustum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * 0.01f * 1000) / frustum_length));
        projectionMatrix.m33(0);
        return projectionMatrix;
    }

    //All the things here is camera.
    public static Matrix4f view(Camera camera) {
        Matrix4f viewMatrix = new Matrix4f();
        viewMatrix.identity().
                rotate((float) Math.toRadians(camera.getRotation().x), new Vector3f(1, 0, 0)).
                rotate((float) Math.toRadians(camera.getRotation().y), new Vector3f(0, 1, 0)).
                translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
        return viewMatrix;
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
