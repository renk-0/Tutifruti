package mx.cotapro.dev.oveja;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;

public class Maquina {
	private Texture textura;
	private World mundo;
	public Image actor;
	public Body body;
	public Fixture fixture;
	public float width = 50f;

	public Maquina(World mundo, AssetManager manager) {
		this.textura = manager.get("oveja/Maquina.png", Texture.class);
		this.mundo = mundo;
		actor = new Image(textura);
		float factor = textura.getHeight()/textura.getWidth();
		actor.setSize(width, width*factor);
		actor.setZIndex(5);
		define();
		actor.addListener(new DragListener() {
			private float x, y;
			@Override
			public void dragStart(InputEvent event, float x, float y, int pointer) {
				this.x = x;
				this.y = y;
			}
			@Override
			public void drag(InputEvent event, float x, float y, int pointer) {
				Vector2 pos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
				actor.getStage().getViewport().unproject(pos);
				actor.setPosition(pos.x - this.x, pos.y - this.y);
				body.setTransform(
						actor.getX() + actor.getWidth()/2f, 
						actor.getY() + actor.getHeight() - 20,
						0);
			}
		});
	}

	private void define() {
		BodyDef bdef = new BodyDef();
		bdef.gravityScale = 0;
		bdef.type = BodyType.StaticBody;
		body = mundo.createBody(bdef);

		FixtureDef fixdef = new FixtureDef();
		CircleShape shape = new CircleShape();
		shape.setRadius(width/2f);
		fixdef.shape = shape;
		fixdef.density = 0;
		fixdef.filter.categoryBits = 0x0002;
		fixdef.friction = 0;
		fixture = body.createFixture(fixdef);
	}
}
