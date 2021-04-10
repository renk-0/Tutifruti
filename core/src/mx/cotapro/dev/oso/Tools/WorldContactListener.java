package mx.cotapro.dev.oso.Tools;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import mx.cotapro.dev.oso.Sprites.InteractiveTileObject;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA =contact.getFixtureA();
        Fixture fixB =contact.getFixtureB();

        if (fixA.getUserData()=="head"){
            Fixture head = fixA.getUserData()=="head"?fixA:fixB;
            Fixture object =head==fixA?fixB:fixA;

            if(object.getUserData()!=null&& InteractiveTileObject.class.isAssignableFrom(object.getUserData().getClass()))
            {
               ((InteractiveTileObject)object.getUserData()).onHeadHit();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
