package EntityItem;

import Builder.MeshBuilder;
import Component.Transformation;
import Loader.MeshLoader;
import Material.Material;
import Model.RawModel;

public class Entity {

    private Transformation transformation;
    private Material material;
    private Mesh[] mesh;
    private RawModel[] rawModel;

    public Entity(Transformation transformation, Material material, String path) {
        this.transformation = transformation;
        this.material = material;
        this.mesh = new MeshLoader().loadMesh(path);
        this.rawModel = new MeshBuilder().buildMesh(mesh);
    }

    public Transformation getTransformation() {
        return transformation;
    }

    public Material getMaterial() {
        return material;
    }

    public Mesh[] getMesh() {
        return mesh;
    }

    public RawModel[] getRawModel() {
        return rawModel;
    }
}
