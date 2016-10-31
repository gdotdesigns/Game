package net.gdotdesigns.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.InterstitialAd;

import net.gdotdesigns.game.Game;

public class AndroidLauncher extends AndroidApplication {

		private InterstitialAd interstitialAd;
		private static final String INTERSTITIAL_UNTI_AD ="ca-app-pub-2895382750471159~5396822225";
		private static final String TEST_DEVICE= "";
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        interstitialAd = new InterstitialAd(this);
		interstitialAd.setAdUnitId(INTERSTITIAL_UNTI_AD);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MainGameScreen(), config);
	}
}
