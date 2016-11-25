package net.gdotdesigns.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Todd on 8/26/2016.
 */
public class EntityManager {

    private Array<Entity> activeEntityList = new Array<Entity>();
    private Array<Entity> deadEntityList = new Array<Entity>();


    public void addEntity (Entity newEntity){

        activeEntityList.add(newEntity);
        newEntity.setAlive();
    }

    public void setToDestroyEntity(Entity oldEntity){

        //deadEntityList.add(oldEntity);
        //activeEntityList.removeValue(oldEntity,true);
        oldEntity.setDead();
    }


    public void destroyEntity(World world){

        for(Entity e: deadEntityList){
            if(e instanceof Enemy){
                world.destroyBody(e.getBody());
                e.freeEntity();
            }
        }
        deadEntityList.clear();
    }

    public void interpolate(float alpha) {
        for (Entity entity : activeEntityList) {
            Transform transform = entity.getBody().getTransform();
            Vector2 bodyPosition = transform.getPosition();
            Vector2 position = entity.getPosition();
            float angle = entity.getAngle();
            float bodyAngle = transform.getRotation();
            position.x = bodyPosition.x * alpha + position.x * (1.0f - alpha);
            position.y = bodyPosition.y * alpha + position.y * (1.0f - alpha);
            entity.setBody(position.x,position.y,bodyAngle * alpha + angle * (1.0f - alpha));
        }
    }




    public void update(float deltaTime,Camera cam){

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


    public void render(SpriteBatch spriteBatch){

        for(Entity e : activeEntityList){

            e.render(spriteBatch);
        }

    }

    public void dispose(){

        for(Entity e : activeEntityList){

            e.dispose();
        }
    }






}
