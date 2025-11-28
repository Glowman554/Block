package de.toxicfox.block.world;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import de.toxicfox.block.debug.DebugOverlay;
import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.block.BlockTags;
import de.toxicfox.block.world.chunk.block.models.SlabModel;
import de.toxicfox.block.world.chunk.generator.ChunkGenerator;

import java.util.ArrayList;

public class World implements DebugOverlay.DataProvider {

    private final Vector3 playerPosition = new Vector3();
    private final ArrayList<Chunk> loadedChunks = new ArrayList<>();
    private final ChunkGenerator generator;
    private final WorldStore store;
    private final int renderDistance;
    private final long seed;

    public World(int renderDistance, boolean persistWorld, String worldFolder) {
        this.store = new WorldStore(worldFolder, persistWorld);

        this.seed = store.loadOrGenerateWorldSeed();
        this.generator = new ChunkGenerator(seed);
        this.renderDistance = renderDistance;
    }

    public void update(Vector3 playerPos) {
        this.playerPosition.set(playerPos);

        loadChunksAroundPlayer();
    }

    private Chunk getChunk(int chunkX, int chunkZ) {
        for (Chunk chunk : loadedChunks) {
            if (chunk.getChunkX() == chunkX && chunk.getChunkZ() == chunkZ) {
                return chunk;
            }
        }
        return null;
    }

    public Block getBlock(int worldX, int worldY, int worldZ) {
        int chunkX = (int) Math.floor((double) worldX / Chunk.SIZE);
        int chunkZ = (int) Math.floor((double) worldZ / Chunk.SIZE);

        Chunk chunk = getChunk(chunkX, chunkZ);
        if (chunk == null) {
            return null;
        }

        int localX = Math.floorMod(worldX, Chunk.SIZE);
        int localZ = Math.floorMod(worldZ, Chunk.SIZE);

        return chunk.get(localX, worldY, localZ);
    }

    public boolean isBlock(int worldX, int worldY, int worldZ) {
        return getBlock(worldX, worldY, worldZ) != null;
    }

    public void setBlock(int worldX, int worldY, int worldZ, Block block) {
        setBlock(worldX, worldY, worldZ, block, (byte) 0);
    }

    public void setBlock(int worldX, int worldY, int worldZ, Block block, byte data) {
        int chunkX = (int) Math.floor((double) worldX / Chunk.SIZE);
        int chunkZ = (int) Math.floor((double) worldZ / Chunk.SIZE);

        Chunk chunk = getChunk(chunkX, chunkZ);
        if (chunk == null) {
            return;
        }

        int localX = Math.floorMod(worldX, Chunk.SIZE);
        int localZ = Math.floorMod(worldZ, Chunk.SIZE);

        chunk.set(localX, worldY, localZ, block);
        chunk.setData(localX, worldY, localZ, data);
        chunk.rebuild();
    }

    public boolean hittingBox(Vector3 point) {
        int x = Math.round(point.x);
        int y = Math.round(point.y) - 1;
        int z = Math.round(point.z);

        return isBlock(x, y, z) || isBlock(x, y - 1, z);
    }

    /*
    public void editBoxByRayCast(Vector3 startPoint, Vector3 direction, Block block) {
        int lastPointX = 0;
        int lastPointY = 0;
        int lastPointZ = 0;

        for (int i = 1; i < 100; i++) {
            Vector3 tmpStart = new Vector3(startPoint);
            Vector3 tmpDirection = new Vector3(direction);
            tmpDirection.nor();
            tmpDirection.scl(i);
            Vector3 line = tmpStart.add(tmpDirection);

            int x = Math.round(line.x);
            int y = Math.round(line.y);
            int z = Math.round(line.z);

            if (getBlock(x, y, z) != null) {
                if (block != null) {
                    setBlock(lastPointX, lastPointY, lastPointZ, block);
                } else {
                    setBlock(x, y, z, null);
                }
                break;
            }

            lastPointX = x;
            lastPointY = y;
            lastPointZ = z;
        }
    }
     */

