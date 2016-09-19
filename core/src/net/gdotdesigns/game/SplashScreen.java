package net.gdotdesigns.game;

import com.badlogic.gdx.Screen;

/**
 * Created by Todd on 9/19/2016.
 */
public class SplashScreen implements Screen {

    private MainGameScreen mainGameScreen;

    public SplashScreen(MainGameScreen mainGameScreen){
        this.mainGameScreen=mainGameScreen;
    }
    @Override
    public void show() {
        mainGameScreen.setScreen(new Game());
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
