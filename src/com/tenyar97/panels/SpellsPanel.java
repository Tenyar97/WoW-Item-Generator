package com.tenyar97.panels;

import javax.swing.*;
import java.awt.GridLayout;
import com.tenyar97.util.UIBuilder;
import attributes.SpellEffect;

public class SpellsPanel extends JPanel
	{

		private final SpellEffect[] spellEffects = new SpellEffect[5];
		private final SpellEffectPanel[] effectPanels = new SpellEffectPanel[5];
		private final JCheckBox[] checkboxes = new JCheckBox[5];
		private final JPanel hiddenPanel = new JPanel();
		private boolean suppressEditorOpen = false;

		public SpellsPanel()
			{
				setLayout(new GridLayout(0, 2));

				for (int i = 0; i < 5; i++)
					{
						spellEffects[i] = new SpellEffect();
						effectPanels[i] = new SpellEffectPanel(spellEffects[i], i);

						checkboxes[i] = new JCheckBox();
						updateCheckboxLabel(i);
						checkboxes[i].setName("chckbxSpell" + (i + 1));
						checkboxes[i].setToolTipText("Enable or disable spell slot " + (i + 1) + " for this item.");

						final int index = i;
						checkboxes[i].addActionListener(e ->
						{
							if (!suppressEditorOpen)
								{
									openEditor(index);
								}
						});

						add(checkboxes[i]);
					}

				hiddenPanel.setVisible(false);
				for (SpellEffectPanel panel : effectPanels) // it *needs* to be here because the data system only finds
															// components in the container tree.
					{ 										// so we add this invisible ghost to keep SpellEffects save/reloadable.
						hiddenPanel.add(panel); 			// yes, it's the wrong way to do it and yes, I hate it.
					}
				add(hiddenPanel);

				UIBuilder.autoNameFields(this);
			}

		private void openEditor(int index)
			{
				SpellEffect old = new SpellEffect(spellEffects[index]);

				SpellEffectPanel tempEditor = new SpellEffectPanel(spellEffects[index], index);

				if (JOptionPane.showConfirmDialog(null, tempEditor, "Edit Spell Effect: " + (index + 1),
						JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE) == JOptionPane.OK_OPTION)
					{

						tempEditor.updateEffect(spellEffects[index]);

						effectPanels[index].setValuesFromEffect(spellEffects[index]);
						updateCheckboxLabel(index);
						syncCheckboxesToSpellState();

						logSpellEffectChanges("Spell " + (index + 1), old, spellEffects[index]);
					}
			}

		public SpellEffect[] getAllSpellEffects()
			{
				return spellEffects;
			}

		private void updateCheckboxLabel(int index)
			{
				String spellID = spellEffects[index].spellId != 0 ? " - " + spellEffects[index].spellId : "";
				String label = "Spell " + (index + 1) + spellID;
				checkboxes[index].setText(label);
			}

		public void reloadFromLoadedFields()
			{
				for (int i = 0; i < effectPanels.length; i++)
					{
						effectPanels[i].updateEffect(spellEffects[i]);
					}
			}

		public void setSuppressEditorOpen(boolean suppress)
			{
				this.suppressEditorOpen = suppress;
			}

		private static void logSpellEffectChanges(String spellName, SpellEffect oldEffect, SpellEffect newEffect)
			{
				StringBuilder changes = new StringBuilder("Changes for " + spellName + ":\n");

				if (oldEffect.spellId != newEffect.spellId)
					changes.append(" - spellId changed from ").append(oldEffect.spellId).append(" to ")
							.append(newEffect.spellId).append("\n");
				if (oldEffect.trigger != newEffect.trigger)
					changes.append(" - trigger changed from ").append(oldEffect.trigger).append(" to ")
							.append(newEffect.trigger).append("\n");
				if (oldEffect.charges != newEffect.charges)
					changes.append(" - charges changed from ").append(oldEffect.charges).append(" to ")
							.append(newEffect.charges).append("\n");
				if (Float.compare(oldEffect.ppmRate, newEffect.ppmRate) != 0)
					changes.append(" - ppmRate changed from ").append(oldEffect.ppmRate).append(" to ")
							.append(newEffect.ppmRate).append("\n");
				if (oldEffect.cooldown != newEffect.cooldown)
					changes.append(" - cooldown changed from ").append(oldEffect.cooldown).append(" to ")
							.append(newEffect.cooldown).append("\n");
				if (oldEffect.category != newEffect.category)
					changes.append(" - category changed from ").append(oldEffect.category).append(" to ")
							.append(newEffect.category).append("\n");
				if (oldEffect.categoryCooldown != newEffect.categoryCooldown)
					changes.append(" - categoryCooldown changed from ").append(oldEffect.categoryCooldown)
							.append(" to ").append(newEffect.categoryCooldown).append("\n");

				if (changes.toString().equals("Changes for " + spellName + ":\n"))
					{
						System.out.println("No changes made to " + spellName + ".");
					}
				else
					{
						System.out.println(changes);
					}
			}

		public void syncCheckboxesToSpellState()
			{
				for (int i = 0; i < 5; i++)
					{
						checkboxes[i].setSelected(spellEffects[i].spellId != 0);
						updateCheckboxLabel(i);
					}
			}

	}
