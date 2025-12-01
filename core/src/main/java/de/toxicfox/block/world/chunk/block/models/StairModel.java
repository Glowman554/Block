package de.toxicfox.block.world.chunk.block.models;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import de.toxicfox.block.texture.DynamicAtlas;
import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.block.BlockModel;

public class StairModel extends BlockModel {
    private TextureRegion texture;
    private TextureRegion textureTop;
    private TextureRegion textureBottom;
    private TextureRegion textureSide;

    public static final byte NORTH = 0;
    public static final byte EAST = 1;
    public static final byte SOUTH = 2;
    public static final byte WEST = 3;

    @Override
    public void addVisibleFaces(Chunk chunk, MeshPartBuilder mb, Block b, int x, int y, int z, int attr) {
        byte type = chunk.getData(x, y, z);

        if (shouldAddFace(b, chunk, x, y, z, 0, 0, -1)) {
            rectWithUV(mb,
                x, y, z,
                x, y + 0.5f, z,
                x + 1, y + 0.5f, z,
                x + 1, y, z,
                0, 0, -1,
                textureBottom
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 0, 0, 1)) {
            rectWithUV(mb,
                x, y + 0.5f, z + 1,
                x, y, z + 1,
                x + 1, y, z + 1,
                x + 1, y + 0.5f, z + 1,
                0, 0, 1,
                textureBottom
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 0, -1, 0)) {
            rectWithUV(mb,
                x, y, z + 1,
                x, y, z,
                x + 1, y, z,
                x + 1, y, z + 1,
                0, -1, 0,
                texture
            );
        }


        switch (type) {
            case NORTH:
                addNorthStair(chunk, mb, b, x, y, z);
                break;
            case SOUTH:
                addSouthStair(chunk, mb, b, x, y, z);
                break;
            case EAST:
                addEastStair(chunk, mb, b, x, y, z);
                break;
            case WEST:
                addWestStair(chunk, mb, b, x, y, z);
                break;
        }

        rectWithUV(mb,
            x, y + 0.5f, z,
            x, y + 0.5f, z + 1,
            x + 1, y + 0.5f, z + 1,
            x + 1, y + 0.5f, z,
            0, 1, 0,
            texture
        );


        if (shouldAddFace(b, chunk, x, y, z, -1, 0, 0)) {
            rectWithUV(mb,
                x, y, z + 1,
                x, y + 0.5f, z + 1,
                x, y + 0.5f, z,
                x, y, z,
                -1, 0, 0,
                textureBottom
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 1, 0, 0)) {
            rectWithUV(mb,
                x + 1, y, z,
                x + 1, y + 0.5f, z,
                x + 1, y + 0.5f, z + 1,
                x + 1, y, z + 1,
                1, 0, 0,
                textureBottom
            );
        }
    }

    private void addSouthStair(Chunk chunk, MeshPartBuilder mb, Block b, int x, int y, int z) {
        if (shouldAddFace(b, chunk, x, y, z, 0, 1, 0)) {
            rectWithUV(mb,
                x, y + 1, z,
                x, y + 1, z + 0.5f,
                x + 1, y + 1, z + 0.5f,
                x + 1, y + 1, z,
                0, 1, 0,
                textureTop
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 0, 0, -1)) {
            rectWithUV(mb,
                x, y + 0.5f, z,
                x, y + 1, z,
                x + 1, y + 1, z,
                x + 1, y + 0.5f, z,
                0, 0, -1,
                textureTop
            );
        }

        rectWithUV(mb,
            x + 1, y + 0.5f, z + 0.5f,
            x + 1, y + 1, z + 0.5f,
            x, y + 1, z + 0.5f,
            x, y + 0.5f, z + 0.5f,
            0, 0, 1,
            textureTop
        );

        if (shouldAddFace(b, chunk, x, y, z, -1, 0, 0)) {
            rectWithUV(mb,
                x, y + 0.5f, z + 0.5f,
                x, y + 1, z + 0.5f,
                x, y + 1, z,
                x, y + 0.5f, z,
                -1, 0, 0,
                textureSide
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 1, 0, 0)) {
            rectWithUV(mb,
                x + 1, y + 0.5f, z,
                x + 1, y + 1, z,
                x + 1, y + 1, z + 0.5f,
                x + 1, y + 0.5f, z + 0.5f,
                1, 0, 0,
                textureSide
            );
        }
    }


