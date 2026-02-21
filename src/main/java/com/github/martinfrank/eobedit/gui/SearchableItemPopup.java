package com.github.martinfrank.eobedit.gui;

import com.github.martinfrank.eobedit.data.Item;
import com.github.martinfrank.eobedit.data.Items;
import com.github.martinfrank.eobedit.image.ImageProvider;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A popup-like dialog that allows searching and selecting an item.
 * Designed to appear integrated with the UI.
 */
public class SearchableItemPopup extends JDialog {

    private final JTextField searchField = new JTextField();
    private final JList<Item> itemList = new JList<>();
    private final ImageProvider imageProvider;
    private Item selectedItem = null;
    private boolean confirmed = false;

    private static final Item EMPTY_ITEM = new Item(0, "(empty)", Items.ItemType.ITEM, -1);

    public SearchableItemPopup(Window owner, ImageProvider imageProvider) {
        super(owner, ModalityType.APPLICATION_MODAL);
        this.imageProvider = imageProvider;

        setUndecorated(true);
        setLayout(new BorderLayout());
        ((JComponent)getContentPane()).setBorder(BorderFactory.createLineBorder(Color.GRAY));

        buildUI();
        
        // Close on focus lost
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {}
            @Override
            public void windowLostFocus(WindowEvent e) {
                if (isShowing()) {
                    dispose();
                }
            }
        });

        // Close on Escape
        getRootPane().registerKeyboardAction(e -> dispose(),
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0),
                JComponent.WHEN_IN_FOCUSED_WINDOW);
    }

    private void buildUI() {
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(UIManager.getColor("TextField.background"));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        
        JLabel searchIcon = new JLabel("🔍 ");
        searchPanel.add(searchIcon, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);
        
        searchField.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
        add(searchPanel, BorderLayout.NORTH);

        itemList.setCellRenderer(new ItemListRenderer());
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollPane = new JScrollPane(itemList);
        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        add(scrollPane, BorderLayout.CENTER);

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
                if (itemList.getSelectedIndex() < 0) {
                    itemList.setSelectedIndex(0);
                }
                onConfirm();
            }
        });

        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    itemList.requestFocus();
                    if (itemList.getModel().getSize() > 0 && itemList.getSelectedIndex() < 0) {
                        itemList.setSelectedIndex(0);
                    }
                }
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
        
        itemList.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    onConfirm();
                }
            }
        });

        filter(); // Initial load
    }

    private void filter() {
        String text = searchField.getText().toLowerCase();
        Item[] allItems = Items.getAllItems();
        List<Item> filtered = Arrays.stream(allItems)
                .filter(it -> {
                    String searchBase = (it.description + " " + it.getId() + " " + getTechnicalStats(it)).toLowerCase();
                    return searchBase.contains(text);
                })
                .collect(Collectors.toList());
        
        DefaultListModel<Item> model = new DefaultListModel<>();
        // Always allow clearing the slot
        if (text.isEmpty() || "(empty)".contains(text)) {
            model.addElement(EMPTY_ITEM);
        }
        for (Item it : filtered) {
            model.addElement(it);
        }
        itemList.setModel(model);
    }

    private String getTechnicalStats(Item item) {
        StringBuilder sb = new StringBuilder();
        if ((item.flags & 0x20) != 0) sb.append("cursed ");
        if ((item.flags & 0x40) == 0) sb.append("unidentified ");
        if (item.armorClass != 0) sb.append("ac").append(item.armorClass).append(" ");
        if (item.dmgNumDiceS != 0) sb.append("dmg").append(item.dmgNumDiceS).append("d").append(item.dmgNumPipsS).append(" ");
        return sb.toString();
    }

    private void onConfirm() {
        selectedItem = itemList.getSelectedValue();
        confirmed = true;
        dispose();
    }

    public Item showPopup(Component invoker) {
        Point location = invoker.getLocationOnScreen();
        setBounds(location.x, location.y + invoker.getHeight(), invoker.getWidth(), 300);
        
        SwingUtilities.invokeLater(searchField::requestFocusInWindow);
        setVisible(true);
        
        return confirmed ? selectedItem : null;
    }

    private class ItemListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Item) {
                Item item = (Item) value;
                setText(item.getDetailString() + " #" + item.getId());

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

        private boolean isCombatItem(Item item) {
            if (item.type == null) return false;
            String abbr = item.type.typeAbbreviation;
            return abbr.equals("PR") || abbr.equals("SE") || abbr.equals("TW") || abbr.equals("AR") || abbr.equals("SH") || abbr.equals("DA");
        }
    }
}
