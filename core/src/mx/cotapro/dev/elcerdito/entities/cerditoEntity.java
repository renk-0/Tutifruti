package mx.cotapro.dev.elcerdito.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static mx.cotapro.dev.elcerdito.constants.PIXELS_IN_METER;

public class cerditoEntity extends Image{
    private Texture texture;
	private Sound oink;

    public cerditoEntity(Sound sonido, Texture texture, Vector2 position)
    {
		super(texture);
        this.texture=texture;
      	this.oink = sonido;

		addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				oink.stop();
				oink.play();
			}
		});
		setPosition(0, 0);
    }

}

