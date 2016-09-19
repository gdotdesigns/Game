package net.gdotdesigns.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import net.gdotdesigns.game.Game;
import net.gdotdesigns.game.MainGameScreen;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width= Game.WIDTH;
		config.height =Game.HEIGHT;
		config.title=Game.TITLE;
		new LwjglApplication(new MainGameScreen(), config);
	}
}
