package de.toxicfox.block.world.chunk.block.models;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import de.toxicfox.block.texture.DynamicAtlas;
import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.block.BlockModel;

public class WaterModel extends BlockModel {
    private TextureRegion texture;

    @Override
    public void addVisibleFaces(Chunk chunk, MeshPartBuilder mb, Block b, int x, int y, int z, int attr) {
        float top = y + 0.9f;

        if (shouldAddFace(b, chunk, x, y, z, 0, 1, 0)) {
            rectWithUV(mb,
                x, top, z,        // TL
                x, top, z + 1,    // BL
                x + 1, top, z + 1,// BR
                x + 1, top, z,    // TR
                0, 1, 0,
                texture
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 0, -1, 0)) {
            rectWithUV(mb,
                x, y, z,
                x, y, z + 1,
                x + 1, y, z + 1,
                x + 1, y, z,
                0, -1, 0,
                texture
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 0, 0, -1)) {
            rectWithUV(mb,
                x, y, z,
                x, top, z,
                x + 1, top, z,
                x + 1, y, z,
                0, 0, -1,
                texture
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 0, 0, 1)) {
            rectWithUV(mb,
                x + 1, y, z + 1,
                x + 1, top, z + 1,
                x, top, z + 1,
                x, y, z + 1,
                0, 0, 1,
                texture
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, -1, 0, 0)) {
            rectWithUV(mb,
                x, y, z + 1,
                x, top, z + 1,
                x, top, z,
                x, y, z,
                -1, 0, 0,
                texture
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 1, 0, 0)) {
            rectWithUV(mb,
                x + 1, y, z,
                x + 1, top, z,
                x + 1, top, z + 1,
                x + 1, y, z + 1,
                1, 0, 0,
                texture
            );
        }
    }

    @Override
    public void initialize(String id) {
        texture = DynamicAtlas.BLOCK_ATLAS.addTexture(load("texture/" + id + ".png"), true);
    }

    @Override
    public void dispose() {
    }
}
