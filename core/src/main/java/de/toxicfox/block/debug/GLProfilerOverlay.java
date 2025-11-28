package de.toxicfox.block.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.profiling.GLProfiler;

public class GLProfilerOverlay implements DebugOverlay.DataProvider {

    private final GLProfiler glProfiler;

    public GLProfilerOverlay() {
        glProfiler = new GLProfiler(Gdx.graphics);
        glProfiler.enable();
    }

    @Override
    public void addDebugLines(DebugOverlay r, BitmapFont font) {
        r.text(String.format("Draw calls: %d", glProfiler.getDrawCalls()), font);
        r.text(String.format("Texture binds: %d", glProfiler.getTextureBindings()), font);
        r.text(String.format("Shader switches: %d", glProfiler.getShaderSwitches()), font);

        glProfiler.reset();
    }
}
