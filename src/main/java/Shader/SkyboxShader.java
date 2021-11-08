package Shader;

import org.joml.Matrix4f;

public class SkyboxShader extends Shader {

    private static final String VERTEX_SHADER = "assets/shaders/skybox_vertex_shader.glsl";
    private static final String FRAGMENT_SHADER = "assets/shaders/skybox_fragment_shader.glsl";

    private int location_viewMatrix;
    private int location_projectionMatrix;

    public SkyboxShader() {
        super(VERTEX_SHADER, FRAGMENT_SHADER);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0, "aPos");
    }

    @Override
    protected void getUniformLocations() {
        location_viewMatrix = super.getUniformLocation("view");
        location_projectionMatrix = super.getUniformLocation("projection");
    }

    public void loadViewMatrix(Matrix4f matrix) {
        super.loadMatrix(location_viewMatrix, matrix);
    }

    public void loadProjectionMatrix(Matrix4f matrix) {
        super.loadMatrix(location_projectionMatrix, matrix);
    }

}
