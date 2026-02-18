package com.github.martinfrank.eobedit.gui;

import com.github.martinfrank.eobedit.data.PlayerData;
import com.github.martinfrank.eobedit.data.Stat;

import javax.swing.*;
import java.awt.*;

public class StatsPanel extends JPanel {

    private final JSpinner[] currentSpinners = new JSpinner[Stat.Stats.values().length];
    private final JSpinner[] maxSpinners = new JSpinner[Stat.Stats.values().length];
    private PlayerData playerData;
    private boolean updating = false;

    public StatsPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Stats"));
        buildUI();
    }

    private void buildUI() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 4, 2, 4);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Header row
        gbc.gridy = 0;
        gbc.gridx = 1;
        add(new JLabel("Current", SwingConstants.CENTER), gbc);
        gbc.gridx = 2;
        add(new JLabel("Max", SwingConstants.CENTER), gbc);

        Stat.Stats[] stats = Stat.Stats.values();
        for (int i = 0; i < stats.length; i++) {
            Stat.Stats s = stats[i];
            gbc.gridy = i + 1;

            gbc.gridx = 0;
            gbc.anchor = GridBagConstraints.EAST;
            add(new JLabel(s.longName + ":"), gbc);

            gbc.anchor = GridBagConstraints.WEST;
            gbc.gridx = 1;
            JSpinner current = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
            current.setPreferredSize(new Dimension(60, 25));
            currentSpinners[i] = current;
            add(current, gbc);

            if (s != Stat.Stats.AC) {
                gbc.gridx = 2;
                JSpinner max = new JSpinner(new SpinnerNumberModel(0, 0, 255, 1));
                max.setPreferredSize(new Dimension(60, 25));
                maxSpinners[i] = max;
                add(max, gbc);
            }

            final int idx = i;
            current.addChangeListener(e -> onStatChanged(idx));
            if (maxSpinners[i] != null) {
                maxSpinners[i].addChangeListener(e -> onStatChanged(idx));
            }
        }
    }

    private void onStatChanged(int idx) {
        if (updating || playerData == null) {
            return;
        }
        Stat.Stats s = Stat.Stats.values()[idx];
        int cur = (Integer) currentSpinners[idx].getValue();
        if (s == Stat.Stats.AC) {
            playerData.setStat(s, cur);
        } else {
            int max = (Integer) maxSpinners[idx].getValue();
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
                    currentSpinners[i].setValue(0);
                    currentSpinners[i].setEnabled(false);
                    if (maxSpinners[i] != null) {
                        maxSpinners[i].setValue(0);
                        maxSpinners[i].setEnabled(false);
                    }
                }
                return;
            }
            Stat.Stats[] stats = Stat.Stats.values();
            for (int i = 0; i < stats.length; i++) {
                Stat stat = playerData.getStat(stats[i]);
                currentSpinners[i].setValue(stat.getCurrent());
                currentSpinners[i].setEnabled(true);
                if (maxSpinners[i] != null) {
                    maxSpinners[i].setValue(stat.getMax());
                    maxSpinners[i].setEnabled(true);
                }
            }
        } finally {
            updating = false;
        }
    }
}
