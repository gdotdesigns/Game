package net.gdotdesigns.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Todd on 9/19/2016.
 */

public class MainGameScreen extends com.badlogic.gdx.Game {
    //public static final int WIDTH=1920, HEIGHT =1080; //large
   //public static final int WIDTH=800,HEIGHT = 480; //medium
    public static final int WIDTH=480,HEIGHT = 320; //small
    public static final String TITLE = "Game";
    public static final float WORLD_HEIGHT=9f;
    public OrthographicCamera camera;
    public SpriteBatch spriteBatch;




    @Override
    public void create() {
        camera=new OrthographicCamera();
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
        super.dispose();
        this.getScreen().dispose();
        spriteBatch.dispose();
    }

    @Override
    public void resize(int width, int height){
        super.resize(width, height);
    }




}
