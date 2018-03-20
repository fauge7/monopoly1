package monopoly.core.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import monopoly.core.Monopoly;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

			config.height = Monopoly.HEIGHT;
			config.width = Monopoly.WIDTH;
			config.title = Monopoly.TITLE;
			config.resizable = false;

		new LwjglApplication(new Monopoly(), config);
	}
}
