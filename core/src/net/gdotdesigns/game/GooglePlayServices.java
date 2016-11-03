package net.gdotdesigns.game;

/**
 * Created by Todd on 11/2/2016.
 */

public interface GooglePlayServices {

    //http://stackoverflow.com/questions/32982472/libgdx-google-play-game-services-in-android-studio

    boolean getSignedInGPGS();
    void loginGPGS();
    void submitScoreGPGS(int score, String id);
    void unlockAchievementGPGS(String achievementId);
    void getLeaderboardGPGS();
    void getAchievementsGPGS();

}
