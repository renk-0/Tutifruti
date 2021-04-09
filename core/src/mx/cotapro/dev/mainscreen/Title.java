package mx.cotapro.dev.mainscreen;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.TimeUtils;

public class Title extends Image {
	private Texture imagen;
	private boolean EASING_IN, EASING_OUT;
	private long current_time;
	private int duration = 500;

	public Title(Texture imagen) {
		super(imagen);
		this.imagen = imagen;
		setScale(0.5f);
		setOrigin(Align.center);
		EASING_IN = true;
		EASING_OUT = false;
		current_time = TimeUtils.millis();
	}

	public void easeIn() {
		if(EASING_IN)
			return;
		EASING_IN = true;
		EASING_OUT = false;
		current_time = TimeUtils.millis();
	}

	public void easeOut() {
		if(EASING_OUT)
			return;
		EASING_OUT = true;
		EASING_IN = false;
		current_time = TimeUtils.millis();
	}

	public void act(float delta) {
		if(EASING_IN) {
			long diff = TimeUtils.millis() - current_time;
			if(diff < duration) {
				setScale(diff * 0.001f);
			} else {
				EASING_IN = false;
			}
		} else if(EASING_OUT) {
			long diff = TimeUtils.millis() - current_time;
			if(diff < duration) {
				setScale(diff * -0.001f + 0.5f);
			} else {
				EASING_OUT = false;
			}
		}
	}

	public void dispose() {
		imagen.dispose();
	}
}
