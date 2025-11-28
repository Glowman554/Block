package de.toxicfox.block.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

import de.toxicfox.block.Configuration;
import de.toxicfox.block.MainGame;

public class Lwjgl3Launcher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Block");

        configuration.useVsync(true);
        configuration.setForegroundFPS(60);
        configuration.setWindowedMode(640*2, 640);
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        configuration.setOpenGLEmulation(Lwjgl3ApplicationConfiguration.GLEmulation.ANGLE_GLES20, 0, 0);

        new Lwjgl3Application(new MainGame(new Configuration(false, false, 8)), configuration);
    }
}
