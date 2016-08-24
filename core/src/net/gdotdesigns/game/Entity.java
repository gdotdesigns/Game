package net.gdotdesigns.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Todd on 8/22/2016.
 */
public class Entity {

    Sprite sprite;
    Body body;
    World world;

    public Entity(){

        body.setUserData(this);

    }
}
