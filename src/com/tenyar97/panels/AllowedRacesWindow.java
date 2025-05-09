package com.tenyar97.panels;

import javax.swing.*;

import com.tenyar97.components.Textbox;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class AllowedRacesWindow
	{

		private static int raceMask = -1;

		public static int getRaceMask()
			{
				return raceMask;
			}

		public static void showRaceBitmaskPopup(Textbox raceBitMask)
			{
				SwingUtilities.invokeLater(() ->
				{
					final Map<JCheckBox, Integer>[] checkboxMap = new Map[] { new LinkedHashMap<>() };

					JFrame frame = new JFrame("Select Allowed Races");
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

					frame.addWindowListener(new java.awt.event.WindowAdapter()
						{
							@Override
							public void windowClosed(java.awt.event.WindowEvent e)
								{
									boolean anySelected = checkboxMap[0].keySet().stream()
											.anyMatch(JCheckBox::isSelected);
									if (!anySelected)
										{
											raceMask = -1;
											if (raceBitMask != null)
												{
													raceBitMask.setText("-1");
												}
										}
								}

							@Override
							public void windowClosing(java.awt.event.WindowEvent e)
								{
									frame.dispose();
								}
						});

					frame.setSize(310, 142);
					frame.setLocationRelativeTo(null);

					JPanel panel = new JPanel();
					panel.setLayout(new BorderLayout());

					Map<String, Integer> raceMap = new LinkedHashMap<>()
						{
								{
									put("Human", 0x1);
									put("Orc", 0x2);
									put("Dwarf", 0x4);
									put("Night Elf", 0x8);
									put("Undead", 0x10);
									put("Tauren", 0x20);
									put("Gnome", 0x40);
									put("Troll", 0x80);
									// put("Goblin", 0x100);
									// put("Blood Elf", 0x200);
									// put("Dranei", 0x400);
								}
						};

					JPanel leftPanel = new JPanel();
					leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
					JPanel rightPanel = new JPanel();
					rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

					for (Map.Entry<String, Integer> entry : raceMap.entrySet())
						{
							JCheckBox checkBox = new JCheckBox(entry.getKey());
							checkboxMap[0].put(checkBox, entry.getValue());

							checkBox.addActionListener(e ->
							{
								raceMask = 0;
								for (Map.Entry<JCheckBox, Integer> box : checkboxMap[0].entrySet())
									{
										if (box.getKey().isSelected())
											{
												raceMask |= box.getValue();
											}
									}
								if (raceBitMask != null)
									{
										raceBitMask.setText(String.valueOf(raceMask));
									}
							});

							switch (entry.getKey())
							{
							case "Human", "Dwarf", "Night Elf", "Gnome" -> leftPanel.add(checkBox);
							case "Orc", "Undead", "Tauren", "Troll" -> rightPanel.add(checkBox);
							}
						}

					JPanel bottomPanel = new JPanel();
					bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
					for (String race : new String[] { "Goblin", "High Elf" })
						{
							JCheckBox box = checkboxMap[0].keySet().stream().filter(cb -> cb.getText().equals(race))
									.findFirst().orElse(null);
							if (box != null)
								bottomPanel.add(box);
						}

					JPanel columnsPanel = new JPanel(new GridLayout(1, 2));
					columnsPanel.add(leftPanel);
					columnsPanel.add(rightPanel);

					panel.add(columnsPanel, BorderLayout.CENTER);
					panel.add(bottomPanel, BorderLayout.SOUTH);

					frame.getContentPane().add(panel);
					frame.setVisible(true);
				});

			}
	}
