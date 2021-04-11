package mx.cotapro.dev.rana.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import static mx.cotapro.dev.rana.utils.B2DConstants.PPM;
import mx.cotapro.dev.rana.utils.*;

public final class B2DBodyBuilder {

    private B2DBodyBuilder() {}

    public static Body createBox(
			World world, 
			float x, float y, 
			float width, float height, 
			boolean isStatic, boolean isSensor) {
        Body body;

        BodyDef bDef = new BodyDef();
        bDef.type = isStatic? BodyDef.BodyType.StaticBody : BodyDef.BodyType.KinematicBody;
        bDef.fixedRotation = true;
		bDef.linearDamping = 0f;
        bDef.position.set(x, y);
        body = world.createBody(bDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 2 / PPM, height / 2 / PPM);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = 0f;
		fDef.friction = 0f;
		fDef.restitution = 1f;
        fDef.isSensor = isSensor;
        body.createFixture(fDef);
        shape.dispose();
        return body;
    }

    public static Body createBall(World world, float x, float y, float radius) {
        Body body;
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.DynamicBody;
        bDef.fixedRotation = false;
		bDef.linearDamping = 0f;
        bDef.position.set(x, y);
		body = world.createBody(bDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius/PPM);

        FixtureDef fDef = new FixtureDef();
        fDef.shape = shape;
        fDef.density = 0f;
		fDef.friction = 0f;
		fDef.restitution = 1f;
		body.createFixture(fDef);

        shape.dispose();
        return body;
    }

    public static Body createChainShape(World world, Vector2[] verts) {
        Body body;
        BodyDef bDef = new BodyDef();
        bDef.type = BodyDef.BodyType.StaticBody;
		bDef.linearDamping = 0f;
        body = world.createBody(bDef);

        ChainShape shape = new ChainShape();
        shape.createChain(verts);
		
		FixtureDef def = new FixtureDef();
		def.shape = shape;
		def.friction = 0f;
		def.density = 0f;
		def.restitution = 1.0f;
        body.createFixture(def);
        shape.dispose();
        return body;
    }
}
