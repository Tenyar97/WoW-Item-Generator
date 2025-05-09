package com.tenyar97.panels;

import java.awt.Font;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.tenyar97.Main;
import com.tenyar97.components.Textbox;
import com.tenyar97.util.SQLGenerator;
import com.tenyar97.util.UIBuilder;

import attributes.HonorRank;
import attributes.ReputationRank;
import attributes.SkillEntry;

public class RequirementsPanel extends JPanel
	{
		public JComboBox<SkillEntry> skillBox;
		public JComboBox<ReputationRank> reputationBox;
		public JComboBox<HonorRank> honorBox;
		public JComboBox<ReputationRank> allyHonorBox;

		public JCheckBox reqLvLChk, reqSkillChk, reqFactionChk, reqHonorChk, reqSpellChk;
		public JCheckBox allowedClassChk, allowedRaceChk, disenchantableChk;
		public JSpinner reqLvLSpinner, reqSkillSpinner, disEnchantmentTemplateSpinner, enchantSkill;

		private Textbox spellIDTextArea, holidayIDTextArea, txtbxFactionID;
		public static Textbox classBitMask, raceBitMask;
		private DefaultComboBoxModel<HonorRank> allianceHonorModel = new DefaultComboBoxModel<>();
		private DefaultComboBoxModel<HonorRank> hordeHonorModel = new DefaultComboBoxModel<>();
		private final int maxEnchantSkill = 315;

		public Font font = Main.lifeCraft;

		public RequirementsPanel()
			{
				setLayout(null);
				initHonorModels();
				SpinnerNumberModel defaultModel = new SpinnerNumberModel(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1);

				int y = 14;
				int labelX = 10;
				int fieldX = 145;
				int rowHeight = 30;

				// Require Level
				reqLvLChk = new JCheckBox("Require a Level?");
				reqLvLChk.setName("reqLvLChk");
				reqLvLChk.setFont(font);
				reqLvLChk.setBounds(labelX, y, 138, 23);
				reqLvLChk.setOpaque(false);
				reqLvLChk.setToolTipText("Minimum character level required to use the item.");
				add(reqLvLChk);

				reqLvLSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 255, 1));
				reqLvLSpinner.setName("reqLvLSpinner");
				reqLvLSpinner.setBounds(fieldX, y, 59, 22);
				reqLvLSpinner.setVisible(false);
				add(reqLvLSpinner);

				UIBuilder.bindVisibility(reqLvLChk, reqLvLSpinner);

				// Require Skill
				y += rowHeight;
				reqSkillChk = new JCheckBox("Require a Skill?");
				reqSkillChk.setName("reqSkillChk");
				reqSkillChk.setFont(font);
				reqSkillChk.setBounds(labelX, y, 138, 23);
				reqSkillChk.setOpaque(false);
				reqSkillChk.setToolTipText("Character must have a specific skill to use.");
				add(reqSkillChk);

				skillBox = new JComboBox<>(SkillEntry.getAllSkills());
				skillBox.setName("skillBox");
				skillBox.setBounds(fieldX, y, 161, 22);
				skillBox.setVisible(false);
				add(skillBox);

				reqSkillSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 315, 1));
				reqSkillSpinner.setName("reqSkillSpinner");
				reqSkillSpinner.setBounds(fieldX + 165, y, 40, 22);
				reqSkillSpinner.setToolTipText("Minimum skill level in the above skill.");
				reqSkillSpinner.setVisible(false);
				add(reqSkillSpinner);

				UIBuilder.bindVisibility(reqSkillChk, skillBox, reqSkillSpinner);

				// Require Spell
				y += rowHeight;
				reqSpellChk = new JCheckBox("Require a Spell?");
				reqSpellChk.setName("reqSpellChk");
				reqSpellChk.setFont(font);
				reqSpellChk.setBounds(labelX, y, 138, 23);
				reqSpellChk.setOpaque(false);
				reqSpellChk.setToolTipText("Player must know this spell to use the item.");
				add(reqSpellChk);

				spellIDTextArea = new Textbox("SpellID");
				spellIDTextArea.setName("spellIDTextArea");
				spellIDTextArea.setBounds(fieldX, y, 79, 22);
				spellIDTextArea.setVisible(false);
				add(spellIDTextArea);

				UIBuilder.bindVisibility(reqSpellChk, spellIDTextArea);

				// Require Faction
				y += rowHeight;
				reqFactionChk = new JCheckBox("Require a Faction?");
				reqFactionChk.setName("reqFactionChk");
				reqFactionChk.setFont(font);
				reqFactionChk.setBounds(labelX, y, 138, 23);
				reqFactionChk.setOpaque(false);
				reqFactionChk.setToolTipText("Minimum reputation with a faction needed.");
				add(reqFactionChk);

				txtbxFactionID = new Textbox("FactionID");
				txtbxFactionID.setName("txtbxFactionID");
				txtbxFactionID.setFont(font);
				txtbxFactionID.setBounds(fieldX, y, 194, 23);
				txtbxFactionID.setVisible(false);
				add(txtbxFactionID);

				reputationBox = new JComboBox<>(ReputationRank.getAllRanks());
				reputationBox.setName("reputationBox");
				reputationBox.setBounds(fieldX + 205, y, 125, 23);
				reputationBox.setVisible(false);
				add(reputationBox);

				UIBuilder.bindVisibility(reqFactionChk, txtbxFactionID, reputationBox);

				// Require Honor
				y += rowHeight;
				reqHonorChk = new JCheckBox("Require Honor?");
				reqHonorChk.setName("reqHonorChk");
				reqHonorChk.setFont(font);
				reqHonorChk.setBounds(labelX, y, 138, 23);
				reqHonorChk.setOpaque(false);
				reqHonorChk.setToolTipText("Honor rank required to equip this item.");
				add(reqHonorChk);

				JComboBox<String> honorSideBox = new JComboBox<>(new String[] { "Alliance", "Horde" });
				honorSideBox.setName("honorSideBox");
				honorSideBox.setBounds(fieldX, y, 84, 23);
				honorSideBox.setVisible(false);
				add(honorSideBox);

				honorBox = new JComboBox<>(allianceHonorModel);
				honorBox.setName("honorBox");
				honorBox.setBounds(fieldX + 90, y, 161, 22);
				honorBox.setVisible(false);
				add(honorBox);

				UIBuilder.bindVisibility(reqHonorChk, honorBox, honorSideBox);
				honorSideBox.addActionListener(e ->
				{
					honorBox.setModel(
							"Horde".equals(honorSideBox.getSelectedItem()) ? hordeHonorModel : allianceHonorModel);
				});

				// Class Restriction
				y += rowHeight;
				allowedClassChk = new JCheckBox("Class Exclusive?");
				allowedClassChk.setName("allowedClassChk");
				allowedClassChk.setFont(font);
				allowedClassChk.setBounds(labelX, y, 138, 23);
				allowedClassChk.setOpaque(false);
				allowedClassChk.setToolTipText("The character classes are allowed to equip or use this item.");
				add(allowedClassChk);

				classBitMask = new Textbox("");
				classBitMask.setName("classBitMask");
				classBitMask.setBounds(fieldX, y, 79, 22);
				classBitMask.setVisible(false);
				add(classBitMask);

				UIBuilder.bindVisibility(allowedClassChk, classBitMask);
				allowedClassChk.addActionListener(e ->
				{
					if (allowedClassChk.isSelected())
						AllowedClassesWindow.showClassBitmaskPopup(classBitMask);
				});

				// Race Restriction
				y += rowHeight;
				
				allowedRaceChk = new JCheckBox("Race Exclusive?");
				allowedRaceChk.setName("allowedRaceChk");
				allowedRaceChk.setFont(font);
				allowedRaceChk.setBounds(labelX, y, 138, 23);
				allowedRaceChk.setOpaque(false);
				allowedRaceChk.setToolTipText("The character races are allowed to equip or use this item.");
				add(allowedRaceChk);

				raceBitMask = new Textbox("");
				raceBitMask.setName("raceBitMask");
				raceBitMask.setBounds(fieldX, y, 79, 22);
				raceBitMask.setVisible(false);
				add(raceBitMask);

				UIBuilder.bindVisibility(allowedRaceChk, raceBitMask);
				allowedRaceChk.addActionListener(e ->
				{
					if (allowedRaceChk.isSelected())
						AllowedRacesWindow.showRaceBitmaskPopup(raceBitMask);
				});

				// Disenchantable
				y += rowHeight;
				disenchantableChk = new JCheckBox("Disenchantable?");
				disenchantableChk.setName("disenchantableChk");
				disenchantableChk.setFont(font);
				disenchantableChk.setBounds(labelX, y, 138, 23);
				disenchantableChk.setOpaque(false);
				add(disenchantableChk);

				enchantSkill = new JSpinner(new SpinnerNumberModel(1, 1, maxEnchantSkill, 1));
				enchantSkill.setName("enchantSkill");
				enchantSkill.setBounds(fieldX, y, 47, 23);
				enchantSkill.setToolTipText("Minimum Enchanting level needed to disenchant this item.");
				enchantSkill.setVisible(false);
				add(enchantSkill);

				disEnchantmentTemplateSpinner = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
				disEnchantmentTemplateSpinner.setName("disEnchantmentTemplateSpinner");
				disEnchantmentTemplateSpinner.setBounds(fieldX + 55, y, 47, 23);
				disEnchantmentTemplateSpinner.setToolTipText("Links this item to a disenchant loot table...");
				disEnchantmentTemplateSpinner.setVisible(false);
				add(disEnchantmentTemplateSpinner);

				UIBuilder.bindVisibility(disenchantableChk, enchantSkill, disEnchantmentTemplateSpinner);

				// Holiday
				y += rowHeight;
				JCheckBox holidayChk = new JCheckBox("Require a Holiday?");
				holidayChk.setName("holidayChk");
				holidayChk.setFont(font);
				holidayChk.setBounds(labelX, y, 138, 23);
				holidayChk.setOpaque(false);
				holidayChk.setToolTipText("Restricts this item to a specific in-game holiday event.");
				add(holidayChk);

				holidayIDTextArea = new Textbox("HolidayID");
				holidayIDTextArea.setName("holidayIDTextArea");
				holidayIDTextArea.setBounds(fieldX, y, 79, 22);
				holidayIDTextArea.setVisible(false);
				add(holidayIDTextArea);

				UIBuilder.bindVisibility(holidayChk, holidayIDTextArea);
			}

		private void initHonorModels()
			{
				String[] allianceTitles = { "Unranked", "Private", "Corporal", "Sergeant", "Master Sergeant",
						"Sergeant Major", "Knight", "Knight-Lieutenant", "Knight-Captain", "Knight-Champion",
						"Lieutenant-Commander", "Commander", "Marshal", "Field Marshal", "Grand Marshal" };
				String[] hordeTitles = { "Unranked", "Scout", "Grunt", "Sergeant", "Senior Sergeant", "First Sergeant",
						"Stone Guard", "Blood Guard", "Legionnaire", "Centurion", "Champion", "Lieutenant General",
						"General", "Warlord", "High Warlord" };
				for (int i = 0; i < allianceTitles.length; i++)
					{
						allianceHonorModel.addElement(new HonorRank(i, allianceTitles[i]));
						hordeHonorModel.addElement(new HonorRank(i, hordeTitles[i]));
					}
			}

		public int getRequiredHonorRankId()
			{
				if (reqHonorChk.isSelected())
					{
						HonorRank selected = (HonorRank) honorBox.getSelectedItem();
						return selected != null ? selected.id : 0;
					}
				return 0;
			}

		public int getRequiredFaction()
			{
				return SQLGenerator.getInt(txtbxFactionID);
			}

		public int getRequiredReputationId()
			{
				if (reqFactionChk.isSelected())
					{
						ReputationRank selected = (ReputationRank) reputationBox.getSelectedItem();
						return selected != null ? selected.id : 0;
					}
				return 0;
			}

		public int getRequiredLvl()
			{
				Number lvl = (Number) reqLvLSpinner.getValue();
				return lvl.intValue();
			}

		public int getDisenchantTable()
			{
				Number disID = (Number) disEnchantmentTemplateSpinner.getValue();
				return disID.intValue();
			}

		public int getRequiredSpell()
			{
				return SQLGenerator.getInt(spellIDTextArea);
			}
	}
