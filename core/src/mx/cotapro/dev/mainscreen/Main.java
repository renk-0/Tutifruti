package mx.cotapro.dev.mainscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.cotapro.dev.Tutifruti;
import mx.cotapro.dev.selectscreen.Select;

public class Main implements Screen {
	private final Tutifruti game;
	private Viewport view;
	private Stage stage;
	private Title titulo;
	private MainLabel label;
	private Color bgcolor;
	private boolean easing = false;
	private long time;

	public Main(final Tutifruti game) {
		this.game = game;
		if(!game.cancion.isPlaying())
			game.cancion.play();
		bgcolor = new Color(0xd5ffb8ff);
		view = new ScalingViewport(Scaling.fit, 400, 700);
		stage = new Stage(view, game.batch);
		// Title logo
		titulo = new Title(new Texture(Gdx.files.internal("Tutif.png")));
		titulo.setPosition(
				(stage.getWidth() - titulo.getWidth())/2f, 
				(stage.getHeight()/2f) + 100);
		stage.addActor(titulo);
		// Touch to start label
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
				Gdx.files.internal("fuentes/LondrinaSolid-Black.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = 
			new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 20;
		LabelStyle style = new LabelStyle(generator.generateFont(parameter), new Color(0x5f5f5fff));
		label = new MainLabel(style);
		label.setPosition(
				(stage.getWidth() - label.getWidth())/2f, 
				stage.getHeight()/2f);
		stage.addActor(label);
		generator.dispose();
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(bgcolor);
		if(Gdx.input.justTouched() && !easing) {
			titulo.easeOut();
			label.easeOut();
			easing = true;
			time = TimeUtils.millis();
		}
		if(easing) {
			long diff = TimeUtils.millis() - time;
			if(diff > 500) {
				game.setScreen(new Select(game));
				dispose();
				return;
			}
		}
		stage.act(delta);
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
		titulo.dispose();
		label.dispose();
	}

	@Override
	public void resize(int width, int height) {
		view.update(width, height);
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
	public void show() {
	}
}
