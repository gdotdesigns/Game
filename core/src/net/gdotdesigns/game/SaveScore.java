package net.gdotdesigns.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;
import com.googlecode.gwt.crypto.bouncycastle.DataLengthException;
import com.googlecode.gwt.crypto.bouncycastle.InvalidCipherTextException;
import com.googlecode.gwt.crypto.client.TripleDesCipher;

/**
 * Created by Todd on 10/17/2016.
 * Using enryption with 3'rd party utility "GWT-Crypto" library.
 * http://badlogicgames.com/forum/viewtopic.php?t=14078&p=61731
 * https://github.com/libgdx/libgdx/wiki/Project-Setup-Gradle
 * Dependency added to android module build.gradle. GWT-Crypto.jar
 *
 */

public class SaveScore {
    //TODO Make a more unique cypher key.
    private static final byte[] GWT_DES_KEY = new byte[]{(byte)1,(byte)5,(byte)1,(byte)1,(byte)1,(byte)1,(byte)1,(byte)1,(byte)1,(byte)1,
            (byte)6,(byte)1,(byte)1,(byte)1,(byte)6,(byte)1,(byte)7,(byte)1,(byte)1,(byte)1,(byte)1,(byte)1,(byte)1,(byte)1};
    Preferences preferences;
    Json json;
    String savedScore;
    TripleDesCipher tripleDesCipher;
    boolean status;

    public SaveScore(){
        //TODO Encrypt the preference key and obfuscate this class.
        preferences= Gdx.app.getPreferences("High Score");
        json = new Json();
        tripleDesCipher = new TripleDesCipher();
        tripleDesCipher.setKey(GWT_DES_KEY);
    }

    public void writeScore(int currentScore){
        if(currentScore> readScore() ){
            String score = json.toJson(currentScore);
            try {
                score=tripleDesCipher.encrypt(Base64Coder.encodeString(score));
                //TODO Add functionality to the error code... ie. load a default score of 0 maybe.
            } catch (DataLengthException e1) {
                e1.printStackTrace();
            } catch (IllegalStateException e1) {
                e1.printStackTrace();
            } catch (InvalidCipherTextException e1) {
                e1.printStackTrace();
            }
            preferences.putString("savedScore",score );
            preferences.flush();
        }
    }

    public int readScore(){
        savedScore = preferences.getString("savedScore","0");
        if(savedScore == "0"){
            return 0;
        }
        try {
            savedScore=tripleDesCipher.decrypt(savedScore);
            //TODO Add functionality to the error code... ie. load a default score of 0 maybe.
        } catch (DataLengthException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (InvalidCipherTextException e) {
            e.printStackTrace();
        }
        int score  = json.fromJson(int.class,Base64Coder.decodeString(savedScore));
        return score;
    }

    public void writePlayServiceStatus(boolean status){
        preferences.putBoolean("status",status);
        preferences.flush();
    }

    public boolean readPlayServiceStatus(){
        status = preferences.getBoolean("status",false);
        return status;
    }


}
