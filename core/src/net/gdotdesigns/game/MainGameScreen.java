package net.gdotdesigns.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.googlecode.gwt.crypto.util.Sys;

/**
 * Created by Todd on 9/19/2016.
 */

public class MainGameScreen extends com.badlogic.gdx.Game {
    //public static final int WIDTH=1920, HEIGHT =1080; //large
   public static final int WIDTH=800,HEIGHT = 480; //medium
    //public static final int WIDTH=480,HEIGHT = 320; //small
    public static final String TITLE = "Game";
    public static final float WORLD_HEIGHT=9f;
    public OrthographicCamera camera;
    public SpriteBatch spriteBatch;
    public Assets assets;




    @Override
    public void create() {
        camera=new OrthographicCamera();
        camera.update();
        spriteBatch = new SpriteBatch();
        assets=new Assets();
        this.setScreen(new SplashScreen(this,camera,spriteBatch,assets));
    }

    @Override
    public void render(){
        super.render();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void dispose(){
        System.out.println("MAINGAMESCREEN.DISPOSE");
        super.dispose();
        this.getScreen().dispose();//This causes the previous screen to be disposed when setscreen is called...
        spriteBatch.dispose();
        assets.dispose();
    }

    @Override
    public void resize(int width, int height){
        super.resize(width, height);
    }




}
