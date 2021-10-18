package Render;

import EntityItem.Entity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class EntityRenderer extends Renderer {

    public void render(Entity entity) {
        for (int i = 0; i < entity.getRawModel().length; i++) {
            GL30.glBindVertexArray(entity.getRawModel()[i].getVaoID());
            GL20.glEnableVertexAttribArray(0);
            GL20.glEnableVertexAttribArray(1);
            GL20.glEnableVertexAttribArray(2);
            GL13.glActiveTexture(GL13.GL_TEXTURE0);
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, entity.getMaterial().getDiffuse().getTextureID());
            GL11.glDrawElements(GL11.GL_TRIANGLES, entity.getRawModel()[i].getIndices(), GL11.GL_UNSIGNED_INT, 0);
            GL20.glDisableVertexAttribArray(0);
            GL20.glDisableVertexAttribArray(1);
            GL20.glDisableVertexAttribArray(2);
            GL30.glBindVertexArray(0);
        }
    }

}