    public void editBoxByRayCast(Vector3 start, Vector3 direction, Block block) {
        direction.nor();

        int x = (int) Math.floor(start.x);
        int y = (int) Math.floor(start.y);
        int z = (int) Math.floor(start.z);

        int stepX = (direction.x > 0) ? 1 : -1;
        int stepY = (direction.y > 0) ? 1 : -1;
        int stepZ = (direction.z > 0) ? 1 : -1;

        float tMaxX = intBound(start.x, direction.x);
        float tMaxY = intBound(start.y, direction.y);
        float tMaxZ = intBound(start.z, direction.z);

        float tDeltaX = stepX / direction.x;
        float tDeltaY = stepY / direction.y;
        float tDeltaZ = stepZ / direction.z;

        int lastX = x, lastY = y, lastZ = z;

        for (int i = 0; i < 100; i++) {
            if (getBlock(x, y, z) != null) {
                Vector3 hitPoint = start.cpy().add(direction.cpy().scl(Math.min(tMaxX, Math.min(tMaxY, tMaxZ))));
                float localY = hitPoint.y - (float)Math.floor(hitPoint.y);
                boolean hitUpper = localY > 0.5f;


                if (block != null) {
                    byte blockData = 0;
                    if (block.hasTag(BlockTags.SLAB)) {
                        if (getBlock(x, y, z) == block) {
                            setBlock(x, y, z, block, SlabModel.FULL);
                            return;
                        } else {
                            blockData = hitUpper ? SlabModel.UPPER : SlabModel.LOWER;
                        }
                    }

                    setBlock(lastX, lastY, lastZ, block, blockData);
                } else {
                    setBlock(x, y, z, null);
                }
                break;
            }

            lastX = x;
            lastY = y;
            lastZ = z;

            if (tMaxX < tMaxY) {
                if (tMaxX < tMaxZ) {
                    x += stepX;
                    tMaxX += tDeltaX;
                } else {
                    z += stepZ;
                    tMaxZ += tDeltaZ;
                }
            } else {
                if (tMaxY < tMaxZ) {
                    y += stepY;
                    tMaxY += tDeltaY;
                } else {
                    z += stepZ;
                    tMaxZ += tDeltaZ;
                }
            }
        }
    }

    private float intBound(float s, float ds) {
        if (ds == 0) {
            return Float.POSITIVE_INFINITY;
        } else if (ds > 0) {
            return (float) ((Math.floor(s + 1) - s) / ds);
        } else {
            return (float) ((s - Math.floor(s)) / -ds);
        }
    }


    private boolean isChunkLoaded(int chunkX, int chunkZ) {
        return getChunk(chunkX, chunkZ) != null;
    }

    private void loadChunksAroundPlayer() {
        int playerChunkX = (int) Math.floor(playerPosition.x / Chunk.SIZE);
        int playerChunkZ = (int) Math.floor(playerPosition.z / Chunk.SIZE);

        for (int x = -renderDistance; x <= renderDistance; x++) {
            for (int z = -renderDistance; z <= renderDistance; z++) {
                int chunkX = playerChunkX + x;
                int chunkZ = playerChunkZ + z;

                if (!isChunkLoaded(chunkX, chunkZ)) {
                    Chunk newChunk = new Chunk(store.getChunkStore(), generator, chunkX, chunkZ);
                    loadedChunks.add(newChunk);
                }
            }
        }

        unloadChunksOutsideRenderDistance(playerChunkX, playerChunkZ);
    }


    private void unloadChunksOutsideRenderDistance(int playerChunkX, int playerChunkZ) {
        ArrayList<Chunk> toUnload = new ArrayList<>();

        for (Chunk chunk : loadedChunks) {
            int distX = Math.abs(chunk.getChunkX() - playerChunkX);
            int distZ = Math.abs(chunk.getChunkZ() - playerChunkZ);

            if (distX > renderDistance || distZ > renderDistance) {
                toUnload.add(chunk);
            }
        }

        for (Chunk chunk : toUnload) {
            chunk.dispose(store.getChunkStore());
            loadedChunks.remove(chunk);
        }
    }

    public void render(ModelBatch batch, Environment environment, Camera camera) {
        for (Chunk chunk : loadedChunks) {
            chunk.render(batch, environment, camera);
        }
    }

    public void dispose() {
        for (Chunk chunk : loadedChunks) {
            chunk.dispose(store.getChunkStore());
        }
        store.dispose();
    }

    @Override
    public void addDebugLines(DebugOverlay r, BitmapFont font) {
        r.text(String.format("Chunks loaded: %d", loadedChunks.size()), font);
        r.text(String.format("Seed: %d", seed), font);
    }
}
