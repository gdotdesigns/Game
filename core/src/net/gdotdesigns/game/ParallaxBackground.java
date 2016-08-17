package net.gdotdesigns.game;

/**
 * Created by Todd on 8/14/2016.
 */

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ParallaxBackground {

    private ParallaxLayer[] layers;
    private Camera camera;
    private SpriteBatch batch;
    private Vector2 speed = new Vector2();

    /**
     * @param layers  The  background layers
     //* @param width   The screenWith
    // * @param height The screenHeight
     * @param speed A Vector2 attribute to point out the x and y speed
     */
    public ParallaxBackground(ParallaxLayer[] layers,Camera camera,SpriteBatch batch,Vector2 speed){
        this.layers = layers;
        this.speed.set(speed);
        this.camera = camera;
        this.batch=batch;
    }

    public void render(float delta){
       camera.position.add(speed.x*delta,speed.y*delta, 0);
        camera.update();
        for(ParallaxLayer layer:layers){
            batch.setProjectionMatrix(camera.projection);

            batch.begin();
            float currentX = - camera.position.x*layer.parallaxRatio.x % (Game.BACKGROUND_WIDTH) ;


            if( speed.x < 0 )currentX += -(Game.BACKGROUND_WIDTH);
            do{
                float currentY = - camera.position.y*layer.parallaxRatio.y % (Game.BACKGROUND_HEIGHT) ;
                if( speed.y < 0 )currentY += - (Game.BACKGROUND_HEIGHT);
                do{
                    batch.draw(layer.texture,
                            -this.camera.viewportWidth/2+currentX,
                            -this.camera.viewportHeight/2 + currentY,Game.BACKGROUND_WIDTH, Game.BACKGROUND_HEIGHT);
                    currentY += (Game.BACKGROUND_HEIGHT);
                }while( currentY < camera.viewportHeight);
                currentX += ( Game.BACKGROUND_WIDTH);
            }while( currentX < camera.viewportWidth);
            batch.end();
        }
    }
}
