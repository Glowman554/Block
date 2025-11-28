package de.toxicfox.block;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import de.toxicfox.block.debug.DebugOverlay;
import de.toxicfox.block.debug.FPSOverlay;
import de.toxicfox.block.debug.GLProfilerOverlay;
import de.toxicfox.block.debug.PositionOverlay;
import de.toxicfox.block.texture.DynamicAtlas;
import de.toxicfox.block.world.World;
import de.toxicfox.block.world.chunk.Chunk;
import de.toxicfox.block.world.chunk.block.Block;


public class MainGame extends ApplicationAdapter {
    private final int corsairSize = 40;
    private PerspectiveCamera camera;
    private ModelBatch modelBatch;
    private SpriteBatch spriteBatch;
    private BitmapFont font;
    private Texture corsair;
    private Environment environment;
    private World world;
    private InputController inputController;
    private DebugOverlay debugOverlay;
    private TouchInputController touchInputController;

    private final Configuration configuration;

    public MainGame(Configuration configuration) {
        this.configuration = configuration;
    }

    // TODO:
    //       sounds
    //       add flowers and proper berries

    @Override
    public void create() {
        modelBatch = new ModelBatch();
        spriteBatch = new SpriteBatch();
        font = new BitmapFont(Gdx.files.internal("font/Calibri.fnt"), false);
        corsair = new Texture(Gdx.files.internal("corsair.png"));

        camera = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.position.set(0, Chunk.HEIGHT, 10f);
        camera.near = 1;
        camera.far = 1000;
        camera.update();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.5f));
        environment.add(new DirectionalLight().set(0.2f, 0.2f, 0.2f, 1f, 0.8f, 0.5f));

        Block.create();
        // DynamicAtlas.BLOCK_ATLAS.saveAtlas("../debug", "block_atlas.png");
        world = new World(configuration.renderDistance(), configuration.persistWorld(), configuration.worldFolder());


        inputController = new InputController(camera, world, configuration.touchMode());

        if (configuration.touchMode()) {
            touchInputController = new TouchInputController(world, camera);
        }

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputController);
        if (configuration.touchMode()) {
            inputMultiplexer.addProcessor(touchInputController.getStage());
        }
        Gdx.input.setInputProcessor(inputMultiplexer);
        if (configuration.captureCursor()) {
            Gdx.input.setCursorCatched(true);
        }

        debugOverlay = new DebugOverlay(spriteBatch);
        debugOverlay.register(world);
        debugOverlay.register(new FPSOverlay());
        debugOverlay.register(new GLProfilerOverlay());
        debugOverlay.register(inputController);
        debugOverlay.register(new PositionOverlay(camera));
    }

    public void update() {
        inputController.update();
        if (configuration.touchMode()) {
            touchInputController.update();
        }

        world.update(camera.position);
    }

    @Override
    public void render() {
        update();

        Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

        modelBatch.begin(camera);
        world.render(modelBatch, environment, camera);
        modelBatch.end();

        float corsairX = (Gdx.graphics.getWidth() - corsairSize) / 2f;
        float corsairY = (Gdx.graphics.getHeight() - corsairSize) / 2f;

        spriteBatch.begin();
        debugOverlay.render(spriteBatch, font);
        spriteBatch.draw(corsair, corsairX, corsairY, corsairSize, corsairSize);
        spriteBatch.end();

        if (configuration.touchMode()) {
            touchInputController.render();
        }

    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        spriteBatch.dispose();
        font.dispose();
        Block.dispose();
        DynamicAtlas.BLOCK_ATLAS.dispose();
        world.dispose();
        if (configuration.touchMode()) {
            touchInputController.dispose();
        }
    }
}


