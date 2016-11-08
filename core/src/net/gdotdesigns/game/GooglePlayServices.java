package net.gdotdesigns.game;

/**
 * Created by Todd on 11/2/2016.
 */

public interface GooglePlayServices {

    //http://stackoverflow.com/questions/32982472/libgdx-google-play-game-services-in-android-studio
    //https://developers.google.com/android/guides/permissions
    //https://developers.google.com/games/services/android/quickstart

    boolean isSignedInGPGS();
    void signInGPGS();
    void submitScoreGPGS(int score, String id);
    void unlockAchievementGPGS(String achievementId);
    void getLeaderboardGPGS(String id);
    void getAchievementsGPGS();
    void signOutGPGS();
    void disconnectGPGS();
}
