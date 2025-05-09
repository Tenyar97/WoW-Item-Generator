package com.tenyar97.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class IconLoaderUtil
	{
		public static List<ImageIconFile> loadIconsFromDirectory(String directory)
			{
				List<ImageIconFile> icons = new ArrayList<>();
				File folder = new File(directory);
				File[] files = folder
						.listFiles((dir, name) -> name.toLowerCase().matches(".*\\.(png|jpg|jpeg|gif|bmp)$"));

				if (files != null)
					{
						for (File file : files)
							{
								try
									{
										BufferedImage img = ImageIO.read(file);
										if (img != null)
											{
												icons.add(new ImageIconFile(new ImageIcon(file.getAbsolutePath()), file.getName(), file));
											}
									}
								catch (IOException ex)
									{
										System.err.println("Failed loading: " + file.getName());
									}
							}
					}

				return icons;
			}

		public static ImageIcon loadImageById(String directory, String id)
			{
				File file = new File(directory, id + ".png");
				if (file.exists())
					{
						try
							{
								BufferedImage img = ImageIO.read(file);
								if (img != null)
									return new ImageIcon(img);
							}
						catch (IOException e)
							{
								e.printStackTrace();
							}
					}
				return null;
			}
	}
