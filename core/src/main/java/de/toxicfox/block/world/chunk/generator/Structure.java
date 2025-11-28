package de.toxicfox.block.world.chunk.generator;

import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.block.Block;

public class Structure {
    public static void place(Chunk chunk, int posX, int posY, int posZ, Block[][][] structure) {
        int structOffsetX = posX - structure[0].length / 2;
        int structOffsetZ = posZ - structure[0][0].length / 2;

        for (int y = 0; y < structure.length; y++) {
            for (int x = 0; x < structure[y].length; x++) {
                for (int z = 0; z < structure[y][x].length; z++) {
                    chunk.set(x + structOffsetX, y + posY, z + structOffsetZ, structure[y][x][z]);
                }
            }
        }
    }
}
