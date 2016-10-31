package net.gdotdesigns.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AndroidLauncher extends AndroidApplication {

		private InterstitialAd interstitialAd;
		private static final String INTERSTITIAL_UNIT_AD ="ca-app-pub-2895382750471159~5396822225";
		private static final String TEST_DEVICE= "8ABB25975BCF7ED6E7C49D16043D1A12";
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(INTERSTITIAL_UNIT_AD);
        requestNewInterstitial();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MainGameScreen(), config);
	}

    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(TEST_DEVICE)
                .build();
        interstitialAd.loadAd(adRequest);
    }
}
