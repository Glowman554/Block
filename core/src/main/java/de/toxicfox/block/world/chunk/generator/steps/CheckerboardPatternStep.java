package de.toxicfox.block.world.chunk.generator.steps;

import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.generator.ChunkGenerator;
import de.toxicfox.block.world.chunk.generator.GenerationStep;
import de.toxicfox.block.world.chunk.generator.HeightMap;

public class CheckerboardPatternStep implements GenerationStep {
    @Override
    public void generate(ChunkGenerator generator, Chunk chunk, int offsetX, int offsetZ, HeightMap heightMap) {
        for (int y = 0; y < Chunk.HEIGHT; y++) {
            for (int x = 0; x < Chunk.SIZE; x++) {
                boolean flip = (x % 2) == 0;
                if (y % 2 == 0) {
                    flip = !flip;
                }

                for (int z = 0; z < Chunk.SIZE; z++) {
                    if (flip) {
                        chunk.set(x, y, z, null);
                    } else {
                        chunk.set(x, y, z, Block.STONE);
                    }
                    flip = !flip;
                }
            }
        }
    }
}
