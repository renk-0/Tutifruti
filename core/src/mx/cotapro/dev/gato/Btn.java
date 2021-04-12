package mx.cotapro.dev.gato;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class Btn extends Image {
	public boolean status;
	public int user;
	private final Gato gato;
	Texture current;

	public Btn(final Gato gato) {
		super();
		status = false;
		user = 0;

		this.gato = gato;
		this.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if(status)
					return;
				status = true;
				user = gato.turno? 2 : 1;
				current = user == 2?
					gato.manager.get("gato/X.png", Texture.class) :
					gato.manager.get("gato/rueda.png", Texture.class);
				gato.turno = !gato.turno;
				gato.change = true;
				TextureRegionDrawable drawable = new TextureRegionDrawable(current);
				setDrawable(drawable);
			}
		});
	}
}