    private void addNorthStair(Chunk chunk, MeshPartBuilder mb, Block b, int x, int y, int z) {

        if (shouldAddFace(b, chunk, x, y, z, 0, 1, 0)) {
            rectWithUV(mb,
                x, y + 1, z + 0.5f,
                x, y + 1, z + 1,
                x + 1, y + 1, z + 1,
                x + 1, y + 1, z + 0.5f,
                0, 1, 0,
                textureTop
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 0, 0, 1)) {
            rectWithUV(mb,
                x + 1, y + 0.5f, z + 1,
                x + 1, y + 1, z + 1,
                x, y + 1, z + 1,
                x, y + 0.5f, z + 1,
                0, 0, 1,
                textureTop
            );
        }

        rectWithUV(mb,
            x, y + 0.5f, z + 0.5f,
            x, y + 1, z + 0.5f,
            x + 1, y + 1, z + 0.5f,
            x + 1, y + 0.5f, z + 0.5f,
            0, 0, -1,
            textureTop
        );

        if (shouldAddFace(b, chunk, x, y, z, -1, 0, 0)) {
            rectWithUV(mb,
                x, y + 0.5f, z + 1,
                x, y + 1, z + 1,
                x, y + 1, z + 0.5f,
                x, y + 0.5f, z + 0.5f,
                -1, 0, 0,
                textureSide
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 1, 0, 0)) {
            rectWithUV(mb,
                x + 1, y + 0.5f, z + 0.5f,
                x + 1, y + 1, z + 0.5f,
                x + 1, y + 1, z + 1,
                x + 1, y + 0.5f, z + 1,
                1, 0, 0,
                textureSide
            );
        }
    }


    private void addEastStair(Chunk chunk, MeshPartBuilder mb, Block b, int x, int y, int z) {

        if (shouldAddFace(b, chunk, x, y, z, 0, 1, 0)) {
            rectWithUV(mb,
                x + 0.5f, y + 1, z + 1,
                x + 1, y + 1, z + 1,
                x + 1, y + 1, z,
                x + 0.5f, y + 1, z,
                0, 1, 0,
                textureTop
            );
        }


        if (shouldAddFace(b, chunk, x, y, z, 1, 0, 0)) {
            rectWithUV(mb,
                x + 1, y + 0.5f, z,
                x + 1, y + 1, z,
                x + 1, y + 1, z + 1,
                x + 1, y + 0.5f, z + 1,
                1, 0, 0,
                textureTop
            );
        }

        rectWithUV(mb,
            x + 0.5f, y + 0.5f, z + 1,
            x + 0.5f, y + 1, z + 1,
            x + 0.5f, y + 1, z,
            x + 0.5f, y + 0.5f, z,
            -1, 0, 0,
            textureTop
        );

        if (shouldAddFace(b, chunk, x, y, z, 0, 0, -1)) {
            rectWithUV(mb,
                x + 0.5f, y + 0.5f, z,
                x + 0.5f, y + 1, z,
                x + 1, y + 1, z,
                x + 1, y + 0.5f, z,
                0, 0, -1,
                textureSide
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, 0, 0, 1)) {
            rectWithUV(mb,
                x + 1, y + 0.5f, z + 1,
                x + 1, y + 1, z + 1,
                x + 0.5f, y + 1, z + 1,
                x + 0.5f, y + 0.5f, z + 1,
                0, 0, 1,
                textureSide
            );
        }
    }

    private void addWestStair(Chunk chunk, MeshPartBuilder mb, Block b, int x, int y, int z) {

        if (shouldAddFace(b, chunk, x, y, z, 0, 1, 0)) {
            rectWithUV(mb,
                x, y + 1, z + 1,
                x + 0.5f, y + 1, z + 1,
                x + 0.5f, y + 1, z,
                x, y + 1, z,
                0, 1, 0,
                textureTop
            );
        }

        if (shouldAddFace(b, chunk, x, y, z, -1, 0, 0)) {
            rectWithUV(mb,
                x, y + 0.5f, z + 1,
                x, y + 1, z + 1,
                x, y + 1, z,
                x, y + 0.5f, z,
                -1, 0, 0,
                textureTop
            );
        }

        rectWithUV(mb,
            x + 0.5f, y + 0.5f, z,
            x + 0.5f, y + 1, z,
            x + 0.5f, y + 1, z + 1,
            x + 0.5f, y + 0.5f, z + 1,
            1, 0, 0,
            textureTop
        );


        if (shouldAddFace(b, chunk, x, y, z, 0, 0, -1)) {
            rectWithUV(mb,
                x, y + 0.5f, z,
                x, y + 1, z,
                x + 0.5f, y + 1, z,
                x + 0.5f, y + 0.5f, z,
                0, 0, -1,
                textureSide
            );
        }


        if (shouldAddFace(b, chunk, x, y, z, 0, 0, 1)) {
            rectWithUV(mb,
                x + 0.5f, y + 0.5f, z + 1,
                x + 0.5f, y + 1, z + 1,
                x, y + 1, z + 1,
                x, y + 0.5f, z + 1,
                0, 0, 1,
                textureSide
            );
        }
    }


    @Override
    public void initialize(String id) {
        texture = DynamicAtlas.BLOCK_ATLAS.addTexture(load("texture/" + id + ".png"), true);
        textureTop = subRegion(texture, 0f, 0f, 0.5f, 1f);
        textureBottom = subRegion(texture, 0.5f, 0f, 1f, 1f);
        textureSide = subRegion(texture, 0f, 0f, 0.5f, 0.5f);
    }

    @Override
    public void dispose() {

    }
}
