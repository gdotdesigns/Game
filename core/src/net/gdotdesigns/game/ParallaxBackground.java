package net.gdotdesigns.game;

/**
 * Created by Todd on 8/14/2016.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class ParallaxBackground{

    private ParallaxLayer[] layers;
    public OrthographicCamera camera ;
    private SpriteBatch batch;
    private Vector2 speed = new Vector2();
    public Viewport viewport;

    /**
     * @param layers  The  background layers
     //* @param width   The screenWith
    // * @param height The screenHeight
     * @param speed A Vector2 attribute to point out the x and y speed
     */
    public ParallaxBackground(ParallaxLayer[] layers,SpriteBatch batch,Vector2 speed){
        this.layers = layers;
        this.speed.set(speed);
        this.batch=batch;

        camera = new OrthographicCamera();
        camera.setToOrtho(false);
        camera.position.set(0,0,0);
        camera.update();

        float ratio=(float)Gdx.graphics.getWidth()/(float)Gdx.graphics.getHeight();
        viewport= new FitViewport(MainGameScreen.WORLD_HEIGHT * ratio, MainGameScreen.WORLD_HEIGHT,camera);
        viewport.apply();
    }

    public void render(float delta){
        camera.position.add(speed.x*delta,speed.y*delta,0);
        camera.update();

        for(ParallaxLayer layer:layers){
            batch.setProjectionMatrix(camera.projection);
            camera.update();
            float currentX = - camera.position.x*layer.parallaxRatio.x % (Game.backgroundWidth) ;

            if( speed.x < 0 )currentX += -(Game.backgroundWidth);
            do{
                float currentY = - camera.position.y*layer.parallaxRatio.y % (Game.BACKGROUND_HEIGHT) ;
                if( speed.y < 0 )currentY += - (Game.BACKGROUND_HEIGHT);
                do{

                    batch.draw(layer.region, -camera.viewportWidth/2+currentX, -camera.viewportHeight/2+currentY,Game.backgroundWidth, Game.BACKGROUND_HEIGHT);

                    currentY += (Game.BACKGROUND_HEIGHT);
                }while( currentY < camera.viewportHeight);
                currentX += ( Game.backgroundWidth);

            }while( currentX < camera.viewportWidth);


        }
    }
}
