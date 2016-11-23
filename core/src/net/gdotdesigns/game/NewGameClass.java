package net.gdotdesigns.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Todd on 11/22/2016.
 */

public class NewGameClass implements Screen {

    Assets assets;
    OrthographicCamera camera;
    SpriteBatch spriteBatch;
    EntityManager entityManager;
    SaveState saveScore;
    MainGameScreen mainGameScreen;
    MainMenu mainMenu;


    public NewGameClass(Assets assets, SpriteBatch spriteBatch, MainGameScreen mainGameScreen, MainMenu mainMenu){
        this.assets=assets;
        this.camera= new OrthographicCamera();
        this.spriteBatch=spriteBatch;
        this.entityManager=new EntityManager();//Made an object instead of a static class due to Android issues with glitched textures...
        saveScore = new SaveState();
        this.mainGameScreen=mainGameScreen;
        this.mainMenu=mainMenu;



    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

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

    }
}
