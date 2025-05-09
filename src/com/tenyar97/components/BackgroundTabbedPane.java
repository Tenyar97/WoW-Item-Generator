package com.tenyar97.components;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import com.tenyar97.util.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class BackgroundTabbedPane extends JTabbedPane 
{
	private Image backgroundImage;
	private Image frameImage;
    

    public BackgroundTabbedPane() {
        try {
        	
            backgroundImage = new ImageIcon(ResourceLoader.getResourceDirectoryPath(ResourceLoader.ICONS) + "\\BG.PNG").getImage();
            frameImage = new ImageIcon(ResourceLoader.getResourceDirectoryPath(ResourceLoader.ICONS) + "\\frame.PNG").getImage();
            System.out.println("Found BG");
        } catch (Exception e) {
            System.err.println("Background image not found.");
        }
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) 
    {
        super.paintComponent(g);
        
        if (backgroundImage != null) 
        {
        	int tabHeight = getTabCount() > 0 ? getBoundsAt(0).height : 30;
        	int rowCount = getTabRowCount();
        	int yOffset = tabHeight * rowCount;


            //g.drawImage(backgroundImage, 0, yOffset, getWidth(), getHeight() - yOffset, this);
        }
    }
    

    public int getTabRowCount() 
    {
        if (getUI() instanceof BasicTabbedPaneUI ui) 
        {
            int tabCount = getTabCount();
            int rows = 1;
            int lastRowY = -1;

            for (int i = 0; i < tabCount; i++) {
                Rectangle rect = ui.getTabBounds(this, i);
                if (lastRowY == -1 || rect.y > lastRowY) {
                    lastRowY = rect.y;
                    rows++;
                }
            }
            return rows;
        }
        return 1;
    }


}