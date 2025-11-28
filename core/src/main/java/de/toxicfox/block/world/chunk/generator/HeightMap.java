package de.toxicfox.block.world.chunk.generator;

import de.toxicfox.block.world.chunk.Chunk;

public class HeightMap {
    public final int[][] heights;

    public HeightMap() {
        this.heights = new int[Chunk.SIZE][Chunk.SIZE];
        for (int x = 0; x < Chunk.SIZE; x++) {
            for (int z = 0; z < Chunk.SIZE; z++) {
                this.heights[x][z] = -1;
            }
        }
    }

    public void set(int x, int z, int height) {
        this.heights[x][z] = height;
    }

    public int get(int x, int z) {
        int height = this.heights[x][z];
        if (height == -1) {
            throw new IllegalStateException("Height at (" + x + ", " + z + ") has not been set yet.");
        }
        return height;
    }
}
