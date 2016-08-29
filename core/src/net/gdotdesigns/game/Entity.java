package net.gdotdesigns.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Todd on 8/26/2016.
 */
public abstract class Entity {

    public abstract void update(float deltaTime);

    public abstract void render(SpriteBatch spriteBatch);

    public abstract void dispose( );

}
