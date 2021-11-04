package Shader;

import org.joml.Matrix4f;

public class DefaultShader extends Shader {

    private static final String VERTEX_SHADER = "assets/shaders/vertex_shader.glsl";
    private static final String FRAGMENT_SHADER = "assets/shaders/fragment_shader.glsl";

    private int location_viewMatrix;
    private int location_translationMatrix;
    private int location_projectionMatrix;

    public DefaultShader() {
        super(VERTEX_SHADER, FRAGMENT_SHADER);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "aPos");
        super.bindAttribute(1, "aTexture");
    }

    @Override
    protected void getUniformLocations() {
        location_viewMatrix = super.getUniformLocation("view");
        location_translationMatrix = super.getUniformLocation("transform");
        location_projectionMatrix = super.getUniformLocation("projection");
    }

    public void loadTranslationMatrix(Matrix4f matrix) {
        super.loadMatrix(location_translationMatrix, matrix);
    }

    public void loadViewMatrix(Matrix4f matrix) {
        super.loadMatrix(location_viewMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(location_projectionMatrix, matrix);
    }

}
