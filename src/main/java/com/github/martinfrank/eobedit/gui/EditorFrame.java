package com.github.martinfrank.eobedit.gui;

import com.github.martinfrank.eobedit.data.Items;
import com.github.martinfrank.eobedit.data.PlayerData;
import com.github.martinfrank.eobedit.data.SavegameFile;
import com.github.martinfrank.eobedit.event.ChangeEventType;
import com.github.martinfrank.eobedit.event.PlayerDataChangeEventListener;
import com.github.martinfrank.eobedit.image.ImageProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class EditorFrame extends JFrame implements PlayerDataChangeEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(EditorFrame.class);
    private static final String APP_TITLE = "EoB Savegame Editor";
    private static final String PREF_GAME_DATA_PATH = "gameDataPath";

    private final SavegameFile savegameFile = new SavegameFile();
    private final ImageProvider imageProvider = new ImageProvider();
    private final StatsPanel statsPanel = new StatsPanel();
    private final InventoryPanel inventoryPanel = new InventoryPanel(imageProvider, savegameFile);
    private final JComboBox<String> playerSelector = new JComboBox<>();
    private final JLabel statusLabel = new JLabel();
    private final Preferences prefs = Preferences.userNodeForPackage(EditorFrame.class);

    private File currentFile;

    public EditorFrame() {
        super(APP_TITLE);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        savegameFile.registerChangeListener(this);
        buildUI();
        updateStatusLabel();
        tryLoadStoredGameData();
        pack();
        setMinimumSize(new Dimension(700, 500));
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });
    }

    private void buildUI() {
        setJMenuBar(createMenuBar());

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Player:"));
        playerSelector.setEnabled(false);
        playerSelector.addActionListener(e -> onPlayerSelected());
        topPanel.add(playerSelector);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, statsPanel, new JScrollPane(inventoryPanel));
        splitPane.setResizeWeight(0.3);

        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        statusBar.add(statusLabel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(splitPane, BorderLayout.CENTER);
        getContentPane().add(statusBar, BorderLayout.SOUTH);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");

        JMenuItem openItem = new JMenuItem("Open...");
        openItem.addActionListener(e -> onOpen());
        fileMenu.add(openItem);

        JMenuItem saveItem = new JMenuItem("Save");
        saveItem.addActionListener(e -> onSave());
        fileMenu.add(saveItem);

        fileMenu.addSeparator();

        JMenuItem quitItem = new JMenuItem("Quit");
        quitItem.addActionListener(e -> onClose());
        fileMenu.add(quitItem);

        menuBar.add(fileMenu);

        JMenu settingsMenu = new JMenu("Settings");
        JMenuItem gameDataItem = new JMenuItem("Set Game Data Path...");
        gameDataItem.addActionListener(e -> onSetGameDataPath());
        settingsMenu.add(gameDataItem);
        menuBar.add(settingsMenu);

        return menuBar;
    }

    private void onOpen() {
        if (savegameFile.hasUnsavedChanges()) {
            int result = JOptionPane.showConfirmDialog(this,
                    "You have unsaved changes. Discard them?",
                    "Unsaved Changes", JOptionPane.YES_NO_CANCEL_OPTION);
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        }
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Open Savegame File");
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            loadFile(chooser.getSelectedFile());
        }
    }

    public void loadFile(File file) {
        try {
            savegameFile.load(file);
            currentFile = file;
            populatePlayerSelector();
            updateTitle();
        } catch (IOException | IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this,
                    "Failed to load file: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void populatePlayerSelector() {
        playerSelector.removeAllItems();
        for (int i = 0; i < SavegameFile.AMOUNT_CHARACTERS; i++) {
            PlayerData pd = savegameFile.getPlayer(i);
            String label;
            if (pd.hasPlayerData()) {
                label = "Player " + i + " - \"" + pd.getName().trim() + "\"";
            } else {
                label = "Player " + i + " - (empty)";
            }
            playerSelector.addItem(label);
        }
        playerSelector.setEnabled(true);
        playerSelector.setSelectedIndex(0);
    }

    private void onPlayerSelected() {
        int idx = playerSelector.getSelectedIndex();
        if (idx < 0) {
            return;
        }
        PlayerData pd = savegameFile.getPlayer(idx);
        statsPanel.setPlayerData(pd);
        inventoryPanel.setPlayerData(pd, idx);
    }

    private void onSave() {
        if (currentFile == null) {
            return;
        }
        try {
            savegameFile.save();
            updateTitle();
            JOptionPane.showMessageDialog(this, "Saved successfully.", "Save", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Failed to save: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onClose() {
        if (savegameFile.hasUnsavedChanges()) {
            int result = JOptionPane.showConfirmDialog(this,
                    "You have unsaved changes. Exit anyway?",
                    "Unsaved Changes", JOptionPane.YES_NO_OPTION);
            if (result != JOptionPane.YES_OPTION) {
                return;
            }
        }
        dispose();
        System.exit(0);
    }

    private void updateTitle() {
        String title = APP_TITLE;
        if (currentFile != null) {
            title += " - " + currentFile.getName();
        }
        if (savegameFile.hasUnsavedChanges()) {
            title += " *";
        }
        setTitle(title);
    }

    private void onSetGameDataPath() {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Select Game Data Directory");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        String storedPath = prefs.get(PREF_GAME_DATA_PATH, null);
        if (storedPath != null) {
            chooser.setCurrentDirectory(new File(storedPath));
        }
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File dir = chooser.getSelectedFile();
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
            JOptionPane.showMessageDialog(this,
                    "Failed to load game data: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void tryLoadStoredGameData() {
        String storedPath = prefs.get(PREF_GAME_DATA_PATH, null);
        if (storedPath != null) {
            File dir = new File(storedPath);
            if (dir.isDirectory()) {
                loadGameData(dir);
            }
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
        SwingUtilities.invokeLater(() -> {
            updateTitle();
            int selectedIdx = playerSelector.getSelectedIndex();
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
}
