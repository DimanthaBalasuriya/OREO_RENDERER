package Shader;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL20C;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.FloatBuffer;

public abstract class Shader {

    private static FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
    private int programID;
    private int vertexShaderID;
    private int fragmentShaderID;

    public Shader(String vertexShaderFile, String fragmentShaderFile) {
        this.programID = GL20.glCreateProgram();
        this.vertexShaderID = loadShader(vertexShaderFile, GL20.GL_VERTEX_SHADER);
        this.fragmentShaderID = loadShader(fragmentShaderFile, GL20C.GL_FRAGMENT_SHADER);
        GL20.glAttachShader(programID, vertexShaderID);
        GL20.glAttachShader(programID, fragmentShaderID);
        bindAttributes();
        GL20.glLinkProgram(programID);
        GL20.glValidateProgram(programID);
        getAllUniformLocations();
    }

    public void start() {
        GL20.glUseProgram(programID);
    }

    public void stop() {
        GL20.glUseProgram(0);
    }

    public void cleanUp() {
        stop();
        GL20.glDetachShader(programID, vertexShaderID);
        GL20.glDetachShader(programID, fragmentShaderID);
        GL20.glDeleteShader(vertexShaderID);
        GL20.glDeleteShader(fragmentShaderID);
        GL20.glDeleteProgram(programID);
    }

    public abstract void bindAttributes();

    public abstract void getAllUniformLocations();

    protected int getUniformLocation(String name) {
        return GL20.glGetUniformLocation(programID, name);
    }

    protected void bindAttribute(int attribute, String name) {
        GL20.glBindAttribLocation(programID, attribute, name);
    }

    protected void loadFloat(int location, float value) {
        GL20.glUniform1f(location, value);
    }

    protected void loadVector(int location, Vector3f vector) {
        GL20C.glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected void loadMatrix(int location, Matrix4f matrix) {
        GL20.glUniformMatrix4fv(location, false, matrix.get(matrixBuffer));
    }

    protected void loadBoolean(int location, boolean value) {
        float toLoad = 0;
        if (value) {
            toLoad = 1;
        }
        GL20.glUniform1f(location, toLoad);
    }

    private int loadShader(String filePath, int shaderType) {
        StringBuilder shaderBuilder = new StringBuilder();
        try {
            BufferedReader sReader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = sReader.readLine()) != null) {
                shaderBuilder.append(line).append("\n");
            }
            sReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return createShaderProgram(shaderBuilder.toString(), shaderType);
    }

    private int createShaderProgram(String shaderSource, int shaderType) {
        int shaderID = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println("Could not compile shader!");
        }
        return shaderID;
    }

}
