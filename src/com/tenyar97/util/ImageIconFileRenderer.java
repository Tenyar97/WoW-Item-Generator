package com.tenyar97.util;

import java.awt.Component;
import java.io.IOException;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class ImageIconFileRenderer extends DefaultListCellRenderer
	{
		@Override
		public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
				boolean cellHasFocus)
			{
				if (value instanceof ImageIconFile)
					{

						ImageIconFile iconFile = (ImageIconFile) value;
						String comment = "No comment";
						try
							{
								comment = PngMetadataReader.readComment(iconFile.getFile());
							}
						catch (IOException e)
							{
								e.printStackTrace();
							}
						setIcon(iconFile.getIcon());
						setText(iconFile.getName());
						setToolTipText(comment);
					}
				return this;
			}
	}