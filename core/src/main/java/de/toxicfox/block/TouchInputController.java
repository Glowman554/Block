package de.toxicfox.block;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import de.toxicfox.block.world.World;
import de.toxicfox.block.world.chunk.block.Block;

import java.util.ArrayList;

public class TouchInputController {
    private final Skin skin;
    private final Stage stage;

    private boolean forwardPressed = false;
    private boolean backwardPressed = false;
    private final Camera camera;

    private final float scale = 2f;

    public TouchInputController(World world, Camera camera) {
        this.camera = camera;
        skin = new Skin(Gdx.files.internal("ui/uiskin.json"));
        stage = new Stage(new ScreenViewport());

        Button place = new TextButton("Place Block", skin, "default");
        place.setPosition(Gdx.graphics.getWidth() - 350, Gdx.graphics.getHeight() - 100);
        place.setTransform(true);
        place.scaleBy(scale);
        place.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                world.editBoxByRayCast(camera.position, camera.direction, Block.DIRT);
                return true;
            }
        });
        stage.addActor(place);

        Button destroy = new TextButton("Destroy Block", skin, "default");
        destroy.setPosition(Gdx.graphics.getWidth() - 700, Gdx.graphics.getHeight() - 100);
        destroy.setTransform(true);
        destroy.scaleBy(scale);
        destroy.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                world.editBoxByRayCast(camera.position, camera.direction, null);
                return true;
            }
        });
        stage.addActor(destroy);

        Button forward = new TextButton("Forward", skin, "default");
        forward.setPosition(200, 200);
        forward.setTransform(true);
        forward.scaleBy(scale);
        forward.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                forwardPressed = false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                forwardPressed = true;
                return true;
            }
        });
        stage.addActor(forward);

        Button backward = new TextButton("Backward", skin, "default");
        backward.setPosition(200, 100);
        backward.setTransform(true);
        backward.scaleBy(scale);
        backward.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                backwardPressed = false;
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                backwardPressed = true;
                return true;
            }
        });
        stage.addActor(backward);
    }

    public void update() {
        stage.act();

        Vector3 tmp = new Vector3();
        if (forwardPressed) {
            tmp.set(camera.direction).nor().scl(0.5f); // 0.1f is movement speed, tweak as needed
            camera.position.add(tmp);
        }
        if (backwardPressed) {
            tmp.set(camera.direction).nor().scl(-0.5f);
            camera.position.add(tmp);
        }
    }

    public void render() {
        stage.draw();
    }


    public Skin getSkin() {
        return skin;
    }

    public Stage getStage() {
        return stage;
    }

    public void dispose() {
        skin.dispose();
        stage.dispose();
    }
}
