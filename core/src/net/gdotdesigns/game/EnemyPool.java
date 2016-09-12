package net.gdotdesigns.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Pool;

/**
 * Created by Todd on 9/10/2016.
 */
public class EnemyPool extends Pool<Enemy> {

    World world;
    TextureAtlas textureAtlas;



    public EnemyPool(int initialCapacity, int max, World world, TextureAtlas textureAtlas){
        super(initialCapacity,max);
        this.world=world;
        this.textureAtlas=textureAtlas;
    }



    @Override
    protected Enemy newObject() {
        return new Enemy(Game.ENEMY_BIRD_WIDTH*5f, 0, Game.ENEMY_BIRD_WIDTH, Game.ENEMY_BIRD_HEIGHT, 1f, .8f, world,textureAtlas,this);
    }
}
