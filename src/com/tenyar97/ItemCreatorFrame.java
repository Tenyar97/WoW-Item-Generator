package com.tenyar97;

import java.awt.Component;
import java.awt.Container;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tenyar97.components.BackgroundTabbedPane;
import com.tenyar97.panels.BasicInfoPanel;
import com.tenyar97.panels.FlagsPanel;
import com.tenyar97.panels.LootAndMiscPanel;
import com.tenyar97.panels.OutputPanel;
import com.tenyar97.panels.PricingPanel;
import com.tenyar97.panels.PropertiesPanel;
import com.tenyar97.panels.RequirementsPanel;
import com.tenyar97.panels.SpellsPanel;
import com.tenyar97.panels.StatsPanel;
import com.tenyar97.panels.UseRestrictionPanel;
import com.tenyar97.util.ResourceLoader;
import com.tenyar97.util.SavedDataManager;

import attributes.ItemClass;

public class ItemCreatorFrame extends JFrame
	{
		private JTabbedPane tabbedPane;
		private BasicInfoPanel basicInfoPanel;
		private RequirementsPanel requirementsPanel;
		private StatsPanel statsPanel;
		private SpellsPanel spellsPanel;
		private PricingPanel pricingPanel;
		private FlagsPanel flagsPanel;
		private UseRestrictionPanel useRestrictionPanel;
		private LootAndMiscPanel lootAndMiscPanel;
		private PropertiesPanel propertiesPanel;
		private OutputPanel outputPanel;

		private boolean statsTabVisible = false;
		private int previousTabIndex = 0;

		public ItemCreatorFrame() throws IOException
			{
				JMenuBar menuBar = new JMenuBar();							// 10 panels? Slapped in. Menu bar? Sure. Data reset logic? Why not.
				JMenu fileMenu = new JMenu("File");							// Yes, it's all in the constructor. No, we’re not proud.
				JMenuItem saveMenuItem = new JMenuItem("Save");				// Someday maybe we’ll refactor this. But not today.
				saveMenuItem.addActionListener(e -> saveUserData());
				JMenuItem loadMenuItem = new JMenuItem("Load");
				loadMenuItem.addActionListener(e -> loadUserData());
				JMenuItem newItem = new JMenuItem("Reset Item");
				newItem.addActionListener(e ->
				{
					SwingUtilities.invokeLater(() ->
					{
						SavedDataManager.loadAndApplyDeep(this.getContentPane(), new File(
								ResourceLoader.getResourceDirectoryPath(ResourceLoader.ITEMS + "default_item.json")));

					});

					tabbedPane.setSelectedIndex(0);
				});

				setTitle("Tenyar97's Item Creator");
				setSize(597, 524);
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setLocationRelativeTo(null);

				tabbedPane = Main.customIcons ? new BackgroundTabbedPane() : new JTabbedPane();

				basicInfoPanel = new BasicInfoPanel(this);
				requirementsPanel = new RequirementsPanel();
				statsPanel = new StatsPanel();
				spellsPanel = new SpellsPanel();
				pricingPanel = new PricingPanel();
				flagsPanel = new FlagsPanel();
				useRestrictionPanel = new UseRestrictionPanel();
				propertiesPanel = new PropertiesPanel();
				lootAndMiscPanel = new LootAndMiscPanel();

				outputPanel = new OutputPanel(basicInfoPanel, requirementsPanel, statsPanel, spellsPanel, pricingPanel,
						flagsPanel, useRestrictionPanel, lootAndMiscPanel, propertiesPanel);

				tabbedPane.add("Basic Info", basicInfoPanel);
				tabbedPane.add("Requirements", requirementsPanel);
				tabbedPane.add("Spells", spellsPanel);
				tabbedPane.add("Pricing & Vendor", pricingPanel);
				tabbedPane.add("Flags", flagsPanel);
				tabbedPane.add("Use Restrictions", useRestrictionPanel);
				tabbedPane.add("Loot and Misc.", lootAndMiscPanel);
				tabbedPane.add("Properties", propertiesPanel);
				tabbedPane.add("Output", outputPanel);

				getContentPane().add(tabbedPane);

				setVisible(true);

				tabbedPane.addChangeListener(e ->
				{
					int newTabIndex = tabbedPane.getSelectedIndex();

					Component currentTab = tabbedPane.getComponentAt(previousTabIndex);

					if (currentTab instanceof LootAndMiscPanel)
						{
							LootAndMiscPanel panel = (LootAndMiscPanel) currentTab;
							boolean unEqual = panel.getMaxHeldGold() < panel.getMinHeldGold();

							if (panel.mnyBxChckbx.isSelected() && unEqual)
								{
									SwingUtilities.invokeLater(() -> tabbedPane.setSelectedIndex(previousTabIndex));
									JOptionPane.showMessageDialog(panel,
											"Maximum held Gold cannot be less then the Minimum abount!");
									return;
								}
						}

					previousTabIndex = newTabIndex;
				});

				fileMenu.add(saveMenuItem);
				fileMenu.add(loadMenuItem);
				fileMenu.add(newItem);
				menuBar.add(fileMenu);

				// resetItem
				setJMenuBar(menuBar);
			}

		private void loadUserData()
			{
				JFileChooser fileChooser = new JFileChooser();

				// Suggest "presets/" as the default folder (create if missing)
				// System.out.println(ResourceLoader.ICONS);
				File presetDir = new File(ResourceLoader.ITEMS);
				if (!presetDir.exists())
					presetDir.mkdirs();
				fileChooser.setCurrentDirectory(presetDir);

				// Set JSON filter
				fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));

				if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
					{
						File file = fileChooser.getSelectedFile();

						spellsPanel.setSuppressEditorOpen(true);
						Map<String, Object> data = SavedDataManager.loadValuesFromFileAndReturn(file);

						SavedDataManager.applyValuesDeep(tabbedPane, data);

						spellsPanel.reloadFromLoadedFields();
						spellsPanel.syncCheckboxesToSpellState(); // ← here
						spellsPanel.setSuppressEditorOpen(false);

					}
			}

		private void saveUserData()
			{
				JFileChooser fileChooser = new JFileChooser();

				File presetDir = new File(ResourceLoader.ITEMS);
				if (!presetDir.exists())
					presetDir.mkdirs();
				fileChooser.setCurrentDirectory(presetDir);

				fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));

				fileChooser.setSelectedFile(new File(presetDir, basicInfoPanel.getName() + ".json"));

				if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
					{
						File file = fileChooser.getSelectedFile();
						if (!file.getName().toLowerCase().endsWith(".json"))
							{
								file = new File(file.getAbsolutePath() + ".json");
							}

						SavedDataManager.saveValuesToFile(tabbedPane, file);

					}
			}

		public static void makeTransparent(Component component)
			{
				if (component instanceof JComponent)
					{
						((JComponent) component).setOpaque(false);
					}

				if (component instanceof Container)
					{
						for (Component child : ((Container) component).getComponents())
							{
								makeTransparent(child);
							}
					}
			}

		public void updateStatsTabVisibility(ItemClass itemClass)
			{
				boolean shouldBeVisible = Main.isEquipable(itemClass);

				if (shouldBeVisible && !statsTabVisible)
					{
						tabbedPane.insertTab("Stats", null, statsPanel, null, 2);
						statsTabVisible = true;
					}
				else if (!shouldBeVisible && statsTabVisible)
					{
						int index = tabbedPane.indexOfComponent(statsPanel);
						if (index != -1)
							{
								tabbedPane.remove(index);
								statsTabVisible = false;
							}
					}
			}

	}



































