package mx.cotapro.dev.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import mx.cotapro.dev.Tutifruti;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 400;
		config.height = 700;
		config.title = "Tutifruti";
		new LwjglApplication(new Tutifruti(), config);
	}
}
