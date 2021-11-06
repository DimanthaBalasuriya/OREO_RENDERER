package Component.Model;

import Texture.Material;

public class Object {

    private MeshBuilder meshBuilder;
    private Material material;
    private Translation translation;

    public Object(MeshBuilder meshBuilder, Material material, Translation translation) {
        this.meshBuilder = meshBuilder;
        this.material = material;
        this.translation = translation;
    }

    public MeshBuilder getMeshBuilder() {
        return meshBuilder;
    }

    public Material getMaterial() {
        return material;
    }

    public Translation getTranslation() {
        return translation;
    }
}
