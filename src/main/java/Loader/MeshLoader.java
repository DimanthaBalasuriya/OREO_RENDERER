package Loader;

import EntityItem.Mesh;
import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class MeshLoader {

    public Mesh[] loadMesh(String filePath) {

        AIScene aiScene = Assimp.aiImportFile(filePath, Assimp.aiProcess_GenSmoothNormals | Assimp.aiProcess_JoinIdenticalVertices | Assimp.aiProcess_Triangulate
                | Assimp.aiProcess_FixInfacingNormals | Assimp.aiProcess_PreTransformVertices);
        if (aiScene == null) {
            System.out.println("Model loading error..");
        }
        assert aiScene != null;
        int numMeshes = aiScene.mNumMeshes();
        Mesh[] meshes = new Mesh[numMeshes];
        PointerBuffer aiMeshes = aiScene.mMeshes();
        for (int i = 0; i < numMeshes; i++) {
            assert aiMeshes != null;
            AIMesh aiMesh = AIMesh.create(aiMeshes.get(i));
            Mesh mesh = processMesh(aiMesh);
            meshes[i] = mesh;
        }
        return meshes;

    }

    private Mesh processMesh(AIMesh aiMesh) {

        List<Float> vertices = new ArrayList<>();
        List<Float> textures = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        List<Float> normals = new ArrayList<>();

        AIVector3D.Buffer aiVertices = aiMesh.mVertices();
        while (aiVertices.remaining() > 0) {
            AIVector3D aiVertex = aiVertices.get();
            vertices.add(aiVertex.x());
            vertices.add(aiVertex.y());
            vertices.add(aiVertex.z());
        }

        AIVector3D.Buffer aiNormals = aiMesh.mNormals();
        while (aiNormals != null && aiNormals.remaining() > 0) {
            AIVector3D aiNormal = aiNormals.get();
            normals.add(aiNormal.x());
            normals.add(aiNormal.y());
            normals.add(aiNormal.z());
        }

        AIVector3D.Buffer aiTexture = aiMesh.mTextureCoords(0);
        int numTextCoords = aiTexture != null ? aiTexture.remaining() : 0;
        for (int i = 0; i < numTextCoords; i++) {
            AIVector3D textureCoord = aiTexture.get();
            textures.add(textureCoord.x());
            textures.add(1-textureCoord.y());
        }

        int numFaces = aiMesh.mNumFaces();
        AIFace.Buffer aiFaces = aiMesh.mFaces();
        for (int i = 0; i < numFaces; i++) {
            AIFace aiFace = aiFaces.get(i);
            IntBuffer buffer = aiFace.mIndices();
            while (buffer.remaining() > 0) {
                indices.add(buffer.get());
            }
        }

        return new Mesh(vertices, normals, textures, indices);

    }

}
