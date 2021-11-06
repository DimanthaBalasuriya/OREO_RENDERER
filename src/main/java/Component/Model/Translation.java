package Component.Model;

import org.joml.Vector3f;

public class Translation {

    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;

    public Translation(Vector3f position, Vector3f rotation, Vector3f scale) {
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

    public Vector3f getScale() {
        return scale;
    }
}
