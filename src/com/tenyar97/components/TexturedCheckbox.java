package com.tenyar97.components;
import javax.swing.*;

import com.tenyar97.Main;
import com.tenyar97.util.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class TexturedCheckbox extends JCheckBox {
    private static final String ICONS_DIRECTORY = ResourceLoader.getResourceDirectoryPath(ResourceLoader.ICONS);

	public TexturedCheckbox(String message) 
	{
        try {
            BufferedImage unchecked = ImageIO.read(new File("C:\\Users\\Brady\\eclipse-workspace\\WoW Item Generator\\CustomIcons\\checkBox_unchecked.png"));
            BufferedImage checked = ImageIO.read(new File("C:\\\\Users\\\\Brady\\\\eclipse-workspace\\\\WoW Item Generator\\\\CustomIcons\\\\checkBox_checked.png"));
            BufferedImage overlay = ImageIO.read(new File("C:\\\\Users\\\\Brady\\\\eclipse-workspace\\\\WoW Item Generator\\\\CustomIcons\\\\checkBox_rollover.png"));

            this.setIcon(new HoverOverlayIcon(unchecked, checked, overlay, 25, 25));
            this.setBorderPainted(false);
            this.setContentAreaFilled(false);
            this.setFocusPainted(false);
            this.setOpaque(false);
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private static class HoverOverlayIcon implements Icon {
	    private final Image unchecked, checked, overlay;
	    private final int width, height;

	    public HoverOverlayIcon(Image unchecked, Image checked, Image overlay, int width, int height) {
	        this.unchecked = unchecked;
	        this.checked = checked;
	        this.overlay = overlay;
	        this.width = width;
	        this.height = height;
	    }

	    @Override
	    public void paintIcon(Component c, Graphics g, int x, int y) {
	        AbstractButton btn = (AbstractButton) c;
	        ButtonModel model = btn.getModel();
	        Graphics2D g2d = (Graphics2D) g.create();

	        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

	        Image base = model.isSelected() ? checked : unchecked;
	        g2d.drawImage(base, x, y, width, height, null);

	        if (model.isRollover()) {
	            g2d.drawImage(overlay, x, y, width, height, null);
	        }

	        g2d.dispose();
	    }

	    @Override public int getIconWidth() { return width; }
	    @Override public int getIconHeight() { return height; }
	}

}
