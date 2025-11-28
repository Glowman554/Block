package de.toxicfox.block.world.chunk.generator.steps;

import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.NoiseGenerator;
import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.block.BlockTags;
import de.toxicfox.block.world.chunk.generator.ChunkGenerator;
import de.toxicfox.block.world.chunk.generator.GenerationStep;
import de.toxicfox.block.world.chunk.generator.HeightMap;

public class PlantGenerationStep implements GenerationStep {
    private final NoiseGenerator noise;
    private final Block block;

    public PlantGenerationStep(NoiseGenerator noise, Block block) {
        this.noise = noise;
        this.block = block;
    }

    @Override
    public void generate(ChunkGenerator generator, Chunk chunk, int offsetX, int offsetZ, HeightMap heightMap) {
        for (int x = 0; x < Chunk.SIZE; x++) {
            for (int z = 0; z < Chunk.SIZE; z++) {
                if (noise.noise(x + offsetX, z + offsetZ) > 0.6) {
                    int height = heightMap.get(x, z);
                    if (chunk.get(x, height, z) != null && chunk.get(x, height, z) == Block.GRASS) {
                        chunk.set(x, height + 1, z, block);
                    }
                }
            }
        }
    }
}
