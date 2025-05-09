package com.tenyar97.panels;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import com.tenyar97.components.Textbox;

public class AllowedClassesWindow
	{

		private static int classMask = -1;

		public static int getClassMask()
			{
				return classMask;
			}

		public static void showClassBitmaskPopup(Textbox classBitMask)
			{
				SwingUtilities.invokeLater(() ->
				{
					final Map<JCheckBox, Integer>[] checkboxMap = new Map[] { new LinkedHashMap<>() };

					JFrame frame = new JFrame("Select Allowed Races");
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

					// On close, check if anything was selected
					frame.addWindowListener(new java.awt.event.WindowAdapter()
						{
							@Override
							public void windowClosed(java.awt.event.WindowEvent e)
								{
									boolean anySelected = checkboxMap[0].keySet().stream()
											.anyMatch(JCheckBox::isSelected);
									if (!anySelected)
										{
											classMask = -1;
											if (classBitMask != null)
												{
													classBitMask.setText("-1");
												}
										}
								}

							// Required for proper close event to fire
							@Override
							public void windowClosing(java.awt.event.WindowEvent e)
								{
									frame.dispose();
								}
						});

					frame.setSize(300, 400);
					frame.setLocationRelativeTo(null);

					JPanel panel = new JPanel();
					panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

					Map<String, Integer> classMap = new LinkedHashMap<>()
						{
								{
									put("WARRIOR", 0x1);
									put("PALADIN", 0x2);
									put("HUNTER", 0x4);
									put("ROGUE", 0x8);
									put("PRIEST", 0x10);
									put("SHAMAN", 0x40);
									put("MAGE", 0x80);
									put("WARLOCK", 0x100);
									put("DRUID", 0x400);
								}
						};

					JLabel resultLabelDec = new JLabel("Decimal: 0");
					JLabel resultLabelHex = new JLabel("Hex: 0x0");

					Runnable updateBitmask = () ->
					{
						classMask = 0;
						for (Map.Entry<JCheckBox, Integer> entry : checkboxMap[0].entrySet())
							{
								if (entry.getKey().isSelected())
									{
										classMask |= entry.getValue();
									}
							}
						resultLabelDec.setText("Decimal: " + classMask);
						resultLabelHex.setText(String.format("Hex: 0x%X", classMask));
						if (classBitMask != null)
							{
								classBitMask.setText(String.valueOf(classMask));
							}
					};

					for (Map.Entry<String, Integer> entry : classMap.entrySet())
						{
							JCheckBox checkBox = new JCheckBox(entry.getKey());
							checkBox.addActionListener(e -> updateBitmask.run());
							checkboxMap[0].put(checkBox, entry.getValue());
							panel.add(checkBox);
						}

					panel.add(Box.createVerticalStrut(10));
					//panel.add(resultLabelDec);
					//panel.add(resultLabelHex);

					frame.getContentPane().add(panel);
					frame.setVisible(true);
				});
			}
	}
