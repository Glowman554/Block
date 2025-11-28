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

public class TouchInputController {
    private final Skin skin;
    private final Stage stage;


    private final float scale = 2f;

    public TouchInputController(World world, Camera camera) {
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
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector3 tmp = new Vector3();
                tmp.set(camera.direction).nor().scl(1);
                camera.position.add(tmp);
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
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Vector3 tmp = new Vector3();
                tmp.set(camera.direction).nor().scl(-1);
                camera.position.add(tmp);
                return true;
            }
        });
        stage.addActor(backward);
    }

    public void update() {
        stage.act();
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
