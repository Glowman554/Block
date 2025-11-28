package de.toxicfox.block.world;

import de.toxicfox.block.world.chunk.ChunkStore;

import java.io.*;
import java.util.Random;

public class WorldStore {
    private final ChunkStore chunkStore;
    private final File worldDirectory;

    private final boolean persistWorld;

    public WorldStore(String world, boolean persistWorld) {
        this.persistWorld = persistWorld;

        worldDirectory = new File(world);
        if (persistWorld) {
            worldDirectory.mkdirs();
        }

        chunkStore = new ChunkStore(new File(worldDirectory, "chunks"), persistWorld);
    }

    public long loadOrGenerateWorldSeed() {
        File seedFile = new File(worldDirectory, "world.seed");

        if (seedFile.exists() && persistWorld) {
            try (DataInputStream in = new DataInputStream(new FileInputStream(seedFile))) {
                return in.readLong();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Generate new seed
        long worldSeed = new Random().nextLong();

        if (persistWorld) {
            saveWorldSeed(worldSeed);
        }

        return worldSeed;
    }

    private void saveWorldSeed(long worldSeed) {
        File seedFile = new File(worldDirectory, "world.seed");

        try (DataOutputStream out = new DataOutputStream(new FileOutputStream(seedFile))) {
            out.writeLong(worldSeed);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ChunkStore getChunkStore() {
        return chunkStore;
    }

    public void dispose() {
        chunkStore.dispose();
    }
}
