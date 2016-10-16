package net.gdotdesigns.game;

import com.badlogic.gdx.utils.Pool;

/**
 * Created by Todd on 9/10/2016.
 */
public class EnemyPool extends Pool<Enemy> {


    public EnemyPool(int initialCapacity, int max){
        super(initialCapacity,max);
    }



    @Override
    protected Enemy newObject() {
        return new Enemy();
    }
}
