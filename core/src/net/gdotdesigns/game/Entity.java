package net.gdotdesigns.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

/**
 * Created by Todd on 8/26/2016.
 */
public abstract class Entity {

    public abstract Body getBody();

    public abstract boolean isAlive();

    public abstract void setAlive();

    public abstract void setDead();

    public abstract Vector2 findEntityLocation();

    public abstract void freeEntity();

    public abstract void update(float deltaTime);

    public abstract void render(SpriteBatch spriteBatch);

    public abstract void dispose( );

    public abstract Vector2 getCurrentPosition();

    public abstract float getCurrentAngle();

    public abstract void savePreviousPosition();

    public abstract Vector2 getPreviousPosition();

    public abstract float getPreviousAngle();

    public abstract void setBody(float x, float y, float angle);

}
