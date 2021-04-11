package mx.cotapro.dev.rana.managers;

import mx.cotapro.dev.rana.screens.*;
import mx.cotapro.dev.rana.utils.*;
import mx.cotapro.dev.rana.App;

import java.util.HashMap;
import java.util.Map;

public class GameScreenManager {

    public final App app;
    private HashMap<STATE, AbstractScreen> gameScreens;

    public enum STATE {
        MAIN_MENU,
        PLAY,
        SETTINGS
    }

    public GameScreenManager(final App app){
        this.app = app;

        initGameScreens();
        setScreen(STATE.PLAY);
    }

    private void initGameScreens() {
        this.gameScreens = new HashMap<STATE, AbstractScreen>();
        this.gameScreens.put(STATE.PLAY, new GameScreen(app));
    }

    public void setScreen(STATE nextScreen) {
        app.game.setScreen(gameScreens.get(nextScreen));
    }

    public void dispose() {
        for (AbstractScreen screen : gameScreens.values()) {
            if (screen != null) {
                screen.dispose();
            }
        }
    }
}
