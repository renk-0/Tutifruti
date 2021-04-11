package mx.cotapro.dev.carcal.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

import mx.cotapro.dev.Tutifruti;

/**
 * This is our main game. This is the class that we pass to the Application in Android launcher
 * as well as in desktop launcher. Because we want to create a screen-based game, we use Game
 * class, which has methods for creating multiple screens.
 */
public class MainGame {
	public final Tutifruti game; 
    /** This is the asset manager we use to centralize the assets. */
    private AssetManager manager;

    /**
     * These are the screens that we use in this game. I invite you to use a better system than
     * just public variables. For instance, you could create an ArrayList or maybe use some
     * structure such as a map where you can associate a number or a string to a screen.
     */
    public BaseScreen loadingScreen, menuScreen, gameScreen, gameOverScreen, creditsScreen;

    public MainGame(final Tutifruti game) {
		this.game = game;
        // Initialize the asset manager. We add every aset to the manager so that it can be loaded
        // inside the LoadingScreen screen. Remember to put the name of the asset in the first
        // argument, then the type of the asset in the second argument.
        manager = new AssetManager();
        manager.load("carcal/floor.png", Texture.class);
        manager.load("carcal/gameover.png", Texture.class);
        manager.load("carcal/overfloor.png", Texture.class);
        manager.load("carcal/logo.png", Texture.class);
        manager.load("carcal/spike.png", Texture.class);
        manager.load("carcal/player.png", Texture.class);
        manager.load("carcal/audio/die.ogg", Sound.class);
        manager.load("carcal/audio/jump.ogg", Sound.class);
        manager.load("carcal/audio/song.ogg", Music.class);

        // Enter the loading screen to load the assets.
        loadingScreen = new LoadingScreen(this);
	}

	public Screen loadingScreen() {
		return loadingScreen;
	}

    /**
     * This method is invoked by LoadingScreen when all the assets are loaded. Use this method
     * as a second-step loader. You can load the rest of the screens here and jump to the main
     * screen now that everything is loaded.
     */
    public void finishLoading() {
        menuScreen = new MenuScreen(this);
        gameScreen = new GameScreen(this);
        gameOverScreen = new GameOverScreen(this);
        game.setScreen(menuScreen);
    }

    public AssetManager getManager() {
        return manager;
    }

}
