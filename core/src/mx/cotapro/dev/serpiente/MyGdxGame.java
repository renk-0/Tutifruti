package mx.cotapro.dev.serpiente;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import mx.cotapro.dev.Tutifruti;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.util.Iterator;


public class MyGdxGame implements Screen {
	private Tutifruti game;
	private Texture serpiente;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Music arabe;
	private Sound sonidoserpiente;
	private Rectangle[][]posiciones;
	public boolean[][]status={
			{false, false, false},
			{false, false, false},
			{false, false, false},
			{false, false, false}
	};
	private Viewport viewport;
	public long time;
	public int a, b;
	public int marcador;
	private BitmapFont texto;
	public int velocity;

	public MyGdxGame(final Tutifruti game) {
		this.game = game;
		//cargamos las imagenes
		serpiente = new Texture("serpiente/serpiente.png");
		//cargamos los sonidos
		arabe = Gdx.audio.newMusic(Gdx.files.internal("serpiente/arabe.mp3"));
		sonidoserpiente = Gdx.audio.newSound(Gdx.files.internal("serpiente/sonidoserpiente.mp3"));
		//comenzamos a reproducir la música automáticamente
		arabe.setLooping(true);
		arabe.play();
		//creamos el sprite en el que se dibujará
		batch = game.batch;
		//creamos la camara y le asignamos un tamaño
		camera = new OrthographicCamera();
		//Creamos el viewport
		viewport = new FitViewport(400, 700, camera);
		posiciones = new Rectangle [4][3];
		for (int i = 0; i < status.length; i++){
			for (int j = 0; j < status[i].length;j++){
				int serpienteW, serpienteH;
				serpienteH = serpienteW = 100;
				posiciones[i][j]=new Rectangle((-viewport.getWorldWidth()/2f)+(j*(serpienteW + 50)),(-viewport.getWorldHeight()/2f)+(i * (serpienteH + 100)), serpienteW, serpienteH);
			}
		}
        time = TimeUtils.millis();
		a = b = 0;
		marcador = 0;
		texto = new BitmapFont();
		velocity = 2000;
	}

	@Override
	public void dispose() {
		serpiente.dispose();
		sonidoserpiente.dispose();
		arabe.dispose();
	}

	@Override
	public void render(float dt) {
		ScreenUtils.clear(1, 0, 0.5f, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (int i = 0; i < status.length; i++){
			for (int j = 0; j < status[i].length;j++){
			    if(status[i][j]){
                    batch.draw(serpiente, posiciones[i][j].x, posiciones[i][j].y,posiciones[i][j].width, posiciones[i][j].height);
                }
			}
		}
		texto.draw(batch, "Puntuación: "+String.valueOf(marcador), -50,  0);
		texto.draw(batch,"¡Mata la serpiente!", -65,-30);

		batch.end();

		if(Gdx.input.justTouched()){
			boolean lepresioneauno = false;
			for (int i = 0; i < status.length; i++){
				for (int j = 0; j < status[i].length;j++){
					Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(),0);
					camera.unproject(touchPos);
					if(touchPos.x > posiciones[i][j].x && touchPos.x < (posiciones[i][j].x + posiciones[i][j].width) && touchPos.y > posiciones[i][j].y && touchPos.y < (posiciones[i][j].y + posiciones[i][j].height)) {
						if(status[i][j]){
							lepresioneauno = true;
							marcador++;
							sonidoserpiente.play();
							velocity = velocity -50;
						}
                        status[i][j] = false;
                    }
				}
		    }
			if(!lepresioneauno){
				marcador = 0;
				velocity = 2000;
			}
	    }
		long diferencia_de_tiempo = TimeUtils.millis() - time;
            if(diferencia_de_tiempo > velocity){
                time  = TimeUtils.millis();
                status[a][b] = false;
                a = MathUtils.random(0,status.length-1);
                b = MathUtils.random(0,status[0].length-1);
                status[a][b] = true;
          }
        }
	@Override
	public void resize(int w, int h) {
		viewport.update(w, h);
	}

	@Override
	public void show() {
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

