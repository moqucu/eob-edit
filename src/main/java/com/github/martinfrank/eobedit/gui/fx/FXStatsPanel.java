package com.github.martinfrank.eobedit.gui.fx;

import com.github.martinfrank.eobedit.data.PlayerData;
import com.github.martinfrank.eobedit.data.Stat;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;

public class FXStatsPanel extends GridPane {

    @SuppressWarnings("unchecked")
    private final Spinner<Integer>[] currentSpinners = new Spinner[Stat.Stats.values().length];
    @SuppressWarnings("unchecked")
    private final Spinner<Integer>[] maxSpinners = new Spinner[Stat.Stats.values().length];
    private PlayerData playerData;
    private boolean updating = false;

    public FXStatsPanel() {
        setHgap(10);
        setVgap(5);
        setPadding(new Insets(10));
        setStyle("-fx-border-color: lightgray; -fx-border-width: 1; -fx-border-radius: 5;");
        buildUI();
    }

    private void buildUI() {
        add(new Label("Stats"), 0, 0, 3, 1);
        
        var curLabel = new Label("Current");
        curLabel.setMaxWidth(Double.MAX_VALUE);
        curLabel.setAlignment(Pos.CENTER);
        add(curLabel, 1, 1);

        var maxLabel = new Label("Max");
        maxLabel.setMaxWidth(Double.MAX_VALUE);
        maxLabel.setAlignment(Pos.CENTER);
        add(maxLabel, 2, 1);

        var stats = Stat.Stats.values();
        for (int i = 0; i < stats.length; i++) {
            var s = stats[i];
            int row = i + 2;

            var nameLabel = new Label(s.longName + ":");
            GridPane.setConstraints(nameLabel, 0, row);
            add(nameLabel, 0, row);

            var current = createSpinner();
            currentSpinners[i] = current;
            add(current, 1, row);

            if (s != Stat.Stats.AC) {
                var max = createSpinner();
                maxSpinners[i] = max;
                add(max, 2, row);
            }

            final int idx = i;
            current.valueProperty().addListener((obs, oldVal, newVal) -> onStatChanged(idx));
            if (maxSpinners[i] != null) {
                maxSpinners[i].valueProperty().addListener((obs, oldVal, newVal) -> onStatChanged(idx));
            }
        }
    }

    private Spinner<Integer> createSpinner() {
        var spinner = new Spinner<Integer>(0, 255, 0);
        spinner.setEditable(true);
        spinner.setPrefWidth(80);
        return spinner;
    }

    private void onStatChanged(int idx) {
        if (updating || playerData == null) return;
        
        var s = Stat.Stats.values()[idx];
        int cur = currentSpinners[idx].getValue();
        if (s == Stat.Stats.AC) {
            playerData.setStat(s, cur);
        } else {
            int max = maxSpinners[idx].getValue();
            playerData.setStat(s, cur, max);
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
                for (int i = 0; i < currentSpinners.length; i++) {
                    currentSpinners[i].getValueFactory().setValue(0);
                    currentSpinners[i].setDisable(true);
                    if (maxSpinners[i] != null) {
                        maxSpinners[i].getValueFactory().setValue(0);
                        maxSpinners[i].setDisable(true);
                    }
                }
                return;
            }
            var stats = Stat.Stats.values();
            for (int i = 0; i < stats.length; i++) {
                var stat = playerData.getStat(stats[i]);
                currentSpinners[i].getValueFactory().setValue(stat.getCurrent());
                currentSpinners[i].setDisable(false);
                if (maxSpinners[i] != null) {
                    maxSpinners[i].getValueFactory().setValue(stat.getMax());
                    maxSpinners[i].setDisable(false);
                }
            }
        } finally {
            updating = false;
        }
    }
}
