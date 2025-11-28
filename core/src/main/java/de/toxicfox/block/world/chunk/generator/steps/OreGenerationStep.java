package de.toxicfox.block.world.chunk.generator.steps;

import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.NoiseGenerator;
import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.generator.ChunkGenerator;
import de.toxicfox.block.world.chunk.generator.GenerationStep;
import de.toxicfox.block.world.chunk.generator.HeightMap;

public class OreGenerationStep implements GenerationStep {
    private final NoiseGenerator noise;
    private final Block block;

    public OreGenerationStep(NoiseGenerator noise, Block block) {
        this.noise = noise;
        this.block = block;
    }

        @Override
        public void generate(ChunkGenerator generator, Chunk chunk, int offsetX, int offsetZ, HeightMap heightMap) {
            for (int x = 0; x < Chunk.SIZE; x++) {
                for (int z = 0; z < Chunk.SIZE; z++) {
                    for (int y = 0; y < ChunkGenerator.caveHeight; y++) {
                        if (noise.noise(x + offsetX, y, z + offsetZ) > 0.7) {
                            chunk.set(x, y, z, block);
                        }
                    }
                }
            }
        }


}
