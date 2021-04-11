package mx.cotapro.dev.selectscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScalingViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.cotapro.dev.Tutifruti;

public class Select implements Screen{
	private final Tutifruti game;
	private Color bgcolor;
	private Stage stage;
	private Viewport viewport;
	private Cara[][] caras;
	private ScrollPane pane;
	private Table tabla;

	public Select(final Tutifruti game) {
		this.game = game;
		if(!game.cancion.isPlaying())
			game.cancion.play();
		bgcolor = new Color(0xd5ffb8ff);
		viewport = new ScalingViewport(Scaling.fit, 400, 700);
		stage = new Stage(viewport, game.batch);
		Gdx.input.setInputProcessor(stage);
		tabla = new Table();
		pane = new ScrollPane(tabla);
		// tabla.setDebug(true);
		pane.setFillParent(true);
		createTable(tabla);
		stage.addActor(pane);
	}

	public void createTable(Table tabla) {
		String[][] images = {
			{"oveja", "oso"},
			{"serpiente", "tortuga"},
			{"cerdo", "abeja"},
			{"oruga", "carcal"},
			{"rana", "rana"}
		};
		caras = new Cara[images.length][images[0].length];
		for(int i = 0; i < images.length; i++) {
			tabla.row();
			for(int j = 0; j < images[i].length; j++) {
				caras[i][j] = new Cara(new Texture(Gdx.files.internal(images[i][j] + "/ico.png")), i, j);
				float factor = caras[i][j].getHeight()/caras[i][j].getWidth();
				tabla.add(caras[i][j]).size(120f, 120f * factor).pad(5f);
				caras[i][j].addListener(new ClickListener() {
					@Override
					public void clicked(InputEvent ev, float x, float y) {
						Cara cara = (Cara)ev.getTarget();
						game.setScreen(cara.getScreen(game));
					}
				});
			}
		}
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(bgcolor);
		stage.act();
		stage.draw();
	}

	@Override
	public void dispose() {
		stage.dispose();
		for(Cara[] cara : caras) {
			for(Cara textura : cara) {
				textura.textura.dispose();
			}
		}
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void show() {
		
	}

	@Override
	public void hide() {
		dispose();
	}
}
