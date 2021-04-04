package mx.cotapro.dev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

public class MainScreen implements Screen {
	public final Tutifruti game;
	public Sprite titulo;
	public ShapeRenderer background;
	public SpriteBatch batch;
	public BitmapFont touch;
	public long startTime;
	public boolean easing = false;

	public MainScreen(final Tutifruti game) {
		// Screen properties
		this.game = game;
		batch = new SpriteBatch();
		// Sprites
		titulo = new Sprite(new Texture(Gdx.files.internal("Tutif.png")));
		titulo.setScale(0.6f);
		titulo.setX(0 - (titulo.getWidth()/2f));
		titulo.setY(200f);
		background = new ShapeRenderer();
		background.setColor(new Color(0xd5ffb8ff));
		// Text
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("LondrinaSolid-Black.ttf"));
		FreeTypeFontGenerator.FreeTypeFontParameter parametros = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parametros.size = 20;
		parametros.shadowColor = new Color(0, 0, 0, 0.2f);
		parametros.shadowOffsetX = 2;
		parametros.shadowOffsetY = 2;
		touch = generator.generateFont(parametros);
		touch.setColor(0.16f, 0.16f, 0.16f, 0);
		startTime = TimeUtils.millis();
	}

	public void textBlinking() {
		long diff = TimeUtils.millis() - startTime;
		if(diff < 1000) {
			float mult = diff * 0.002f;
			touch.setColor(0.16f, 0.16f, 0.16f, mult * (-mult + 2));
		} else {
			startTime = TimeUtils.millis();
			touch.setColor(0.16f, 0.16f, 0.16f, 0);
		}
	}

	public void quitTitle() {
		long diff = TimeUtils.millis() - startTime;
		if(diff > 500) {
			game.setScreen(new Selection(game));
			dispose();
			return;
		}
		float mult = diff * 0.002f;
		float hg = -mult * (mult - 2);
		float res = hg * (game.WIDTH - 200) + 200;
		titulo.setY(res);
	}

	@Override
	public void render(float delta) {
		draw();
		if(easing) {
			quitTitle();
			return;
		}
		textBlinking();
		if(Gdx.input.justTouched()) {
			easing = true;
			startTime = TimeUtils.millis();
		}
	}

	public void draw() {
		game.camara.update();
		ScreenUtils.clear(0, 0, 0, 1);
		background.setProjectionMatrix(game.camara.combined);
		background.begin(ShapeRenderer.ShapeType.Filled);
		background.rect(game.bottomL.x, game.bottomL.y, game.WIDTH, game.HEIGHT);
		background.end();
		batch.setProjectionMatrix(game.camara.combined);
		batch.begin();
		titulo.draw(batch);
		touch.draw(batch, "Toca para empezar a jugar", 0, 0, 0, 1, false);
		batch.end();
	}

	@Override
	public void dispose() {
		titulo.getTexture().dispose();
		background.dispose();
		batch.dispose();
		touch.dispose();
	}
	@Override
	public void resize(int width, int height) { game.view.update(width, height); }
	@Override
	public void show() {}
	@Override
	public void hide() {}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
}
