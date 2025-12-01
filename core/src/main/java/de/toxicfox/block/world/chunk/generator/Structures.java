package de.toxicfox.block.world.chunk.generator;

import de.toxicfox.block.world.chunk.block.Block;
import de.toxicfox.block.world.chunk.block.BlockData;
import de.toxicfox.block.world.chunk.block.models.SlabModel;
import de.toxicfox.block.world.chunk.block.models.StairModel;

public class Structures {

    public static final BlockData[][][] treeStructure = {
        {
            {null, null, null},
            {null, new BlockData(Block.LOG), null},
            {null, null, null},
        },
        {
            {null, null, null},
            {null, new BlockData(Block.LOG), null},
            {null, null, null},
        },
        {
            {null, null, null},
            {null, new BlockData(Block.LOG), null},
            {null, null, null},
        },
        {
            {new BlockData(Block.LEAVES), new BlockData(Block.LEAVES), new BlockData(Block.LEAVES)},
            {new BlockData(Block.LEAVES), new BlockData(Block.LOG), new BlockData(Block.LEAVES)},
            {new BlockData(Block.LEAVES), new BlockData(Block.LEAVES), new BlockData(Block.LEAVES)},
        },
        {
            {new BlockData(Block.LEAVES), new BlockData(Block.LEAVES), new BlockData(Block.LEAVES)},
            {new BlockData(Block.LEAVES), new BlockData(Block.LEAVES), new BlockData(Block.LEAVES)},
            {new BlockData(Block.LEAVES), new BlockData(Block.LEAVES), new BlockData(Block.LEAVES)},
        },
        {
            {null, new BlockData(Block.LEAVES), null},
            {new BlockData(Block.LEAVES), new BlockData(Block.LEAVES), new BlockData(Block.LEAVES)},
            {null, new BlockData(Block.LEAVES), null},
        },
    };
    public static final BlockData[][][] houseStructure = {
        {
            {new BlockData(Block.STONE), new BlockData(Block.STONE), new BlockData(Block.STONE), new BlockData(Block.STONE), new BlockData(Block.STONE)},
            {new BlockData(Block.STONE), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.STONE)},
            {new BlockData(Block.STONE), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.STONE)},
            {new BlockData(Block.STONE), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.STONE)},
            {new BlockData(Block.STONE), new BlockData(Block.STONE), new BlockData(Block.STONESTAIR, StairModel.WEST), new BlockData(Block.STONE), new BlockData(Block.STONE)},
        },
        {
            {new BlockData(Block.LOG), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.LOG)},
            {new BlockData(Block.WOOD), null, null, null, new BlockData(Block.WOOD)},
            {new BlockData(Block.WOOD), null, null, null, new BlockData(Block.WOOD)},
            {new BlockData(Block.WOOD), null, null, null, new BlockData(Block.WOOD)},
            {new BlockData(Block.LOG), new BlockData(Block.WOOD), null, new BlockData(Block.WOOD), new BlockData(Block.LOG)},
        },
        {
            {new BlockData(Block.LOG), new BlockData(Block.WOOD), new BlockData(Block.GLASS), new BlockData(Block.WOOD), new BlockData(Block.LOG)},
            {new BlockData(Block.WOOD), null, null, null, new BlockData(Block.WOOD)},
            {new BlockData(Block.GLASS), null, null, null, new BlockData(Block.GLASS)},
            {new BlockData(Block.WOOD), null, null, null, new BlockData(Block.WOOD)},
            {new BlockData(Block.LOG), new BlockData(Block.WOOD), null, new BlockData(Block.WOOD), new BlockData(Block.LOG)},
        },
        {
            {new BlockData(Block.LOG), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.LOG)},
            {new BlockData(Block.WOOD), null, null, null, new BlockData(Block.WOOD)},
            {new BlockData(Block.WOOD), null, null, null, new BlockData(Block.WOOD)},
            {new BlockData(Block.WOOD), null, null, null, new BlockData(Block.WOOD)},
            {new BlockData(Block.LOG), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.LOG)},
        },
        {
            {new BlockData(Block.STONE), new BlockData(Block.STONE), new BlockData(Block.STONE), new BlockData(Block.STONE), new BlockData(Block.STONE)},
            {new BlockData(Block.STONE), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.STONE)},
            {new BlockData(Block.STONE), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.STONE)},
            {new BlockData(Block.STONE), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.WOOD), new BlockData(Block.STONE)},
            {new BlockData(Block.STONE), new BlockData(Block.STONE), new BlockData(Block.STONE), new BlockData(Block.STONE), new BlockData(Block.STONE)},
        }
    };

    public static final BlockData[][][] wellStructure = {
        {
            {new BlockData(Block.LOG), new BlockData(Block.STONE), new BlockData(Block.LOG)},
            {new BlockData(Block.STONE), new BlockData(Block.WATER), new BlockData(Block.STONE)},
            {new BlockData(Block.LOG), new BlockData(Block.STONE), new BlockData(Block.LOG)},
        },
        {
            {new BlockData(Block.LOG), null, new BlockData(Block.LOG)},
            {null, null, null},
            {new BlockData(Block.LOG), null, new BlockData(Block.LOG)},
        },
        {
            {new BlockData(Block.LOG), null, new BlockData(Block.LOG)},
            {null, null, null},
            {new BlockData(Block.LOG), null, new BlockData(Block.LOG)},
        },
        {
            {new BlockData(Block.STONESLAB, SlabModel.LOWER), new BlockData(Block.STONESLAB, SlabModel.LOWER), new BlockData(Block.STONESLAB, SlabModel.LOWER)},
            {new BlockData(Block.STONESLAB, SlabModel.LOWER), new BlockData(Block.STONE), new BlockData(Block.STONESLAB, SlabModel.LOWER)},
            {new BlockData(Block.STONESLAB, SlabModel.LOWER), new BlockData(Block.STONESLAB, SlabModel.LOWER), new BlockData(Block.STONESLAB, SlabModel.LOWER)},
        }
    };


}
