package de.toxicfox.block.world.chunk.block.models;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;

import de.toxicfox.block.texture.DynamicAtlas;
import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.block.BlockModel;

public class CrossPlaneModel extends BlockModel {

    private TextureRegion texture;


    @Override
    public void addVisibleFaces(Chunk chunk, MeshPartBuilder mb, Block b, int x, int y, int z, int attr) {
        rectWithUV(mb,
            x, y, z,
            x, y + 1, z,
            x + 1, y + 1, z + 1,
            x + 1, y, z + 1,
            0, 0, 1,
            texture
        );

        rectWithUV(mb,
            x + 1, y, z + 1,
            x + 1, y + 1, z + 1,
            x, y + 1, z,
            x, y, z,
            0, 0, -1,
            texture
        );

        rectWithUV(mb,
            x + 1, y, z,
            x + 1, y + 1, z,
            x, y + 1, z + 1,
            x, y, z + 1,
            0, 0, 1,
            texture
        );

        rectWithUV(mb,
            x, y, z + 1,
            x, y + 1, z + 1,
            x + 1, y + 1, z,
            x + 1, y, z,
            0, 0, -1,
            texture
        );
    }

    @Override
    public void initialize(String id) {
        texture = DynamicAtlas.BLOCK_ATLAS.addTexture(load("texture/" + id + ".png"), true);
    }

    @Override
    public void dispose() {
    }
}
