package Material;

public class Material {

    private Texture diffuse;

    public Material(Texture diffuse) {
        this.diffuse = diffuse;
    }

    public Texture getDiffuse() {
        return diffuse;
    }
}