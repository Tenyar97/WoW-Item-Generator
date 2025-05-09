package com.tenyar97.panels;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;

import com.tenyar97.ItemCreatorFrame;
import com.tenyar97.Main;
import com.tenyar97.components.DocFilter;
import com.tenyar97.components.Textbox;
import com.tenyar97.util.IconLoaderUtil;
import com.tenyar97.util.IconSelectionDialog;
import com.tenyar97.util.ImageIconFile;
import com.tenyar97.util.MruImageManager;
import com.tenyar97.util.ResourceLoader;
import com.tenyar97.util.SQLGenerator;
import com.tenyar97.util.UIBuilder;

import attributes.Bonding;
import attributes.InventorySlot;
import attributes.ItemClass;
import attributes.ItemSubClass;
import attributes.Material;
import attributes.Quality;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class BasicInfoPanel extends JPanel
	{

		private ItemCreatorFrame parentFrame;

		private Textbox entryField, nameField, descriptionField, displayIdField;
		private JSpinner maxCountSpinner, stackableSpinner, containerCountSpinner, durabilitySpinner;
		private JLabel qualityLabel, bondingLabel, stackSizeLabel, classLabel, subClassLabel, maxCountLabel,
				containerLabel, meterialLabel, sheathLabel;
		private static JCheckBox limitedCheck, armorCheck, containerCheck;
		private JComboBox<InventorySlot> armorSlotBox;
		private final Map<Integer, List<String>> subclassMap = new HashMap<>();

		public JComboBox<ItemClass> itemClassBox;
		public JComboBox<ItemSubClass> itemSubClassBox;
		public JComboBox<Quality> qualityBox;
		public JComboBox<Bonding> bondingBox;
		public JComboBox<Material> materialBox;

		public JComboBox<String> sheathBox;

		private final JButton spellIcon = new JButton();
		private String selectedIconFilename = "001.png";
		private final MruImageManager mruManager = new MruImageManager();
		private static final List<ImageIconFile> cachedImageList = new ArrayList<>();
		private JLabel durabilityLabel;

		public Font font = Main.lifeCraft;

		public BasicInfoPanel(ItemCreatorFrame frame) throws IOException
			{
				this.parentFrame = frame;

				setLayout(null);

				spellIcon.requestDefaultFocus();
				initializeSubclassMap();
				setupSpellIcon(spellIcon, this);

				initTextFields();
				initComboBoxes();
				initSpinnersAndChecks();
				initLabels();
				initEventListeners();

				UIBuilder.autoNameFields(this);
			}

		private void initTextFields()
			{

				entryField = new Textbox("90001");
				entryField.setName("entryField");
				entryField.setBounds(84, 11, 103, 33);
				entryField.setToolTipText("A unique identifier for this item template.");
				PlainDocument doc = (PlainDocument) entryField.getDocument();
				doc.setDocumentFilter(new DocFilter(entryField));
				add(entryField);

				nameField = new Textbox("Item Name");
				nameField.setName("nameField");
				nameField.setBounds(10, 99, 177, 25);
				nameField.setToolTipText("The display name of the item shown to players.");
				add(nameField);

				displayIdField = new Textbox("DisplayID");
				displayIdField.setName("displayIdField");
				displayIdField.setBounds(84, 52, 103, 36);
				displayIdField.setToolTipText("References the 3D model or icon shown in-game.");
				add(displayIdField);

				descriptionField = new Textbox("Item Description");
				descriptionField.setName("descriptionField");
				// descriptionField.setLineWrap(true);
				// descriptionField.setWrapStyleWord(true);
				descriptionField.setAlignmentY(TOP_ALIGNMENT);
				descriptionField.setAlignmentX(LEFT_ALIGNMENT);
				descriptionField.setCaretPosition(0);
				descriptionField.setBounds(10, 338, 314, 106);
				descriptionField.setToolTipText("Flavor text displayed on the item.");

				add(descriptionField);

				JScrollPane scrollPane = new JScrollPane(descriptionField);
				scrollPane.setBounds(10, 338, 314, 106);
				scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
				scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

				add(scrollPane);
			}

		private void initComboBoxes()
			{
				qualityBox = Quality.createQualityBox();
				qualityBox.setName("qualityBox");
				qualityBox.setToolTipText("Indicates the rarity and color tier of the item.");
				qualityBox.setBounds(84, 143, 120, 36);
				add(qualityBox);

				bondingBox = Bonding.createBondingBox();
				bondingBox.setName("bondingBox");
				bondingBox.setBounds(84, 187, 120, 36);
				add(bondingBox);

				itemClassBox = ItemClass.createItemClassBox();
				itemClassBox.setName("itemClassBox");
				itemClassBox.setToolTipText("Broad overview category of the item.");
				itemClassBox.setBounds(326, 5, 152, 36);
				itemClassBox.setSelectedIndex(11);
				add(itemClassBox);

				itemSubClassBox = createItemSubClassBox();
				itemSubClassBox.setName("itemSubClassBox");
				itemSubClassBox.setToolTipText("More specific item type depending on the class");
				itemSubClassBox.setBounds(326, 46, 152, 36);
				itemSubClassBox.setSelectedIndex(0);
				add(itemSubClassBox);

				armorSlotBox = InventorySlot.createArmorSlotBox();
				armorSlotBox.setName("armorSlotBox");
				armorSlotBox.setToolTipText("Specifies the equipment slot");
				armorSlotBox.setBounds(195, 262, 140, 22);
				armorSlotBox.setVisible(false);
				add(armorSlotBox);

				materialBox = Material.createMaterialBox();
				materialBox.setName("materialBox");
				materialBox.setBounds(326, 88, 152, 36);
				add(materialBox);

				sheathBox = new JComboBox<>(new String[] { "None", "On the back, pointing down",
						"On the back, pointing up", "Large weapon left", "Large weapon right", "To the left side",
						"To the right side", "On the back, in the middle" });
				sheathBox.setName("sheathBox");
				sheathBox.setBounds(326, 135, 152, 36);
				sheathBox.setVisible(false);
				add(sheathBox);
			}

		private void initSpinnersAndChecks()
			{

				stackableSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 20, 1));
				stackableSpinner.setValue(20);
				stackableSpinner.setName("stackableSpinner");
				stackableSpinner.setBounds(102, 313, 39, 20);
				add(stackableSpinner);

				maxCountSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
				maxCountSpinner.setName("maxCountSpinner");
				maxCountSpinner.setBounds(259, 240, 39, 20);
				maxCountSpinner.setVisible(false);
				add(maxCountSpinner);

				limitedCheck = new JCheckBox("Limited in Bag?");
				limitedCheck.setName("limitedCheck");
				limitedCheck.setToolTipText("Maximum number of this item a player can carry.");
				limitedCheck.setFont(font);
				limitedCheck.setOpaque(false);
				limitedCheck.setBounds(6, 242, 140, 18);
				add(limitedCheck);

				setArmorCheck(new JCheckBox("Is this item equipable?"));
				armorCheck.setName("armorCheck");
				getArmorCheck().setFont(font);
				getArmorCheck().setOpaque(false);
				getArmorCheck().setBounds(6, 262, 209, 23);
				add(getArmorCheck());

				containerCheck = new JCheckBox("Is this item a container?");
				containerCheck.setName("containerCheck");
				containerCheck.setToolTipText("Sets the amount of slots in the container");
				containerCheck.setFont(font);
				containerCheck.setBounds(6, 288, 198, 23);
				containerCheck.setOpaque(false);
				add(containerCheck);

				containerCountSpinner = new JSpinner();
				containerCountSpinner.setName("containerCountSpinner");
				containerCountSpinner.setFont(font);
				containerCountSpinner.setBounds(332, 290, 39, 20);

				add(containerCountSpinner);

				durabilitySpinner = new JSpinner();
				durabilitySpinner.setName("durabilitySpinner");
				durabilitySpinner.setFont(font);
				durabilitySpinner.setBounds(312, 315, 58, 20);
				durabilitySpinner.setVisible(false);
				add(durabilitySpinner);
			}

		private void initLabels()
			{
				qualityLabel = new JLabel("Quality:");
				qualityLabel.setFont(font);
				qualityLabel.setBounds(17, 145, 90, 33);
				add(qualityLabel);

				bondingLabel = new JLabel("Bonding:");
				bondingLabel.setFont(font);
				bondingLabel.setBounds(16, 195, 131, 25);
				add(bondingLabel);

				stackSizeLabel = new JLabel("Stack Size:");
				stackSizeLabel.setToolTipText("Defines how many copies of this item can stack in one inventory slot.");
				stackSizeLabel.setFont(font);
				stackSizeLabel.setBounds(10, 311, 113, 25);
				add(stackSizeLabel);

				classLabel = new JLabel("Class:");
				classLabel.setFont(font);
				classLabel.setBounds(266, 11, 45, 25);
				add(classLabel);

				subClassLabel = new JLabel("Subclass:");
				subClassLabel.setFont(font);
				subClassLabel.setBounds(258, 57, 113, 14);
				add(subClassLabel);

				maxCountLabel = new JLabel("Max Count:");
				maxCountLabel.setFont(font);
				maxCountLabel.setBounds(150, 241, 111, 23);
				maxCountLabel.setVisible(false);
				add(maxCountLabel);

				containerLabel = new JLabel("Container Slots:");
				containerLabel.setFont(font);
				containerLabel.setBounds(205, 293, 140, 14);
				add(containerLabel);

				UIBuilder.bindVisibility(containerCheck, containerLabel, containerCountSpinner);

				durabilityLabel = new JLabel("Max Durability:");
				durabilityLabel.setToolTipText("The maximum durability this item (armor/weapon) can have.");
				durabilityLabel.setFont(font);
				durabilityLabel.setBounds(167, 317, 168, 16);
				durabilityLabel.setVisible(false);
				add(durabilityLabel);

				meterialLabel = new JLabel("Material:");
				meterialLabel.setToolTipText("Material type that influences sound in-game.");
				meterialLabel.setFont(font);
				meterialLabel.setBounds(259, 104, 101, 14);
				add(meterialLabel);

				sheathLabel = new JLabel("Sheath Position:");
				sheathLabel.setToolTipText("Controls how characters will show or hide items worn.");
				sheathLabel.setBounds(221, 146, 101, 14);
				sheathLabel.setVisible(false);
				add(sheathLabel);
			}

		private void initEventListeners()
			{
				displayIdField.getDocument().addDocumentListener(new DocumentListener()
					{
						public void insertUpdate(DocumentEvent e)
							{
								updateIconFromDisplayId();
							}

						public void removeUpdate(DocumentEvent e)
							{
								updateIconFromDisplayId();
							}

						public void changedUpdate(DocumentEvent e)
							{
								updateIconFromDisplayId();
								
							}
					});

				limitedCheck.addActionListener(e ->
				{
					maxCountSpinner.setVisible(limitedCheck.isSelected());
					getComponentByName("Max Count:").setVisible(limitedCheck.isSelected());
				});

				getArmorCheck().addActionListener(e ->
				{
					armorSlotBox.setVisible(getArmorCheck().isSelected());
					updateDurabilityVisibility();
					parentFrame.updateStatsTabVisibility((ItemClass) itemClassBox.getSelectedItem());
				});

				itemClassBox.addActionListener(e ->
				{
					ItemClass selected = (ItemClass) itemClassBox.getSelectedItem();

					itemSubClassBox.removeAllItems();

					if (selected != null)
						{
							for (ItemSubClass sub : ItemSubClass.getAllSubclasses())
								{
									if (sub.id == selected.id)
										{
											itemSubClassBox.addItem(sub);
										}
								}
						}
					updateDurabilityVisibility();
					parentFrame.updateStatsTabVisibility(selected);
				});

				itemSubClassBox.addActionListener(e ->
				{
					ItemSubClass subclass = ((ItemSubClass) itemSubClassBox.getSelectedItem());

					StatsPanel.setBlockVisibility(ItemSubClass.isShield(subclass));

					StatsPanel.setRangedModVisibility(ItemSubClass.isRanged(subclass));

					UseRestrictionPanel.setLockVisibility(subclass != null && subclass.name == "Bag");

				});

			}

		private Component getComponentByName(String text)
			{
				for (Component comp : getComponents())
					{
						if (comp instanceof JLabel && ((JLabel) comp).getText().equals(text))
							{
								return comp;
							}
					}
				return null;
			}

		private void setupSpellIcon(JButton button, Container container) throws IOException
			{
				button.setBounds(10, 15, 64, 64);
				button.setToolTipText("Click to change Spell Icon.");
				button.setMargin(new Insets(0, 0, 0, 0));
				button.setBorder(BorderFactory.createEmptyBorder());
				button.setContentAreaFilled(false);
				button.setBorderPainted(false);
				button.addActionListener(e ->
				{
					IconSelectionDialog.show(this, ResourceLoader.getResourceDirectoryPath(ResourceLoader.ICONS),
							cachedImageList, mruManager, selected ->
							{
								spellIcon.setIcon(selected.getIcon());
								selectedIconFilename = selected.getName();
								displayIdField.setText(getDisplayIdString());
							});
				});

				container.add(button);

				File file = new File(ResourceLoader.getResourceDirectoryPath(ResourceLoader.ICONS) + "/001.png");
				if (file.exists())
					{
						BufferedImage img = ImageIO.read(file);
						if (img != null)
							{
								ImageIcon icon = new ImageIcon(img);
								button.setIcon(icon);
								button.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
							}
					}
			}

		private void updateIconFromDisplayId()
			{
				String id = displayIdField.getText().trim();
				if (id.matches("\\d+"))
					{
						ImageIcon icon = IconLoaderUtil
								.loadImageById(ResourceLoader.getResourceDirectoryPath(ResourceLoader.ICONS), id);
						if (icon != null)
							{
								spellIcon.setIcon(icon);
								selectedIconFilename = id + ".png";
							}
					}
			}

		public int getInventoryTypeId()
			{
				if (getArmorCheck().isSelected())
					{
						InventorySlot selected = (InventorySlot) armorSlotBox.getSelectedItem();
						return selected != null ? selected.id : 0;
					}
				return 0;
			}

		public String getDisplayIdString()
			{
				if (selectedIconFilename != null && selectedIconFilename.contains("."))
					{
						return selectedIconFilename.substring(0, selectedIconFilename.lastIndexOf('.'));
					}
				return "000";
			}

		public void setSelectedIconFilename(String filename)
			{
				this.selectedIconFilename = filename;
			}

		private void initializeSubclassMap()
			{
				subclassMap.put(0, List.of("0 - Consumable"));
				subclassMap.put(1, List.of("0 - Bag", "1 - Soul Bag", "2 - Herb Bag", "3 - Enchanting Bag",
						"4 - Engineering Bag"));
				subclassMap.put(2,
						List.of("0 - Axe 1H", "1 - Axe 2H", "2 - Bow", "3 - Gun", "4 - Mace 1H", "5 - Mace 2H",
								"6 - Polearm", "7 - Sword 1H", "8 - Sword 2H", "10 - Staff", "13 - Fist Weapon",
								"14 - Miscellaneous", "15 - Dagger", "16 - Thrown", "17 - Spear", "18 - Crossbow",
								"19 - Wand", "20 - Fishing Pole"));
				subclassMap.put(4, List.of("0 - Misc", "1 - Cloth", "2 - Leather", "3 - Mail", "4 - Plate",
						"6 - Shield", "7 - Libram", "8 - Idol", "9 - Totem"));
				subclassMap.put(5, List.of("0 - Reagent"));
				subclassMap.put(6, List.of("2 - Arrow", "3 - Bullet"));
				subclassMap.put(7, List.of("0 - Trade Goods", "1 - Parts", "2 - Explosives", "3 - Devices"));
				subclassMap.put(9,
						List.of("0 - Book", "1 - Leatherworking", "2 - Tailoring", "3 - Engineering",
								"4 - Blacksmithing", "5 - Cooking", "6 - Alchemy", "7 - First Aid", "8 - Enchanting",
								"9 - Fishing"));
				subclassMap.put(11, List.of("2 - Quiver", "3 - Ammo Pouch"));
				subclassMap.put(12, List.of("0 - Quest"));
				subclassMap.put(13, List.of("0 - Key", "1 - Lockpick"));
				subclassMap.put(15, List.of("0 - Junk"));
			}

		private void updateDurabilityVisibility()
			{
				if (Main.isEquipable((ItemClass) itemClassBox.getSelectedItem()))
					{
						durabilityLabel.setVisible(true);
						durabilitySpinner.setVisible(true);
						sheathLabel.setVisible(true);
						sheathBox.setVisible(true);

					}
				else
					{
						durabilityLabel.setVisible(false);
						durabilitySpinner.setVisible(false);
						sheathLabel.setVisible(false);
						sheathBox.setVisible(false);
					}
			}

		private JComboBox<ItemSubClass> createItemSubClassBox()
			{

				JComboBox<ItemSubClass> subClassBox = new JComboBox<>(
						ItemSubClass.getAllSubclasses().toArray(new ItemSubClass[0]));
				return subClassBox;
			}

		public static JCheckBox getArmorCheck()
			{
				return armorCheck;
			}

		public static void setArmorCheck(JCheckBox armorCheck)
			{
				BasicInfoPanel.armorCheck = armorCheck;
			}

		public int getEntryID()
			{
				return SQLGenerator.getInt(entryField);
			}

		public int getItemClass()
			{
				return ((ItemClass) itemClassBox.getSelectedItem()).id;
			}

		public int getItemSubClass()
			{
				return ((ItemSubClass) itemSubClassBox.getSelectedItem()).subID;
			}

		public String getName()
			{
				return SQLGenerator.sanitizeString(nameField);
			}

		public String getDisplayID()
			{
				String displayID = SQLGenerator.sanitizeString(displayIdField);
				return displayID.equalsIgnoreCase("DisplayID") ? "001" : displayID;
			}

		public int getQuality()
			{
				Quality quality = (Quality) qualityBox.getSelectedItem();
				return quality.id;
			}

		public int getMaxCount()
			{
				return limitedCheck.isSelected() ? (int) maxCountSpinner.getValue() : 0;
			}

		public int getStackSize()
			{
				Number stack = (Number) stackableSpinner.getValue();
				return stack.intValue();
			}

		public int getContainerSlots()
			{
				Number slots = (Number) containerCountSpinner.getValue();
				return slots.intValue();
			}

		public String getDescription()
			{
				return SQLGenerator.sanitizeString(descriptionField);
			}

		public int getMaterial()
			{
				Material material = (Material) materialBox.getSelectedItem();
				return material.id;
			}

		public int getMaxDuribility()
			{
				Number dur = (Number) durabilitySpinner.getValue();
				return dur.intValue();
			}

		public int getBonding()
			{
				Bonding bondType = (Bonding) bondingBox.getSelectedItem();
				return bondType.id;
			}

		public int getBagFamily()
			{
				ItemClass item = (ItemClass) itemClassBox.getSelectedItem();
				ItemSubClass subClass = (ItemSubClass) itemSubClassBox.getSelectedItem();
				return item.id == ItemClass.CONTAINER ? subClass.id : 0;
			}

		public Integer getSheathType()
			{
				int selectedIndex = sheathBox.getSelectedIndex();
				return selectedIndex != -1 ? selectedIndex : null;
			}

		private void openModelViewer()
			{

				new javafx.embed.swing.JFXPanel(); // Initialize JavaFX

				javafx.application.Platform.runLater(() ->
				{
					Stage stage = new Stage();
					WebView webView = new WebView();

					int entryId = getEntryID(); // Get the item entryID (e.g., 18803)
					String url = "https://www.wowhead.com/item=" + 17705 + "#modelviewer";

					webView.getEngine().load(url);

					Scene scene = new Scene(webView, 900, 700);
					stage.setScene(scene);
					stage.setTitle("WoW Item 3D Model Viewer");
					stage.show();
				});
			}

		@Override
		public boolean requestFocusInWindow()
			{
				// Block automatic focus when added to a container
				return false;
			}
	}
