package net.gdotdesigns.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FillViewport;

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
    private float splashImageWidth;
    private float splashImageHeight=9f;
    private float aspectRatio;
    private boolean fadeInComplete =false;
    private boolean fadeOutComplete = false;
    private FillViewport fillViewport;

    public SplashScreen(MainGameScreen mainGameScreen,OrthographicCamera camera,SpriteBatch spriteBatch){
        this.mainGameScreen=mainGameScreen;
        this.camera=camera;
        this.spriteBatch=spriteBatch;
        assets = new Assets();
        assets.loadMenuAssets();}


    @Override
    public void show() {
        fillViewport = new FillViewport(1920f,1080f);
        stage = new Stage(fillViewport,spriteBatch);
        //aspectRatio =(float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
        splashTexture = new Texture(Gdx.files.internal("libgdx_splash.jpg"));
        splashImage= new Image(splashTexture);


        //splashImageWidth=splashImageHeight*aspectRatio;
        splashImage.setPosition(fillViewport.getWorldWidth()/2f-splashImage.getWidth()/2f,fillViewport.getWorldHeight()/2f-splashImage.getHeight()/2f);
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
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
            dispose();

        }
    }

    @Override
    public void resize(int width, int height) {
        fillViewport.update(width,height,true);
        //aspectRatio=(float)width/height;
        //splashImageWidth=splashImageHeight*aspectRatio;
    }

    @Override
    public void pause() {
        dispose();
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        splashTexture.dispose();
        stage.dispose();
    }
}
