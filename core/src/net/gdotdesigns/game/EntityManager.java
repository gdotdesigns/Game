package net.gdotdesigns.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Todd on 8/26/2016.
 */
public class EntityManager {

    private static Array<Entity> myEntityList = new Array<Entity>();


    public static void addEntity (Entity newEntity){
        myEntityList.add(newEntity);
    }


    public static void update(float deltaTime){

        for(Entity e: myEntityList){

            e.update(deltaTime);
        }
    }


    public static void render(SpriteBatch spriteBatch){

        for(Entity e : myEntityList){

            e.render(spriteBatch);
        }

    }

    public static void dispose(){
        for(Entity e : myEntityList){

            e.dispose();
        }
    }






}
