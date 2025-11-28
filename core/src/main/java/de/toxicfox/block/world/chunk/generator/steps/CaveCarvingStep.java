package de.toxicfox.block.world.chunk.generator.steps;

import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.generator.ChunkGenerator;
import de.toxicfox.block.world.chunk.generator.GenerationStep;
import de.toxicfox.block.world.chunk.generator.HeightMap;

public class CaveCarvingStep implements GenerationStep {
    @Override
    public void generate(ChunkGenerator generator, Chunk chunk, int offsetX, int offsetZ, HeightMap heightMap) {
        for (int x = 0; x < Chunk.SIZE; x++) {
            for (int z = 0; z < Chunk.SIZE; z++) {
                for (int y = 0; y < Chunk.HEIGHT; y++) {
                    if (generator.caveNoise.noise(x + offsetX, y, z + offsetZ) > 0.4) {
                        chunk.set(x, y, z, null);
                    }
                }

                if (chunk.get(x, 0, z) == null) {
                    chunk.set(x, 0, z, Block.STONE);
                }
            }
        }
    }
}
