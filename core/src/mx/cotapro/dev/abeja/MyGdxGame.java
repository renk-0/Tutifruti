package mx.cotapro.dev.abeja;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import mx.cotapro.dev.Tutifruti;

import com.badlogic.gdx.Screen;

import java.util.Iterator;

public class MyGdxGame implements Screen {
	private Texture beeimg;
	private Tutifruti game;
	private Texture panalimg;
	private Music fondocris;
	private Sound plups;
	private OrthographicCamera camara;
	private SpriteBatch camara2;
	private Rectangle panal;
	private Array<Rectangle> bee;
	private long lastDropTime;
	int abejasatrapadas;

	public MyGdxGame(final Tutifruti game) {
		this.game = game;
		beeimg = new Texture(Gdx.files.internal("abeja/ABEJA.png"));
		panalimg = new Texture(Gdx.files.internal("abeja/PANAL.png"));

		camara = new OrthographicCamera();
		camara.setToOrtho(false, 400, 700);
		camara2 = game.batch;

		panal = new Rectangle();
		panal.x = 400 / 2 - 64 / 2;
		panal.y = 20;
		panal.width = 64;
		panal.height = 64;

		plups = Gdx.audio.newSound(Gdx.files.internal("abeja/plup.mp3"));
		fondocris = Gdx.audio.newMusic(Gdx.files.internal("abeja/Fondo.mp3"));
		fondocris.setLooping(true);
		fondocris.play();

		bee = new Array<Rectangle>();
		spawnBee();
	}

	private void spawnBee() {
		Rectangle bees = new Rectangle();
		bees.x = MathUtils.random(0, 400-64);
		bees.y = 700;
		bees.width = 64;
		bees.height = 64;
		bee.add(bees);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render(float dt) {
		ScreenUtils.clear(0, 0.4f, 0, .2f);

		camara.update();
		camara2.setProjectionMatrix(camara.combined);
		camara2.begin();
		camara2.draw(panalimg, panal.x, panal.y,85,85);
		for(Rectangle bees: bee) {
			camara2.draw(beeimg, bees.x, bees.y,75,75);
		}
		camara2.end();

		if(Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camara.unproject(touchPos);
			panal.x = touchPos.x - 64 / 2;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) panal.x -= 200 * Gdx.graphics.getDeltaTime();
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) panal.x += 200 * Gdx.graphics.getDeltaTime();
		if(panal.x < 0) panal.x = 0;
		if(panal.x > 400 - 64) panal.x = 400 - 64;
		if(TimeUtils.nanoTime() - lastDropTime > 1000000000) spawnBee();

		for (Iterator<Rectangle> iter = bee.iterator(); iter.hasNext(); ) {
			Rectangle bees = iter.next();
			bees.y -= 200 * Gdx.graphics.getDeltaTime();
			if(bees.y + 64 < 0) iter.remove();
			if(bees.overlaps(panal)) {
				plups.play();
				iter.remove();
			}
		}
	}
	
	@Override
	public void dispose() {
		beeimg.dispose();
		panalimg.dispose();
		fondocris.dispose();
		plups.dispose();
		camara2.dispose();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
