package com.github.martinfrank.eobedit.gui.fx;

import com.github.martinfrank.eobedit.data.Items;
import com.github.martinfrank.eobedit.data.PlayerData;
import com.github.martinfrank.eobedit.data.SavegameFile;
import com.github.martinfrank.eobedit.event.ChangeEventType;
import com.github.martinfrank.eobedit.event.PlayerDataChangeEventListener;
import com.github.martinfrank.eobedit.image.ImageProvider;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.prefs.Preferences;

public class FXEditorController implements PlayerDataChangeEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(FXEditorController.class);
    private static final String APP_TITLE = "EoB Savegame Editor (JavaFX)";
    private static final String PREF_GAME_DATA_PATH = "gameDataPath";

    private final SavegameFile savegameFile = new SavegameFile();
    private final ImageProvider imageProvider = new ImageProvider();
    
    private final FXStatsPanel statsPanel = new FXStatsPanel();
    private final FXInventoryPanel inventoryPanel = new FXInventoryPanel(imageProvider, savegameFile);
    
    private final ComboBox<String> playerSelector = new ComboBox<>();
    private final Label statusLabel = new Label("Ready");
    private final Preferences prefs = Preferences.userNodeForPackage(FXEditorController.class);
    private final Stage stage;
    private final BorderPane root = new BorderPane();

    private File currentFile;

    public FXEditorController(Stage stage) {
        this.stage = stage;
        savegameFile.registerChangeListener(this);
        buildUI();
        updateStatusLabel();
        tryLoadStoredGameData();
    }

    public Parent getView() {
        return root;
    }

    private void buildUI() {
        // Menu Bar
        var menuBar = new MenuBar();
        var fileMenu = new Menu("File");
        var openItem = new MenuItem("Open...");
        openItem.setAccelerator(KeyCombination.valueOf("Shortcut+L"));
        openItem.setOnAction(e -> onOpen());
        var saveItem = new MenuItem("Save");
        saveItem.setAccelerator(KeyCombination.valueOf("Shortcut+S"));
        saveItem.setOnAction(e -> onSave());
        var quitItem = new MenuItem("Quit");
        quitItem.setAccelerator(KeyCombination.valueOf("Shortcut+Q"));
        quitItem.setOnAction(e -> onClose());
        fileMenu.getItems().addAll(openItem, saveItem, new SeparatorMenuItem(), quitItem);

        var settingsMenu = new Menu("Settings");
        var gameDataItem = new MenuItem("Set Game Data Path...");
        gameDataItem.setOnAction(e -> onSetGameDataPath());
        settingsMenu.getItems().add(gameDataItem);
        
        menuBar.getMenus().addAll(fileMenu, settingsMenu);

        // Top Panel
        var topPanel = new HBox(10);
        topPanel.setPadding(new Insets(10));
        topPanel.getChildren().addAll(new Label("Player:"), playerSelector);
        playerSelector.setDisable(true);
        playerSelector.setOnAction(e -> onPlayerSelected());

        var topVBox = new VBox(menuBar, topPanel);
        root.setTop(topVBox);

        // Center Split Pane
        var splitPane = new SplitPane();
        var invScroll = new ScrollPane(inventoryPanel);
        invScroll.setFitToWidth(true);
        splitPane.getItems().addAll(statsPanel, invScroll);
        splitPane.setDividerPositions(0.3);
        
        root.setCenter(splitPane);

        // Status Bar
        var statusBar = new HBox(5);
        statusBar.setPadding(new Insets(5));
        statusBar.setStyle("-fx-border-color: lightgray; -fx-border-width: 1 0 0 0;");
        statusBar.getChildren().add(statusLabel);
        root.setBottom(statusBar);
    }

    private void onOpen() {
        if (!confirmDiscardChanges()) return;

        var chooser = new FileChooser();
        chooser.setTitle("Open Savegame File");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Savegames", "EOBDATA.SAV", "*.SAV"));
        var file = chooser.showOpenDialog(stage);
        if (file != null) {
            loadFile(file);
        }
    }

    public void loadFile(File file) {
        try {
            savegameFile.load(file);
            currentFile = file;
            populatePlayerSelector();
            updateTitle();
        } catch (IOException | IllegalArgumentException ex) {
            showError("Failed to load file", ex.getMessage());
        }
    }

    private void populatePlayerSelector() {
        playerSelector.getItems().clear();
        for (int i = 0; i < SavegameFile.AMOUNT_CHARACTERS; i++) {
            var pd = savegameFile.getPlayer(i);
            String label = pd.hasPlayerData() ? "Player " + i + " - \"" + pd.getName().trim() + "\"" : "Player " + i + " - (empty)";
            playerSelector.getItems().add(label);
        }
        playerSelector.setDisable(false);
        playerSelector.getSelectionModel().select(0);
    }

    private void onPlayerSelected() {
        int idx = playerSelector.getSelectionModel().getSelectedIndex();
        if (idx < 0) return;
        var pd = savegameFile.getPlayer(idx);
        statsPanel.setPlayerData(pd);
        inventoryPanel.setPlayerData(pd, idx);
    }

    private void onSave() {
        if (currentFile == null) return;
        try {
            savegameFile.save();
            updateTitle();
            showInfo("Save", "Saved successfully.");
        } catch (IOException ex) {
            showError("Failed to save", ex.getMessage());
        }
    }

    public boolean canClose() {
        return confirmDiscardChanges();
    }

    private void onClose() {
        if (canClose()) {
            Platform.exit();
            System.exit(0);
        }
    }

    private boolean confirmDiscardChanges() {
        if (savegameFile.hasUnsavedChanges()) {
            var alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Unsaved Changes");
            alert.setHeaderText("You have unsaved changes.");
            alert.setContentText("Discard them?");
            var result = alert.showAndWait();
            return result.isPresent() && result.get() == ButtonType.OK;
        }
        return true;
    }

    private void updateTitle() {
        String title = APP_TITLE;
        if (currentFile != null) title += " - " + currentFile.getName();
        if (savegameFile.hasUnsavedChanges()) title += " *";
        stage.setTitle(title);
    }

    private void onSetGameDataPath() {
        var chooser = new DirectoryChooser();
        chooser.setTitle("Select Game Data Directory");
        var storedPath = prefs.get(PREF_GAME_DATA_PATH, null);
        if (storedPath != null) {
            File initialDir = new File(storedPath);
            if (initialDir.exists()) chooser.setInitialDirectory(initialDir);
        }
        var dir = chooser.showDialog(stage);
        if (dir != null) {
            loadGameData(dir);
            prefs.put(PREF_GAME_DATA_PATH, dir.getAbsolutePath());
        }
    }

    public void loadGameData(File gameDir) {
        try {
            Items.loadFromGameData(gameDir);
            savegameFile.resolveGlobalItemNames();
            inventoryPanel.reloadItems();
            updateStatusLabel();
            LOGGER.info("Loaded game data from {}", gameDir);
        } catch (IOException ex) {
            LOGGER.error("Failed to load game data", ex);
            showError("Failed to load game data", ex.getMessage());
        }
    }

    private void tryLoadStoredGameData() {
        var storedPath = prefs.get(PREF_GAME_DATA_PATH, null);
        if (storedPath != null) {
            var dir = new File(storedPath);
            if (dir.isDirectory()) loadGameData(dir);
        }
    }

    private void updateStatusLabel() {
        if (Items.isUsingGameData()) {
            statusLabel.setText("Items: Game Data (" + Items.getItemCount() + " items)");
        } else {
            statusLabel.setText("Items: Built-in (" + Items.getItemCount() + " items)");
        }
    }

    @Override
    public void playerDataChanged(int playerDataIndex, ChangeEventType type) {
        Platform.runLater(() -> {
            updateTitle();
            int selectedIdx = playerSelector.getSelectionModel().getSelectedIndex();
            if (selectedIdx == playerDataIndex) {
                if (type == ChangeEventType.STATS) {
                    statsPanel.refreshFromModel();
                } else if (type == ChangeEventType.INVENTORY) {
                    inventoryPanel.refreshFromModel();
                } else if (type == ChangeEventType.NAME || type == ChangeEventType.LOAD_DATA) {
                    populatePlayerSelector();
                }
            }
        });
    }

    private void showError(String title, String content) {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void showInfo(String title, String content) {
        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
