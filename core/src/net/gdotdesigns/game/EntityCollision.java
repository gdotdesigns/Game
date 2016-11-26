package net.gdotdesigns.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
/**
 * Created by Todd on 8/24/2016.
 */
public class EntityCollision implements ContactListener {

    EntityManager entityManager;
    Game game;

    public EntityCollision(EntityManager entityManager,Game game){
        this.entityManager=entityManager;
        this.game=game;
    }


    @Override
    public void beginContact(Contact contact) {
        Body bodyA = contact.getFixtureA().getBody();
        Body bodyB = contact.getFixtureB().getBody();

        //do something if player and enemy collide
        if (bodyA.getUserData() instanceof Player && bodyB.getUserData() instanceof Enemy ||
                bodyA.getUserData() instanceof Enemy && bodyB.getUserData() instanceof Player) {
            Body player = bodyA.getUserData() instanceof Player ? bodyA : bodyB;
            Body enemy = player == bodyA ? bodyB : bodyA;
            //.setToDestroyEntity((Entity)enemy.getUserData());
        }


        //do something if player collides with ground
        else if(bodyA.getUserData() instanceof Player && bodyB.getUserData() == Game.groundBody ||
                bodyA.getUserData() == Game.groundBody && bodyB.getUserData() instanceof Player){
            Body player = bodyA.getUserData() instanceof Player ? bodyA : bodyB;
            game.gameOver();
        }


        //do something if enemy collides with ground
        else if(bodyA.getUserData() instanceof Enemy && bodyB.getUserData() == Game.groundBody||
                bodyA.getUserData() == Game.groundBody && bodyB.getUserData() instanceof Player){
            Body enemy = bodyA.getUserData() instanceof Enemy ? bodyA : bodyB;
            Body ground = enemy == bodyA ? bodyB : bodyA;
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
