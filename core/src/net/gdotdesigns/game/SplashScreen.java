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
    private boolean fadeComplete =false;

    public SplashScreen(MainGameScreen mainGameScreen,OrthographicCamera camera,SpriteBatch spriteBatch){
        this.mainGameScreen=mainGameScreen;
        this.camera=camera;
        this.spriteBatch=spriteBatch;
        stage = new Stage();
    }


    @Override
    public void show() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        aspectRatio =(float)Gdx.graphics.getWidth()/Gdx.graphics.getHeight();
        camera.setToOrtho(false, MainGameScreen.WORLD_HEIGHT * aspectRatio, MainGameScreen.WORLD_HEIGHT);
        camera.update();
        splashTexture = new Texture(Gdx.files.internal("libgdx_splash.jpg"));
        splashImage= new Image(splashTexture);
        assets = new Assets();
        assets.loadGameAssets();
        splashImageWidth=splashImageHeight*aspectRatio;
        splashImage.setPosition(Gdx.graphics.getWidth()/2f-splashImage.getWidth()/2f,Gdx.graphics.getHeight()/2f-splashImage.getHeight()/2f);
        //splashImage.setSize(splashImageWidth,splashImageHeight);
        splashImage.addAction(Actions.sequence(Actions.alpha(0f),Actions.fadeIn(1f),Actions.delay(2f),Actions.fadeOut(1f),Actions.run(new Runnable() {
            @Override
            public void run() {
                fadeComplete =true;
            }
        })));

        stage.addActor(splashImage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);
        stage.draw();

        if(assets.manager.update() && fadeComplete == true) {
            mainGameScreen.setScreen(new Game(assets, camera, spriteBatch));
            dispose();
        }

    }

    private void update(float delta) {
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        aspectRatio=(float)width/height;
        splashImageWidth=splashImageHeight*aspectRatio;

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
        splashTexture.dispose();
        stage.dispose();
    }
}
