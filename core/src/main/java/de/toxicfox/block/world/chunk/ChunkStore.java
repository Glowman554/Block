package de.toxicfox.block.world.chunk;

import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.generator.ChunkGenerator;

import javax.security.sasl.SaslException;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChunkStore {
    private final ExecutorService pool = Executors.newFixedThreadPool(8);

    private Connection connection;

    public ChunkStore(File chunkFile, boolean persistWorld) {


        try {

            if (persistWorld) {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + chunkFile.getPath());

            createTables();
        }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void scheduleStore(Chunk chunk) {
        if (connection == null) {
            return;
        }

        pool.submit(() -> {
            System.out.printf("[%d, %d] Saving chunk...%n", chunk.getChunkX(), chunk.getChunkZ());

            int size = Chunk.SIZE * Chunk.SIZE * Chunk.HEIGHT;
            byte[] blockIds = new byte[size];
            byte[] blockData = new byte[size];

            int index = 0;
            for (int y = 0; y < Chunk.HEIGHT; y++) {
                for (int z = 0; z < Chunk.SIZE; z++) {
                    for (int x = 0; x < Chunk.SIZE; x++) {
                        Block block = chunk.get(x, y, z);
                        blockIds[index] = (block == null) ? 0 : block.getNumericId();
                        blockData[index] = chunk.getData(x, y, z);
                        index++;
                    }
                }
            }

            String sql = "INSERT INTO chunks (chunkX, chunkZ, blockIds, blockData) " +
                "VALUES (?, ?, ?, ?) " +
                "ON CONFLICT(chunkX, chunkZ) DO UPDATE SET blockIds = excluded.blockIds, blockData = excluded.blockData";

            update(sql, chunk.getChunkX(), chunk.getChunkZ(), blockIds, blockData);
        });
    }


    public void scheduleLoadOrGenerate(Chunk chunk, ChunkGenerator generator) {
        pool.submit(() -> {
            if (!(connection != null &&  loadChunk(chunk))) {
                generator.generateChunk(chunk);
            }
        });
    }

    private boolean loadChunk(Chunk chunk) {
        String sqlIds = "SELECT blockIds FROM chunks WHERE chunkX = ? AND chunkZ = ?";
        String sqlData = "SELECT blockData FROM chunks WHERE chunkX = ? AND chunkZ = ?";

        List<byte[]> idsResult = query(sqlIds, rs -> {
            try {
                return rs.getBytes("blockIds");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, chunk.getChunkX(), chunk.getChunkZ());

        if (idsResult.isEmpty()) {
            return false;
        }

        byte[] blockIds = idsResult.get(0);

        List<byte[]> dataResult = query(sqlData, rs -> {
            try {
                return rs.getBytes("blockData");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }, chunk.getChunkX(), chunk.getChunkZ());

        byte[] blockData = dataResult.get(0);

        int index = 0;
        for (int y = 0; y < Chunk.HEIGHT; y++) {
            for (int z = 0; z < Chunk.SIZE; z++) {
                for (int x = 0; x < Chunk.SIZE; x++) {
                    byte id = blockIds[index];
                    byte data = blockData[index];
                    chunk.set(x, y, z, id == 0 ? null : Block.fromNumericId(id));
                    chunk.setData(x, y, z, data);
                    index++;
                }
            }
        }

        chunk.rebuild();
        return true;
    }


    public void dispose() {
        pool.shutdown();
    }


    private void createTables() {
        update("""
            CREATE TABLE IF NOT EXISTS chunks (
            chunkX INTEGER,
            chunkZ INTEGER,
            blockIds BLOB,
            blockData BLOB,
            PRIMARY KEY (chunkX, chunkZ))
            """);
    }


    public void update(String query, Object... args) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    statement.setObject(i + 1, args[i]);
                }
            }

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private  <T> List<T> query(String query, QueryListMapper<T> mapper, Object... args) {
        List<T> list = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if (args != null) {
                for (int i = 0; i < args.length; i++) {
                    statement.setObject(i + 1, args[i]);
                }
            }

            ResultSet hasResult = statement.executeQuery();

            while (hasResult.next()) {
                list.add(mapper.apply(hasResult));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    private interface QueryListMapper<T> {
        T apply(ResultSet rs);
    }
}
