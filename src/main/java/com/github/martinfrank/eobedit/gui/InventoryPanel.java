package com.github.martinfrank.eobedit.gui;

import com.github.martinfrank.eobedit.data.Item;
import com.github.martinfrank.eobedit.data.Items;
import com.github.martinfrank.eobedit.data.PlayerData;
import com.github.martinfrank.eobedit.image.ImageProvider;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class InventoryPanel extends JPanel {

    private static final int SLOT_COUNT = PlayerData.INVENTORY_SLOT_AMOUNT;

    private final JComboBox<Item>[] slotCombos;
    private final JLabel[] iconLabels;
    private final ImageProvider imageProvider;
    private PlayerData playerData;
    private boolean updating = false;

    @SuppressWarnings("unchecked")
    public InventoryPanel(ImageProvider imageProvider) {
        this.imageProvider = imageProvider;
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
            JLabel slotLabel = new JLabel("Slot " + i + ":");
            add(slotLabel, gbc);

            gbc.gridx = 1;
            gbc.weightx = 0;
            iconLabels[i] = new JLabel();
            iconLabels[i].setPreferredSize(new Dimension(24, 24));
            add(iconLabels[i], gbc);

            gbc.gridx = 2;
            gbc.weightx = 1.0;
            JComboBox<Item> combo = new JComboBox<>(Items.ITEMS);
            combo.setRenderer(new ItemListRenderer());
            combo.setMaximumRowCount(15);
            slotCombos[i] = combo;
            add(combo, gbc);

            final int slot = i;
            combo.addActionListener(e -> onSlotChanged(slot));
        }
    }

    private void onSlotChanged(int slot) {
        if (updating || playerData == null) {
            return;
        }
        Item selected = (Item) slotCombos[slot].getSelectedItem();
        if (selected != null) {
            playerData.setInventory(slot, selected);
            updateIcon(slot, selected);
        }
    }

    private void updateIcon(int slot, Item item) {
        if (item != null) {
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

    public void setPlayerData(PlayerData playerData) {
        this.playerData = playerData;
        refreshFromModel();
    }

    public void refreshFromModel() {
        updating = true;
        try {
            if (playerData == null || !playerData.hasPlayerData()) {
                for (int i = 0; i < SLOT_COUNT; i++) {
                    slotCombos[i].setSelectedIndex(-1);
                    slotCombos[i].setEnabled(false);
                    iconLabels[i].setIcon(null);
                }
                return;
            }
            for (int i = 0; i < SLOT_COUNT; i++) {
                slotCombos[i].setEnabled(true);
                Item item = playerData.getInventory(i);
                if (item != null) {
                    slotCombos[i].setSelectedItem(item);
                    updateIcon(i, item);
                } else {
                    slotCombos[i].setSelectedIndex(0);
                    iconLabels[i].setIcon(null);
                }
            }
        } finally {
            updating = false;
        }
    }

    private class ItemListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Item) {
                Item item = (Item) value;
                setText(item.description != null ? item.description : item.getId());
                BufferedImage img = imageProvider.getItem(item);
                if (img != null) {
                    Image scaled = img.getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                    setIcon(new ImageIcon(scaled));
                } else {
                    setIcon(null);
                }
            }
            return this;
        }
    }
}
