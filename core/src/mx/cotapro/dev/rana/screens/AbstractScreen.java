package mx.cotapro.dev.rana.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import mx.cotapro.dev.rana.App;
import mx.cotapro.dev.rana.managers.GameScreenManager;

public abstract class AbstractScreen implements Screen {

    protected final App app;

	Color bgcol;
    Stage stage;

    public AbstractScreen(final App app) {
        this.app = app;
        this.stage = new Stage();
    	bgcol = new Color(0x77d6f7);
	}

    public abstract void update(float delta);

    @Override
    public void render(float delta) {
        update(delta);
        Gdx.gl.glClearColor(bgcol.r, bgcol.g, bgcol.b, bgcol.a);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        this.stage.dispose();
    }
}
