package de.toxicfox.block.world.chunk.block;

public class BlockData {
    private final Block block;
    private final byte data;

    public BlockData(Block block, byte data) {
        this.block = block;
        this.data = data;
    }

    public BlockData(Block block) {
        this.block = block;
        this.data = 0;
    }

    public Block getBlock() {
        return block;
    }

    public byte getData() {
        return data;
    }
}
