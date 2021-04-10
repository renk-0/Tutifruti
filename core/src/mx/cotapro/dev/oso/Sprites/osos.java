package mx.cotapro.dev.oso.Sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import mx.cotapro.dev.oso.Oso;

public class osos extends Sprite {
    public World world;
    public Body b2body;
    public Texture osoSprite;

    public osos(World world){
        this.world=world;
        defineOso();
    }

    public void defineOso(){
        BodyDef bdef = new BodyDef();
        bdef.position.set(32/Oso.PPM, 32/Oso.PPM);
        bdef.type=BodyDef.BodyType.DynamicBody;
        b2body=world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape= new CircleShape();
        shape.setRadius(0/Oso.PPM);

        fdef.filter.categoryBits=Oso.OSO_BIT;
        fdef.filter.maskBits=Oso.DEFAULT_BIT|Oso.COIN_BIT|Oso.BRICK_BIT;

        fdef.shape=shape;
        b2body.createFixture(fdef);

        EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2/Oso.PPM, 6/Oso.PPM), new Vector2(2/Oso.PPM, 6/Oso.PPM));
        fdef.shape=head;
        fdef.isSensor=true;
        b2body.createFixture(fdef).setUserData("head");

        osoSprite = new Texture(Gdx.files.internal("oso/Oso_camina.png"));
    }

    public void draw (Batch batch){
        batch.draw(osoSprite, b2body.getPosition().x, b2body.getPosition().y, 25/Oso.PPM, 25/Oso.PPM);
    }


}
