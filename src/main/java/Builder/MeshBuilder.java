package Builder;

import EntityItem.Mesh;
import Model.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class MeshBuilder {

    private final List<Integer> vaos = new ArrayList<>();
    private final List<Integer> vbos = new ArrayList<>();

    public RawModel[] buildMesh(Mesh[] meshes) {
        RawModel[] rawModels = new RawModel[meshes.length];
        for (int i = 0; i < meshes.length; i++) {
            int vaoID = createVertexArrayObject();
            bindIndicesBuffer(meshes[i].getIndices());
            storeDataInAttributeList(0, 3, meshes[i].getVertices());
            storeDataInAttributeList(1, 3, meshes[i].getNormals());
            storeDataInAttributeList(2, 2, meshes[i].getTexturecoords());
            rawModels[i] = new RawModel(vaoID, meshes[i].getIndices().size());
            unbinedVertexArrayObject();
        }
        return rawModels;
    }

    private void storeDataInAttributeList(int attribute, int coordinate, List<Float> data) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attribute, coordinate, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private void bindIndicesBuffer(List<Integer> indices) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private int createVertexArrayObject() {
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private void unbinedVertexArrayObject() {
        GL30C.glBindVertexArray(0);
    }

    private FloatBuffer storeDataInFloatBuffer(List<Float> data) {
        int size = data != null ? data.size() : 0;
        float[] floatArr = new float[size];
        for (int i = 0; i < size; i++) {
            floatArr[i] = data.get(i);
        }
        assert data != null;
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.size());
        buffer.put(floatArr).flip();
        return buffer;
    }

    private IntBuffer storeDataInIntBuffer(List<Integer> data) {
        int size = data != null ? data.size() : 0;
        int[] intArr = new int[size];
        for (int i = 0; i < size; i++) {
            intArr[i] = data.get(i);
        }
        assert data != null;
        IntBuffer buffer = BufferUtils.createIntBuffer(data.size());
        buffer.put(intArr).flip();
        return buffer;
    }

    public void cleanMeshBuilder() {
        for (int vao : vaos) {
            GL30.glDeleteVertexArrays(vao);
        }
        for (int vbo : vbos) {
            GL15.glDeleteBuffers(vbo);
        }
    }

}
