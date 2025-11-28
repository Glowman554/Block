package de.toxicfox.block.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class FPSOverlay implements DebugOverlay.DataProvider {
    @Override
    public void addDebugLines(DebugOverlay r, BitmapFont font) {
        int frameRate = Gdx.graphics.getFramesPerSecond();
        r.text(String.format("FPS: %d", frameRate), font);
    }
}
