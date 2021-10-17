package Model;

public class RawModel {

    private int vaoID;
    private int indices;

    public RawModel(int vaoID, int indices) {
        this.vaoID = vaoID;
        this.indices = indices;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getIndices() {
        return indices;
    }
}
