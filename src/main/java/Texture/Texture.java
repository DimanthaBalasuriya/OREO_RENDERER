package Texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL11C;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class Texture {

    private int textureID;
    private int width;
    private int height;

    public Texture(String file) {
        loadTextureResource(file);
    }

    private void loadTextureResource(String file) {
        ByteBuffer buf;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            buf = STBImage.stbi_load(file, w, h, channels, 4);
            if (buf == null) {
                try {
                    throw new Exception("Image file [" + file + "] not loaded: " + STBImage.stbi_failure_reason());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            width = w.get();
            height = h.get();
        }

        textureID = createTextureID(buf);
        STBImage.stbi_image_free(buf);
        STBImage.stbi_set_flip_vertically_on_load(true);

    }

    private int createTextureID(ByteBuffer buf) {
        int textureId = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
        GL11.glPixelStorei(GL11C.GL_UNPACK_ALIGNMENT, 1);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        return textureId;
    }

    public int getTextureID() {
        return textureID;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
