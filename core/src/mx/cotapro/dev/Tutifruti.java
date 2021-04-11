package mx.cotapro.dev;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.audio.Music;

import mx.cotapro.dev.mainscreen.Main;
import mx.cotapro.dev.selectscreen.Select;

public class Tutifruti extends Game{
	public SpriteBatch batch;
	public Music cancion;
	@Override
	public void create () {
		batch = new SpriteBatch();
		cancion = Gdx.audio.newMusic(Gdx.files.internal("Musica_fondo.mp3"));
		cancion.setLooping(true);
		cancion.play();
		Gdx.input.setCatchKey(Keys.BACK, true);
		this.setScreen(new Main(this));
	}

	@Override
	public void render () {
		super.render();
		if(Gdx.input.isKeyPressed(Keys.BACK))
			this.setScreen(new Select(this));
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		cancion.dispose();
	}

}
