package com.tenyar97.util;

import java.io.File;
import java.net.URISyntaxException;

import com.tenyar97.Main;

public class ResourceLoader
	{
		public static final String ICONS = "Resources/Icons/";
		public static final String FONTS = "Resources/Fonts/";
		public static final String SECRET = "Resources/Icons/superSecret/";
		public static final String ITEMS = "Resources/Items/";
		public static final String COMPONENTS = "Resources/Components/";//Components

		public static String getResourceDirectoryPath(String subDir)
			{
				try
					{
						File jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI())
								.getParentFile();
						File targetDir = new File(jarDir, subDir);
						return targetDir.getAbsolutePath() + File.separator;
					}
				catch (URISyntaxException e)
					{
						e.printStackTrace();
						return null;
					}
			}
	}
