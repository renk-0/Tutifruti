package mx.cotapro.dev.selectscreen;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import mx.cotapro.dev.Tutifruti;
import mx.cotapro.dev.mainscreen.Main;
import mx.cotapro.dev.oso.Screens.PlayScreen;
import mx.cotapro.dev.oveja.Oveja;
import mx.cotapro.dev.serpiente.MyGdxGame;
import mx.cotapro.dev.tortuga.GameScreen;
import mx.cotapro.dev.oruga.Oruga;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;

public class Cara extends Image {
	public Texture textura;
	private int i, j;
	public Cara(Texture textura, int i, int j) {
		super(textura);
		this.i = i;
		this.j = j;
		this.textura = textura;
		setTouchable(Touchable.enabled);
	}

	public Screen getScreen(final Tutifruti game) {
		game.cancion.stop();
		switch(i) {
			case 0:
				if(j == 0)
					return new Oveja(game);
				else
					return new PlayScreen(game);
			case 1:
				if(j == 0)
					return new MyGdxGame(game);
				else
					return new GameScreen(game);
			case 2:
				if(j == 0) 
					return new mx.cotapro.dev.elcerdito.GameScreen(game);
				else
					return new mx.cotapro.dev.abeja.MyGdxGame(game);
			case 3:
				if(j == 0)
					return new Oruga(game);
			default:
				return new Main(game);
		}
	}
}
