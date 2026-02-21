package com.github.martinfrank.eobedit.gui;

import com.github.martinfrank.eobedit.data.GlobalItem;
import com.github.martinfrank.eobedit.data.Item;
import com.github.martinfrank.eobedit.data.Items;
import com.github.martinfrank.eobedit.data.PlayerData;
import com.github.martinfrank.eobedit.data.SavegameFile;
import com.github.martinfrank.eobedit.image.ImageProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class InventoryPanel extends JPanel {

    private static final int SLOT_COUNT = PlayerData.INVENTORY_SLOT_AMOUNT;

    // Sentinel used for the "empty" combo entry at position 0
    private static final Item EMPTY_ITEM = new Item(0, "(empty)", Items.ItemType.ITEM, -1);

    private final JComboBox<Item>[] slotCombos;
    private final JLabel[] iconLabels;
    private final ImageProvider imageProvider;
    private final SavegameFile saveFile;
    private PlayerData playerData;
    private int charIndex = -1;
    private boolean updating = false;

    @SuppressWarnings("unchecked")
    public InventoryPanel(ImageProvider imageProvider, SavegameFile saveFile) {
        this.imageProvider = imageProvider;
        this.saveFile = saveFile;
        this.slotCombos = new JComboBox[SLOT_COUNT];
        this.iconLabels = new JLabel[SLOT_COUNT];

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Inventory"));
        buildUI();
    }

    private void buildUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(1, 4, 1, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        for (int i = 0; i < SLOT_COUNT; i++) {
            gbc.gridy = i;

            gbc.gridx = 0;
            gbc.weightx = 0;
            JLabel slotLabel = new JLabel(PlayerData.getSlotName(i) + ":");
            add(slotLabel, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0;
            iconLabels[i] = new JLabel();
            iconLabels[i].setPreferredSize(new Dimension(24, 24));
            add(iconLabels[i], gbc);

            gbc.gridx = 2;
            gbc.weightx = 1.0;
            JComboBox<Item> combo = new JComboBox<>(buildComboModel());
            combo.setRenderer(new ItemListRenderer());
            combo.setMaximumRowCount(15);
            slotCombos[i] = combo;
            add(combo, gbc);

            gbc.gridx = 3;
            gbc.weightx = 0;
            JButton searchButton = new JButton("...");
            searchButton.setMargin(new Insets(0, 2, 0, 2));
            searchButton.setToolTipText("Search item...");
            add(searchButton, gbc);

            final int slot = i;
            combo.addActionListener(e -> onSlotChanged(slot));
            searchButton.addActionListener(e -> openSearchDialog(slot));
            iconLabels[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            iconLabels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openSearchDialog(slot);
                }
            });
        }
    }

    private void openSearchDialog(int slot) {
        Window ancestor = SwingUtilities.getWindowAncestor(this);
        Frame owner = (ancestor instanceof Frame) ? (Frame) ancestor : null;
        SearchableItemDialog dialog = new SearchableItemDialog(owner, imageProvider);
        dialog.setVisible(true);
        Item selected = dialog.getSelectedItem();
        if (selected != null) {
            slotCombos[slot].setSelectedItem(selected);
        }
    }

    private DefaultComboBoxModel<Item> buildComboModel() {
        Item[] allItems = Items.getAllItems();
        DefaultComboBoxModel<Item> model = new DefaultComboBoxModel<>();
        model.addElement(EMPTY_ITEM);
        for (Item item : allItems) {
            model.addElement(item);
        }
        return model;
    }

    private void onSlotChanged(int slot) {
        if (updating || playerData == null) {
            return;
        }
        Item selected = (Item) slotCombos[slot].getSelectedItem();
        if (selected == null || selected == EMPTY_ITEM) {
            playerData.setInventoryIndex(slot, 0);
            iconLabels[slot].setIcon(null);
        } else {
            int protoIdx = (selected.id[0] & 0xFF) | ((selected.id[1] & 0xFF) << 8);
            playerData.setInventoryIndex(slot, protoIdx);
            updateIcon(slot, selected);
        }
    }

    private void updateIcon(int slot, Item item) {
        if (item != null && item != EMPTY_ITEM) {
            BufferedImage img = imageProvider.getItem(item);
            if (img != null) {
                Image scaled = img.getScaledInstance(24, 24, Image.SCALE_SMOOTH);
                iconLabels[slot].setIcon(new ImageIcon(scaled));
            } else {
                iconLabels[slot].setIcon(null);
            }
        } else {
            iconLabels[slot].setIcon(null);
        }
    }

    public void setPlayerData(PlayerData playerData, int charIndex) {
        this.playerData = playerData;
        this.charIndex = charIndex;
        refreshFromModel();
    }

    public void reloadItems() {
        updating = true;
        try {
            for (int i = 0; i < SLOT_COUNT; i++) {
                slotCombos[i].setModel(buildComboModel());
            }
        } finally {
            updating = false;
        }
        refreshFromModel();
    }

    public void refreshFromModel() {
        updating = true;
        try {
            if (playerData == null || !playerData.hasPlayerData()) {
                for (int i = 0; i < SLOT_COUNT; i++) {
                    slotCombos[i].setSelectedIndex(0);
                    slotCombos[i].setEnabled(false);
                    iconLabels[i].setIcon(null);
                }
                return;
            }
            GlobalItem[] globalItems = saveFile.getGlobalItems();
            for (int i = 0; i < SLOT_COUNT; i++) {
                slotCombos[i].setEnabled(true);
                int globalIdx = playerData.getInventoryIndex(i);
                if (globalIdx > 0 && globalIdx < globalItems.length && !globalItems[globalIdx].isEmpty()) {
                    GlobalItem gi = globalItems[globalIdx];
                    Item match = findPrototype(gi);
                    if (match != null) {
                        slotCombos[i].setSelectedItem(match);
                        updateIcon(i, match);
                    } else {
                        slotCombos[i].setSelectedIndex(0);
                        iconLabels[i].setIcon(null);
                    }
                } else {
                    slotCombos[i].setSelectedIndex(0);
                    iconLabels[i].setIcon(null);
                }
            }
        } finally {
            updating = false;
        }
    }

    /** Finds the best prototype item whose properties match the given global item. */
    private Item findPrototype(GlobalItem gi) {
        for (Item item : Items.getAllItems()) {
            if (item.nameId == gi.nameId &&
                    item.nameUnid == gi.nameUnid &&
                    item.rawType == gi.type &&
                    item.iconIndex == gi.icon) {
                return item;
            }
        }
        // Fallback: match only nameId and nameUnid if perfect match failed
        for (Item item : Items.getAllItems()) {
            if (item.nameId == gi.nameId && item.nameUnid == gi.nameUnid) {
                return item;
            }
        }
        return null;
    }

    private class ItemListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Item) {
                Item item = (Item) value;
                String label = (item.description != null && !item.description.isEmpty()) ? item.description : "Item " + item.getId();
                setText(label);
                if (item != EMPTY_ITEM) {
                    BufferedImage img = imageProvider.getItem(item);
                    if (img != null) {
                        Image scaled = img.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                        setIcon(new ImageIcon(scaled));
                    } else {
                        setIcon(null);
                    }
                } else {
                    setIcon(null);
                }
            }
            return this;
        }
    }
}
