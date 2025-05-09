package com.tenyar97.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JViewport;
import javax.swing.SwingUtilities;
import javax.swing.text.JTextComponent;
import javax.swing.text.NumberFormatter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tenyar97.components.MoneyPanel;

import attributes.SpellCategory;

public class SavedDataManager
	{

		private static Set<String> excludeAutoTrigger = Set.of("allowedClassChk", "allowedRaceChk", "chckbxSpellOne",
				"chckbxSpellTwo", "chckbxSpellThree", "chckbxSpellFour", "chckbxSpellFive");

		public static void applyValues(Component comp, Map<String, Object> data)
			{
				if (comp == null)
					return;

				if (comp instanceof Container)
					{
						for (Component child : ((Container) comp).getComponents())
							{
								applyValues(child, data);
							}
					}

				String name = comp.getName();
				if (name == null || !data.containsKey(name))
					return;

				Object value = data.get(name);

				try
					{
						if (comp instanceof JTextComponent && value instanceof String)
							{
								((JTextComponent) comp).setText((String) value);
							}
						else if (comp instanceof MoneyPanel && value instanceof Number num)
							{
								int totalCopper = ((Number) value).intValue();
								((MoneyPanel) comp).setPanelValue(totalCopper);
								System.out.println(
										">>> Rehydrating MoneyPanel " + comp.getName() + " as: " + totalCopper);
							}
						else if (comp instanceof JCheckBox && value instanceof Boolean)
							{
								((JCheckBox) comp).setSelected((Boolean) value);
								if (!excludeAutoTrigger.contains(comp.getName()))
									{
										for (ActionListener al : ((JCheckBox) comp).getActionListeners())
											{
												al.actionPerformed(
														new ActionEvent(comp, ActionEvent.ACTION_PERFORMED, null));
											}
									}
							}
						else if (comp instanceof JSpinner)
							{
								safelySetSpinnerValue((JSpinner) comp, value);
							}

						else if (comp instanceof JSpinner && name.equals("statOneSpinner"))
							{
								// ((JSpinner) comp).setValue(value);
								safelySetSpinnerValue((JSpinner) comp, value);
							}

						else if (comp instanceof JComboBox)
							{
								JComboBox<?> box = (JComboBox<?>) comp;

								if (box.getItemCount() > 0)
									{
										Object firstItem = box.getItemAt(0);

										if (firstItem instanceof Identifiable && value instanceof Map<?, ?> mapVal
												&& mapVal.containsKey("id"))
											{
												int idToMatch = ((Number) mapVal.get("id")).intValue();

												for (int i = 0; i < box.getItemCount(); i++)
													{
														Object item = box.getItemAt(i);
														if (item instanceof Identifiable idObj)
															{
																if (idObj.getId() == idToMatch)
																	{
																		box.setSelectedItem(item);
																		return;
																	}
															}
													}

												if (firstItem instanceof SpellCategory)
													{
														for (int i = 0; i < box.getItemCount(); i++)
															{
																Object item = box.getItemAt(i);
																if (item instanceof SpellCategory cat)
																	{
																		if (cat.id == idToMatch)
																			{
																				box.setSelectedItem(cat);
																				return;
																			}
																	}
															}
													}
											}

										if (value instanceof String strVal)
											{
												for (int i = 0; i < box.getItemCount(); i++)
													{
														Object item = box.getItemAt(i);
														if (strVal.equalsIgnoreCase(item.toString()))
															{
																box.setSelectedItem(item);
																return;
															}
													}
											}
									}

								box.setSelectedItem(value);
							}

						if (comp instanceof JFormattedTextField && name != null
								&& name.toLowerCase().contains("spinner"))
							{
								// System.out.println("Skipping inner spinner field: " + name);
								return;
							}

					}
				catch (Exception e)
					{
						System.err.println("Failed to apply: " + name + " → " + value + " (" + e + ")");
					}
			}

		public static void captureValues(Component comp, Map<String, Object> data)
			{
				if (comp == null)
					return;

				if (comp instanceof Container)
					{
						for (Component child : ((Container) comp).getComponents())
							{
								captureValues(child, data);
							}
					}

				if (comp.getName() == null)
					return;

				try
					{
						if (comp instanceof JTextComponent)
							{
								data.put(comp.getName(), ((JTextComponent) comp).getText());
							}
						else if (comp instanceof JCheckBox)
							{
								data.put(comp.getName(), ((JCheckBox) comp).isSelected());
							}
						else if (comp instanceof JSpinner)
							{
								data.put(comp.getName(), ((JSpinner) comp).getValue());
							}
						else if (comp instanceof JComboBox)
							{
								Object selected = ((JComboBox<?>) comp).getSelectedItem();

								if (selected instanceof Identifiable)
									{
										Identifiable item = (Identifiable) selected;
										Map<String, Object> idMap = new LinkedHashMap<>();
										idMap.put("id", item.getId());
										idMap.put("type", item.getClass().getSimpleName()); // optional
										data.put(comp.getName(), idMap);
									}
								else
									{
										data.put(comp.getName(), selected);
									}
							}
						else if (comp instanceof MoneyPanel moneyPanel)
							{
								int totalCopper = moneyPanel.getTotalCopper();
								data.put(comp.getName(), totalCopper);
								System.out
										.println(">>> Capturing MoneyPanel " + comp.getName() + " as: " + totalCopper);
							}

					}
				catch (Exception e)
					{
						System.err.println("Failed to save: " + comp.getName() + " (" + e + ")");
					}
			}

		public static void saveValuesToFile(Component comp, File file)
			{
				try (FileWriter writer = new FileWriter(file))
					{
						Map<String, Object> data = new LinkedHashMap<>();
						captureValues(comp, data);
						new Gson().toJson(data, writer);
						System.out.println("Saved to: " + file.getAbsolutePath());
					}
				catch (IOException e)
					{
						System.err.println("Error saving file: " + e.getMessage());
					}
			}

		public static Map<String, Object> loadValuesFromFileAndReturn(File file)
			{
				try (FileReader reader = new FileReader(file))
					{
						Gson gson = new Gson();
						Map<String, Object> data = gson.fromJson(reader, new TypeToken<Map<String, Object>>()
							{
							}.getType());
						// System.out.println("Loaded keys: " + data.keySet());
						return data;
					}
				catch (IOException e)
					{
						System.err.println("Error loading file: " + e.getMessage());
						return new HashMap<>();
					}
			}

		public static void applyValuesDeep(Component root, Map<String, Object> data)
			{
				if (root == null)
					return;

				applyValues(root, data);

				if (root instanceof Container container)
					{
						for (Component child : container.getComponents())
							{
								if (child instanceof JTabbedPane tabs)
									{
										for (int i = 0; i < tabs.getTabCount(); i++)
											{
												applyValuesDeep(tabs.getComponentAt(i), data);
											}
									}
								else if (child instanceof JScrollPane scroll)
									{
										applyValuesDeep(scroll.getViewport().getView(), data);
									}
								else if (child instanceof JSplitPane split)
									{
										applyValuesDeep(split.getLeftComponent(), data);
										applyValuesDeep(split.getRightComponent(), data);
									}
								else if (child instanceof JViewport viewport)
									{
										applyValuesDeep(viewport.getView(), data);
									}
								else
									{
										applyValuesDeep(child, data);
									}
							}
					}
			}

		public static void loadAndApplyDeep(Component root, File file)
			{
				Map<String, Object> data = loadValuesFromFileAndReturn(file);
				applyValuesDeep(root, data);
			}

		public static void safelySetSpinnerValue(JSpinner spinner, Object value)
			{
				if (spinner == null || value == null)
					return;

				spinner.setValue(value);

				if (spinner.getEditor() instanceof JSpinner.DefaultEditor editor)
					{
						NumberFormat format = NumberFormat.getIntegerInstance();
						format.setGroupingUsed(false);
						JFormattedTextField tf = editor.getTextField();
						((NumberFormatter) tf.getFormatter()).setFormat(format);
					}

				SwingUtilities.invokeLater(() ->
				{
					JComponent editor = spinner.getEditor();
					if (editor != null && editor instanceof JSpinner.DefaultEditor defaultEditor)						// HUGE.
						{																								// STINKY.
							JFormattedTextField tf = defaultEditor.getTextField();										// HACK.
							String str = value.toString();																// BUT IT'S
							if (spinner != null && !spinner.getName().contains("ppmRateField_") && str.endsWith(".0")) // 2AM AND I WANT TO GO TO SLEEP
							{
									// System.out.println(spinner.getName());
									str = str.substring(0, str.length() - 2);
							}
							tf.setText(str);
							tf.repaint();

							try
								{
									spinner.commitEdit();
								}
							catch (ParseException e)
								{
									System.err.println(
											"ParseException while committing spinner value: " + e.getMessage());
								}
						}
				});
			}

	}
