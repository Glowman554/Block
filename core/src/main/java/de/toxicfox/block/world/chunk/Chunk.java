package de.toxicfox.block.world.chunk;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import de.toxicfox.block.texture.DynamicAtlas;
import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.block.BlockTags;
import de.toxicfox.block.world.chunk.generator.ChunkGenerator;

import java.util.ArrayList;

public class Chunk {
    public static final int SIZE = 8;
    public static final int HEIGHT = 64;

    private final int chunkX;
    private final int chunkZ;

    private final Block[][][] blocks = new Block[SIZE][HEIGHT][SIZE];
    private final byte[][][] blockdata = new byte[SIZE][HEIGHT][SIZE];
    private final Vector3 position = new Vector3();
    private Model model;
    private ModelInstance instance;
    private Model transparentModel;
    private ModelInstance transparentInstance;
    private boolean rebuildNeeded = false;

    private final ArrayList<BlockData> transparentBlocks = new ArrayList<>();


    public Chunk(ChunkStore store, ChunkGenerator generator, int chunkX, int worldZ) {
        this.chunkX = chunkX;
        this.chunkZ = worldZ;

        setPosition(chunkX * SIZE, 0, chunkZ * SIZE);

        store.scheduleLoadOrGenerate(this, generator);
    }


    public Block get(int x, int y, int z) {
        if (x < 0 || x >= SIZE || y < 0 || y >= HEIGHT || z < 0 || z >= SIZE) {
            return null;
        }
        return blocks[x][y][z];
    }

    public void set(int x, int y, int z, Block block) {
        if (x < 0 || x >= SIZE || y < 0 || y >= HEIGHT || z < 0 || z >= SIZE) {
            return;
        }
        blocks[x][y][z] = block;
    }

    public byte getData(int x, int y, int z) {
        if (x < 0 || x >= SIZE || y < 0 || y >= HEIGHT || z < 0 || z >= SIZE) {
            return 0;
        }
        return blockdata[x][y][z];
    }

    public void setData(int x, int y, int z, byte data) {
        if (x < 0 || x >= SIZE || y < 0 || y >= HEIGHT || z < 0 || z >= SIZE) {
            return;
        }
        blockdata[x][y][z] = data;
    }

    private void setPosition(float x, float y, float z) {
        position.set(x, y, z);
        if (instance != null) {
            instance.transform.setToTranslation(position);
        }
    }

    public Block adj(int x, int y, int z, int dx, int dy, int dz) {
        int nx = x + dx;
        int ny = y + dy;
        int nz = z + dz;

        if (nx < 0 || ny < 0 || nz < 0 ||
            nx >= SIZE || ny >= HEIGHT || nz >= SIZE) {
            return null;
        }

        return blocks[nx][ny][nz];
    }

    public int getChunkX() {
        return chunkX;
    }

    public int getChunkZ() {
        return chunkZ;
    }

    public void rebuild() {
        rebuildNeeded = true;
    }


    private void buildOpaqueAndStoreTransparent() {
        if (model != null) {
            model.dispose();
        }

        int attr = VertexAttributes.Usage.Position |
            VertexAttributes.Usage.Normal |
            VertexAttributes.Usage.TextureCoordinates;

        ModelBuilder mb = new ModelBuilder();
        mb.begin();
        MeshPartBuilder mpb = mb.part("chunk", GL20.GL_TRIANGLES, attr, DynamicAtlas.BLOCK_ATLAS.getAtlasMaterial());

        transparentBlocks.clear();

        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int z = 0; z < SIZE; z++) {
                    Block b = blocks[x][y][z];
                    if (b == null) continue;

                    if (b.hasTag(BlockTags.TRANSPARENT)) {
                        transparentBlocks.add(new BlockData(b, x, y, z));
                    } else {
                        b.getModel().addVisibleFaces(this, mpb, b, x, y, z, attr);
                    }
                }
            }
        }

        model = mb.end();
        instance = new ModelInstance(model);
        instance.transform.setToTranslation(position);
    }

    private void buildTransparentModel(Camera camera) {
        if (transparentModel != null) {
            transparentModel.dispose();
        }

        int attr = VertexAttributes.Usage.Position |
            VertexAttributes.Usage.Normal |
            VertexAttributes.Usage.TextureCoordinates;

        // Sort transparent blocks by camera distance
        transparentBlocks.sort((a, b) -> {
            float distA = camera.position.dst2(a.getWorldPosition(position));
            float distB = camera.position.dst2(b.getWorldPosition(position));
            return Float.compare(distB, distA);
        });

        ModelBuilder mb = new ModelBuilder();
        mb.begin();
        MeshPartBuilder mpbTransparent = mb.part("chunkTransparent", GL20.GL_TRIANGLES, attr, DynamicAtlas.BLOCK_ATLAS.getAtlasMaterial());

        for (BlockData bd : transparentBlocks) {
            bd.block.getModel().addVisibleFaces(this, mpbTransparent, bd.block, bd.x, bd.y, bd.z, attr);
        }

        transparentModel = mb.end();
        transparentInstance = new ModelInstance(transparentModel);
        transparentInstance.transform.setToTranslation(position);
    }

    public void render(ModelBatch batch, Environment env, Camera camera) {
        if (rebuildNeeded) {
            buildOpaqueAndStoreTransparent();
            rebuildNeeded = false;
        }

        buildTransparentModel(camera);

        if (instance != null) {
            batch.render(instance, env);
        }

        if (transparentInstance != null) {
            batch.render(transparentInstance, env);
        }
    }

        private record BlockData(Block block, int x, int y, int z) {

        private Vector3 getWorldPosition(Vector3 chunkPos) {
                return new Vector3(chunkPos.x + x, chunkPos.y + y, chunkPos.z + z);
            }
        }

    public void dispose(ChunkStore store) {
        store.scheduleStore(this);

        if (model != null) {
            model.dispose();
        }

        if (transparentModel != null) {
            transparentModel.dispose();
        }
    }
}
