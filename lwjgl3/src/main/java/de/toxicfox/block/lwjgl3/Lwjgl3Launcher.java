package de.toxicfox.block.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import de.toxicfox.block.Configuration;
import de.toxicfox.block.MainGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Lwjgl3Launcher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Lwjgl3Launcher::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Block Game Configuration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 350);
        frame.setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JCheckBox touchModeCheck = new JCheckBox("Touch Mode");
        JCheckBox persistWorldCheck = new JCheckBox("Persist World");
        persistWorldCheck.setSelected(true);
        JCheckBox captureCursorCheck = new JCheckBox("Capture Cursor");
        captureCursorCheck.setSelected(true);

        JLabel renderLabel = new JLabel("Render Distance:");
        JSpinner renderDistanceSpinner = new JSpinner(new SpinnerNumberModel(8, 1, 32, 1));

        JLabel folderLabel = new JLabel("World Folder:");
        JTextField folderField = new JTextField("world");

        panel.add(touchModeCheck);
        panel.add(new JLabel());
        panel.add(persistWorldCheck);
        panel.add(new JLabel());
        panel.add(captureCursorCheck);
        panel.add(new JLabel());
        panel.add(renderLabel);
        panel.add(renderDistanceSpinner);
        panel.add(folderLabel);
        panel.add(folderField);

        JButton launchButton = new JButton("Launch");
        launchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean touchMode = touchModeCheck.isSelected();
                boolean persistWorld = persistWorldCheck.isSelected();
                boolean captureCursor = captureCursorCheck.isSelected();
                int renderDistance = (int) renderDistanceSpinner.getValue();
                String worldFolder = folderField.getText();

                Configuration config = new Configuration(touchMode, persistWorld, renderDistance, worldFolder, captureCursor);

                frame.dispose();

                launchGame(config);
            }
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.add(launchButton, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void launchGame(Configuration config) {
        System.out.println("Launching game with configuration:");
        System.out.println("Touch Mode: " + config.touchMode());
        System.out.println("Persist World: " + config.persistWorld());
        System.out.println("Render Distance: " + config.renderDistance());
        System.out.println("World Folder: " + config.worldFolder());
        System.out.println("Capture Cursor: " + config.captureCursor());

        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Block");
        configuration.useVsync(true);
        configuration.setForegroundFPS(60);
        configuration.setWindowedMode(640*2, 640);
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        configuration.setOpenGLEmulation(Lwjgl3ApplicationConfiguration.GLEmulation.ANGLE_GLES20, 0, 0);

        new Lwjgl3Application(new MainGame(config), configuration);
    }
}
