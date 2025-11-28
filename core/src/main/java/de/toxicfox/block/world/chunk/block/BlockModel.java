package de.toxicfox.block.world.chunk.block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;

import de.toxicfox.block.world.chunk.Chunk;

public abstract class BlockModel {
    MeshPartBuilder.VertexInfo v1 = new MeshPartBuilder.VertexInfo();
    MeshPartBuilder.VertexInfo v2 = new MeshPartBuilder.VertexInfo();
    MeshPartBuilder.VertexInfo v3 = new MeshPartBuilder.VertexInfo();
    MeshPartBuilder.VertexInfo v4 = new MeshPartBuilder.VertexInfo();

    public abstract void addVisibleFaces(Chunk chunk, MeshPartBuilder mb, Block b, int x, int y, int z, int attr);

    public abstract void initialize(String id);

    protected Pixmap load(String file) {
        System.out.println("Loading " + file + "...");
        return new Pixmap(Gdx.files.internal(file));
    }

    protected void rectWithUV(MeshPartBuilder mb,
                              float x1, float y1, float z1,
                              float x2, float y2, float z2,
                              float x3, float y3, float z3,
                              float x4, float y4, float z4,
                              float nx, float ny, float nz,
                              TextureRegion tex) {
        v1.setPos(x1, y1, z1).setNor(nx, ny, nz).setUV(tex.getU(), tex.getV2());
        v2.setPos(x2, y2, z2).setNor(nx, ny, nz).setUV(tex.getU2(), tex.getV2());
        v3.setPos(x3, y3, z3).setNor(nx, ny, nz).setUV(tex.getU2(), tex.getV());
        v4.setPos(x4, y4, z4).setNor(nx, ny, nz).setUV(tex.getU(), tex.getV());

        mb.rect(v1, v2, v3, v4);
    }

    public abstract void dispose();
}
