package net.gdotdesigns.game;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.ads.InterstitialAd;

import net.gdotdesigns.game.Game;

public class AndroidLauncher extends AndroidApplication {

		private InterstitialAd interstitialAd;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        interstitialAd = new InterstitialAd(this);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MainGameScreen(), config);
	}
}
