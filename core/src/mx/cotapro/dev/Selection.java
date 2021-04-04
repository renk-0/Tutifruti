package mx.cotapro.dev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Selection implements Screen {
	public final Tutifruti game;
	public SpriteBatch batch;

	public Selection(final Tutifruti game) {
		this.game = game;
		batch = new SpriteBatch();
	}

	public void draw() {
		game.camara.update();
		ScreenUtils.clear(0, 0, 0, 1);
		batch.setProjectionMatrix(game.camara.combined);
		batch.begin();
		batch.end();
	}

	@Override
	public void render(float delta) {

		draw();
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void resize(int width, int height) {
		game.view.update(width, height);
	}

	@Override
	public void show() {}
	@Override
	public void pause() {}
	@Override
	public void resume() {}
	@Override
	public void hide() {}
}
