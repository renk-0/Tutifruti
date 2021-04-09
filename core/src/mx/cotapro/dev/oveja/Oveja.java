package mx.cotapro.dev.oveja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.cotapro.dev.Tutifruti;
import mx.cotapro.dev.selectscreen.Select;

public class Oveja implements Screen {
	private final Tutifruti game;
	private Viewport view;
	private World world;
	private AssetManager manager;
	private Color bgcolor;
	private Stage stage;
	private Maquina maquina;
	private ObeeJa obveja;

	public Oveja(final Tutifruti game) {
		this.game = game;
		bgcolor = new Color(0xacace9ff);
		// bgcolor = new Color(0x000000ff);
		world = new World(
				new Vector2(0, -98.1f), true);
		view = new ScalingViewport(Scaling.fit, 400, 700);
		stage = new Stage(view, game.batch);
		Gdx.input.setInputProcessor(stage);
		manager = new AssetManager();
		loadTextures();
		maquina =  new Maquina(world, manager);
		obveja = new ObeeJa(manager, world);
		stage.addActor(obveja.cuerpo);
		stage.addActor(obveja.cabeza);
		stage.addActor(maquina.actor);
	}

	public void loadTextures() {
		manager.load("oveja/cabeza.png", Texture.class);
		manager.load("oveja/Maquina.png", Texture.class);
		manager.load("oveja/Oveja.png", Texture.class);
		manager.load("oveja/Pelo.png", Texture.class);
		manager.load("oveja/bee.mp3", Sound.class);
		manager.finishLoading();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(bgcolor);
		stage.act();
		stage.draw();
		world.step(1f/60f, 10, 10);
		game.batch.setProjectionMatrix(view.getCamera().combined);
		game.batch.begin();
		for(int i = 0; i < obveja.pelos.size; i++) {
			game.batch.draw(obveja.pelo,
					obveja.pelos.get(i).getPosition().x - 30, 
					obveja.pelos.get(i).getPosition().y - 30,
					60, 60);
		}
		obveja.act();
		game.batch.end();
		view.getCamera().update();
		if(obveja.pelos.size <= 0) {
			game.setScreen(new Select(game));
			dispose();
		}
	}
	
	@Override
	public void resize(int width, int height) {
		view.update(width, height);
	}
	
	@Override
	public void dispose() {
		world.dispose();
		manager.dispose();
		stage.dispose();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void show() {
		Sound bee = manager.get("oveja/bee.mp3", Sound.class);
		bee.play();
	}
	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}
}
