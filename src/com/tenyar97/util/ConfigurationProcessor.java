package com.tenyar97.util;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationProcessor
	{
		private final File configFile;
		private static Map<String, String> configMap = new LinkedHashMap<>();

		public ConfigurationProcessor(String filePath) throws IOException
			{
				this.configFile = new File(filePath);

				if (!configFile.exists())
					{
						configFile.createNewFile();
					}
				loadConfigurations();
			}

		private void loadConfigurations() throws IOException
			{
				try (BufferedReader reader = new BufferedReader(new FileReader(configFile)))
					{
						String line;
						while ((line = reader.readLine()) != null)
							{
								parseConfigurationLine(line);
							}
					}
			}

		private void parseConfigurationLine(String line)
			{
				if (line == null || line.trim().isEmpty())
					return;

				String[] pairs = line.split(";");
				for (String pair : pairs)
					{
						if (!pair.trim().isEmpty())
							{
								String[] keyValue = pair.split("=", 2);
								if (keyValue.length == 2)
									{
										configMap.put(keyValue[0].trim(), keyValue[1].trim());
									}
							}
					}
			}

		public static String getValue(String key)
			{
				return configMap.get(key);
			}

		public void setValue(String key, String value)
			{
				configMap.put(key, value);
			}

		public void save() throws IOException
			{
				try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile)))
					{
						for (Map.Entry<String, String> entry : configMap.entrySet())
							{
								writer.write(entry.getKey() + "=" + entry.getValue() + ";");
								writer.newLine();
							}
					}
				loadConfigurations();
			}

		public void addOrUpdateValue(String key, String value) throws IOException
			{
				List<String> lines = new ArrayList<>();
				boolean foundKey = false;
				boolean modified = false;

				try (BufferedReader reader = new BufferedReader(new FileReader(configFile)))
					{
						String line;
						while ((line = reader.readLine()) != null)
							{
								if (line.contains("="))
									{
										String[] parts = line.split("=", 2);
										String existingKey = parts[0].trim();
										String existingValue = parts[1].replace(";", "").trim();

										if (existingKey.equals(key))
											{
												foundKey = true;
												if (!existingValue.equals(value))
													{
														line = key + "=" + value + ";";
														modified = true;
													}
											}
									}
								lines.add(line);
							}
					}

				if (!foundKey)
					{
						lines.add(key + "=" + value + ";");
						modified = true;
					}

				if (modified)
					{
						try (BufferedWriter writer = new BufferedWriter(new FileWriter(configFile)))
							{
								for (String l : lines)
									{
										writer.write(l);
										writer.newLine();
									}
							}
					}

				configMap.put(key, value);
			}

		public static Map<String, String> getConfigMap()
			{
				return configMap;
			}

	}
