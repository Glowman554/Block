package de.toxicfox.block.world.chunk.generator;

import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.NoiseGenerator;
import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.generator.steps.*;

import java.util.ArrayList;
import java.util.Random;

public class ChunkGenerator {
    public static final int caveHeight = 16;
    public static final int surfaceHeight = Chunk.HEIGHT - caveHeight;

    private final ArrayList<GenerationStep> generators = new ArrayList<>();

    public final NoiseGenerator caveNoise;
    public final NoiseGenerator heightNoise;
    public final NoiseGenerator berryNoise;
    public final NoiseGenerator flowerNoise;
    public final NoiseGenerator treeNoise;
    public final NoiseGenerator waterNoise;
    public final NoiseGenerator coalNoise;
    public final NoiseGenerator goldNoise;
    public final NoiseGenerator sandNoise;

    public ChunkGenerator(long seed) {
        System.out.printf("World seed: %d%n", seed);
        Random seeder = new Random(seed);
        caveNoise = new NoiseGenerator(seeder.nextInt());
        heightNoise = new NoiseGenerator(seeder.nextInt());
        berryNoise = new NoiseGenerator(seeder.nextInt());
        flowerNoise = new NoiseGenerator(seeder.nextInt());
        treeNoise = new NoiseGenerator(seeder.nextInt());
        waterNoise = new NoiseGenerator(seeder.nextInt());
        coalNoise = new NoiseGenerator(seeder.nextInt());
        goldNoise = new NoiseGenerator((seeder.nextInt()));
        sandNoise = new NoiseGenerator((seeder.nextInt()));

        generators.add(new SubSurfaceGenerationStep());
        generators.add(new OreGenerationStep(coalNoise, Block.COAL));
        generators.add(new OreGenerationStep(goldNoise, Block.GOLD));
        generators.add(new SurfaceGenerationStep());
        generators.add(new CaveCarvingStep());
        generators.add(new SpotGenerationStep(waterNoise, Block.WATER));
        generators.add(new SpotGenerationStep(sandNoise, Block.SAND));
        generators.add(new PlantGenerationStep(berryNoise, Block.BERRY));
        generators.add(new PlantGenerationStep(flowerNoise, Block.FLOWER));
        generators.add(new TreeGenerationStep());
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
}
