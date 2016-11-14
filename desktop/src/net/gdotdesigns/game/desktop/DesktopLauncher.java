package net.gdotdesigns.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import net.gdotdesigns.game.AdController;
import net.gdotdesigns.game.GooglePlayServices;
import net.gdotdesigns.game.MainGameScreen;

public class DesktopLauncher implements AdController ,GooglePlayServices {

	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width= MainGameScreen.WIDTH;
		config.height =MainGameScreen.HEIGHT;
		config.title=MainGameScreen.TITLE;
		new LwjglApplication(new MainGameScreen(new DesktopLauncher(),new DesktopLauncher()), config);
	}

	@Override
	public void showorLoadInterstitials(Runnable runnable) {
        Gdx.app.postRunnable(runnable);
    }

	@Override
	public boolean isSignedInGPGS() {
		return false;
	}

	@Override
	public void signInGPGS() {

	}

	@Override
	public void submitScoreGPGS(int score, String id) {

	}

	@Override
	public void unlockAchievementGPGS(String achievementId) {

	}

	@Override
	public void getLeaderboardGPGS(String id) {

	}

	@Override
	public void getAchievementsGPGS() {

	}

	@Override
	public void signOutGPGS() {

	}

	@Override
	public void disconnectGPGS() {

	}

	@Override
	public boolean getConnectionStatus() {
		return false;
	}
}


