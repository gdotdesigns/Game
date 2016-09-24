package net.gdotdesigns.game;

/**
 * Created by Todd on 8/14/2016.
 */

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ParallaxBackground {

    private ParallaxLayer[] layers;
    public static OrthographicCamera camera ;
    private SpriteBatch batch;
    private Vector2 speed = new Vector2();

    /**
     * @param layers  The  background layers
     //* @param width   The screenWith
    // * @param height The screenHeight
     * @param speed A Vector2 attribute to point out the x and y speed
     */
    public ParallaxBackground(ParallaxLayer[] layers,SpriteBatch batch,Vector2 speed,float worldWidth,float worldHeight){
        this.layers = layers;
        this.speed.set(speed);
        this.batch=batch;
        camera = new OrthographicCamera();
        camera.setToOrtho(false,worldWidth,worldHeight);


    }

    public void render(float delta){
       camera.position.add(speed.x*delta,speed.y*delta,0);
        camera.update();
        for(ParallaxLayer layer:layers){
           //batch.setProjectionMatrix(camera.projection);
            camera.update();
           // batch.begin();
            float currentX = - camera.position.x*layer.parallaxRatio.x % (Game.backgroundWidth) ;

            if( speed.x < 0 )currentX += -(Game.backgroundWidth);
            do{
                float currentY = - camera.position.y*layer.parallaxRatio.y % (Game.BACKGROUND_HEIGHT) ;
                if( speed.y < 0 )currentY += - (Game.BACKGROUND_HEIGHT);
                do{
                    //batch.draw(layer.region,
                           //-this.camera.viewportWidth/2+currentX,
                            //-this.camera.viewportHeight/2 + currentY,Game.BACKGROUND_WIDTH, Game.BACKGROUND_HEIGHT);

                    batch.draw(layer.region, currentX, currentY,Game.backgroundWidth, Game.BACKGROUND_HEIGHT);

                    currentY += (Game.BACKGROUND_HEIGHT);
                }while( currentY < camera.viewportHeight);
                currentX += ( Game.backgroundWidth);
            }while( currentX < camera.viewportWidth);
            //batch.end();

        }
    }
}
