package net.gdotdesigns.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Todd on 8/26/2016.
 */
public class EntityManager {

    private static Array<Entity> myEntityList = new Array<Entity>();
    private static Array<Entity> removeEntityList = new Array<Entity>();


    public static void addEntity (Entity newEntity){

        myEntityList.add(newEntity);
    }

    public static void removeEntity(Entity oldEntity){

        removeEntityList.add(oldEntity);
    }

    public static void destroyEntity(World world){

        for(Entity e: removeEntityList){

            world.destroyBody(e.getBody());
            //something wrong here.
            e.freeEntity();
        }
    }

    public static void clearRemoveEntityList(){

        removeEntityList.clear();
    }

    public static void removeFromActiveList(Entity entity){
        myEntityList.removeValue(entity,true);
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
