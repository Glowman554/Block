package de.toxicfox.block.world.chunk.block.models;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;

import de.toxicfox.block.texture.DynamicAtlas;
import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.block.BlockModel;
import de.toxicfox.block.world.chunk.block.BlockTags;

public class DefaultModel extends BlockModel {
    private TextureRegion front;
    private TextureRegion back;
    private TextureRegion left;
    private TextureRegion right;
    private TextureRegion top;
    private TextureRegion bottom;

    private boolean shouldAddFace(Block self, Chunk chunk, int x, int y, int z, int dx, int dy, int dz) {
        Block neighbor = chunk.adj(x, y, z, dx, dy, dz);

        if (neighbor == null) {
            return true;
        }

        if (self.hasTag(BlockTags.TRANSPARENT) && neighbor.hasTag(BlockTags.TRANSPARENT)) {
            return false;
        }

        return !neighbor.hasTag(BlockTags.FULL_BLOCK);
    }


    @Override
    public void addVisibleFaces(Chunk chunk, MeshPartBuilder mb, Block b, int x, int y, int z, int attr) {
        if (shouldAddFace(b, chunk, x, y, z, 0, 0, -1)) {
            rectWithUV(mb,
                x, y, z,     // BL
                x, y + 1, z,     // TL
                x + 1, y + 1, z,     // TR
                x + 1, y, z,     // BR
                0, 0, -1,
                front
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 0, 0, 1)) {
            rectWithUV(mb,
                x, y + 1, z + 1,
                x, y, z + 1,
                x + 1, y, z + 1,
                x + 1, y + 1, z + 1,
                0, 0, 1,
                back
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 0, -1, 0)) {
            rectWithUV(mb,
                x, y, z + 1,
                x, y, z,
                x + 1, y, z,
                x + 1, y, z + 1,
                0, -1, 0,
                bottom
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 0, 1, 0)) {
            rectWithUV(mb,
                x, y + 1, z,
                x, y + 1, z + 1,
                x + 1, y + 1, z + 1,
                x + 1, y + 1, z,
                0, 1, 0,
                top
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, -1, 0, 0)) {
            rectWithUV(mb,
                x, y, z + 1,
                x, y + 1, z + 1,
                x, y + 1, z,
                x, y, z,
                -1, 0, 0,
                left
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 1, 0, 0)) {
            rectWithUV(mb,
                x + 1, y, z,
                x + 1, y + 1, z,
                x + 1, y + 1, z + 1,
                x + 1, y, z + 1,
                1, 0, 0,
                right
            );
        }
    }

    @Override
    public void initialize(String id) {
        front = DynamicAtlas.BLOCK_ATLAS.addTexture(load("texture/" + id + "/front.png"), true);
        back = DynamicAtlas.BLOCK_ATLAS.addTexture(load("texture/" + id + "/back.png"), true);
        left = DynamicAtlas.BLOCK_ATLAS.addTexture(load("texture/" + id + "/left.png"), true);
        right = DynamicAtlas.BLOCK_ATLAS.addTexture(load("texture/" + id + "/right.png"), true);
        top = DynamicAtlas.BLOCK_ATLAS.addTexture(load("texture/" + id + "/top.png"), true);
        bottom = DynamicAtlas.BLOCK_ATLAS.addTexture(load("texture/" + id + "/bottom.png"), true);
    }

    @Override
    public void dispose() {
    }
}
