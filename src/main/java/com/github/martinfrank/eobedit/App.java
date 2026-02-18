package com.github.martinfrank.eobedit;

import com.github.martinfrank.eobedit.gui.EditorFrame;

import javax.swing.*;
import java.io.File;

public class App {

    public static void main(String[] args) {
        File savegameFile = null;
        File gameDataDir = null;

        for (int i = 0; i < args.length; i++) {
            if ("--game-data".equals(args[i]) && i + 1 < args.length) {
                gameDataDir = new File(args[++i]);
            } else if (savegameFile == null) {
                savegameFile = new File(args[i]);
            }
        }

        final File savegame = savegameFile;
        final File gameData = gameDataDir;

        SwingUtilities.invokeLater(() -> {
            EditorFrame frame = new EditorFrame();
            frame.setVisible(true);
            if (gameData != null) {
                frame.loadGameData(gameData);
            }
            if (savegame != null) {
                frame.loadFile(savegame);
            }
        });
    }
}
