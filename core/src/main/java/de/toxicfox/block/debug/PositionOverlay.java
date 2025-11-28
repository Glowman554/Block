package de.toxicfox.block.debug;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import de.toxicfox.block.world.chunk.Chunk;

public class PositionOverlay implements DebugOverlay.DataProvider {
    private final Camera camera;

    public PositionOverlay(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void addDebugLines(DebugOverlay r, BitmapFont font) {
        int chunkX = (int) (camera.position.x / Chunk.SIZE);
        int chunkY = (int) (camera.position.z / Chunk.SIZE);

        int playerX = (int) (camera.position.x);
        int playerY = (int) (camera.position.y);
        int playerZ = (int) (camera.position.z);

        r.text(String.format("Player: %d, %d, %d", playerX, playerY, playerZ), font);
        r.text(String.format("Chunk: %d, %d", chunkX, chunkY), font);
    }
}
