package com.tenyar97.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Panel;
import java.lang.reflect.Field;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import com.tenyar97.components.Textbox;
import com.tenyar97.panels.SpellsPanel;

public class UIBuilder
	{
		public static void bindVisibility(JCheckBox toggle, JComponent... targets)
			{
				// Initial visibility (based on checkbox state)
				boolean visible = toggle.isSelected();
				for (JComponent target : targets)
					{
						target.setVisible(visible);
					}

				// Add listener to react to checkbox changes
				toggle.addActionListener(e ->
				{
					boolean show = toggle.isSelected();
					for (JComponent target : targets)
						{
							target.setVisible(show);
						}

					// DO NOT reset any values — just toggle visibility
				});
			}

		public static void setAllComponentsNonOpaque(Component comp)
			{
				if (comp instanceof JComponent)
					{
						((JComponent) comp).setOpaque(false);
						System.out.println(comp.toString());
					}

				if (comp instanceof Container)
					{
						for (Component child : ((Container) comp).getComponents())
							{
								setAllComponentsNonOpaque(child);
							}
					}
			}

		public static void autoNameFields(Object panel)
			{
				for (Field field : panel.getClass().getDeclaredFields())
					{
						field.setAccessible(true);
						try
							{
								Object value = field.get(panel);
								if (value instanceof JComponent)
									{
										JComponent comp = (JComponent) value;
										if (comp.getName() == null)
											{ // Only name if not already named
												comp.setName(field.getName());
												// System.out.println("Auto-named component: " + field.getName());
											}
									}
							}
						catch (IllegalAccessException e)
							{
								e.printStackTrace();
							}
					}
			}

		public static Component findComponentByName(Container root, String name)
			{
				for (Component comp : root.getComponents())
					{
						if (name.equals(comp.getName()))
							{
								return comp;
							}
						if (comp instanceof Container)
							{
								Component child = findComponentByName((Container) comp, name);
								if (child != null)
									{
										return child;
									}
							}
					}
				return null;
			}

	}
