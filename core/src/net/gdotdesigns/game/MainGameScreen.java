package net.gdotdesigns.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Camera;
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
    public Camera camera;
    public SpriteBatch spriteBatch;
    public static float worldWidth;



    @Override
    public void create() {
        worldWidth = WORLD_HEIGHT * (float) Gdx.graphics.getWidth() / (float) Gdx.graphics.getHeight();
        camera=new OrthographicCamera(worldWidth,WORLD_HEIGHT);
        camera.update();
        spriteBatch = new SpriteBatch();
        this.setScreen(new SplashScreen(this,camera,spriteBatch));
    }

    @Override
    public void render(){
        super.render();
    }


    @Override
    public void dispose(){
        spriteBatch.dispose();
    }




}
