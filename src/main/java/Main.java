import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
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

    private static final float[] vertices = {
            0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f
    };

    private static final int[] indices = {
            0, 1, 3,
            1, 2, 3
    };

    private static final float[] texture = {
            1.0f, 1.0f,
            1.0f, 0.0f,
            0.0f, 0.0f,
            0.0f, 1.0f
    };

    public static void main(String[] args) {

        glfwInit();
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);

        long window = glfwCreateWindow(WIDTH, HEIGHT, TITLE, MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == MemoryUtil.NULL) {
            System.out.println("Window not created...");
            glfwTerminate();
        }

        glfwMakeContextCurrent(window);

        GL.createCapabilities();

        GL11.glViewport(0, 0, WIDTH, HEIGHT);
        //GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);

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

        STBImage.stbi_set_flip_vertically_on_load(true);

        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);

        while (!glfwWindowShouldClose(window)) {
            GL11.glClearColor(RED, GREEN, BLUE, ALPHA);
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

            int transformation = GL20.glGetUniformLocation(shaderProgram, "transform");
            GL20.glUniformMatrix4fv(transformation, false, transform(new Vector3f(0, 0, -1f), new Vector3f(0, 0, 0), new Vector3f(1, 1.0f, 1)).get(buffer));

            int projection = GL20.glGetUniformLocation(shaderProgram, "projection");
            GL20.glUniformMatrix4fv(projection, false, projection().get(buffer));

            try {
                GL13.glActiveTexture(texture(fileName1));
            } catch (Exception e) {
                e.printStackTrace();
            }
            GL11.glDrawElements(GL11.GL_TRIANGLES, 6, GL11.GL_UNSIGNED_INT, 0);

            glfwSwapInterval(1);
            glfwSwapBuffers(window);
            glfwPollEvents();
        }

        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);

        glfwTerminate();

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

    public static int texture(String fileName) throws Exception {
        ByteBuffer buf;
        int width, height;
        // Load Texture file
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            buf = STBImage.stbi_load(fileName, w, h, channels, 4);
            if (buf == null) {
                throw new Exception("Image file [" + fileName + "] not loaded: " + STBImage.stbi_failure_reason());
            }

            width = w.get();
            height = h.get();
        }

        int id = createTexture(buf, width, height);

        STBImage.stbi_image_free(buf);
        return id;
    }

    private static int createTexture(ByteBuffer buf, int width, int height) {
        // Create a new OpenGL texture
        int textureId = GL11.glGenTextures();
        // Bind the texture
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

        // Tell OpenGL how to unpack the RGBA bytes. Each component is 1 byte size
        GL11.glPixelStorei(GL11C.GL_UNPACK_ALIGNMENT, 1);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        // Upload the texture data
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
        // Generate Mip Map
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        return textureId;
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
        Matrix4f projectionMatrix = new Matrix4f();
        float aspectRatio = WIDTH / HEIGHT;
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(60 / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = 1000.0f - 0.01f;

        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((1000.0f + 0.01f) / frustum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * 0.01f * 1000.0f) / frustum_length));
        projectionMatrix.m33(0);
        return projectionMatrix;
    }


}
