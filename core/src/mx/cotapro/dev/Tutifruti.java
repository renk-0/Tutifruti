package mx.cotapro.dev;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music;

import mx.cotapro.dev.mainscreen.Main;

public class Tutifruti extends Game {
	public SpriteBatch batch;
	public Music cancion;
	@Override
	public void create () {
		batch = new SpriteBatch();
		cancion = Gdx.audio.newMusic(Gdx.files.internal("Musica_fondo.mp3"));
		cancion.setLooping(true);
		cancion.play();
		this.setScreen(new Main(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		cancion.dispose();
	}
}
