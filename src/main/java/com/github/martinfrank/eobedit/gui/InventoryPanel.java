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

    // Sentinel used for the "empty" item
    private static final Item EMPTY_ITEM = new Item(0, "(empty)", Items.ItemType.ITEM, -1);

    private final JButton[] slotButtons;
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
        this.slotButtons = new JButton[SLOT_COUNT];
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
            JButton button = new JButton("(empty)");
            button.setHorizontalAlignment(SwingConstants.LEFT);
            button.setBackground(UIManager.getColor("TextField.background"));
            button.setMargin(new Insets(2, 5, 2, 2));
            slotButtons[i] = button;
            add(button, gbc);

            final int slot = i;
            button.addActionListener(e -> openSearchPopup(slot));
            iconLabels[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            iconLabels[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openSearchPopup(slot);
                }
            });
        }
    }

    private void openSearchPopup(int slot) {
        if (playerData == null) return;
        
        Window ancestor = SwingUtilities.getWindowAncestor(this);
        SearchableItemPopup popup = new SearchableItemPopup(ancestor, imageProvider);
        Item selected = popup.showPopup(slotButtons[slot]);
        
        if (selected != null) {
            onItemChosen(slot, selected);
        }
    }

    private void onItemChosen(int slot, Item item) {
        if (updating || playerData == null) {
            return;
        }
        if (item == null || item == EMPTY_ITEM) {
            playerData.setInventoryIndex(slot, 0);
            slotButtons[slot].setText("(empty)");
            slotButtons[slot].setToolTipText(null);
            iconLabels[slot].setToolTipText(null);
            iconLabels[slot].setIcon(null);
        } else {
            int protoIdx = (item.id[0] & 0xFF) | ((item.id[1] & 0xFF) << 8);
            playerData.setInventoryIndex(slot, protoIdx);
            slotButtons[slot].setText(item.description);
            String tooltip = item.getDetailString();
            slotButtons[slot].setToolTipText(tooltip);
            iconLabels[slot].setToolTipText(tooltip);
            updateIcon(slot, item);
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
        // No longer using combo models, but we need to refresh the current display
        refreshFromModel();
    }

    public void refreshFromModel() {
        updating = true;
        try {
            if (playerData == null || !playerData.hasPlayerData()) {
                for (int i = 0; i < SLOT_COUNT; i++) {
                    slotButtons[i].setText("(empty)");
                    slotButtons[i].setEnabled(false);
                    iconLabels[i].setIcon(null);
                }
                return;
            }
            GlobalItem[] globalItems = saveFile.getGlobalItems();
            for (int i = 0; i < SLOT_COUNT; i++) {
                slotButtons[i].setEnabled(true);
                int globalIdx = playerData.getInventoryIndex(i);
                if (globalIdx > 0 && globalIdx < globalItems.length && !globalItems[globalIdx].isEmpty()) {
                    GlobalItem gi = globalItems[globalIdx];
                    Item match = findPrototype(gi);
                    if (match != null) {
                        slotButtons[i].setText(match.description);
                        String tooltip = match.getDetailString();
                        slotButtons[i].setToolTipText(tooltip);
                        iconLabels[i].setToolTipText(tooltip);
                        updateIcon(i, match);
                    } else {
                        String label = "Item #" + globalIdx;
                        slotButtons[i].setText(label);
                        slotButtons[i].setToolTipText(label);
                        iconLabels[i].setToolTipText(label);
                        iconLabels[i].setIcon(null);
                    }
                } else {
                    slotButtons[i].setText("(empty)");
                    slotButtons[i].setToolTipText(null);
                    iconLabels[i].setToolTipText(null);
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
}
