package Component.Model;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class MeshBuilder {

    private int vaoID;
    private int indicesCount;

    public MeshBuilder(float[] positions, float[] uvs, int[] indices) {
        this.vaoID = createNewVao();
        storeDataInVertexBuffer(0, 3, positions);
        storeDataInIndicesBuffer(indices);
        storeDataInVertexBuffer(1, 2, uvs);
        enableVertexAttribArrays();
    }

    private void storeDataInVertexBuffer(int index, int size, float[] data) {
        int vbo = GL15.glGenBuffers();
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, convertFloatToBuffer(data), GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(index, size, GL11.GL_FLOAT, false, 0, 0);
    }

    private void storeDataInIndicesBuffer(int[] data) {
        int vbo = GL15.glGenBuffers();
        this.indicesCount = data.length;
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, convertIntegerToBuffer(data), GL15.GL_STATIC_DRAW);
    }

    private void enableVertexAttribArrays() {
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
    }

    private int createNewVao() {
        int vao = GL30.glGenVertexArrays();
        GL30.glBindVertexArray(vao);
        return vao;
    }

    private FloatBuffer convertFloatToBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    private IntBuffer convertIntegerToBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data).flip();
        return buffer;
    }

    public int getVaoID() {
        return vaoID;
    }

    public int getIndicesCount() {
        return indicesCount;
    }
}
