package Math;

import Tool.Camera;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Transformation {

    private Matrix4f projectionMatrix;
    private Matrix4f translationMatrix;
    private Matrix4f viewMatrix;

    public Transformation() {
        this.projectionMatrix = new Matrix4f();
        this.translationMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();
    }

    public Matrix4f getViewMatrix(Camera camera) {
        return viewMatrix.identity().
                rotate((float) Math.toRadians(camera.getRotation().x), new Vector3f(1, 0, 0)).
                rotate((float) Math.toRadians(camera.getRotation().y), new Vector3f(0, 1, 0)).
                translate(-camera.getPosition().x, -camera.getPosition().y, -camera.getPosition().z);
    }

    public Matrix4f getTranslationMatrix(Vector3f position, Vector3f rotation, Vector3f scale) {
        translationMatrix.identity().
                translate(position).
                rotateX((float) Math.toRadians(rotation.x)).
                rotateY((float) Math.toRadians(rotation.y)).
                rotateZ((float) Math.toRadians(rotation.z)).
                scale(scale);
        return translationMatrix;
    }

    public Matrix4f getProjectionMatrix(float fov, float width, float height, float near, float far) {
        float aspectRatio = width / height;
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = far - near;

        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((far + near) / frustum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * near * far) / frustum_length));
        projectionMatrix.m33(0);
        return projectionMatrix;
    }

}
