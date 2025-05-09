package com.tenyar97.panels;

import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerNumberModel;

import com.tenyar97.Main;
import com.tenyar97.components.Textbox;
import com.tenyar97.util.SQLGenerator;
import com.tenyar97.util.UIBuilder;

import attributes.DamageSchools;
import attributes.Stats;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class StatsPanel extends JPanel
	{
		public Font font = Main.lifeCraft;
		public JComboBox<Stats> statBox, statTwoBox, statThreeBox, statFourBox, statFiveBox, statSixBox, statSevenBox,
				statEightBox, statNineBox, statTenBox;
		private JComboBox<DamageSchools> damageTypeOneBox, damageTypeTwoBox, damageTypeThreeBox, damageTypeFourBox,
				damageTypeFiveBox;

		private JCheckBox holyResChckbx, fireResChckbx, natResChckbx, frostResChckbx, shadowResChckbx, arcaneResChckbx,
				armorChckbx;
		private JSpinner holyResSpinner, fireResSpinner, natResSpinner, frostResSpinner, shadowResSpinner,
				arcaneResSpinner, armorSpinner;

		public JSpinner statOneSpinner, statTwoSpinner, statThreeSpinner, statFourSpinner, statFiveSpinner,
				statSixSpinner, statSevenSpinner, statEightSpinner, statNineSpinner, statTenSpinner;
		public JCheckBox statOneChckbx, statTwoChckbx, statThreeChckbx, statFourChckbx, statFiveChckbx, statSixChckbx,
				statSevenChckbx, statEightChckbx, statNineChckbx, statTenChckbx;
		private JCheckBox damageTypeOneChckbx, damageTypeTwoChckbx, damageTypeThreeChckbx, damageTypeFourChckbx,
				damageTypeFiveChckbx;
		private JSpinner damageOneMinSpinner, damageOneMaxSpinner, damageTwoMinSpinner, damageTwoMaxSpinner,
				damageThreeMinSpinner, damageThreeMaxSpinner, damageFourMinSpinner, damageFourMaxSpinner,
				damageFiveMinSpinner, damageFiveMaxSpinner;

		private JSpinner delaySpinner;

		private static JSpinner rangedModRangeSpinner;
		private static Textbox blockBox;
		private static JLabel rangedModLbl;

		public static boolean showBlock;
		boolean isEquipable = BasicInfoPanel.getArmorCheck().isSelected();

		private JLabel delayLbl;

		public StatsPanel()
			{
				setLayout(null);

				createCheckbox();
				createSpinner();
				createComboBox();

				// Stats
				UIBuilder.bindVisibility(statOneChckbx, statBox, statOneSpinner);
				UIBuilder.bindVisibility(statTwoChckbx, statTwoBox, statTwoSpinner);
				UIBuilder.bindVisibility(statThreeChckbx, statThreeBox, statThreeSpinner);
				UIBuilder.bindVisibility(statFourChckbx, statFourBox, statFourSpinner);
				UIBuilder.bindVisibility(statFiveChckbx, statFiveBox, statFiveSpinner);
				UIBuilder.bindVisibility(statSixChckbx, statSixBox, statSixSpinner);
				UIBuilder.bindVisibility(statSevenChckbx, statSevenBox, statSevenSpinner);
				UIBuilder.bindVisibility(statEightChckbx, statEightBox, statEightSpinner);
				UIBuilder.bindVisibility(statNineChckbx, statNineBox, statNineSpinner);
				UIBuilder.bindVisibility(statTenChckbx, statTenBox, statTenSpinner);

				// DamageTypes
				UIBuilder.bindVisibility(damageTypeOneChckbx, damageTypeOneBox, damageOneMinSpinner,
						damageOneMaxSpinner);
				UIBuilder.bindVisibility(damageTypeTwoChckbx, damageTypeTwoBox, damageTwoMinSpinner,
						damageTwoMaxSpinner);
				UIBuilder.bindVisibility(damageTypeThreeChckbx, damageTypeThreeBox, damageThreeMinSpinner,
						damageThreeMaxSpinner);
				UIBuilder.bindVisibility(damageTypeFourChckbx, damageTypeFourBox, damageFourMinSpinner,
						damageFourMaxSpinner);
				UIBuilder.bindVisibility(damageTypeFiveChckbx, damageTypeFiveBox, damageFiveMinSpinner,
						damageFiveMaxSpinner);

				// Resistances
				UIBuilder.bindVisibility(armorChckbx, armorSpinner);
				UIBuilder.bindVisibility(holyResChckbx, holyResSpinner);
				UIBuilder.bindVisibility(fireResChckbx, fireResSpinner);
				UIBuilder.bindVisibility(natResChckbx, natResSpinner);
				UIBuilder.bindVisibility(frostResChckbx, frostResSpinner);
				UIBuilder.bindVisibility(shadowResChckbx, shadowResSpinner);
				UIBuilder.bindVisibility(arcaneResChckbx, arcaneResSpinner);

				blockBox = new Textbox("Shield Block Value");
				blockBox.setName("blockBox");
				blockBox.setBounds(166, 199, 105, 22);
				blockBox.setVisible(false);
				add(blockBox);

				rangedModRangeSpinner = new JSpinner(new SpinnerNumberModel(0.0, -100.0, 100.0, 0.5));
				rangedModRangeSpinner.setName("rangedModRangeSpinner");
				rangedModRangeSpinner.setBounds(267, 202, 57, 20);
				rangedModRangeSpinner.setVisible(false);
				add(rangedModRangeSpinner);
				// rangedModLbl, rangedModRangeSpinner

				rangedModLbl = new JLabel("Ranged Modifier");
				rangedModLbl.setFont(font);
				rangedModLbl.setVisible(false);
				rangedModLbl.setBounds(163, 203, 96, 14);
				add(rangedModLbl);

				UIBuilder.autoNameFields(this);

				delaySpinner = new JSpinner(new SpinnerNumberModel(0, -100, 100, 1));



				delaySpinner.setBounds(105, 197, 57, 20);
				add(delaySpinner);

				delayLbl = new JLabel("Weapon Speed");
				delayLbl.setBounds(13, 198, 90, 14);
				add(delayLbl);
			}

		private void createComboBox()
			{
				statBox = new JComboBox<>(Stats.getAllStats());
				statBox.setName("statBox");
				statBox.setBounds(110, 20, 120, 30);
				add(statBox);

				statTwoBox = new JComboBox<>(Stats.getAllStats());
				statTwoBox.setName("statTwoBox");
				statTwoBox.setBounds(397, 22, 120, 30);
				add(statTwoBox);

				statThreeBox = new JComboBox<>(Stats.getAllStats());
				statThreeBox.setName("statThreeBox");
				statThreeBox.setBounds(110, 52, 120, 30);
				add(statThreeBox);

				statFourBox = new JComboBox<>(Stats.getAllStats());
				statFourBox.setName("statFourBox");
				statFourBox.setBounds(397, 54, 120, 30);
				add(statFourBox);

				statFiveBox = new JComboBox<>(Stats.getAllStats());
				statFiveBox.setName("statFiveBox");
				statFiveBox.setBounds(110, 80, 120, 30);
				add(statFiveBox);

				statSixBox = new JComboBox<>(Stats.getAllStats());
				statSixBox.setName("statSixBox");
				statSixBox.setBounds(397, 82, 120, 30);
				add(statSixBox);

				statSevenBox = new JComboBox<>(Stats.getAllStats());
				statSevenBox.setName("statSevenBox");
				statSevenBox.setBounds(110, 112, 120, 30);
				add(statSevenBox);

				statEightBox = new JComboBox<>(Stats.getAllStats());
				statEightBox.setName("statEightBox");
				statEightBox.setBounds(397, 114, 120, 30);
				add(statEightBox);

				statNineBox = new JComboBox<>(Stats.getAllStats());
				statNineBox.setName("statNineBox");
				statNineBox.setBounds(110, 144, 120, 30);
				add(statNineBox);

				statTenBox = new JComboBox<>(Stats.getAllStats());
				statTenBox.setName("statTenBox");
				statTenBox.setBounds(397, 146, 120, 30);
				add(statTenBox);

				damageTypeOneBox = new JComboBox<>(DamageSchools.getAllSchools());
				damageTypeOneBox.setBounds(135, 228, 80, 30);
				add(damageTypeOneBox);

				damageTypeTwoBox = new JComboBox<>(DamageSchools.getAllSchools());
				damageTypeTwoBox.setBounds(135, 262, 80, 30);
				add(damageTypeTwoBox);

				damageTypeThreeBox = new JComboBox<>(DamageSchools.getAllSchools());
				damageTypeThreeBox.setBounds(135, 295, 80, 30);
				add(damageTypeThreeBox);

				damageTypeFourBox = new JComboBox<>(DamageSchools.getAllSchools());
				damageTypeFourBox.setBounds(135, 329, 80, 30);
				add(damageTypeFourBox);

				damageTypeFiveBox = new JComboBox<>(DamageSchools.getAllSchools());
				damageTypeFiveBox.setBounds(135, 366, 80, 30);
				add(damageTypeFiveBox);
			}

		private void createSpinner()
			{

				statOneSpinner = new JSpinner();
				statOneSpinner.setName("statOneSpinner");
				statOneSpinner.setBounds(240, 24, 43, 23);
				add(statOneSpinner);

				statTwoSpinner = new JSpinner();
				statTwoSpinner.setName("statTwoSpinner");
				statTwoSpinner.setBounds(527, 26, 43, 23);
				add(statTwoSpinner);

				statThreeSpinner = new JSpinner();
				statThreeSpinner.setName("statThreeSpinner");
				statThreeSpinner.setBounds(240, 56, 43, 23);
				add(statThreeSpinner);

				statFourSpinner = new JSpinner();
				statFourSpinner.setName("statFourSpinner");
				statFourSpinner.setBounds(527, 58, 43, 23);
				add(statFourSpinner);

				statFiveSpinner = new JSpinner();
				statFiveSpinner.setName("statFiveSpinner");
				statFiveSpinner.setBounds(240, 84, 43, 23);
				add(statFiveSpinner);

				statSixSpinner = new JSpinner();
				statSixSpinner.setName("statSixSpinner");
				statSixSpinner.setBounds(527, 86, 43, 23);
				add(statSixSpinner);

				statSevenSpinner = new JSpinner();
				statSevenSpinner.setName("statSevenSpinner");
				statSevenSpinner.setBounds(240, 116, 43, 23);
				add(statSevenSpinner);

				statEightSpinner = new JSpinner();
				statEightSpinner.setName("statEightSpinner");
				statEightSpinner.setBounds(527, 118, 43, 23);
				add(statEightSpinner);

				statNineSpinner = new JSpinner();
				statNineSpinner.setName("statNineSpinner");
				statNineSpinner.setBounds(240, 148, 43, 23);
				add(statNineSpinner);

				statTenSpinner = new JSpinner();
				statTenSpinner.setName("statTenSpinner");
				statTenSpinner.setBounds(527, 150, 43, 23);
				add(statTenSpinner);

				damageOneMinSpinner = new JSpinner();
				damageOneMinSpinner.setName("damageOneMinSpinner");
				damageOneMinSpinner.setBounds(225, 233, 43, 23);
				add(damageOneMinSpinner);

				damageOneMaxSpinner = new JSpinner();
				damageOneMaxSpinner.setName("damageOneMaxSpinner");
				damageOneMaxSpinner.setBounds(278, 233, 43, 23);
				add(damageOneMaxSpinner);

				damageTwoMinSpinner = new JSpinner();
				damageTwoMinSpinner.setName("damageTwoMinSpinner");
				damageTwoMinSpinner.setBounds(225, 267, 43, 23);
				add(damageTwoMinSpinner);

				damageTwoMaxSpinner = new JSpinner();
				damageTwoMaxSpinner.setName("damageTwoMaxSpinner");
				damageTwoMaxSpinner.setBounds(278, 267, 43, 23);
				add(damageTwoMaxSpinner);

				damageThreeMinSpinner = new JSpinner();
				damageThreeMinSpinner.setName("damageThreeMinSpinner");
				damageThreeMinSpinner.setBounds(225, 300, 43, 23);
				add(damageThreeMinSpinner);

				damageThreeMaxSpinner = new JSpinner();
				damageThreeMaxSpinner.setName("damageThreeMaxSpinner");
				damageThreeMaxSpinner.setBounds(278, 300, 43, 23);
				add(damageThreeMaxSpinner);

				damageFourMinSpinner = new JSpinner();
				damageFourMinSpinner.setName("damageFourMinSpinner");
				damageFourMinSpinner.setBounds(225, 334, 43, 23);
				add(damageFourMinSpinner);

				damageFourMaxSpinner = new JSpinner();
				damageFourMaxSpinner.setName("damageFourMaxSpinner");
				damageFourMaxSpinner.setBounds(278, 334, 43, 23);
				add(damageFourMaxSpinner);

				damageFiveMinSpinner = new JSpinner();
				damageFiveMinSpinner.setName("damageFiveMinSpinner");
				damageFiveMinSpinner.setBounds(225, 371, 43, 23);
				add(damageFiveMinSpinner);

				damageFiveMaxSpinner = new JSpinner();
				damageFiveMaxSpinner.setName("damageFiveMaxSpinner");
				damageFiveMaxSpinner.setBounds(278, 371, 43, 23);
				add(damageFiveMaxSpinner);

				holyResSpinner = new JSpinner();
				holyResSpinner.setName("holyResSpinner");
				holyResSpinner.setBounds(513, 232, 57, 23);
				add(holyResSpinner);

				fireResSpinner = new JSpinner();
				fireResSpinner.setName("fireResSpinner");
				fireResSpinner.setBounds(513, 266, 57, 23);
				add(fireResSpinner);

				natResSpinner = new JSpinner();
				natResSpinner.setName("natResSpinner");
				natResSpinner.setBounds(513, 299, 57, 23);
				add(natResSpinner);

				frostResSpinner = new JSpinner();
				frostResSpinner.setName("frostResSpinner");
				frostResSpinner.setBounds(513, 333, 57, 23);
				add(frostResSpinner);

				shadowResSpinner = new JSpinner();
				shadowResSpinner.setName("shadowResSpinner");
				shadowResSpinner.setBounds(513, 370, 57, 23);
				add(shadowResSpinner);

				arcaneResSpinner = new JSpinner();
				arcaneResSpinner.setName("arcaneResSpinner");
				arcaneResSpinner.setBounds(513, 400, 57, 23);
				add(arcaneResSpinner);

				armorSpinner = new JSpinner();
				armorSpinner.setName("armorSpinner");
				armorSpinner.setBounds(513, 202, 57, 23);
				add(armorSpinner);
			}

		private void createCheckbox()
			{
				statOneChckbx = new JCheckBox("Stat one");
				statOneChckbx.setName("statOneChckbx");
				statOneChckbx.setToolTipText("Enable or disable the first stat slot for this item.");
				statOneChckbx.setFont(font);
				statOneChckbx.setBounds(6, 22, 97, 23);
				add(statOneChckbx);

				statTwoChckbx = new JCheckBox("Stat two");
				statTwoChckbx.setName("statTwoChckbx");
				statTwoChckbx.setToolTipText("Enable or disable the second stat slot for this item.");
				statTwoChckbx.setFont(font);
				statTwoChckbx.setBounds(293, 24, 97, 23);
				add(statTwoChckbx);

				statThreeChckbx = new JCheckBox("Stat three");
				statThreeChckbx.setName("statThreeChckbx");
				statThreeChckbx.setToolTipText("Enable or disable the third stat slot for this item.");
				statThreeChckbx.setFont(font);
				statThreeChckbx.setBounds(6, 54, 97, 23);
				add(statThreeChckbx);

				statFourChckbx = new JCheckBox("Stat four");
				statFourChckbx.setName("statFourChckbx");
				statFourChckbx.setToolTipText("Enable or disable the fourth stat slot for this item.");
				statFourChckbx.setFont(font);
				statFourChckbx.setBounds(293, 56, 97, 23);
				add(statFourChckbx);

				statFiveChckbx = new JCheckBox("Stat five");
				statFiveChckbx.setName("statFiveChckbx");
				statFiveChckbx.setToolTipText("Enable or disable the firth stat slot for this item.");
				statFiveChckbx.setFont(font);
				statFiveChckbx.setBounds(6, 82, 97, 23);
				add(statFiveChckbx);

				statSixChckbx = new JCheckBox("Stat six");
				statSixChckbx.setName("statSixChckbx");
				statSixChckbx.setToolTipText("Enable or disable the sixth stat slot for this item.");
				statSixChckbx.setFont(font);
				statSixChckbx.setBounds(293, 84, 97, 23);
				add(statSixChckbx);

				statSevenChckbx = new JCheckBox("Stat seven");
				statSevenChckbx.setName("statSevenChckbx");
				statSevenChckbx.setToolTipText("Enable or disable the seventh stat slot for this item.");
				statSevenChckbx.setFont(font);
				statSevenChckbx.setBounds(6, 114, 97, 23);
				add(statSevenChckbx);

				statEightChckbx = new JCheckBox("Stat eight");
				statEightChckbx.setName("statEightChckbx");
				statEightChckbx.setToolTipText("Enable or disable the eighth stat slot for this item.");
				statEightChckbx.setFont(font);
				statEightChckbx.setBounds(293, 116, 97, 23);
				add(statEightChckbx);

				statNineChckbx = new JCheckBox("Stat nine");
				statNineChckbx.setName("statNineChckbx");
				statNineChckbx.setToolTipText("Enable or disable the ninth stat slot for this item.");
				statNineChckbx.setFont(font);
				statNineChckbx.setBounds(6, 146, 97, 23);
				add(statNineChckbx);

				statTenChckbx = new JCheckBox("Stat ten");
				statTenChckbx.setName("statTenChckbx");
				statTenChckbx.setToolTipText("Enable or disable the tenth stat slot for this item.");
				statTenChckbx.setFont(font);
				statTenChckbx.setBounds(293, 148, 97, 23);
				add(statTenChckbx);

				damageTypeOneChckbx = new JCheckBox("Damagetype one");
				damageTypeOneChckbx.setName("damageTypeOneChckbx");
				damageTypeOneChckbx.setToolTipText("Enable or disable the first damage type for this item.");
				damageTypeOneChckbx.setFont(null);
				damageTypeOneChckbx.setBounds(6, 232, 128, 23);
				add(damageTypeOneChckbx);

				damageTypeTwoChckbx = new JCheckBox("Damagetype two");
				damageTypeTwoChckbx.setName("damageTypeTwoChckbx");
				damageTypeTwoChckbx.setToolTipText("Enable or disable the second damage type for this item.");
				damageTypeTwoChckbx.setFont(null);
				damageTypeTwoChckbx.setBounds(6, 265, 128, 23);
				add(damageTypeTwoChckbx);

				damageTypeThreeChckbx = new JCheckBox("Damagetype three");
				damageTypeThreeChckbx.setName("damageTypeThreeChckbx");
				damageTypeThreeChckbx.setToolTipText("Enable or disable the third damage type for this item.");
				damageTypeThreeChckbx.setFont(null);
				damageTypeThreeChckbx.setBounds(6, 299, 128, 23);
				add(damageTypeThreeChckbx);

				damageTypeFourChckbx = new JCheckBox("Damagetype four");
				damageTypeFourChckbx.setName("damageTypeFourChckbx");
				damageTypeFourChckbx.setToolTipText("Enable or disable the fourth damage type for this item.");
				damageTypeFourChckbx.setFont(null);
				damageTypeFourChckbx.setBounds(6, 333, 128, 23);
				add(damageTypeFourChckbx);

				damageTypeFiveChckbx = new JCheckBox("Damagetype five");
				damageTypeFiveChckbx.setName("damageTypeFiveChckbx");
				damageTypeFiveChckbx.setToolTipText("Enable or disable the fifth damage type for this item.");
				damageTypeFiveChckbx.setFont(null);
				damageTypeFiveChckbx.setBounds(6, 370, 128, 23);
				add(damageTypeFiveChckbx);

				holyResChckbx = new JCheckBox("Holy Resistance");
				holyResChckbx.setName("holyResChckbx");
				holyResChckbx.setFont(null);
				holyResChckbx.setBounds(374, 232, 136, 23);
				add(holyResChckbx);

				fireResChckbx = new JCheckBox("Fire Resistance");
				fireResChckbx.setName("fireResChckbx");
				fireResChckbx.setFont(null);
				fireResChckbx.setBounds(374, 265, 136, 23);
				add(fireResChckbx);

				natResChckbx = new JCheckBox("Nature Resistance");
				natResChckbx.setName("natResChckbx");
				natResChckbx.setFont(null);
				natResChckbx.setBounds(374, 299, 136, 23);
				natResChckbx.setOpaque(false);
				add(natResChckbx);

				frostResChckbx = new JCheckBox("Frost Resistance");
				frostResChckbx.setName("frostResChckbx");
				frostResChckbx.setFont(null);
				frostResChckbx.setBounds(374, 333, 136, 23);
				add(frostResChckbx);

				shadowResChckbx = new JCheckBox("Shadow Resistance");
				shadowResChckbx.setName("shadowResChckbx");
				shadowResChckbx.setFont(null);
				shadowResChckbx.setBounds(374, 370, 136, 23);
				add(shadowResChckbx);

				arcaneResChckbx = new JCheckBox("Arcane Resistance");
				arcaneResChckbx.setName("arcaneResChckbx");
				arcaneResChckbx.setFont(null);
				arcaneResChckbx.setBounds(374, 400, 136, 23);
				add(arcaneResChckbx);

				armorChckbx = new JCheckBox("Armor");
				armorChckbx.setName("armorChckbx");
				armorChckbx.setFont(null);
				armorChckbx.setBounds(374, 202, 136, 23);
				add(armorChckbx);

			}

		public static void setBlockVisibility(boolean isVisible)
			{
				blockBox.setVisible(isVisible);
			}

		public static void setRangedModVisibility(boolean isVisible)
			{
				rangedModRangeSpinner.setVisible(isVisible);
				rangedModLbl.setVisible(isVisible);
			}

		public int getStatType(int statIndex)
			{

				switch (statIndex)
				{
				case 1:
					return (statOneChckbx.isSelected() && statOneChckbx.isEnabled() && isEquipable)
							? ((Stats) statBox.getSelectedItem()).id
							: 0;
				case 2:
					return (statTwoChckbx.isSelected() && statTwoChckbx.isEnabled() && isEquipable)
							? ((Stats) statTwoBox.getSelectedItem()).id
							: 0;
				case 3:
					return (statThreeChckbx.isSelected() && statThreeChckbx.isEnabled() && isEquipable)
							? ((Stats) statThreeBox.getSelectedItem()).id
							: 0;
				case 4:
					return (statFourChckbx.isSelected() && statFourChckbx.isEnabled() && isEquipable)
							? ((Stats) statFourBox.getSelectedItem()).id
							: 0;
				case 5:
					return (statFiveChckbx.isSelected() && statFiveChckbx.isEnabled() && isEquipable)
							? ((Stats) statFiveBox.getSelectedItem()).id
							: 0;
				case 6:
					return (statSixChckbx.isSelected() && statSixChckbx.isEnabled() && isEquipable)
							? ((Stats) statSixBox.getSelectedItem()).id
							: 0;
				case 7:
					return (statSevenChckbx.isSelected() && statSevenChckbx.isEnabled() && isEquipable)
							? ((Stats) statSevenBox.getSelectedItem()).id
							: 0;
				case 8:
					return (statEightChckbx.isSelected() && statEightChckbx.isEnabled() && isEquipable)
							? ((Stats) statEightBox.getSelectedItem()).id
							: 0;
				case 9:
					return (statNineChckbx.isSelected() && statNineChckbx.isEnabled() && isEquipable)
							? ((Stats) statNineBox.getSelectedItem()).id
							: 0;
				case 10:
					return (statTenChckbx.isSelected() && statTenChckbx.isEnabled() && isEquipable)
							? ((Stats) statTenBox.getSelectedItem()).id
							: 0;
				default:
					return 0;
				}
			}

		public int getStatValue(int statIndex)
			{
				switch (statIndex)
				{
				case 1:
					return (statOneChckbx.isSelected() && statOneChckbx.isEnabled() && isEquipable)
							? (int) statOneSpinner.getValue()
							: 0;
				case 2:
					return (statTwoChckbx.isSelected() && statTwoChckbx.isEnabled() && isEquipable)
							? (int) statTwoSpinner.getValue()
							: 0;
				case 3:
					return (statThreeChckbx.isSelected() && statThreeChckbx.isEnabled() && isEquipable)
							? (int) statThreeSpinner.getValue()
							: 0;
				case 4:
					return (statFourChckbx.isSelected() && statFourChckbx.isEnabled() && isEquipable)
							? (int) statFourSpinner.getValue()
							: 0;
				case 5:
					return (statFiveChckbx.isSelected() && statFiveChckbx.isEnabled() && isEquipable)
							? (int) statFiveSpinner.getValue()
							: 0;
				case 6:
					return (statSixChckbx.isSelected() && statSixChckbx.isEnabled() && isEquipable)
							? (int) statSixSpinner.getValue()
							: 0;
				case 7:
					return (statSevenChckbx.isSelected() && statSevenChckbx.isEnabled() && isEquipable)
							? (int) statSevenSpinner.getValue()
							: 0;
				case 8:
					return (statEightChckbx.isSelected() && statEightChckbx.isEnabled() && isEquipable)
							? (int) statEightSpinner.getValue()
							: 0;
				case 9:
					return (statNineChckbx.isSelected() && statNineChckbx.isEnabled() && isEquipable)
							? (int) statNineSpinner.getValue()
							: 0;
				case 10:
					return (statTenChckbx.isSelected() && statTenChckbx.isEnabled() && isEquipable)
							? (int) statTenSpinner.getValue()
							: 0;
				default:
					return 0;
				}
			}

		public int getDamageType(int damageIndex)
			{
				switch (damageIndex)
				{
				case 1:
					return (damageTypeOneChckbx.isSelected() && damageTypeOneChckbx.isEnabled() && isEquipable)
							? ((DamageSchools) damageTypeOneBox.getSelectedItem()).id
							: 0;
				case 2:
					return (damageTypeTwoChckbx.isSelected() && damageTypeTwoChckbx.isEnabled() && isEquipable)
							? ((DamageSchools) damageTypeTwoBox.getSelectedItem()).id
							: 0;
				case 3:
					return (damageTypeThreeChckbx.isSelected() && damageTypeThreeChckbx.isEnabled() && isEquipable)
							? ((DamageSchools) damageTypeThreeBox.getSelectedItem()).id
							: 0;
				case 4:
					return (damageTypeFourChckbx.isSelected() && damageTypeFourChckbx.isEnabled() && isEquipable)
							? ((DamageSchools) damageTypeFourBox.getSelectedItem()).id
							: 0;
				case 5:
					return (damageTypeFiveChckbx.isSelected() && damageTypeFiveChckbx.isEnabled() && isEquipable)
							? ((DamageSchools) damageTypeFiveBox.getSelectedItem()).id
							: 0;
				default:
					return 0;
				}
			}

		public int getDamageMin(int damageMinIndex)
			{
				switch (damageMinIndex)
				{
				case 1:
					return (damageTypeOneChckbx.isSelected() && damageTypeOneChckbx.isEnabled() && isEquipable)
							? (int) damageOneMinSpinner.getValue()
							: 0;
				case 2:
					return (damageTypeTwoChckbx.isSelected() && damageTypeTwoChckbx.isEnabled() && isEquipable)
							? (int) damageTwoMinSpinner.getValue()
							: 0;
				case 3:
					return (damageTypeThreeChckbx.isSelected() && damageTypeThreeChckbx.isEnabled() && isEquipable)
							? (int) damageThreeMinSpinner.getValue()
							: 0;
				case 4:
					return (damageTypeFourChckbx.isSelected() && damageTypeFourChckbx.isEnabled() && isEquipable)
							? (int) damageFourMinSpinner.getValue()
							: 0;
				case 5:
					return (damageTypeFiveChckbx.isSelected() && damageTypeFiveChckbx.isEnabled() && isEquipable)
							? (int) damageFiveMinSpinner.getValue()
							: 0;
				default:
					return 0;
				}
			}

		public int getDamageMax(int damageMaxIndex)
			{
				switch (damageMaxIndex)
				{
				case 1:
					return (damageTypeOneChckbx.isSelected() && damageTypeOneChckbx.isEnabled() && isEquipable)
							? (int) damageOneMaxSpinner.getValue()
							: 0;
				case 2:
					return (damageTypeTwoChckbx.isSelected() && damageTypeTwoChckbx.isEnabled() && isEquipable)
							? (int) damageTwoMaxSpinner.getValue()
							: 0;
				case 3:
					return (damageTypeThreeChckbx.isSelected() && damageTypeThreeChckbx.isEnabled() && isEquipable)
							? (int) damageThreeMaxSpinner.getValue()
							: 0;
				case 4:
					return (damageTypeFourChckbx.isSelected() && damageTypeFourChckbx.isEnabled() && isEquipable)
							? (int) damageFourMaxSpinner.getValue()
							: 0;
				case 5:
					return (damageTypeFiveChckbx.isSelected() && damageTypeFiveChckbx.isEnabled() && isEquipable)
							? (int) damageFiveMaxSpinner.getValue()
							: 0;
				default:
					return 0;
				}
			}

		public int getResValue(String res)
			{
				switch (res)
				{
				case "holy":
					return (holyResChckbx.isSelected() && holyResChckbx.isEnabled() && isEquipable)
							? (int) holyResSpinner.getValue()
							: 0;
				case "fire":
					return (fireResChckbx.isSelected() && fireResChckbx.isEnabled() && isEquipable)
							? (int) fireResSpinner.getValue()
							: 0;
				case "nature":
					return (natResChckbx.isSelected() && natResChckbx.isEnabled() && isEquipable)
							? (int) natResSpinner.getValue()
							: 0;
				case "frost":
					return (frostResChckbx.isSelected() && frostResChckbx.isEnabled() && isEquipable)
							? (int) frostResSpinner.getValue()
							: 0;
				case "shadow":
					return (shadowResChckbx.isSelected() && shadowResChckbx.isEnabled() && isEquipable)
							? (int) shadowResSpinner.getValue()
							: 0;
				case "arcane":
					return (arcaneResChckbx.isSelected() && arcaneResChckbx.isEnabled() && isEquipable)
							? (int) arcaneResSpinner.getValue()
							: 0;
				case "armor":
					return (armorChckbx.isSelected() && armorChckbx.isEnabled() && isEquipable)
							? (int) armorSpinner.getValue()
							: 0;
				default:
					return 0;
				}
			}

		public int getDelay()
			{
				System.out.println("Weapon speed is: " + (int) delaySpinner.getValue());
				return BasicInfoPanel.getArmorCheck().isSelected() ? (int) delaySpinner.getValue() : 0;

			}

		public double getRangeMod()
			{
				return (double) rangedModRangeSpinner.getValue();
			}

		public int getBlockValue()
			{
				return SQLGenerator.getInt(blockBox);
			}
	}
