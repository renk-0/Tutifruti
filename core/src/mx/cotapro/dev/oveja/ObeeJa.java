package mx.cotapro.dev.oveja;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;

public class ObeeJa {
	private World mundo;
	public Image cabeza, cuerpo;
	public Texture pelo;
	public Array<Body> pelos;
		
	public ObeeJa(AssetManager manager, World mundo) {
		this.mundo = mundo;
		pelo = manager.get("oveja/Pelo.png", Texture.class);
		cabeza = new Image(manager.get("oveja/cabeza.png", Texture.class));
		cuerpo = new Image(manager.get("oveja/Oveja.png", Texture.class));
		pelos = new Array<Body>();
		cuerpo.setPosition(0, 0);
		float factor = cuerpo.getHeight()/cuerpo.getWidth();
		cuerpo.setSize(250f, 250f*factor);
		cuerpo.setPosition((400 - cuerpo.getWidth())/2f, (700 - cuerpo.getHeight())/2f);
		cuerpo.setZIndex(1);
		factor = cabeza.getHeight()/cabeza.getWidth();
		cabeza.setSize(140f, 140f*factor);
		cabeza.setY(cuerpo.getY() + cuerpo.getHeight() - (cabeza.getHeight()/2f) + 20f);
		cabeza.setX(cuerpo.getX() - (cabeza.getWidth()/2f) + 20f);
		cabeza.setZIndex(2);
		define();
	}
	
	public void act() {
		for(Body bod : pelos) {
			if(bod.isActive() && bod.getPosition().y < 0) {
				mundo.destroyBody(bod);
				bod.setActive(false);
				pelos.removeValue(bod, true);
			}
		}
	}

	public void define() {
		BodyDef def = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(30f);
		fdef.shape = shape;
		fdef.filter.categoryBits = 0x0001;
		fdef.filter.maskBits = 0x0002;
		fdef.density = 0f;
		fdef.friction = 0f;
		def.type = BodyType.DynamicBody;
		def.awake = false;
		def.fixedRotation = false;
		def.angularDamping = 0f;
		def.bullet = false;
		for(int i = 0; i < 50; i++) {
			def.position.x = MathUtils.random(
					cuerpo.getX(), 
					cuerpo.getX() +  cuerpo.getWidth());
			def.position.y = MathUtils.random(
				cuerpo.getY() + cuerpo.getHeight()/2f, 
				cuerpo.getY() + cuerpo.getHeight());
			Body bdy = mundo.createBody(def);
			bdy.createFixture(fdef);
			pelos.add(bdy);
		}
	}
}
