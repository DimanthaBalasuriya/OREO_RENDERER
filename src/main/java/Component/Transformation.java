package Component;

import org.joml.Vector3f;

public class Transformation {

    private Vector3f position;
    private Vector3f rotation;
    private float scale;

    public Transformation(Vector3f position, Vector3f rotation, float scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public void setRotation(Vector3f rotation) {
        this.rotation = rotation;
    }
}
