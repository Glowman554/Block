package de.toxicfox.block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import de.toxicfox.block.debug.DebugOverlay;
import de.toxicfox.block.world.World;
import de.toxicfox.block.world.chunk.block.Block;

public class InputController extends FirstPersonCameraController implements DebugOverlay.DataProvider {
    private final World world;
    private final boolean touchMode;

    private int selectedBlockIndex = 0;
    private final Block[] blocks = Block.values();

    public InputController(Camera camera, World world, boolean touchMode) {
        super(camera);
        this.world = world;
        this.touchMode = touchMode;

        setDegreesPerPixel(0.3f);
        setVelocity(40);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
        } else if (keycode == Input.Keys.UP) {
            if (selectedBlockIndex < blocks.length - 1) {
                selectedBlockIndex++;
            }
        } else if (keycode == Input.Keys.DOWN) {
            if (selectedBlockIndex > 1) {
                selectedBlockIndex--;
            }
        }

        return super.keyDown(keycode);
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        touchDragged(screenX, screenY, 0);
        return super.mouseMoved(screenX, screenY);
    }


    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (button == 0 && !touchMode) {
            world.editBoxByRayCast(camera.position, camera.direction, blocks[selectedBlockIndex]);
        } else if (button == 1 && !touchMode) {
            world.editBoxByRayCast(camera.position, camera.direction, null);
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void addDebugLines(DebugOverlay r, BitmapFont font) {
        r.text(String.format("Selected block: %s", blocks[selectedBlockIndex].getId()), font);
    }
}
