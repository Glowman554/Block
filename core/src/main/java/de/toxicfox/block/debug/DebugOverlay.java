package de.toxicfox.block.debug;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class DebugOverlay {
    private final ArrayList<DataProvider> provider = new ArrayList<>();
    private final SpriteBatch batch;
    private int lines = 0;

    public DebugOverlay(SpriteBatch batch) {
        this.batch = batch;
    }

    public void register(DataProvider p) {
        provider.add(p);
    }

    public void render(SpriteBatch batch, BitmapFont font) {
        lines = 0;

        for (var p : provider) {
            p.addDebugLines(this, font);
        }
    }

    public void text(String msg, BitmapFont font) {
        lines++;
        font.draw(batch, msg, 10, Gdx.graphics.getHeight() - lines * 30);
    }

    public interface DataProvider {
        void addDebugLines(DebugOverlay r, BitmapFont font);
    }
}
