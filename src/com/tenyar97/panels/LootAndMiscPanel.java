package com.tenyar97.panels;

import javax.swing.*;

import com.tenyar97.Main;
import com.tenyar97.components.MoneyPanel;
import com.tenyar97.components.Textbox;
import com.tenyar97.util.SQLGenerator;
import com.tenyar97.util.UIBuilder;

import attributes.PetFood;

import java.awt.*;

public class LootAndMiscPanel extends JPanel
	{

		JLabel minGold, maxGold;
		public JCheckBox mnyBxChckbx;
		public static JCheckBox petFoodChckbx;
		public static JCheckBox questSrtChckbx;
		public static JCheckBox durationChckbx;
		public static JSpinner durationSpinner;
		public JComboBox<PetFood> petFoodBox;
		public MoneyPanel minMoneyLoot, maxMoneyLoot;
		public Textbox itemLevelBx;
		public static Textbox questIDTextbox;
		public Font font = Main.lifeCraft;

		public LootAndMiscPanel()
			{
				setLayout(null);

				mnyBxChckbx = new JCheckBox("Does it hold gold?");
				mnyBxChckbx.setName("mnyBxChckbx");
				mnyBxChckbx.setFont(font);
				mnyBxChckbx.setBounds(6, 135, 135, 23);
				add(mnyBxChckbx);

				minMoneyLoot = new MoneyPanel();
				minMoneyLoot.setName("minMoneyLoot");
				minMoneyLoot.setBounds(125, 165, 309, 30);
				minMoneyLoot.setToolTipText("Minimum money this container can yield.");
				minMoneyLoot.setVisible(false);
				add(minMoneyLoot);

				maxMoneyLoot = new MoneyPanel();
				maxMoneyLoot.setName("maxMoneyLoot");
				maxMoneyLoot.setBounds(125, 197, 309, 30);
				maxMoneyLoot.setToolTipText("Maximum money this container can yield.");
				maxMoneyLoot.setVisible(false);
				add(maxMoneyLoot);

				minGold = new JLabel("Minimum Amount:");
				minGold.setName("minGold");
				minGold.setFont(font);
				minGold.setBounds(6, 166, 125, 26);
				minGold.setVisible(false);
				add(minGold);

				maxGold = new JLabel("Maximum Amount:");
				maxGold.setName("maxGold");
				maxGold.setFont(font);
				maxGold.setBounds(6, 197, 125, 26);
				maxGold.setVisible(false);
				add(maxGold);

				UIBuilder.bindVisibility(mnyBxChckbx, minGold, minMoneyLoot, maxGold, maxMoneyLoot);

				petFoodChckbx = new JCheckBox("Is it Pet Food?");
				petFoodChckbx.setName("petFoodChckbx");
				petFoodChckbx.setFont(font);
				petFoodChckbx.setToolTipText("Indicates what type of pet food the item is.");
				petFoodChckbx.setBounds(6, 37, 113, 23);
				add(petFoodChckbx);

				petFoodBox = createPetFoodBox();
				petFoodBox.setName("petFoodBox");
				petFoodBox.setBounds(125, 39, 74, 22);
				petFoodBox.setVisible(false);
				UIBuilder.bindVisibility(petFoodChckbx, petFoodBox);
				add(petFoodBox);

				questSrtChckbx = new JCheckBox("Does it start a quest?");
				questSrtChckbx.setName("questSrtChckbx");
				questSrtChckbx.setFont(font);
				questSrtChckbx.setToolTipText("ID of the quest started by this item.");
				questSrtChckbx.setBounds(6, 7, 155, 23);
				add(questSrtChckbx);

				questIDTextbox = new Textbox("QuestID");
				questIDTextbox.setName("questIDTextbox");
				questIDTextbox.setVisible(false);
				UIBuilder.bindVisibility(questSrtChckbx, questIDTextbox);
				questIDTextbox.setBounds(162, 8, 155, 23);
				add(questIDTextbox);

				itemLevelBx = new Textbox("Item Level");
				itemLevelBx.setName("itemLevelBx");
				itemLevelBx.setBounds(6, 105, 155, 23);
				add(itemLevelBx);

				durationChckbx = new JCheckBox("Is it time sensitive?");
				durationChckbx.setName("durationChckbx");
				durationChckbx.setFont(font);
				durationChckbx.setToolTipText("Time (in minutes) before the item disappears after being picked up.");
				durationChckbx.setFont(null);
				durationChckbx.setBounds(6, 63, 135, 29);
				add(durationChckbx);

				durationSpinner = new JSpinner();
				durationSpinner.setName("durationSpinner");
				durationSpinner.setBounds(135, 67, 40, 20);
				UIBuilder.bindVisibility(durationChckbx, durationSpinner);
				add(durationSpinner);

				// enforceMinMaxRule();

				UIBuilder.autoNameFields(this);
			}

		private JComboBox<PetFood> createPetFoodBox()
			{

				return new JComboBox<>(PetFood.getAllFoods());
			}

		public int getItemLevel()
			{

				return SQLGenerator.sanitizeString(itemLevelBx).equalsIgnoreCase("Item Level") ? 0
						: SQLGenerator.getInt(itemLevelBx);
			}

		public int getStartQuestID()
			{// questIDTextbox
				return SQLGenerator.sanitizeString(questIDTextbox).equalsIgnoreCase("QuestID") ? 0
						: SQLGenerator.getInt(questIDTextbox);
			}

		public int getFoodType()
			{
				PetFood food = (PetFood) petFoodBox.getSelectedItem();
				System.out.println(petFoodChckbx.isEnabled());
				return petFoodChckbx.isSelected() ? food.id : 0;
			}

		public int getMinHeldGold()
			{
				return minMoneyLoot.getTotalCopper();
			}

		public int getMaxHeldGold()
			{
				return maxMoneyLoot.getTotalCopper();
			}

		public int getDuration()
			{
				Number duration = (Number) durationSpinner.getValue();
				return duration.intValue();
			}
	}