package Texture;

public class Material {

    private Texture diffuse;
    private CubeMapTexture cubeMapTexture;
    private Color color;

    public Material(Texture diffuse, Color color) {
        this.diffuse = diffuse;
        this.color = color;
    }

    public Material(Texture diffuse, CubeMapTexture cubeMapTexture, Color color) {
        this.diffuse = diffuse;
        this.cubeMapTexture = cubeMapTexture;
        this.color = color;
    }

    public Texture getDiffuse() {
        return diffuse;
    }

    public Color getColor() {
        return color;
    }

    public CubeMapTexture getCubeMapTexture() {
        return cubeMapTexture;
    }
}
