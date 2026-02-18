package com.github.martinfrank.eobedit;

import com.github.martinfrank.eobedit.gui.EditorFrame;

import javax.swing.*;
import java.io.File;

public class App {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EditorFrame frame = new EditorFrame();
            frame.setVisible(true);
            if (args != null && args.length > 0) {
                frame.loadFile(new File(args[0]));
            }
        });
    }
}
