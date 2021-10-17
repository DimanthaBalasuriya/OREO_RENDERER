package Tool;

import EntityItem.Entity;
import org.joml.Matrix4f;

public class Mathematics {

    private static Matrix4f projectionMatrix;

    public static Matrix4f getProjectionMatrix(float fov, float zNear, float zFar, float width, float height) {
        float aspectRatio = width / height;
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(fov / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = zFar - zNear;

        projectionMatrix = new Matrix4f();
        projectionMatrix.m00(x_scale);
        projectionMatrix.m11(y_scale);
        projectionMatrix.m22(-((zFar + zNear) / frustum_length));
        projectionMatrix.m23(-1);
        projectionMatrix.m32(-((2 * zNear * zFar) / frustum_length));
        projectionMatrix.m33(0);
        return projectionMatrix;
    }

    public static Matrix4f getTransformationMatrix(Entity entity) {
        return new Matrix4f().identity().
                translate(entity.getTransformation().getPosition()).
                rotateX((float) Math.toRadians(entity.getTransformation().getRotation().x)).
                rotateY((float) Math.toRadians(entity.getTransformation().getRotation().y)).
                rotateZ((float) Math.toRadians(entity.getTransformation().getRotation().z)).
                scale(entity.getTransformation().getScale());
    }

}
