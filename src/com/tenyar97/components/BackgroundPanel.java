package com.tenyar97.components;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel
	{
		private Image backgroundImage;

		public BackgroundPanel(String imagePath)
			{
				try
					{
						backgroundImage = new ImageIcon(imagePath).getImage();
						System.out.println("Loaded: " + imagePath);
					}
				catch (Exception e)
					{
						e.printStackTrace();
					}
				setLayout(new BorderLayout());
			}

		@Override
		protected void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				if (backgroundImage != null)
					{
						g.drawImage(backgroundImage, 0, 0, getWidth() + 25, getHeight() + 100, this);
					}
			}
	}
