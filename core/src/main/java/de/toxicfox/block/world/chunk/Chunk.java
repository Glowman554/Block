package de.toxicfox.block.world.chunk;

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
import de.toxicfox.block.world.chunk.generator.ChunkGenerator;

public class Chunk {
    public static final int SIZE = 32;
    public static final int HEIGHT = 64;

    private final int chunkX;
    private final int chunkZ;

    private final Block[][][] blocks = new Block[SIZE][HEIGHT][SIZE];
    private final Vector3 position = new Vector3();
    private Model model;
    private ModelInstance instance;
    private boolean rebuildNeeded = false;

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

    private void setPosition(float x, float y, float z) {
        position.set(x, y, z);
        if (instance != null) {
            instance.transform.setToTranslation(position);
        }
    }

    private void build() {
        if (model != null) {
            model.dispose();
        }

        int attr = VertexAttributes.Usage.Position |
            VertexAttributes.Usage.Normal |
            VertexAttributes.Usage.TextureCoordinates;

        ModelBuilder mb = new ModelBuilder();
        mb.begin();
        MeshPartBuilder mpb = mb.part("chunk", GL20.GL_TRIANGLES, attr, DynamicAtlas.BLOCK_ATLAS.getAtlasMaterial());


        for (int x = 0; x < SIZE; x++) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int z = 0; z < SIZE; z++) {
                    Block b = blocks[x][y][z];

                    if (b == null) {
                        continue;
                    }

                    b.getModel().addVisibleFaces(this, mpb, b, x, y, z, attr);
                }
            }
        }

        model = mb.end();
        instance = new ModelInstance(model);
        instance.transform.setToTranslation(position);
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

    public void render(ModelBatch batch, Environment env) {
        if (rebuildNeeded) {
            build();
            rebuildNeeded = false;
        }

        if (instance != null) {
            batch.render(instance, env);
        }
    }

    public void dispose(ChunkStore store) {
        store.scheduleStore(this);

        if (model != null) {
            model.dispose();
        }
    }
}
