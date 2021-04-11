package mx.cotapro.dev.elcerdito;

import com.badlogic.gdx.Screen;

import mx.cotapro.dev.Tutifruti;

public abstract class BaseScreen implements Screen {
    protected final Tutifruti game;
    public BaseScreen(final Tutifruti game) {
        this.game = game;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
		
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

    }
}
