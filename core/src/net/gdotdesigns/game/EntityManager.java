package net.gdotdesigns.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Todd on 8/26/2016.
 */
public class EntityManager {

    private Array<Entity> activeEntityList = new Array<Entity>();
    private Array<Entity> deadEntityList = new Array<Entity>();
    Vector2 currentPosition = new Vector2();
    Vector2 previousPosition = new Vector2();
    float currentAngle;
    float previousAngle;


    public void addEntity (Entity newEntity){

        activeEntityList.add(newEntity);
        newEntity.setAlive();
    }

    public void setToDestroyEntity(Entity oldEntity){

        deadEntityList.add(oldEntity);
        activeEntityList.removeValue(oldEntity,true);
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

    public void copyPosition(){
        for(Entity entity:activeEntityList){
            entity.savePreviousPosition();
        }
    }


    public void interpolate(float alpha){
        for(Entity entity:activeEntityList){

            currentPosition = entity.getCurrentPosition();
            currentAngle = entity.getCurrentAngle();
            previousPosition = entity.getPreviousPosition();
            previousAngle = entity.getPreviousAngle();

            currentPosition.x = currentPosition.x * alpha + previousPosition.x * (1.0f - alpha);
            currentPosition.y = currentPosition.y * alpha + previousPosition.y * (1.0f - alpha);
            currentAngle = currentAngle * alpha + previousAngle * (1.0f - alpha);

            entity.setBody(currentPosition.x, currentPosition.y,currentAngle);
        }
    }


    public void update(float deltaTime,Camera cam){

        for(Entity e: activeEntityList){
            e.update(deltaTime);

            if(e instanceof Enemy) {
                if ((e.findEntityLocation().x < -cam.viewportWidth / 3f || !e.isAlive() && e.findEntityLocation().y < -cam.viewportHeight / 2f)) {
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
