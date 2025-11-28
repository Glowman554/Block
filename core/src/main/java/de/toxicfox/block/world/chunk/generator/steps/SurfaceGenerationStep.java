package de.toxicfox.block.world.chunk.generator.steps;

import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.NoiseGenerator;
import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.generator.ChunkGenerator;
import de.toxicfox.block.world.chunk.generator.GenerationStep;
import de.toxicfox.block.world.chunk.generator.HeightMap;

public class SurfaceGenerationStep implements GenerationStep {
    private final NoiseGenerator noise;

    public SurfaceGenerationStep(int seed) {
        this.noise = new NoiseGenerator(seed);
    }

    @Override
    public void generate(ChunkGenerator generator, Chunk chunk, int offsetX, int offsetZ, HeightMap heightMap) {
        for (int x = 0; x < Chunk.SIZE; x++) {
            for (int z = 0; z < Chunk.SIZE; z++) {
                // General height step
                int height = (int) (noise.noise(x + offsetX, z + offsetZ) * ChunkGenerator.surfaceHeight);
                for (int y = 0; y < height; y++) {

                    if (y < height - 3) {
                        chunk.set(x, y + ChunkGenerator.caveHeight, z, Block.STONE);
                    } else if (y < height - 1) {
                        chunk.set(x, y + ChunkGenerator.caveHeight, z, Block.DIRT);
                    } else {
                        chunk.set(x, y + ChunkGenerator.caveHeight, z, Block.GRASS);
                    }

                    heightMap.set(x, z, y + ChunkGenerator.caveHeight);
                }

                if (chunk.get(x, ChunkGenerator.caveHeight, z) == null) {
                    chunk.set(x, ChunkGenerator.caveHeight, z, Block.GRASS);
                    heightMap.set(x, z, ChunkGenerator.caveHeight);
                }
            }
        }
    }
}
