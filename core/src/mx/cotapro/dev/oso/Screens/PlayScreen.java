package mx.cotapro.dev.oso.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;

import mx.cotapro.dev.Tutifruti;
import mx.cotapro.dev.oso.Controller;
import mx.cotapro.dev.oso.Oso;

import mx.cotapro.dev.oso.Scenes.Hud;
import mx.cotapro.dev.oso.Sprites.osos;
import mx.cotapro.dev.oso.Tools.B2WorldCreator;
import mx.cotapro.dev.oso.Tools.WorldContactListener;

public class PlayScreen implements Screen {
    private final Tutifruti game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private Hud hud;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private World world;
    private Box2DDebugRenderer b2dr;
    private osos player;
	public static AssetManager manager;
    Controller controller;

    public PlayScreen(final Tutifruti game){
		manager = new AssetManager();
        manager.load("oso/jump.wav", Sound.class);
        manager.finishLoading();
		this.game = game;
        gamecam = new OrthographicCamera();
		gamecam.rotate(90f);
		gamePort = new FitViewport(Oso.V_HEIGHT/Oso.PPM, Oso.V_WIDTH/Oso.PPM, gamecam);
        hud = new Hud(game.batch);
        mapLoader = new TmxMapLoader();
        map = mapLoader.load("oso/Nivel1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1/Oso.PPM);
        gamecam.position.set(gamePort.getWorldHeight()/2, gamePort.getWorldWidth()/2, 0);
        world = new World(new Vector2(0,-10), true);
        b2dr = new Box2DDebugRenderer();
        new B2WorldCreator(world, map);
        player = new osos(world);
        world.setContactListener(new WorldContactListener());
        controller = new Controller(game, hud.stage.getViewport());
    }

    @Override
    public void show() {

    }


    public void handleInput(float dt){
        if(controller.isRigthPressed())
            player.b2body.setLinearVelocity(new Vector2(1, player.b2body.getLinearVelocity().y));
        else if (controller.isLeftPressed())
            player.b2body.setLinearVelocity(new Vector2(-1, player.b2body.getLinearVelocity().y));
        else
            player.b2body.setLinearVelocity(new Vector2(0, player.b2body.getLinearVelocity().y));
        if (controller.isUpPressed() && player.b2body.getLinearVelocity().y == 0) {
            player.b2body.applyLinearImpulse(new Vector2(0, 5f), player.b2body.getWorldCenter(), true);
			manager.get("oso/jump.wav", Sound.class).play();
		}

        /*if(Gdx.input.isKeyJustPressed(Input.Keys.UP)){
            player.b2body.applyLinearImpulse(new Vector2(0, 4f), player.b2body.getWorldCenter(), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)&&player.b2body.getLinearVelocity().x<=2)
            player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)&&player.b2body.getLinearVelocity().x>=-2)
            player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);*/
    }

    public void update(float dt){
        handleInput(dt);

        world.step(1/60f, 6, 2);

		hud.update(dt);
        controller.draw();

        gamecam.position.x=player.b2body.getPosition().x;

        gamecam.update();
        renderer.setView(gamecam);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();
        update(delta);

        //b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();

        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
    	hud.stage.getViewport().update(width, height);
	}

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        hud.dispose();
		manager.dispose();
    }
}
