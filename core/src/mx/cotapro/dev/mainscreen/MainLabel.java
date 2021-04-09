package mx.cotapro.dev.mainscreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.TimeUtils;

public class MainLabel extends Label {
	private Color color;
	private boolean EASE_OUT = false;
	private long time;

	public MainLabel(LabelStyle style) {
		super("Toca para empezar", style);
		color = style.fontColor;
		time = TimeUtils.millis();
	}

	public void act(float delta) {
		long diff = TimeUtils.millis() - time;
		if(!EASE_OUT) {
			float alpha = (0.002f * diff) * ((-0.002f * diff) + 2f);
			color.a = alpha;
			setColor(color);
			if(diff > 1000) 
				time = TimeUtils.millis();
		} else {
			float alpha = (-0.002f * diff) + 1f;
			color.a = alpha;
			setColor(color);
		}
	}

	public void easeOut() {
		if(EASE_OUT)
			return;
		EASE_OUT = true;
		time = TimeUtils.millis();
	}

	public void dispose() {
		this.getStyle().font.dispose();
	}
}
