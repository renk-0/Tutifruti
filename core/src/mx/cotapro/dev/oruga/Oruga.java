package mx.cotapro.dev.oruga;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import mx.cotapro.dev.Tutifruti;

public class Oruga implements Screen {
	final Tutifruti game;
	private Image sol;
	private Image oruga;
	private AssetManager manager;
	private Stage stage;
	private SpriteBatch batch;

	public Oruga(final Tutifruti game) {
		this.game = game;
		batch = game.batch;
		manager = new AssetManager();
		cargarArchivos();
		oruga = new Image(manager.get("oruga/oruga.jpg", Texture.class));
		oruga.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sound mp = manager.get("oruga/cuento.mp3", Sound.class);
				mp.stop();
				mp.play();
			}
		});
		sol = new Image(manager.get("oruga/sol.jpg", Texture.class));
		sol.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Sound mp = manager.get("oruga/mal.mp3", Sound.class);
				mp.stop();
				mp.play();
			}
		});
		sol.setPosition(0, 700 - sol.getHeight());
		stage = new Stage(
				new FitViewport(400, 700),
				batch);
		stage.addActor(oruga);
		stage.addActor(sol);
		Gdx.input.setInputProcessor(stage);
	}

	public void cargarArchivos() {
		manager.load("oruga/cuento.mp3", Sound.class);
		manager.load("oruga/mal.mp3", Sound.class);
		manager.load("oruga/oruga.jpg", Texture.class);
		manager.load("oruga/sol.jpg", Texture.class);
		manager.finishLoading();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0, 1);
		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
		manager.dispose();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height);
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
	public void hide() {
		dispose();
	}
}
