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

public class SaveState {
    //TODO Make a more unique cypher key.Do last since this is on public Github repo.
    private static final byte[] GWT_DES_KEY = new byte[]{(byte)1,(byte)5,(byte)1,(byte)1,(byte)1,(byte)1,(byte)1,(byte)1,(byte)1,(byte)1,
            (byte)6,(byte)1,(byte)1,(byte)1,(byte)6,(byte)1,(byte)7,(byte)1,(byte)1,(byte)1,(byte)1,(byte)1,(byte)1,(byte)1};
    Preferences preferences;
    Json json;
    String savedScore;
    TripleDesCipher tripleDesCipher;
    boolean status;

    public SaveState(){
        preferences= Gdx.app.getPreferences("High Score");
        json = new Json();
        tripleDesCipher = new TripleDesCipher();
        tripleDesCipher.setKey(GWT_DES_KEY);
    }

    public void flush(){
        preferences.flush();
    }

    public void writeScore(int currentScore){
            String score = json.toJson(currentScore);
            try {
                score=tripleDesCipher.encrypt(Base64Coder.encodeString(score));

            } catch (DataLengthException e1) {

            } catch (IllegalStateException e1) {

            } catch (InvalidCipherTextException e1) {

            }
            preferences.putString("savedScore",score );
        }

    public int readScore(){
        savedScore = preferences.getString("savedScore","0");
        if(savedScore == "0"){
            return 0;
        }
        try {
            savedScore=tripleDesCipher.decrypt(savedScore);
        }
        catch (DataLengthException e) {
            return 0;
        }
        catch (IllegalStateException e) {
            return 0;
        }
        catch (InvalidCipherTextException e) {
            return 0;
        }
        catch (ArrayIndexOutOfBoundsException e) {
            return 0;
        }
        int score  = json.fromJson(int.class,Base64Coder.decodeString(savedScore));
        return score;
    }

    //Decide if to auto log in to GPGS.
    public void writePlayServiceStatus(boolean status){
        preferences.putBoolean("status",status);
        preferences.flush();
    }

    public boolean readPlayServiceStatus(){
        status = preferences.getBoolean("status",false);
        return status;
    }

    //Track if local score has been uploaded to GPGS.
    public void writeScoreSavedStatus(boolean savedStatus){
        preferences.putBoolean("savedStatus", savedStatus);
    }

    public boolean readScoreSavedStatus(){
        boolean savedStatus = preferences.getBoolean("savedStatus",false);
        return savedStatus;
    }


    //stores a value stating (0 = locked, 1 = unlocked but not updated to GPGS, 2 = unlocked and updated to GPGS.
    public void writeAchievement1(int value){
        preferences.putInteger("Achievement1",value);
    }
    public void writeAchievement2(int value){
        preferences.putInteger("Achievement2",value);
    }
    public void writeAchievement3(int value){
        preferences.putInteger("Achievement3",value);
    }
    public void writeAchievement4(int value){
        preferences.putInteger("Achievement4",value);
    }
    public void writeAchievement5(int value){
        preferences.putInteger("Achievement5",value);
    }

    public int readAchievement1(){
        int value = preferences.getInteger("Achievement1",0);
        return value;
    }
    public int readAchievement2(){
        int value = preferences.getInteger("Achievement2",0);
        return value;
    }
    public int readAchievement3(){
        int value = preferences.getInteger("Achievement3",0);
        return value;
    }
    public int readAchievement4(){
        int value = preferences.getInteger("Achievement4",0);
        return value;
    }
    public int readAchievement5(){
        int value = preferences.getInteger("Achievement5",0);
        return value;
    }
}
