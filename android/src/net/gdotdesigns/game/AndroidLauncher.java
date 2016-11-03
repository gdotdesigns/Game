package net.gdotdesigns.game;

import android.os.Bundle;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AndroidLauncher extends AndroidApplication implements AdController, GooglePlayServices {

    private InterstitialAd interstitialAd;
    private AdRequest adRequest;
    private int playCount = 0;
    private static final int MAX_PLAY_COUNT = 1;
	private static final String INTERSTITIAL_UNIT_ID ="ca-app-pub-2895382750471159/5257221423";
	private static final String TEST_DEVICE= "8ABB25975BCF7ED6E7C49D16043D1A12";

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        interstitialAd = new InterstitialAd(this);
        adRequest = new AdRequest.Builder()
                .addTestDevice(TEST_DEVICE)
                .build();
        interstitialAd.setAdUnitId(INTERSTITIAL_UNIT_ID);
        interstitialAd.loadAd(adRequest);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MainGameScreen(this), config);
	}

    @Override
    public void showorLoadInterstitials(final Runnable runnable) {
        if(playCount >= MAX_PLAY_COUNT) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (runnable != null) {
                        if(interstitialAd.isLoaded()) {
                            interstitialAd.setAdListener(new AdListener() {
                                @Override
                                public void onAdClosed() {
                                    Gdx.app.postRunnable(runnable);
                                    interstitialAd.loadAd(adRequest);
                                }
                            });
                        }
                        else {
                            if(interstitialAd.isLoading() == false) {
                                interstitialAd.loadAd(adRequest);
                            }
                            Gdx.app.postRunnable(runnable);
                            return;

                        }
                    }
                    interstitialAd.show();
                    playCount = 0;

                }
            });
        }
        else{
            playCount++;
            Gdx.app.postRunnable(runnable);
        }
    }

    @Override
    public boolean getSignedInGPGS() {
        return false;
    }

    @Override
    public void loginGPGS() {

    }

    @Override
    public void submitScoreGPGS(int score, String id) {

    }

    @Override
    public void unlockAchievementGPGS(String achievementId) {

    }

    @Override
    public void getLeaderboardGPGS() {

    }

    @Override
    public void getAchievementsGPGS() {

    }
}
