package de.toxicfox.block.world.chunk.generator;

import java.util.ArrayList;
import java.util.Random;

import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.NoiseGenerator;
import de.toxicfox.block.world.chunk.generator.steps.BerryGenerationStep;
import de.toxicfox.block.world.chunk.generator.steps.CaveCarvingStep;
import de.toxicfox.block.world.chunk.generator.steps.SubSurfaceGenerationStep;
import de.toxicfox.block.world.chunk.generator.steps.SurfaceGenerationStep;

public class ChunkGenerator {
    public static final int caveHeight = 16;
    public static final int surfaceHeight = Chunk.HEIGHT - caveHeight;

    private static final ArrayList<GenerationStep> generators = new ArrayList<>() {{
        add(new SubSurfaceGenerationStep());
        add(new SurfaceGenerationStep());
        add(new CaveCarvingStep());
        add(new BerryGenerationStep());
        //add(new DebugTowerStep());
    }};
    public final NoiseGenerator caveNoise;
    public final NoiseGenerator heightNoise;
    public final NoiseGenerator berryNoise;

    public ChunkGenerator(int seed) {
        Random seeder = new Random(seed);
        caveNoise = new NoiseGenerator(seeder.nextInt());
        heightNoise = new NoiseGenerator(seeder.nextInt());
        berryNoise = new NoiseGenerator(seeder.nextInt());
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
