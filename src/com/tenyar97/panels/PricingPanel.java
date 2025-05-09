package com.tenyar97.panels;

import javax.swing.*;

import com.tenyar97.Main;
import com.tenyar97.components.MoneyPanel;
import com.tenyar97.util.UIBuilder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.event.ActionEvent;

public class PricingPanel extends JPanel
	{
		public MoneyPanel buyPricePanel, sellPricePanel, minBuyPricePanel, maxBuyPricePanel, minSellPricePanel,
				maxSellPricePanel;
		private JLabel buyLabel, sellLabel, buyCountLabel, minimumBuyPrice, maximumBuyPrice, minimumSellPrice,
				maximumSellPrice;
		private static JCheckBox randomPricechckbx;
		private static JSpinner buyCountpinner;
		private static JButton randomizePriceBtn;
		private Random rand = new Random();

		public Font font = Main.lifeCraft;

		public PricingPanel()
			{

				setLayout(null);

				buyLabel = new JLabel("Buy Price:");
				buyLabel.setFont(font);
				buyLabel.setToolTipText("Gold cost to purchase from a vendor.");
				buyLabel.setBounds(10, 10, 87, 30);
				add(buyLabel);

				sellLabel = new JLabel("Sell Price:");
				sellLabel.setFont(font);
				sellLabel.setToolTipText("Gold received when selling to a vendor.");
				sellLabel.setBounds(10, 51, 87, 30);
				add(sellLabel);

				buyPricePanel = new MoneyPanel();
				buyPricePanel.setName("buyPricePanel");
				buyPricePanel.setBounds(76, 11, 309, 30);
				add(buyPricePanel);
				
				sellPricePanel = new MoneyPanel();
				sellPricePanel.setName("sellPricePanel");
				sellPricePanel.setBounds(76, 51, 309, 30);
				add(sellPricePanel);

				buyCountLabel = new JLabel("Buy Count:");
				buyCountLabel.setFont(font);
				buyCountLabel.setToolTipText("Number of items sold in a stack from vendors.");
				buyCountLabel.setBounds(395, 10, 77, 30);
				add(buyCountLabel);

				buyCountpinner = new JSpinner();
				buyCountpinner.setName("buyCountpinner");
				buyCountpinner.setBounds(471, 17, 58, 20);
				add(buyCountpinner);

				randomizePriceBtn = new JButton("Randomize price");
				randomizePriceBtn.addActionListener(new ActionListener()
					{

						public void actionPerformed(ActionEvent e)
							{
								buyPricePanel.setPanelValue(getRandomPrice(minBuyPricePanel.getTotalCopper(),
										maxBuyPricePanel.getTotalCopper()));
								sellPricePanel.setPanelValue(getRandomPrice(minSellPricePanel.getTotalCopper(),
										maxSellPricePanel.getTotalCopper()));
							}
					});
				randomizePriceBtn.setBounds(122, 256, 309, 23);
				add(randomizePriceBtn);

				minBuyPricePanel = new MoneyPanel();
				minBuyPricePanel.setName("minBuyPricePanel");
				minBuyPricePanel.setBounds(122, 92, 309, 30);
				add(minBuyPricePanel);

				maxBuyPricePanel = new MoneyPanel();
				maxBuyPricePanel.setName("maxBuyPricePanel");
				maxBuyPricePanel.setBounds(122, 133, 309, 30);
				add(maxBuyPricePanel);

				randomPricechckbx = new JCheckBox("Random Price?");
				randomPricechckbx.setName("randomPricechckbx");
				randomPricechckbx.setBounds(398, 55, 131, 23);
				add(randomPricechckbx);

				minimumBuyPrice = new JLabel("Minimum Buy Price:");
				minimumBuyPrice.setName("minimumBuyPrice");
				minimumBuyPrice.setToolTipText("");
				minimumBuyPrice.setFont(null);
				minimumBuyPrice.setBounds(10, 92, 131, 30);
				add(minimumBuyPrice);

				maximumBuyPrice = new JLabel("Maximum Buy Price");
				maximumBuyPrice.setToolTipText("Gold received when selling to a vendor.");
				maximumBuyPrice.setFont(null);
				maximumBuyPrice.setBounds(10, 133, 131, 30);
				add(maximumBuyPrice);

				minimumSellPrice = new JLabel("Minimum Sell Price:");
				minimumSellPrice.setToolTipText("");
				minimumSellPrice.setFont(null);
				minimumSellPrice.setBounds(10, 174, 131, 30);
				add(minimumSellPrice);

				minSellPricePanel = new MoneyPanel();
				minSellPricePanel.setName("minSellPricePanel");
				minSellPricePanel.setBounds(122, 174, 309, 30);
				add(minSellPricePanel);

				maximumSellPrice = new JLabel("Maximum Sell Price");
				maximumSellPrice.setName("maximumSellPrice");
				maximumSellPrice.setToolTipText("");
				maximumSellPrice.setFont(null);
				maximumSellPrice.setBounds(10, 215, 131, 30);
				add(maximumSellPrice);

				maxSellPricePanel = new MoneyPanel();
				maxSellPricePanel.setName("maxSellPricePanel");
				maxSellPricePanel.setBounds(122, 215, 309, 30);
				add(maxSellPricePanel);

				UIBuilder.bindVisibility(randomPricechckbx, minBuyPricePanel, maxBuyPricePanel, minSellPricePanel,
						maxSellPricePanel, minimumBuyPrice, maximumBuyPrice, minimumSellPrice, maximumSellPrice,
						randomizePriceBtn);

				UIBuilder.autoNameFields(this);
			}

		public int getBuyCount()
			{
				Number val = (Number) buyCountpinner.getValue();
				return val.intValue();

			}

		public int getBuyPrice()
			{
				return (int) buyPricePanel.getTotalCopper();
			}

		public int getSellPrice()
			{
				return (int) sellPricePanel.getTotalCopper();
			}

		public int getRandomPrice(int lowestPrice, int highestPrice)
			{
				if (lowestPrice >= highestPrice)
					{
						return lowestPrice;
					}
				return rand.nextInt(highestPrice - lowestPrice) + lowestPrice;
			}
	}