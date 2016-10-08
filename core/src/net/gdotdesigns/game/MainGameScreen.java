package net.gdotdesigns.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Todd on 9/19/2016.
 */

public class MainGameScreen extends com.badlogic.gdx.Game {
    public static final int WIDTH=1920;
    public static final int HEIGHT=1080;
    public static final String TITLE = "Game";
    public static final float WORLD_HEIGHT=9f;
    public OrthographicCamera camera;
    public SpriteBatch spriteBatch;
    public static float worldWidth;
    private float aspectRatio;
    private Assets assets;
    //private Viewport vp;



    @Override
    public void create() {
        //aspectRatio =(float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
        //worldWidth = WORLD_HEIGHT * aspectRatio;
        //camera=new OrthographicCamera(worldWidth,WORLD_HEIGHT);
        camera=new OrthographicCamera();

        //vp=new FillViewport(16,9,camera);
        //vp.apply();
        camera.update();
        spriteBatch = new SpriteBatch();
        this.setScreen(new SplashScreen(this,camera,spriteBatch));
        assets=new Assets();
    }

    @Override
    public void render(){
        super.render();
    }


    @Override
    public void dispose(){
        super.dispose();
        spriteBatch.dispose();
    }

    @Override
    public void resize(int width, int height){
        super.resize(width, height);
        //aspectRatio =(float)width/height;
        //vp.update(width,height);
        //worldWidth = WORLD_HEIGHT * aspectRatio;
        //camera.setToOrtho(false, worldWidth, WORLD_HEIGHT);

        //spriteBatch.setProjectionMatrix(camera.combined);
        //camera.update();
    }




}
