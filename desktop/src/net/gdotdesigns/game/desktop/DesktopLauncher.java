package net.gdotdesigns.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import net.gdotdesigns.game.AdController;
import net.gdotdesigns.game.MainGameScreen;

public class DesktopLauncher implements AdController{
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width= MainGameScreen.WIDTH;
		config.height =MainGameScreen.HEIGHT;
		config.title=MainGameScreen.TITLE;
		new LwjglApplication(new MainGameScreen(new DesktopLauncher()), config);
	}

	@Override
	public void showorLoadInterstitials(Runnable runnable) {
        Gdx.app.postRunnable(runnable);
    }

}


