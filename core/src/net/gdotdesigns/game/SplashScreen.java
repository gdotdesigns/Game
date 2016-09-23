package net.gdotdesigns.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Todd on 9/19/2016.
 */
public class SplashScreen implements Screen {

    private MainGameScreen mainGameScreen;
    private Assets assets;
    Camera camera;
    SpriteBatch spriteBatch;

    public SplashScreen(MainGameScreen mainGameScreen,Camera camera,SpriteBatch spriteBatch){
        this.mainGameScreen=mainGameScreen;
        this.camera=camera;
        this.spriteBatch=spriteBatch;
        assets = new Assets();
        assets.loadGameAssets();
    }


    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        if(assets.manager.update()){
            mainGameScreen.setScreen(new Game(assets,camera,spriteBatch));
        }

            else  System.out.println(assets.manager.getProgress() * 100 +"%");
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        assets.dispose();
        spriteBatch.dispose();
    }
}
