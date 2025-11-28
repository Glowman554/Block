package de.toxicfox.block.world.chunk.generator.steps;

import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.NoiseGenerator;
import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.generator.ChunkGenerator;
import de.toxicfox.block.world.chunk.generator.GenerationStep;
import de.toxicfox.block.world.chunk.generator.HeightMap;

import java.util.Random;

public class TreeGenerationStep implements GenerationStep {

    private final int treeHeight = 4; // trunk height
    private final NoiseGenerator noise;
    private final float threshold;

    public TreeGenerationStep(int seed, float threshold) {
        this.noise = new NoiseGenerator(seed);
        this.threshold = threshold;
    }

    @Override
    public void generate(ChunkGenerator generator, Chunk chunk, int offsetX, int offsetZ, HeightMap heightMap) {
        if (noise.noise(chunk.getChunkX(), chunk.getChunkZ()) < threshold) {
            return;
        }

        Random random = new Random((long) chunk.getChunkX() * chunk.getChunkZ());
        int treeX = Chunk.SIZE / 2 + (random.nextInt(5) - 2);
        int treeZ = Chunk.SIZE / 2 + (random.nextInt(5) - 2);


        int treeY = heightMap.get(treeX, treeZ) + 1;
        if (chunk.get(treeX, treeY - 1, treeZ) == null || chunk.get(treeX, treeY - 1, treeZ) != Block.GRASS) {
            return;
        }

        for (int y = treeY; y < treeY + treeHeight; y++) {
            chunk.set(treeX, y, treeZ, Block.LOG);
        }


        int leavesBottom = treeY + treeHeight - 1;
        int leavesTop = treeY + treeHeight + 1;

        for (int x = treeX - 1; x <= treeX + 1; x++) {
            for (int y = leavesBottom; y <= leavesTop; y++) {
                for (int z = treeZ - 1; z <= treeZ + 1; z++) {
                    if (x == treeX && z == treeZ && y == leavesBottom) {
                        continue;
                    }

                    chunk.set(x, y, z, Block.LEAVES);
                }
            }
        }

    }
}
