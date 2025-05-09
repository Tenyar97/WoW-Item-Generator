package com.tenyar97.components;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class CustomComboBoxRenderer extends JPanel implements ListCellRenderer<Object> {

    private final JLabel label = new JLabel();
    private final ImageIcon itemBackground;

    public CustomComboBoxRenderer() {
        setLayout(new BorderLayout());
        setOpaque(false);

        label.setOpaque(false);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(Color.WHITE);
        add(label, BorderLayout.CENTER);

        URL bgPath = getClass().getResource("/assets/dropdownDrawer.png");
        this.itemBackground = (bgPath != null) ? new ImageIcon(bgPath) : new ImageIcon();  // fallback if not found
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        label.setText(value != null ? value.toString() : "");
        label.setForeground(isSelected && index != -1 ? Color.YELLOW : Color.WHITE);
        return this;
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (itemBackground.getImage() != null) {
            g.drawImage(itemBackground.getImage(), 0, 0, getWidth(), getHeight(), this);
        }
        super.paintComponent(g);
    }
}