/*package com.tenyar97;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.tenyar97.components.BackgroundPanel;
import com.tenyar97.panels.BasicInfoPanel;
import com.tenyar97.panels.FlagsPanel;
import com.tenyar97.panels.LootAndMiscPanel;
import com.tenyar97.panels.OutputPanel;
import com.tenyar97.panels.PricingPanel;
import com.tenyar97.panels.PropertiesPanel;
import com.tenyar97.panels.RequirementsPanel;
import com.tenyar97.panels.SpellsPanel;
import com.tenyar97.panels.StatsPanel;
import com.tenyar97.panels.UseRestrictionPanel;
import com.tenyar97.util.ResourceLoader;

import attributes.ItemClass;

public class ItemCreatorFrame extends JFrame
	{
		private JPanel buttonPanel;
		private JPanel cardPanel;
		private CardLayout cardLayout;
		

		// Panels
		private BasicInfoPanel basicInfoPanel;
		private RequirementsPanel requirementsPanel;
		private StatsPanel statsPanel;
		private SpellsPanel spellsPanel;
		private PricingPanel pricingPanel;
		private FlagsPanel flagsPanel;
		private UseRestrictionPanel useRestrictionPanel;
		private LootAndMiscPanel lootAndMiscPanel;
		private PropertiesPanel propertiesPanel;
		private OutputPanel outputPanel;

		private boolean statsTabVisible;

		public ItemCreatorFrame()
			{
				setTitle("Item Creator");
				
				
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				setSize(830, 594);
				setLocationRelativeTo(null);
				
				
				String bgPath = ResourceLoader.getResourceDirectoryPath(ResourceLoader.COMPONENTS) + "blank_tab.png";
				BackgroundPanel backgroundPanel = new BackgroundPanel(bgPath);
				setContentPane(backgroundPanel);  // Set it as the main content pane

				initPanels();         // Adds cardPanel to CENTER of backgroundPanel
				initTabButtons();     // Adds buttonPanel to NORTH of backgroundPanel
				//makeTransparent(cardPanel);
				//makeTransparent(buttonPanel);
				//setLayout(new BorderLayout());
				//updateStatsTabVisibility(BasicInfoPanel.it);

				setVisible(true);
			}

		private void initPanels()
			{
				cardLayout = new CardLayout();
				cardPanel = new JPanel(cardLayout);

				try
					{
						basicInfoPanel = new BasicInfoPanel(this);
						requirementsPanel = new RequirementsPanel();
						statsPanel = new StatsPanel();
						spellsPanel = new SpellsPanel();
						pricingPanel = new PricingPanel();
						flagsPanel = new FlagsPanel();
						useRestrictionPanel = new UseRestrictionPanel();
						propertiesPanel = new PropertiesPanel();
						lootAndMiscPanel = new LootAndMiscPanel();
						outputPanel = new OutputPanel(basicInfoPanel, requirementsPanel, statsPanel, spellsPanel,
								pricingPanel, flagsPanel, useRestrictionPanel, lootAndMiscPanel, null);
					}
				catch (IOException e)
					{
						e.printStackTrace();
					}

				cardPanel.add(basicInfoPanel, "Basic");
				cardPanel.add(requirementsPanel, "Requirements");
				cardPanel.add(statsPanel, "Stats");
				cardPanel.add(spellsPanel, "Spells");
				cardPanel.add(pricingPanel, "Pricing");
				cardPanel.add(flagsPanel, "Flags");
				cardPanel.add(useRestrictionPanel, "Restrictions");
				cardPanel.add(propertiesPanel, "Properties");
				cardPanel.add(lootAndMiscPanel, "Loot");
				cardPanel.add(outputPanel, "Output");
				cardPanel.setOpaque(false);

				add(cardPanel, BorderLayout.CENTER);
			}

		private void initTabButtons()
			{
				buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));


				addTabButton("Basic", "Basic");
				addTabButton("Requirements", "Requirements");
				addTabButton("Properties", "Properties");
				addTabButton("Spells", "Spells");
				addTabButton("Pricing", "Pricing");
				addTabButton("Flags", "Flags");
				addTabButton("Restrictions", "Restrictions");
				addTabButton("Loot", "Loot");
				addTabButton("Output", "Output");

				add(buttonPanel, BorderLayout.NORTH);
			}

		private void addTabButton(String label, String cardName)
			{
				int w = 90; int h = 100;

				JButton button = new JButton();
				ImageIcon icon = new ImageIcon(ResourceLoader.getResourceDirectoryPath(ResourceLoader.COMPONENTS)
						+ label.toLowerCase() + "_tab" + ".png");
				
				Image scaledImage = icon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
				
				
				ImageIcon hoverIcon = new ImageIcon(ResourceLoader.getResourceDirectoryPath(ResourceLoader.COMPONENTS)
						+ label.toLowerCase() + "_tab_rollover" + ".png");
				
				Image scaledHoverImage = hoverIcon.getImage().getScaledInstance(w, h, Image.SCALE_SMOOTH);
				
				
				button.setIcon(new ImageIcon(scaledImage));
				button.setPreferredSize(new Dimension(w, h));
				button.setBorder(null);
				button.setContentAreaFilled(false);
				button.setRolloverIcon(new ImageIcon(scaledHoverImage));

				button.addActionListener((ActionEvent e) -> cardLayout.show(cardPanel, cardName));
				buttonPanel.add(button);
			}

		public void updateStatsTabVisibility(ItemClass itemClass)
			{
				boolean shouldBeVisible = Main.isEquipable(itemClass);

				if (shouldBeVisible && !statsTabVisible)
					{
						// tabbedPane.insertTab("Stats", null, statsPanel, null, 2);
						addTabButton("Stats", "Stats");
						statsTabVisible = true;
					}
				else if (!shouldBeVisible && statsTabVisible)
					{

						//buttonPanel.
						statsTabVisible = false;

					}
			}
		
		private void makeTransparent(JComponent component) {
			component.setOpaque(false);
			for (Component child : component.getComponents()) {
				if (child instanceof JComponent)
					makeTransparent((JComponent) child);
			}
		}


		
		 * tabbedPane.addChangeListener(e -> { int newTabIndex =
		 * tabbedPane.getSelectedIndex();
		 * 
		 * Component currentTab = tabbedPane.getComponentAt(previousTabIndex);
		 * 
		 * if (currentTab instanceof LootAndMiscPanel) { LootAndMiscPanel panel =
		 * (LootAndMiscPanel) currentTab; boolean unEqual = panel.getMaxHeldGold() <
		 * panel.getMinHeldGold();
		 * 
		 * if (panel.mnyBxChckbx.isSelected() && unEqual) {
		 * SwingUtilities.invokeLater(() ->
		 * tabbedPane.setSelectedIndex(previousTabIndex));
		 * JOptionPane.showMessageDialog(panel,
		 * "Maximum held Gold cannot be less then the Minimum abount!"); return; } }
		 * 
		 * previousTabIndex = newTabIndex; });
		 * 
		 * fileMenu.add(saveMenuItem); fileMenu.add(loadMenuItem);
		 * fileMenu.add(newItem); menuBar.add(fileMenu);
		 * 
		 * // resetItem setJMenuBar(menuBar); }
		 * 
		 * private void loadUserData() { JFileChooser fileChooser = new JFileChooser();
		 * 
		 * // Suggest "presets/" as the default folder (create if missing) //
		 * System.out.println(ResourceLoader.ICONS); File presetDir = new
		 * File(ResourceLoader.ITEMS); if (!presetDir.exists()) presetDir.mkdirs();
		 * fileChooser.setCurrentDirectory(presetDir);
		 * 
		 * // Set JSON filter fileChooser.setFileFilter(new
		 * FileNameExtensionFilter("JSON Files", "json"));
		 * 
		 * if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { File
		 * file = fileChooser.getSelectedFile();
		 * 
		 * spellsPanel.setSuppressEditorOpen(true); Map<String, Object> data =
		 * SavedDataManager.loadValuesFromFileAndReturn(file);
		 * 
		 * SavedDataManager.applyValuesDeep(tabbedPane, data);
		 * 
		 * spellsPanel.reloadFromLoadedFields();
		 * spellsPanel.syncCheckboxesToSpellState(); // ← here
		 * spellsPanel.setSuppressEditorOpen(false);
		 * 
		 * } }
		 * 
		 * private void saveUserData() { JFileChooser fileChooser = new JFileChooser();
		 * 
		 * File presetDir = new File(ResourceLoader.ITEMS); if (!presetDir.exists())
		 * presetDir.mkdirs(); fileChooser.setCurrentDirectory(presetDir);
		 * 
		 * fileChooser.setFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
		 * 
		 * fileChooser.setSelectedFile(new File(presetDir, basicInfoPanel.getName() +
		 * ".json"));
		 * 
		 * if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) { File
		 * file = fileChooser.getSelectedFile(); if
		 * (!file.getName().toLowerCase().endsWith(".json")) { file = new
		 * File(file.getAbsolutePath() + ".json"); }
		 * 
		 * SavedDataManager.saveValuesToFile(tabbedPane, file);
		 * 
		 * } }
		 * 
		 * public static void makeTransparent(Component component) { if (component
		 * instanceof JComponent) { ((JComponent) component).setOpaque(false); }
		 * 
		 * if (component instanceof Container) { for (Component child : ((Container)
		 * component).getComponents()) { makeTransparent(child); } } }
		 * 
		 * public void updateStatsTabVisibility(ItemClass itemClass) { boolean
		 * shouldBeVisible = Main.isEquipable(itemClass);
		 * 
		 * if (shouldBeVisible && !statsTabVisible) { tabbedPane.insertTab("Stats",
		 * null, statsPanel, null, 2); statsTabVisible = true; } else if
		 * (!shouldBeVisible && statsTabVisible) { int index =
		 * tabbedPane.indexOfComponent(statsPanel); if (index != -1) {
		 * tabbedPane.remove(index); statsTabVisible = false; } } }
		 * 
		 
	}
*/