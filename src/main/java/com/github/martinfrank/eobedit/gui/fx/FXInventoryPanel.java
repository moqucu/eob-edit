package com.github.martinfrank.eobedit.gui.fx;

import com.github.martinfrank.eobedit.data.Item;
import com.github.martinfrank.eobedit.data.Items;
import com.github.martinfrank.eobedit.data.PlayerData;
import com.github.martinfrank.eobedit.data.SavegameFile;
import com.github.martinfrank.eobedit.image.ImageProvider;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

import java.util.Arrays;

public class FXInventoryPanel extends GridPane {

    private static final int SLOT_COUNT = PlayerData.INVENTORY_SLOT_AMOUNT;
    private static final Item EMPTY_ITEM = new Item(0, "(empty)", Items.ItemType.ITEM, -1);

    private final Button[] slotButtons = new Button[SLOT_COUNT];
    private final ImageView[] iconViews = new ImageView[SLOT_COUNT];
    private final ImageProvider imageProvider;
    private final SavegameFile saveFile;
    private PlayerData playerData;
    private boolean updating = false;

    public FXInventoryPanel(ImageProvider imageProvider, SavegameFile saveFile) {
        this.imageProvider = imageProvider;
        this.saveFile = saveFile;

        setHgap(10);
        setVgap(5);
        setPadding(new Insets(10));
        buildUI();
    }

    private void buildUI() {
        for (int i = 0; i < SLOT_COUNT; i++) {
            var slotName = PlayerData.getSlotName(i);
            add(new Label(slotName + ":"), 0, i);

            var iv = new ImageView();
            iv.setFitWidth(24);
            iv.setFitHeight(24);
            iv.setPickOnBounds(true);
            iconViews[i] = iv;
            add(iv, 1, i);

            var btn = new Button("(empty)");
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            GridPane.setHgrow(btn, Priority.ALWAYS);
            slotButtons[i] = btn;
            add(btn, 2, i);

            final int slot = i;
            btn.setOnAction(e -> openSearchPopup(slot));
            iv.setOnMouseClicked(e -> openSearchPopup(slot));
            iv.setCursor(javafx.scene.Cursor.HAND);
        }
    }

    private void openSearchPopup(int slot) {
        if (playerData == null) return;
        var popup = new FXSearchableItemPopup(getScene().getWindow(), imageProvider, item -> onItemChosen(slot, item));
        popup.showPopup(slotButtons[slot]);
    }

    private void onItemChosen(int slot, Item item) {
        if (updating || playerData == null) return;
        
        if (item == null || item == EMPTY_ITEM) {
            playerData.setInventoryIndex(slot, 0);
        } else {
            var protoIdx = (item.id[0] & 0xFF) | ((item.id[1] & 0xFF) << 8);
            playerData.setInventoryIndex(slot, protoIdx);
        }
        refreshFromModel();
    }

    public void setPlayerData(PlayerData playerData, int charIndex) {
        this.playerData = playerData;
        refreshFromModel();
    }

    public void reloadItems() {
        refreshFromModel();
    }

    public void refreshFromModel() {
        updating = true;
        try {
            if (playerData == null || !playerData.hasPlayerData()) {
                for (int i = 0; i < SLOT_COUNT; i++) {
                    slotButtons[i].setText("(empty)");
                    slotButtons[i].setDisable(true);
                    slotButtons[i].setTooltip(null);
                    iconViews[i].setImage(null);
                    iconViews[i].setDisable(true);
                }
                return;
            }

            var globalItems = saveFile.getGlobalItems();
            for (int i = 0; i < SLOT_COUNT; i++) {
                slotButtons[i].setDisable(false);
                iconViews[i].setDisable(false);
                
                int globalIdx = playerData.getInventoryIndex(i);
                if (globalIdx > 0 && globalIdx < globalItems.length && !globalItems[globalIdx].isEmpty()) {
                    var gi = globalItems[globalIdx];
                    var match = findPrototype(gi);
                    if (match != null) {
                        slotButtons[i].setText(match.description);
                        var details = match.getDetailString();
                        slotButtons[i].setTooltip(new Tooltip(details));
                        Tooltip.install(iconViews[i], new Tooltip(details));
                        updateIcon(i, match);
                    } else {
                        slotButtons[i].setText("Item #" + globalIdx);
                        iconViews[i].setImage(null);
                    }
                } else {
                    slotButtons[i].setText("(empty)");
                    slotButtons[i].setTooltip(null);
                    Tooltip.uninstall(iconViews[i], null);
                    iconViews[i].setImage(null);
                }
            }
        } finally {
            updating = false;
        }
    }

    private void updateIcon(int slot, Item item) {
        var bi = imageProvider.getItem(item);
        if (bi != null) {
            iconViews[slot].setImage(SwingFXUtils.toFXImage(bi, null));
        } else {
            iconViews[slot].setImage(null);
        }
    }

    private Item findPrototype(com.github.martinfrank.eobedit.data.GlobalItem gi) {
        var allItems = Items.getAllItems();
        var perfectMatch = Arrays.stream(allItems)
                .filter(item -> item.nameId == gi.nameId &&
                        item.nameUnid == gi.nameUnid &&
                        item.rawType == gi.type &&
                        item.iconIndex == gi.icon)
                .findFirst();
        
        return perfectMatch.orElseGet(() -> Arrays.stream(allItems)
                .filter(item -> item.nameId == gi.nameId && item.nameUnid == gi.nameUnid)
                .findFirst()
                .orElse(null));
    }
}
