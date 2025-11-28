package de.toxicfox.block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g3d.utils.FirstPersonCameraController;
import de.toxicfox.block.debug.DebugOverlay;
import de.toxicfox.block.world.World;
import de.toxicfox.block.world.chunk.block.Block;

import java.util.HashMap;

public class InputController extends FirstPersonCameraController implements DebugOverlay.DataProvider {
    private final World world;
    private final boolean touchMode;

    private final HashMap<Integer, Block> blockMapping = new HashMap<>() {{
        put(Input.Keys.NUM_1, Block.DIRT);
        put(Input.Keys.NUM_2, Block.GRASS);
        put(Input.Keys.NUM_3, Block.LEAVES);
        put(Input.Keys.NUM_4, Block.STONE);
        put(Input.Keys.NUM_5, Block.SAND);
        put(Input.Keys.NUM_6, Block.WOOD);
        put(Input.Keys.NUM_7, Block.GLASS);
        put(Input.Keys.NUM_8, Block.WATER);
        put(Input.Keys.NUM_9, Block.ICE);
        put(Input.Keys.NUM_0, Block.LOG);
    }};
    private Block currentBlock = Block.DIRT;

    public InputController(Camera camera, World world, boolean touchMode) {
        super(camera);
        this.world = world;
        this.touchMode = touchMode;

        setDegreesPerPixel(0.3f);
        setVelocity(40);
    }

    @Override
    public boolean keyDown(int keycode) {
        if (blockMapping.containsKey(keycode)) {
            currentBlock = blockMapping.get(keycode);
        } else if (keycode == Input.Keys.ESCAPE) {
            Gdx.app.exit();
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
            world.editBoxByRayCast(camera.position, camera.direction, currentBlock);
        } else if (button == 1 && !touchMode) {
            world.editBoxByRayCast(camera.position, camera.direction, null);
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public void addDebugLines(DebugOverlay r, BitmapFont font) {
        r.text(String.format("Selected block: %s", currentBlock.getId()), font);
    }
}
