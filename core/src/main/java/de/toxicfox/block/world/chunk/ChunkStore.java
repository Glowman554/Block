package de.toxicfox.block.world.chunk;

import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.generator.ChunkGenerator;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChunkStore {
    private final File chunkDirectory;
    private final ExecutorService pool = Executors.newFixedThreadPool(8);

    private final boolean persistWorld;

    public ChunkStore(File chunkDirectory, boolean persistWorld) {
        this.persistWorld = persistWorld;

        this.chunkDirectory = chunkDirectory;

        if (persistWorld) {
            chunkDirectory.mkdirs();
        }
    }

    private File getDataFile(Chunk chunk) {
        return new File(chunkDirectory, String.format("%d,%d.chunk", chunk.getChunkX(), chunk.getChunkZ()));
    }

    public void scheduleStore(Chunk chunk) {
        if (!persistWorld) {
            return;
        }

        pool.submit(() -> {
            File filePath = getDataFile(chunk);
            System.out.printf("[%d, %d] Saving chunk...%n", chunk.getChunkX(), chunk.getChunkZ());

            try (DataOutputStream out = new DataOutputStream(new FileOutputStream(filePath))) {
                for (int x = 0; x < Chunk.SIZE; x++) {
                    for (int y = 0; y < Chunk.HEIGHT; y++) {
                        for (int z = 0; z < Chunk.SIZE; z++) {

                            Block block = chunk.get(x, y, z);
                            byte id = (block == null) ? 0 : block.getNumericId();

                            out.writeByte(id);
                        }
                    }
                }
            } catch (IOException e) {
                System.err.printf("Failed to save chunk [%d, %d]: %s%n",
                    chunk.getChunkX(), chunk.getChunkZ(), e.getMessage());
            }
        });
    }

    public void scheduleLoadOrGenerate(Chunk chunk, ChunkGenerator generator) {
        pool.submit(() -> {
            File filePath = getDataFile(chunk);

            if (filePath.exists() && persistWorld) {
                loadChunk(chunk, filePath);
            } else {
                generator.generateChunk(chunk);
            }
        });
    }

    private void loadChunk(Chunk chunk, File filePath) {
        try (DataInputStream in = new DataInputStream(new FileInputStream(filePath))) {

            for (int x = 0; x < Chunk.SIZE; x++) {
                for (int y = 0; y < Chunk.HEIGHT; y++) {
                    for (int z = 0; z < Chunk.SIZE; z++) {

                        byte id = in.readByte();
                        chunk.set(x, y, z, id == 0 ? null : Block.fromNumericId(id));
                    }
                }
            }

            chunk.rebuild();

        } catch (IOException e) {
            System.err.printf("Failed to load chunk file '%s': %s%n",
                filePath, e.getMessage());
        }
    }

    public void dispose() {
        pool.shutdown();
    }
}
