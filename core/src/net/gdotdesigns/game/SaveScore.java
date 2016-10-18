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
    Json json;
    String savedScore;

    public SaveScore(){
        preferences= Gdx.app.getPreferences("High Score");
        json = new Json();
    }

    public void writeScore(int currentScore){
        if(currentScore> readScore() ){
            String salt = "SALT";
            String score = json.toJson(currentScore);
            preferences.putString("savedScore", Base64Coder.encodeString(score));
            preferences.flush();
        }
    }

    public int readScore(){
        savedScore = preferences.getString("savedScore","0");
        if(savedScore == "0"){
            return 0;
        }
        int score = json.fromJson(int.class,Base64Coder.decodeString(savedScore));
        return score;
    }
}
