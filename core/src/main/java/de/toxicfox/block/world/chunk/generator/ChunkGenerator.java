package de.toxicfox.block.world.chunk.generator;

import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.generator.steps.*;

import java.util.ArrayList;
import java.util.Random;

public class ChunkGenerator {
    public static final int caveHeight = 16;
    public static final int surfaceHeight = Chunk.HEIGHT - caveHeight;

    private final ArrayList<GenerationStep> generators = new ArrayList<>();
    private final long seed;


    public ChunkGenerator(long seed) {
        this.seed = seed;
        System.out.printf("World seed: %d%n", seed);
        Random seeder = new Random(seed);

        generators.add(new SubSurfaceGenerationStep());
        generators.add(new OreGenerationStep(seeder.nextInt(), Block.COAL, 0.75f));
        generators.add(new OreGenerationStep(seeder.nextInt(), Block.GOLD, 0.75f));
        generators.add(new SurfaceGenerationStep(seeder.nextInt()));
        generators.add(new CaveCarvingStep(seeder.nextInt(), 0.45f));
        generators.add(new SpotGenerationStep(seeder.nextInt(), Block.WATER, 0.6f));
        generators.add(new SpotGenerationStep(seeder.nextInt(), Block.SAND, 0.6f));
        generators.add(new PlantGenerationStep(seeder.nextInt(), Block.BERRY, 0.65f));
        generators.add(new PlantGenerationStep(seeder.nextInt(), Block.FLOWER, 0.65f));
        generators.add(new ForrestGenerationStep(seeder.nextInt(), 0.6f));
        generators.add(new StructureGenerationStep(seeder.nextInt(), 2, Structures.treeStructure, 0.97f)); // Place trees outside a forrest
        generators.add(new StructureGenerationStep(seeder.nextInt(), 1, Structures.houseStructure, 0.99f));
    }


    public void generateChunk(Chunk chunk) {
        int offsetX = chunk.getChunkX() * Chunk.SIZE;
        int offsetZ = chunk.getChunkZ() * Chunk.SIZE;

        long startTime = System.nanoTime();

        HeightMap heightMap = new HeightMap();

        for (GenerationStep generator : generators) {
            generator.generate(this, chunk, offsetX, offsetZ, heightMap);
        }

        chunk.rebuild();

        long endTime = System.nanoTime();
        long durationNs = (endTime - startTime);

        System.out.printf("[%d, %d] Chunk generated in %d ns%n", chunk.getChunkX(), chunk.getChunkZ(), durationNs);
    }

    public long getSeed() {
        return seed;
    }
}
