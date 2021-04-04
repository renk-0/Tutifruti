package mx.cotapro.dev;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Tutifruti extends Game {
	public OrthographicCamera camara;
	public Viewport view;
	public final int WIDTH = 500;
	public final int HEIGHT = 875;
	public Music fondo;
	public Vector2 bottomL;

	@Override
	public void create () {
		camara = new OrthographicCamera();
		view = new FitViewport(WIDTH, HEIGHT, camara);
		bottomL = new Vector2(-WIDTH/2f, -HEIGHT/2f);
		fondo = Gdx.audio.newMusic(Gdx.files.internal("Musica_fondo.wav"));
		fondo.setLooping(true);
		fondo.play();
		this.setScreen(new MainScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		super.dispose();
		fondo.stop();
		fondo.dispose();
	}
}
