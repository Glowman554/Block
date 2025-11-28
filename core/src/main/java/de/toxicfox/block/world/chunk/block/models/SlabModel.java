package de.toxicfox.block.world.chunk.block.models;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import de.toxicfox.block.texture.DynamicAtlas;
import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.block.BlockModel;

public class SlabModel extends BlockModel {

    private TextureRegion texture;
    private TextureRegion textureTop;
    private TextureRegion textureBottom;

    public static final byte UPPER = 0;
    public static final byte LOWER = 1;
    public static final byte FULL = 2;

    @Override
    public void addVisibleFaces(Chunk chunk, MeshPartBuilder mb, Block b, int x, int y, int z, int attr) {
        byte type = chunk.getData(x, y, z);

        float y1 = 0f;
        float y2 = 0f;
        TextureRegion current = texture;

        if (type == UPPER) {
            y1 = 0.5f;
            y2 = 1f;
            current = textureTop;
        } else if (type == LOWER) {
            y1 = 0f;
            y2 = 0.5f;
            current = textureBottom;
        } else if (type == FULL) {
            y1 = 0f;
            y2 = 1f;
        }

        if (shouldAddFace(b, chunk, x, y, z, 0, 0, -1)) {
            rectWithUV(mb,
                x, y + y1, z,     // BL
                x, y + y2, z,     // TL
                x + 1, y + y2, z, // TR
                x + 1, y + y1, z, // BR
                0, 0, -1,
                current
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 0, 0, 1)) {
            rectWithUV(mb,
                x, y + y2, z + 1,
                x, y + y1, z + 1,
                x + 1, y + y1, z + 1,
                x + 1, y + y2, z + 1,
                0, 0, 1,
                current
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 0, -1, 0) || type == UPPER) {
            rectWithUV(mb,
                x, y + y1, z + 1,
                x, y + y1, z,
                x + 1, y + y1, z,
                x + 1, y + y1, z + 1,
                0, -1, 0,
                texture
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 0, 1, 0) || type == LOWER) {
            rectWithUV(mb,
                x, y + y2, z,
                x, y + y2, z + 1,
                x + 1, y + y2, z + 1,
                x + 1, y + y2, z,
                0, 1, 0,
                texture
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, -1, 0, 0)) {
            rectWithUV(mb,
                x, y + y1, z + 1,
                x, y + y2, z + 1,
                x, y + y2, z,
                x, y + y1, z,
                -1, 0, 0,
                current
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 1, 0, 0)) {
            rectWithUV(mb,
                x + 1, y + y1, z,
                x + 1, y + y2, z,
                x + 1, y + y2, z + 1,
                x + 1, y + y1, z + 1,
                1, 0, 0,
                current
            );
        }
    }


    @Override
    public void initialize(String id) {
        texture = DynamicAtlas.BLOCK_ATLAS.addTexture(load("texture/" + id + ".png"), true);
        textureTop = subRegion(texture, 0f, 0f, 0.5f, 1f);
        textureBottom = subRegion(texture, 0.5f, 0f, 1f, 1f);
    }

    @Override
    public void dispose() {
    }
}
