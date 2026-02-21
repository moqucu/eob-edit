package com.github.martinfrank.eobedit.gui.fx;

import com.github.martinfrank.eobedit.data.Item;
import com.github.martinfrank.eobedit.data.Items;
import com.github.martinfrank.eobedit.image.ImageProvider;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Bounds;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.stage.Window;

import java.util.Arrays;
import java.util.function.Consumer;

public class FXSearchableItemPopup extends Popup {

    private final TextField searchField = new TextField();
    private final ListView<Item> listView = new ListView<>();
    private final ImageProvider imageProvider;
    private Item selectedItem = null;
    private Consumer<Item> onItemSelected;

    private static final Item EMPTY_ITEM = new Item(0, "(empty)", Items.ItemType.ITEM, -1);

    public FXSearchableItemPopup(Window owner, ImageProvider imageProvider, Consumer<Item> onItemSelected) {
        this.imageProvider = imageProvider;
        this.onItemSelected = onItemSelected;

        var root = new BorderPane();
        root.setStyle("-fx-background-color: white; -fx-border-color: gray; -fx-border-width: 1;");
        root.setPrefWidth(300);
        root.setPrefHeight(400);

        var searchBox = new HBox(5);
        searchBox.setStyle("-fx-padding: 5;");
        searchBox.getChildren().addAll(new Label("🔍"), searchField);
        root.setTop(searchBox);

        listView.setCellFactory(lv -> new ItemListCell());
        root.setCenter(listView);

        getContent().add(root);
        setAutoHide(true);

        searchField.textProperty().addListener((obs, old, newVal) -> filter());
        searchField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DOWN) {
                listView.requestFocus();
                if (!listView.getItems().isEmpty() && listView.getSelectionModel().getSelectedIndex() < 0) {
                    listView.getSelectionModel().select(0);
                }
            } else if (e.getCode() == KeyCode.ENTER) {
                if (!listView.getItems().isEmpty()) {
                    var selected = listView.getSelectionModel().getSelectedItem();
                    if (selected == null) selected = listView.getItems().get(0);
                    confirmSelection(selected);
                }
            }
        });

        listView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) confirmSelection(listView.getSelectionModel().getSelectedItem());
        });
        listView.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) confirmSelection(listView.getSelectionModel().getSelectedItem());
        });

        filter();
    }

    private void filter() {
        var text = searchField.getText().toLowerCase();
        var allItems = Items.getAllItems();
        var filtered = Arrays.stream(allItems)
                .filter(it -> {
                    var searchBase = (it.description + " " + it.getId() + " " + getTechnicalStats(it)).toLowerCase();
                    return searchBase.contains(text);
                })
                .toList();

        var items = FXCollections.<Item>observableArrayList();
        if (text.isEmpty() || "(empty)".contains(text)) {
            items.add(EMPTY_ITEM);
        }
        items.addAll(filtered);
        listView.setItems(items);
    }

    private String getTechnicalStats(Item item) {
        var sb = new StringBuilder();
        if ((item.flags & 0x20) != 0) sb.append("cursed ");
        if ((item.flags & 0x40) == 0) sb.append("unidentified ");
        if (item.armorClass != 0) sb.append("ac").append(item.armorClass).append(" ");
        if (item.dmgNumDiceS != 0) sb.append("dmg").append(item.dmgNumDiceS).append("d").append(item.dmgNumPipsS).append(" ");
        return sb.toString();
    }

    private void confirmSelection(Item item) {
        if (item != null) {
            onItemSelected.accept(item);
            hide();
        }
    }

    public void showPopup(Control anchor) {
        Bounds bounds = anchor.localToScreen(anchor.getBoundsInLocal());
        show(anchor, bounds.getMinX(), bounds.getMaxY());
        ((BorderPane)getContent().get(0)).setPrefWidth(anchor.getWidth());
        
        Platform.runLater(searchField::requestFocus);
    }

    private class ItemListCell extends ListCell<Item> {
        @Override
        protected void updateItem(Item item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.getDetailString());
                if (item.iconIndex >= 0) {
                    var bi = imageProvider.getItem(item);
                    if (bi != null) {
                        var iv = new ImageView(SwingFXUtils.toFXImage(bi, null));
                        iv.setFitWidth(16);
                        iv.setFitHeight(16);
                        setGraphic(iv);
                    } else {
                        setGraphic(null);
                    }
                } else {
                    setGraphic(null);
                }
            }
        }
    }
}
