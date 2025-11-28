package de.toxicfox.block.world.chunk.generator;

import de.toxicfox.block.world.chunk.Chunk;

public interface GenerationStep {
    void generate(ChunkGenerator generator, Chunk chunk, int offsetX, int offsetZ, HeightMap heightMap);
}
