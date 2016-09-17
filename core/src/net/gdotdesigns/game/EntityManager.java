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

        activeEntityList.add(newEntity);
        newEntity.setAlive();
    }

    public static void setToDestroyEntity(Entity oldEntity){

        //deadEntityList.add(oldEntity);
        //activeEntityList.removeValue(oldEntity,true);
        oldEntity.setDead();
    }


    public static void destroyEntity(World world){

        for(Entity e: deadEntityList){
            if(e instanceof Enemy){
                world.destroyBody(e.getBody());
                e.freeEntity();
            }
        }
        deadEntityList.clear();

    }



    public static void update(float deltaTime,Camera cam){

        for(Entity e: activeEntityList){
            e.update(deltaTime);

            if(e instanceof Enemy) {
                if ((e.findEntityLocation().x < -cam.viewportWidth / 2f || !e.isAlive() && e.findEntityLocation().y < -cam.viewportHeight / 2f)) {
                    if(!deadEntityList.contains(e,true)) {
                        deadEntityList.add(e);
                        activeEntityList.removeValue(e, true);
                    }
                }
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
