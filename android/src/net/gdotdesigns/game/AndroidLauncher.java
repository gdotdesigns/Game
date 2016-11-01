package net.gdotdesigns.game;

import android.os.Bundle;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AndroidLauncher extends AndroidApplication implements AdController {

    private InterstitialAd interstitialAd;
    //private static final String INTERSTITIAL_UNIT_ID ="ca-app-pub-2895382750471159~5396822225";
	private static final String INTERSTITIAL_UNIT_ID ="ca-app-pub-2895382750471159/5257221423";
	private static final String TEST_DEVICE= "8ABB25975BCF7ED6E7C49D16043D1A12";

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        interstitialAd = new InterstitialAd(this);
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(TEST_DEVICE)
                .build();
        interstitialAd.setAdUnitId(INTERSTITIAL_UNIT_ID);
        interstitialAd.loadAd(adRequest);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MainGameScreen(this), config);
	}

//TODO Check for internet connection to disable ad if no connection?????
    @Override
    public void showorLoadInterstitials(final Runnable runnable) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (runnable != null) {
                    interstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            Gdx.app.postRunnable(runnable);
                            AdRequest adRequest = new AdRequest.Builder()
                                    .addTestDevice(TEST_DEVICE)
                                    .build();
                            interstitialAd.loadAd(adRequest);
                        }
                    });
                }
                interstitialAd.show();
            }
        });
    }
}
