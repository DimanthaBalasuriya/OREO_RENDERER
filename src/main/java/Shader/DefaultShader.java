package Shader;

import org.joml.Matrix4f;

public class DefaultShader extends Shader {

    private static final String VERTEX_SHADER_PATH = "assets/shaders/default_vertex.glsl";
    private static final String FRAGMENT_SHADER_PATH = "assets/shaders/default_fragment.glsl";

    private int location_transformationMatrix;
    private int location_projectionMatrix;

    public DefaultShader() {
        super(VERTEX_SHADER_PATH, FRAGMENT_SHADER_PATH);
    }

    @Override
    public void bindAttributes() {
        bindAttribute(0, "position");
        bindAttribute(2, "texture");
    }

    @Override
    public void getAllUniformLocations() {
        location_projectionMatrix = getUniformLocation("projectionMatrix");
        location_transformationMatrix = getUniformLocation("transformMatrix");
    }

    public void loadTransformationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_transformationMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(location_projectionMatrix, matrix);
    }

}
