package Shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public abstract class Shader {

    private final int programID;
    private final int vertexShaderID;
    private final int fragmentShaderID;

    private final FloatBuffer buffer = BufferUtils.createFloatBuffer(16);

    public Shader(String vertexShader, String fragmentShader) {
        this.vertexShaderID = createShader(vertexShader, GL20.GL_VERTEX_SHADER);
        this.fragmentShaderID = createShader(fragmentShader, GL20.GL_FRAGMENT_SHADER);
        bindAttributes();
        this.programID = buildShaderProgram();
        getUniformLocations();
    }

    protected abstract void bindAttributes();

    protected abstract void getUniformLocations();

    protected void bindAttribute(int attribute, String variableName) {
        GL20.glBindAttribLocation(programID, attribute, variableName);
    }

    protected int getUniformLocation(String uniformName) {
        return GL20.glGetUniformLocation(programID, uniformName);
    }

    public void startProgram() {
        GL20.glUseProgram(programID);
    }

    public void cleanShader() {
        stopProgram();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }

    private void stopProgram() {
        GL20.glUseProgram(0);
    }

    //Loading uniform values into the shader.

    protected void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    protected void loadVector(int location, Vector3f vector) {
        GL20.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected void loadBoolean(int location, int value) {
        GL20.glUniform1f(location, value == 0 ? 0 : 1);
    }

    protected void loadMatrix(int location, Matrix4f matrix) {
        GL20.glUniformMatrix4fv(location, false, matrix.get(buffer));
    }

    //End loading section.

    private int buildShaderProgram() {
        int program = GL20.glCreateProgram();
        GL20.glAttachShader(program, this.vertexShaderID);
        GL20.glAttachShader(program, this.fragmentShaderID);
        GL20.glLinkProgram(program);
        return program;
    }

    private int createShader(String shaderSource, int shaderType) {
        int shader = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shader, getShader(shaderSource));
        GL20.glCompileShader(shader);
        if (GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shader, 2400));
            System.err.println("Could not compile shader...");
            System.exit(-1);
        }
        return shader;
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
}
