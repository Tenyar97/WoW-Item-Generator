package com.tenyar97.panels;

import java.awt.GridLayout;
import javax.swing.*;
import com.tenyar97.util.UIBuilder;
import attributes.SpellCategory;
import attributes.SpellEffect;

public class SpellEffectPanel extends JPanel
	{
		public JSpinner spellIdField, chargesField, cooldownField, categoryCooldownField;
		public JSpinner ppmRateField;
		public JComboBox<String> triggerField;
		public JComboBox<SpellCategory> categoryField;

		private static final int[] TRIGGER_VALUES = { 0, 1, 2, 4, 5 };

		public SpellEffectPanel(SpellEffect effect, int index)
			{
				setLayout(new GridLayout(0, 2));

				spellIdField = createSpinner(effect.spellId);
				triggerField = new JComboBox<>(
						new String[] { "On use", "On equip", "Chance on hit", "Soulstone", "On use without delay" });

				triggerField.setSelectedIndex(getTriggerIndex(effect.trigger));

				chargesField = createSpinner(effect.charges);
				ppmRateField = new JSpinner(new SpinnerNumberModel(effect.ppmRate, 0.0, 100.0, 0.1));
				cooldownField = createSpinner(effect.cooldown);
				categoryField = SpellCategory.createSpellCategoryBox();
				categoryField.setSelectedItem(
						new SpellCategory(effect.category, SpellCategory.getNameById(effect.category)));
				categoryCooldownField = createSpinner(effect.categoryCooldown);

				addRow("Spell ID:", spellIdField);
				addRow("Trigger Type", triggerField);
				addRow("Charges:", chargesField);
				addRow("PPM Rate:", ppmRateField);
				addRow("Cooldown (ms):", cooldownField);
				addRow("Category:", categoryField);
				addRow("Category Cooldown:", categoryCooldownField);

				assignFieldNames(index);
			}

		public void assignFieldNames(int index)
			{
				spellIdField.setName("spellIdField_" + index);
				triggerField.setName("triggerField_" + index);
				chargesField.setName("chargesField_" + index);
				ppmRateField.setName("ppmRateField_" + index);
				cooldownField.setName("cooldownField_" + index);
				categoryField.setName("categoryField_" + index);
				categoryCooldownField.setName("categoryCooldownField_" + index);
			}

		public void updateEffect(SpellEffect effect)
			{

				effect.spellId = ((Number) spellIdField.getValue()).intValue();
				effect.trigger = TRIGGER_VALUES[triggerField.getSelectedIndex()];
				effect.charges = ((Number) chargesField.getValue()).intValue();
				effect.ppmRate = ((Number) ppmRateField.getValue()).floatValue();
				effect.cooldown = ((Number) cooldownField.getValue()).intValue();
				effect.category = ((Number) categoryField.getSelectedIndex()).intValue();
				effect.categoryCooldown = ((Number) categoryCooldownField.getValue()).intValue();

			}

		public void setValuesFromEffect(SpellEffect effect)
			{
				spellIdField.setValue(effect.spellId);
				triggerField.setSelectedIndex(getTriggerIndex(effect.trigger));
				chargesField.setValue(effect.charges);
				ppmRateField.setValue((double) effect.ppmRate);
				cooldownField.setValue(effect.cooldown);
				categoryField.setSelectedItem(
						new SpellCategory(effect.category, SpellCategory.getNameById(effect.category)));
				categoryCooldownField.setValue(effect.categoryCooldown);
			}

		private int getTriggerIndex(int triggerValue)
			{
				for (int i = 0; i < TRIGGER_VALUES.length; i++)
					{
						if (TRIGGER_VALUES[i] == triggerValue)
							return i;
					}
				return 0;
			}

		private void addRow(String label, JComponent comp)
			{
				add(new JLabel(label));
				add(comp);
			}

		private JSpinner createSpinner(int value)
			{
				return new JSpinner(new SpinnerNumberModel(value, -1, Integer.MAX_VALUE, 1));
			}
	}
