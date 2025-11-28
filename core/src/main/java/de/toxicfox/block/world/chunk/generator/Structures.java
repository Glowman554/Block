package de.toxicfox.block.world.chunk.generator;

import de.toxicfox.block.world.chunk.block.Block;

public class Structures {

    public static final Block[][][] treeStructure = {
        {
            {null, null, null},
            {null, Block.LOG, null},
            {null, null, null},
        },
        {
            {null, null, null},
            {null, Block.LOG, null},
            {null, null, null},
        },
        {
            {null, null, null},
            {null, Block.LOG, null},
            {null, null, null},
        },
        {
            {Block.LEAVES, Block.LEAVES, Block.LEAVES},
            {Block.LEAVES, Block.LOG, Block.LEAVES},
            {Block.LEAVES, Block.LEAVES, Block.LEAVES},
        },
        {
            {Block.LEAVES, Block.LEAVES, Block.LEAVES},
            {Block.LEAVES, Block.LEAVES, Block.LEAVES},
            {Block.LEAVES, Block.LEAVES, Block.LEAVES},
        },
        {
            {null, Block.LEAVES, null},
            {Block.LEAVES, Block.LEAVES, Block.LEAVES},
            {null, Block.LEAVES, null},
        }
    };

    public static final Block[][][] houseStructure = {
        {
            {Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE},
            {Block.STONE, Block.WOOD, Block.WOOD, Block.WOOD, Block.STONE},
            {Block.STONE, Block.WOOD, Block.WOOD, Block.WOOD, Block.STONE},
            {Block.STONE, Block.WOOD, Block.WOOD, Block.WOOD, Block.STONE},
            {Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE},
        },
        {
            {Block.LOG, Block.WOOD, Block.WOOD, Block.WOOD, Block.LOG},
            {Block.WOOD, null, null, null, Block.WOOD},
            {Block.WOOD, null, null, null, Block.WOOD},
            {Block.WOOD, null, null, null, Block.WOOD},
            {Block.LOG, Block.WOOD, null, Block.WOOD, Block.LOG},
        },
        {
            {Block.LOG, Block.WOOD, Block.GLASS, Block.WOOD, Block.LOG},
            {Block.WOOD, null, null, null, Block.WOOD},
            {Block.GLASS, null, null, null, Block.GLASS},
            {Block.WOOD, null, null, null, Block.WOOD},
            {Block.LOG, Block.WOOD, null, Block.WOOD, Block.LOG},
        },
        {
            {Block.LOG, Block.WOOD, Block.WOOD, Block.WOOD, Block.LOG},
            {Block.WOOD, null, null, null, Block.WOOD},
            {Block.WOOD, null, null, null, Block.WOOD},
            {Block.WOOD, null, null, null, Block.WOOD},
            {Block.LOG, Block.WOOD, Block.WOOD, Block.WOOD, Block.LOG},
        },
        {
            {Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE},
            {Block.STONE, Block.WOOD, Block.WOOD, Block.WOOD, Block.STONE},
            {Block.STONE, Block.WOOD, Block.WOOD, Block.WOOD, Block.STONE},
            {Block.STONE, Block.WOOD, Block.WOOD, Block.WOOD, Block.STONE},
            {Block.STONE, Block.STONE, Block.STONE, Block.STONE, Block.STONE},
        }
    };

    public static final Block[][][] wellStructure = {
        {
            {Block.LOG, Block.STONE, Block.LOG},
            {Block.STONE, Block.WATER, Block.STONE},
            {Block.LOG, Block.STONE, Block.LOG},
        },
        {
            {Block.LOG, null, Block.LOG},
            {null, null, null},
            {Block.LOG, null, Block.LOG},
        },
        {
            {Block.LOG, null, Block.LOG},
            {null, null, null},
            {Block.LOG, null, Block.LOG},
        },
        {
            {Block.STONE, Block.STONE, Block.STONE},
            {Block.STONE, Block.STONE, Block.STONE},
            {Block.STONE, Block.STONE, Block.STONE},
        },
    };


}
