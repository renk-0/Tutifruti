package mx.cotapro.dev.rana;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import mx.cotapro.dev.Tutifruti;
import mx.cotapro.dev.rana.managers.*;



public class App {

	//Variables de la aplicacion
	public static String APP_TITLE = "Ping Pong";
	public static double APP_VERSION = 0.1;
	public static int APP_DESKTOP_WIDTH = 420;
	public static int APP_DESKTOP_HEIGHT = 720;
	public static int APP_FPS = 60;

	//Variables del juego
	public static int V_WIDTH = 420;
	public static int V_HEIGHT = 720;

	//Managers
	public GameScreenManager gsm;
	public AssetManager assets;

	//Batches
	public SpriteBatch batch;
	public ShapeRenderer shapeBatch;

	public final Tutifruti game;

	public App(final Tutifruti game) {
		this.game = game;
		batch = game.batch;
		shapeBatch = new ShapeRenderer();

		//Setup Managers
		assets = new AssetManager();
		gsm = new GameScreenManager(this);
	}

	public void dispose() {
		shapeBatch.dispose();
		assets.dispose();
		gsm.dispose();
	}

}
