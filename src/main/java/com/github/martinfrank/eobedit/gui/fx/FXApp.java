package com.github.martinfrank.eobedit.gui.fx;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.File;

public class FXApp extends Application {

    private static File initialSavegame = null;
    private static File initialGameData = null;

    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if ("--game-data".equals(args[i]) && i + 1 < args.length) {
                initialGameData = new File(args[++i]);
            } else if (initialSavegame == null) {
                initialSavegame = new File(args[i]);
            }
        }
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        var controller = new FXEditorController(primaryStage);
        var scene = new Scene(controller.getView(), 1000, 700);
        
        primaryStage.setTitle("EoB Savegame Editor (JavaFX)");
        primaryStage.setScene(scene);
        primaryStage.show();

        if (initialGameData != null) {
            controller.loadGameData(initialGameData);
        }
        if (initialSavegame != null) {
            controller.loadFile(initialSavegame);
        }
        
        primaryStage.setOnCloseRequest(e -> {
            if (!controller.canClose()) {
                e.consume();
            } else {
                Platform.exit();
                System.exit(0);
            }
        });
    }
}
