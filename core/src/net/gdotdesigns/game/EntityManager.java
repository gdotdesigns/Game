package net.gdotdesigns.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Todd on 8/26/2016.
 */
public class EntityManager {

    private static Array<Entity> activeEntityList = new Array<Entity>();
    private static Array<Entity> deadEntityList = new Array<Entity>();


    public static void addEntity (Entity newEntity){

        newEntity.setAlive();
        activeEntityList.add(newEntity);
    }

    public static void setToDestroyEntity(Entity oldEntity){

        deadEntityList.add(oldEntity);
        oldEntity.setDead();
    }

    public static void destroyEntity(World world){

        for(Entity e: deadEntityList){
            if(!e.isAlive() && e instanceof Enemy){
                world.destroyBody(e.getBody());
                e.freeEntity();
            }
        }
    }

    public static void clearDeadEntityList(){

        deadEntityList.clear();
    }

    public static void removeFromActiveList(Entity entity){

        activeEntityList.removeValue(entity,true);
    }


    public static void update(float deltaTime,Camera cam){

        for(Entity e: activeEntityList){
            e.update(deltaTime);
            if(e.findEntityLocation() < -cam.viewportWidth/2f && e instanceof Enemy){
                setToDestroyEntity(e);
                removeFromActiveList(e);
            }

        }
    }


    public static void render(SpriteBatch spriteBatch){

        for(Entity e : activeEntityList){

            e.render(spriteBatch);
        }

    }

    public static void dispose(){

        for(Entity e : activeEntityList){

            e.dispose();
        }
    }






}
