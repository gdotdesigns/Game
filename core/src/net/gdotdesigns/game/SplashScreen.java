package net.gdotdesigns.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Todd on 9/19/2016.
 */
public class SplashScreen implements Screen {

    private MainGameScreen mainGameScreen;
    private Assets assets;
    OrthographicCamera camera;
    SpriteBatch spriteBatch;
    Texture splashImage;

    public SplashScreen(MainGameScreen mainGameScreen,OrthographicCamera camera,SpriteBatch spriteBatch){
        this.mainGameScreen=mainGameScreen;
        this.camera=camera;
        this.spriteBatch=spriteBatch;
        splashImage = new Texture(Gdx.files.internal("libgdx_splash.jpg"));
        assets = new Assets();
        assets.loadGameAssets();
    }


    @Override
    public void show() {
        Gdx.gl.glClearColor(0, 0, 0, 1);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        spriteBatch.draw(splashImage,0,0,16,9);
        //spriteBatch.draw(splashImage,-camera.viewportWidth/2f,-camera.viewportHeight/2f,16,9);

        spriteBatch.end();

        if(assets.manager.update()) {
            if (Gdx.input.justTouched()) {
                mainGameScreen.setScreen(new Game(assets, camera, spriteBatch));
            }
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
