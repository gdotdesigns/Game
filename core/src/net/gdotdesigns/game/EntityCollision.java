package net.gdotdesigns.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.Shape;

/**
 * Created by Todd on 8/24/2016.
 */
public class EntityCollision implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        if (bodyA.getUserData() instanceof Player && bodyB.getUserData() instanceof Player) {
            return;
        }
            Body player = bodyA.getUserData() instanceof Player ? bodyA : bodyB;
            Body object = player == bodyA ? bodyB : bodyA;

        if (object.getUserData()=="topWallBody") {
            player.setAngularVelocity(0f);
            player.setLinearVelocity(0f, 0f);
        }

        else{
            player.setAngularVelocity(-5f);
            player.setLinearVelocity(0f,MathUtils.random(1f,10f));
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
