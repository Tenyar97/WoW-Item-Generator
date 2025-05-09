package com.tenyar97;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.tenyar97.panels.BasicInfoPanel;
import com.tenyar97.panels.LootAndMiscPanel;
import com.tenyar97.util.ConfigurationProcessor;
import com.tenyar97.util.ResourceLoader;

import attributes.ItemClass;

public class Main
	{
		public static Font lifeCraft;
		public static String defaultSavePath = ConfigurationProcessor.getValue("defaultSavePath");
		public static int mruLimit;
		// public static final String ICONS_DIRECTORY = "Icons";

		public static final String configFile = "config.txt";

		public static boolean customIcons = false;

		public static void main(String[] args) throws IOException
			{
				createDefaultConfig(configFile);
				mruLimit = setMRULIMIT();
				javax.swing.SwingUtilities.invokeLater(() ->
				{
					ItemCreatorFrame frame = null;
					try
						{
							frame = new ItemCreatorFrame();
						}
					catch (IOException e)
						{
							e.printStackTrace();
						}
					frame.setVisible(true);
				});

				loadFont();
			}

		private static int setMRULIMIT()
			{
				int mruLimit = ConfigurationProcessor.getValue("mruLimit") != null
						? Integer.parseInt(ConfigurationProcessor.getValue("mruLimit"))
						: 20;
				System.out.println("Setting MRU limit to: " + mruLimit);
				return mruLimit;
			}

		private static void loadFont()
			{
				try
					{
						Font lifeCraftFont = Font
								.createFont(Font.TRUETYPE_FONT,
										new File(ResourceLoader
												.getResourceDirectoryPath(ResourceLoader.FONTS + "LifeCraft_Font.ttf")))
								.deriveFont(20f);
						lifeCraft = customIcons ? lifeCraftFont : new Font("Tahoma", Font.PLAIN, 14);
						GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
						ge.registerFont(lifeCraftFont);
					}
				catch (FontFormatException | IOException e)
					{
						e.printStackTrace();
					}
			}

		public static boolean isEquipable(ItemClass item)
			{
				return item != null && (item.id == 2 || item.id == 4) || BasicInfoPanel.getArmorCheck().isSelected();
			}

		public static void createDefaultConfig(String filePath)
			{
				File configFile = new File(filePath);
				try
					{
						if (!configFile.exists())
							{
								System.out.println("Creating default config at: " + filePath);
								configFile.createNewFile();
								ConfigurationProcessor config = new ConfigurationProcessor(filePath);

								config.setValue("mruLimit", "20");
								config.setValue("defaultSavePath",
										ResourceLoader.getResourceDirectoryPath(ResourceLoader.ITEMS));

								config.setValue("entry", "entry");
								config.setValue("itemClass", "class");
								config.setValue("subclass", "subclass");
								config.setValue("name", "name");
								config.setValue("displayId", "display_id");
								config.setValue("Quality", "quality");
								config.setValue("Flags", "flags");
								config.setValue("BuyCount", "buy_count");
								config.setValue("BuyPrice", "buy_price");
								config.setValue("SellPrice", "sell_price");
								config.setValue("InventoryType", "inventory_type");
								config.setValue("AllowableClass", "allowable_class");
								config.setValue("AllowableRace", "allowable_race");
								config.setValue("ItemLevel", "item_level");
								config.setValue("RequiredLevel", "required_level");
								config.setValue("RequiredSkill", "required_skill");
								config.setValue("RequiredSkillRank", "required_skill_rank");
								config.setValue("requiredspell", "required_spell");
								config.setValue("requiredhonorrank", "required_honor_rank");
								config.setValue("RequiredReputationFaction", "required_reputation_faction");
								config.setValue("RequiredReputationRank", "required_reputation_rank");
								config.setValue("maxcount", "max_count");
								config.setValue("stackable", "stackable");
								config.setValue("ContainerSlots", "container_slots");

								for (int i = 1; i <= 10; i++)
									{
										config.setValue("stat_type" + i, "stat_type" + i);
										config.setValue("stat_value" + i, "stat_value" + i);
									}
								config.setValue("delay", "delay");
								config.setValue("RangedModRange", "range_mod");
								config.setValue("ammo_type", "ammo_type");

								for (int i = 1; i <= 5; i++)
									{
										config.setValue("dmgMin" + i, "dmgMin" + i);
										config.setValue("dmgMax" + i, "dmgMax" + i);
										config.setValue("dmgType" + i, "dmgType" + i);
									}

								for (int i = 1; i <= 5; i++)
									{
										config.setValue("spellid_" + i, "spellid_" + i);
										config.setValue("spelltrigger_" + i, "spelltrigger_" + i);
										config.setValue("spellcharges_" + i, "spellcharges_" + i);
										config.setValue("spellppmrate_" + i, "spellppmrate_" + i);
										config.setValue("spellcooldown_" + i, "spellcooldown_" + i);
										config.setValue("spellcategory_" + i, "spellcategory_" + i);
										config.setValue("spellcategorycooldown_" + i, "spellcategorycooldown_" + i);
									}

								config.setValue("bonding", "bonding");
								config.setValue("desc", "description");
								config.setValue("LanguageID", "page_language");
								config.setValue("PageMaterial", "page_material");
								config.setValue("startquest", "start_quest");
								config.setValue("lockid", "lock_id");
								config.setValue("Material", "material");
								config.setValue("sheath", "sheath");
								config.setValue("block", "block");
								config.setValue("armor", "armor");
								config.setValue("holy_res", "holy_res");
								config.setValue("fire_res", "fire_res");
								config.setValue("nature_res", "nature_res");
								config.setValue("frost_res", "frost_res");
								config.setValue("shadow_res", "shadow_res");
								config.setValue("arcane_res", "arcane_res");
								config.setValue("MaxDurability", "max_durability");
								config.setValue("area", "area_bound");
								config.setValue("Map", "map_bound");
								config.setValue("BagFamily", "bag_family");
								config.setValue("ScriptName", "ScriptName");
								config.setValue("DisenchantID", "disenchant_id");
								config.setValue("FoodType", "food_type");
								config.setValue("minMoneyLoot", "min_money_loot");
								config.setValue("maxMoneyLoot", "max_money_loot");
								config.setValue("Duration", "duration");
								config.setValue("armor", "armor");
								config.setValue("extra_flags", "extra_flags");

								config.save();
							}
						else
							{
								new ConfigurationProcessor(filePath);
							}
					}
				catch (IOException e)
					{
						e.printStackTrace();
					}
			}

	}
