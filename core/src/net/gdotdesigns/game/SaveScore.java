package net.gdotdesigns.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

/**
 * Created by Todd on 10/17/2016.
 */
public class SaveScore {
    Preferences preferences;
    Base64Coder base64Coder;
    Json json;
    int highScore;

    public SaveScore(){
        preferences= Gdx.app.getPreferences("High Score");
        json = new Json();
    }

    public void writeScore(int score){
        preferences.putInteger("highScore", score);
        preferences.flush();
    }

    public int readScore(){
        highScore = preferences.getInteger("highScore",0);
        return highScore;
    }
}
