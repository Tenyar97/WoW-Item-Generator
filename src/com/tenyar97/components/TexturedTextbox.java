package com.tenyar97.components;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.tenyar97.Main;
import com.tenyar97.util.ResourceLoader;

public class TexturedTextbox extends JPanel {
    private BufferedImage backgroundImage;
    private JTextArea textArea;

    public TexturedTextbox() {
        try {
        	//new ImageIcon(ICONS_DIRECTORY + "\\BG.PNG").getImage();
            backgroundImage = ImageIO.read(new File(ResourceLoader.getResourceDirectoryPath(ResourceLoader.ICONS) + "\\textBox.PNG")); // path to your image
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(null); 

        textArea = new JTextArea();
        textArea.setOpaque(false);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Serif", Font.PLAIN, 18));
        textArea.setLineWrap(true);
       textArea.setLineWrap(true);
        textArea.setBorder(null);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(null);
        add(scrollPane);

        // Listener to adjust JTextArea bounds on resize
        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                resizeTextArea(scrollPane);
            }
        });
    }

    private void resizeTextArea(JScrollPane scrollPane) {
        int w = getWidth();
        int h = getHeight();

        // Proportional insets based on original image layout
        int insetX = (int)(w * 0.075); // 7.5% from each horizontal side
        int insetY = (int)(h * 0.10);  // 10% from each vertical side

        scrollPane.setBounds(insetX, insetY, w - 2 * insetX, h - 2 * insetY);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
