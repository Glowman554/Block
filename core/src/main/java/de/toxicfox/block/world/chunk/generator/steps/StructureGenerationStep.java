package de.toxicfox.block.world.chunk.generator.steps;

import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.block.BlockTags;
import de.toxicfox.block.world.chunk.generator.ChunkGenerator;
import de.toxicfox.block.world.chunk.generator.GenerationStep;
import de.toxicfox.block.world.chunk.generator.HeightMap;
import de.toxicfox.block.world.chunk.generator.Structure;

import java.util.Random;

public class StructureGenerationStep implements GenerationStep {

    private final int generationSeed;
    private final int variation;
    private final Block[][][] structure;
    private final float threshold;

    public StructureGenerationStep(int generationSeed, int variation, Block[][][] structure, float threshold) {
        this.generationSeed = generationSeed;
        this.variation = variation;
        this.structure = structure;
        this.threshold = threshold;
    }

    @Override
    public void generate(ChunkGenerator generator, Chunk chunk, int offsetX, int offsetZ, HeightMap heightMap) {
        long seed = generator.getSeed();
        seed ^= (long) chunk.getChunkX() * 341873128712L;
        seed ^= (long) chunk.getChunkZ() * 132897987541L;
        seed ^= generationSeed;

        Random random = new Random(seed);

        if (random.nextFloat() <= threshold) {
            return;
        }

        int posX = Chunk.SIZE / 2 + (random.nextInt(variation * 2 + 1) - variation);
        int posZ = Chunk.SIZE / 2 + (random.nextInt(variation * 2 + 1) - variation);


        int posY = heightMap.get(posX, posZ) + 1;
        if (chunk.get(posX, posY - 1, posZ) == null || !chunk.get(posX, posY - 1, posZ).hasTag(BlockTags.FULL_BLOCK)) {
            return;
        }

        Structure.place(chunk, posX, posY, posZ, structure);
    }
}
