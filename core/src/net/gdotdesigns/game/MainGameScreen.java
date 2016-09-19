package net.gdotdesigns.game;

import com.badlogic.gdx.*;

/**
 * Created by Todd on 9/19/2016.
 */
public class MainGameScreen extends com.badlogic.gdx.Game {

    @Override
    public void create() {

        this.setScreen(new SplashScreen(this));

    }
}
