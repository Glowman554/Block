package de.toxicfox.block.world.chunk.block;

import de.toxicfox.block.world.chunk.block.models.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum Block {
    GRASS("Grass", (byte) 1, new DefaultModel(), Set.of(BlockTags.FULL_BLOCK)),
    TESTBLOCK("Testblock", (byte) 2, new DefaultModel(), Set.of(BlockTags.FULL_BLOCK)),
    BERRY("Berry", (byte) 3, new CrossPlaneModel(), Set.of()),
    COAL("Coal", (byte) 4, new DefaultModelUniform(), Set.of(BlockTags.FULL_BLOCK)),
    DIRT("Dirt", (byte) 5, new DefaultModelUniform(), Set.of(BlockTags.FULL_BLOCK)),
    GLASS("Glass", (byte) 6, new DefaultModelUniform(), Set.of(BlockTags.TRANSPARENT, BlockTags.FULL_BLOCK)),
    GOLD("Gold", (byte) 7, new DefaultModelUniform(), Set.of(BlockTags.FULL_BLOCK)),
    LEAVES("Leaves", (byte) 8, new DefaultModelUniform(), Set.of(BlockTags.TRANSPARENT, BlockTags.FULL_BLOCK)),
    SAND("Sand", (byte) 9, new DefaultModelUniform(), Set.of(BlockTags.FULL_BLOCK)),
    STONE("Stone", (byte) 10, new DefaultModelUniform(), Set.of(BlockTags.FULL_BLOCK)),
    WATER("Water", (byte) 11, new WaterModel(), Set.of(BlockTags.TRANSPARENT)),
    WOOD("Wood", (byte) 12, new DefaultModelUniform(), Set.of(BlockTags.FULL_BLOCK)),
    FLOWER("Flower", (byte) 13, new CrossPlaneModel(), Set.of()),
    ICE("Ice", (byte) 14, new DefaultModelUniform(), Set.of(BlockTags.TRANSPARENT, BlockTags.FULL_BLOCK)),
    LOG("Log", (byte) 15, new DefaultModel(), Set.of(BlockTags.FULL_BLOCK)),
    WOODSLAB("WoodSlab", (byte) 16, new SlabModel(), Set.of(BlockTags.SLAB)),
    STONESLAB("StoneSlab", (byte) 17, new SlabModel(), Set.of(BlockTags.SLAB)),
    DIRTSLAB("DirtSlab", (byte) 18, new SlabModel(), Set.of(BlockTags.SLAB)),
    TESTSLAB("Testslab", (byte) 19, new SlabModel(), Set.of(BlockTags.SLAB));

    private static final Map<Byte, Block> NUMERIC_ID_MAP = new HashMap<>();

    static {
        for (Block block : values()) {
            NUMERIC_ID_MAP.put(block.numericId, block);
        }
    }

    private final String id;
    private final byte numericId;
    private final BlockModel model;
    private final Set<BlockTags> tags;


    Block(String id, byte numericId, BlockModel model, Set<BlockTags> tags) {
        this.id = id;
        this.numericId = numericId;
        this.model = model;
        this.tags = tags;
    }

    public static void create() {
        for (Block type : values()) {
            type.model.initialize(type.id);
        }
    }

    public static void dispose() {
        for (Block type : values()) {
            type.model.dispose();
        }
    }

    public static Block fromNumericId(byte numericId) {
        return NUMERIC_ID_MAP.get(numericId);
    }

    public String getId() {
        return id;
    }

    public byte getNumericId() {
        return numericId;
    }

    public BlockModel getModel() {
        return model;
    }

    public boolean hasTag(BlockTags tag) {
        return tags.contains(tag);
    }
}
