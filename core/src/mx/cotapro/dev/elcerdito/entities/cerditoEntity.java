package mx.cotapro.dev.elcerdito.entities;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import static mx.cotapro.dev.elcerdito.constants.PIXELS_IN_METER;

public class cerditoEntity extends Actor {

    private Texture texture;
    private World world;
    private Body body;
    private Fixture fixture;

    public cerditoEntity(World world, Texture texture, Vector2 position)
    {

        this.world= world;
        this.texture=texture;
        BodyDef def= new BodyDef();
        def.position.set(position);
        def.type= BodyDef.BodyType.StaticBody;
        body=world.createBody(def);

        PolygonShape box= new PolygonShape();
        box.setAsBox(1f, 1f);
        fixture=body.createFixture(box, 1);
        box.dispose();


        setSize(3*PIXELS_IN_METER, 3*PIXELS_IN_METER);


    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        setPosition((body.getPosition().x-0.5f)*PIXELS_IN_METER,
                (body.getPosition().y-0.5f)*PIXELS_IN_METER);
        batch.draw(texture, getX(), getY(), getWidth(), getHeight());

    }
    public void detach()
    {
        body.destroyFixture(fixture);
        world.destroyBody(body);
    }

}

