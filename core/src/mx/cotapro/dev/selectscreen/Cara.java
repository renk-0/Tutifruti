package mx.cotapro.dev.selectscreen;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import mx.cotapro.dev.Tutifruti;
import mx.cotapro.dev.mainscreen.Main;
import mx.cotapro.dev.oveja.Oveja;

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
					return setOveja(game);
				else
					return setOveja(game);
			default:
				return new Main(game);
		}
	}
	
	private Screen setOveja(final Tutifruti game) {
		return new Oveja(game);
	}
}
