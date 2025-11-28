package de.toxicfox.block.world.chunk.generator.steps;

import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.NoiseGenerator;
import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.generator.*;

import java.util.Random;

public class ForrestGenerationStep implements GenerationStep {


    private final NoiseGenerator noise;
    private final float threshold;

    public ForrestGenerationStep(int seed, float threshold) {
        this.noise = new NoiseGenerator(seed);
        this.threshold = threshold;
    }

    @Override
    public void generate(ChunkGenerator generator, Chunk chunk, int offsetX, int offsetZ, HeightMap heightMap) {
        if (noise.noise(chunk.getChunkX(), chunk.getChunkZ()) <= threshold) {
            return;
        }

        Random random = new Random((long) chunk.getChunkX() * chunk.getChunkZ());
        int treeX = Chunk.SIZE / 2 + (random.nextInt(5) - 2);
        int treeZ = Chunk.SIZE / 2 + (random.nextInt(5) - 2);


        int treeY = heightMap.get(treeX, treeZ) + 1;
        if (chunk.get(treeX, treeY - 1, treeZ) == null || chunk.get(treeX, treeY - 1, treeZ) != Block.GRASS) {
            return;
        }

        Structure.place(chunk, treeX, treeY, treeZ, Structures.treeStructure);
    }
}
