package net.gdotdesigns.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * Created by Todd on 9/19/2016.
 */
public class SplashScreen implements Screen  {

    private MainGameScreen mainGameScreen;
    private Assets assets;
    OrthographicCamera camera;
    SpriteBatch spriteBatch;
    Texture splashTexture;
    Image splashImage;
    Stage stage;

    private boolean fadeInComplete =false;
    private boolean fadeOutComplete = false;
    private Viewport viewport;

    public SplashScreen(MainGameScreen mainGameScreen,OrthographicCamera camera,SpriteBatch spriteBatch,Assets assets){
        this.mainGameScreen=mainGameScreen;
        this.camera=camera;
        this.spriteBatch=spriteBatch;
        this.assets=assets;
        assets.loadMenuAssets();}


    @Override
    public void show() {
        float ratio = (float)Gdx.graphics.getWidth()/2f / (float)Gdx.graphics.getHeight();
        viewport = new FitViewport(1920*ratio,1080,camera);
        viewport.apply();
        stage = new Stage(viewport,spriteBatch);

        //TODO Add this image to the asset manager
        splashTexture = new Texture(Gdx.files.internal("libgdx_splash.jpg"));
        splashImage= new Image(splashTexture);
        splashImage.setPosition(camera.viewportWidth/2f-splashImage.getWidth()/2f,camera.viewportHeight/2f-splashImage.getHeight()/2f);
        splashImage.addAction(Actions.sequence(Actions.alpha(0f),Actions.fadeIn(1f),Actions.delay(2f),Actions.run(new Runnable() {
            @Override
            public void run() {
                fadeInComplete =true;
            }
        })));

        stage.addActor(splashImage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();
    }

    private void update(float delta) {
        stage.act(delta);

        if(assets.manager.update() && fadeInComplete == true) {
            splashImage.addAction(Actions.sequence(Actions.fadeOut(1f),Actions.run(new Runnable() {
                @Override
                public void run() {
                    fadeOutComplete = true;
                }
            })));

        }

        if(fadeInComplete && fadeOutComplete) {
            mainGameScreen.setScreen(new MainMenu(mainGameScreen,assets, camera, spriteBatch));
            spriteBatch.setColor(Color.WHITE);//Reset spriteBatch due to alpha change, it has to be done manually...
            dispose();
        }
    }
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);
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
        System.out.println("SPLASHSCREEN.DISPOSE");
        splashTexture.dispose();
        stage.dispose();
    }
}
