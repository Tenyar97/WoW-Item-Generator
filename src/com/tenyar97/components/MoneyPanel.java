package com.tenyar97.components;

import javax.swing.*;

import com.tenyar97.util.SavedDataManager;

public class MoneyPanel extends JPanel
	{
		private JSpinner goldSpinner, silverSpinner, copperSpinner;

		public MoneyPanel()
			{
				goldSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 10000, 1));
				silverSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
				copperSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));

				add(new JLabel("Gold:"));
				add(goldSpinner);
				add(new JLabel("Silver:"));
				add(silverSpinner);
				add(new JLabel("Copper:"));
				add(copperSpinner);
			}

		public int getTotalCopper()
			{
				int g = (int) goldSpinner.getValue();
				int s = (int) silverSpinner.getValue();
				int c = (int) copperSpinner.getValue();
				return g * 10000 + s * 100 + c;
			}

		public void setPanelValue(int totalCopper)
			{
				int gold = totalCopper / 10000;
				int silver = (totalCopper % 10000) / 100;
				int copper = totalCopper % 100;

				goldSpinner.setValue(gold);
				silverSpinner.setValue(silver);
				copperSpinner.setValue(copper);

			}

	}
