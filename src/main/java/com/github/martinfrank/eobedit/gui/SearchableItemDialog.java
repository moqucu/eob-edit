package com.github.martinfrank.eobedit.gui;

import com.github.martinfrank.eobedit.data.Item;
import com.github.martinfrank.eobedit.data.Items;
import com.github.martinfrank.eobedit.image.ImageProvider;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SearchableItemDialog extends JDialog {

    private final JTextField searchField = new JTextField();
    private final JList<Item> itemList = new JList<>();
    private final ImageProvider imageProvider;
    private Item selectedItem = null;
    private boolean confirmed = false;

    public SearchableItemDialog(Frame owner, ImageProvider imageProvider) {
        super(owner, "Search Item", true);
        this.imageProvider = imageProvider;

        setLayout(new BorderLayout());
        buildUI();
        pack();
        setSize(400, 500);
        setLocationRelativeTo(owner);
    }

    private void buildUI() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        topPanel.add(new JLabel("Search: "), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        itemList.setCellRenderer(new ItemListRenderer());
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(itemList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton okButton = new JButton("OK");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filter(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filter(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filter(); }
        });

        searchField.addActionListener(e -> {
            if (itemList.getModel().getSize() > 0) {
                itemList.setSelectedIndex(0);
                onConfirm();
            }
        });

        itemList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    onConfirm();
                }
            }
        });

        okButton.addActionListener(e -> onConfirm());
        cancelButton.addActionListener(e -> dispose());

        filter(); // Initial load
    }

    private void filter() {
        String text = searchField.getText().toLowerCase();
        Item[] allItems = Items.getAllItems();
        List<Item> filtered = Arrays.stream(allItems)
                .filter(it -> it.description != null && it.description.toLowerCase().contains(text) || it.getId().toLowerCase().contains(text))
                .collect(Collectors.toList());
        
        DefaultListModel<Item> model = new DefaultListModel<>();
        for (Item it : filtered) {
            model.addElement(it);
        }
        itemList.setModel(model);
    }

    private void onConfirm() {
        selectedItem = itemList.getSelectedValue();
        confirmed = true;
        dispose();
    }

    public Item getSelectedItem() {
        return confirmed ? selectedItem : null;
    }

    private class ItemListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Item) {
                Item item = (Item) value;
                setText(item.description + " [" + item.getId() + "]");
                if (item.iconIndex >= 0) {
                    Image img = imageProvider.getItem(item);
                    if (img != null) {
                        setIcon(new ImageIcon(img.getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
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
