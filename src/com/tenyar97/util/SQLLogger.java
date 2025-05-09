package com.tenyar97.util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class SQLLogger
	{

		private static final String LOG_FILE = "output_debug.log";

		public static void log(String header, String content)
			{
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true)))
					{
						writer.write("[" + LocalDateTime.now() + "] " + header + "\n");
						writer.write(content + "\n\n");
					}
				catch (IOException e)
					{
						e.printStackTrace();
					}
			}
	}
