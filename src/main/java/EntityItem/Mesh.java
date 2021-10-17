package EntityItem;

import java.util.List;

public class Mesh {

    private List<Float> vertices;
    private List<Float> normals;
    private List<Float> texturecoords;
    private List<Integer> indices;

    public Mesh(List<Float> vertices, List<Float> normals, List<Float> texturecoords, List<Integer> indices) {
        this.vertices = vertices;
        this.normals = normals;
        this.texturecoords = texturecoords;
        this.indices = indices;
    }

    public List<Float> getVertices() {
        return vertices;
    }

    public List<Float> getNormals() {
        return normals;
    }

    public List<Float> getTexturecoords() {
        return texturecoords;
    }

    public List<Integer> getIndices() {
        return indices;
    }

}
