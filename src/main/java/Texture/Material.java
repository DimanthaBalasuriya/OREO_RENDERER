package Texture;

public class Material {

    private Texture diffuse;
    private Color color;

    public Material(Texture diffuse, Color color) {
        this.diffuse = diffuse;
        this.color = color;
    }

    public Texture getDiffuse() {
        return diffuse;
    }

    public Color getColor() {
        return color;
    }
}
