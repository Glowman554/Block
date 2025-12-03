package de.toxicfox.block.texture;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.FloatAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;

public class DynamicAtlas {
    public static final DynamicAtlas BLOCK_ATLAS = new DynamicAtlas(20, 7 * 7);

    private final int tileSize;
    private final int tilesX;
    private final int tilesY;

    private final Pixmap pixmap;
    private final Texture texture;

    private int nextX = 0;
    private int nextY = 0;

    private Material atlasMaterial;

    public DynamicAtlas(int tileSize, int capacity) {
        this.tileSize = tileSize;

        int pageSize = (int) (Math.sqrt(capacity) * tileSize) + 1;
        this.pixmap = new Pixmap(pageSize, pageSize, Pixmap.Format.RGBA8888);
        this.texture = new Texture(pixmap);

        this.tilesX = pageSize / tileSize;
        this.tilesY = pageSize / tileSize;
    }

    public TextureRegion addTexture(Pixmap pixmap, boolean disposeAfter) {
        if (pixmap.getWidth() != tileSize || pixmap.getHeight() != tileSize) {
            throw new IllegalArgumentException("Texture must be " + tileSize + "x" + tileSize + "!");
        }

        if (nextY >= tilesY) {
            throw new IllegalStateException("Atlas is full!");
        }

        int x = nextX * tileSize;
        int y = nextY * tileSize;

        this.pixmap.drawPixmap(pixmap, x, y);
        texture.draw(this.pixmap, 0, 0);

        TextureRegion region = new TextureRegion(texture, x, y, tileSize, tileSize);

        nextX++;
        if (nextX >= tilesX) {
            nextX = 0;
            nextY++;
        }

        if (disposeAfter) {
            pixmap.dispose();
        }

        return region;
    }

    public Material getAtlasMaterial() {
        if (atlasMaterial == null) {
            atlasMaterial = new Material();
            atlasMaterial.set(TextureAttribute.createDiffuse(texture));
            atlasMaterial.set(new BlendingAttribute(true, 1f));
            atlasMaterial.set(new FloatAttribute(FloatAttribute.AlphaTest, 0.5f));
        }
        return atlasMaterial;
    }

    public void saveAtlas(String directory, String filename) {
        FileHandle dir = Gdx.files.local(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Pixmap copy = new Pixmap(pixmap.getWidth(), pixmap.getHeight(), Pixmap.Format.RGBA8888);
        copy.drawPixmap(pixmap, 0, 0);

        FileHandle file = dir.child(filename);
        PixmapIO.writePNG(file, copy);
        copy.dispose();

        System.out.printf("Saved atlas to '%s'%n", file.path());
    }

    public void dispose() {
        texture.dispose();
        pixmap.dispose();
    }
}
