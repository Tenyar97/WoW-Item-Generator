package com.tenyar97.util;

import javax.swing.JTextArea;

import com.tenyar97.components.Textbox;
import com.tenyar97.panels.AllowedClassesWindow;
import com.tenyar97.panels.AllowedRacesWindow;
import com.tenyar97.panels.BasicInfoPanel;
import com.tenyar97.panels.FlagsPanel;
import com.tenyar97.panels.LootAndMiscPanel;
import com.tenyar97.panels.PricingPanel;
import com.tenyar97.panels.PropertiesPanel;
import com.tenyar97.panels.RequirementsPanel;
import com.tenyar97.panels.SpellsPanel;
import com.tenyar97.panels.StatsPanel;
import com.tenyar97.panels.UseRestrictionPanel;

import attributes.Bonding;
import attributes.DamageSchools;
import attributes.InventorySlot;
import attributes.ItemClass;
import attributes.ItemSubClass;
import attributes.Languages;
import attributes.PetFood;
import attributes.SkillEntry;
import attributes.SpellEffect;
import attributes.Stats;

public class SQLGenerator
	{

		 // static int maxResistanceTypes = 7;
		static boolean isEquipable = BasicInfoPanel.getArmorCheck().isSelected();

		public static String generateSQL(BasicInfoPanel basic, RequirementsPanel req, StatsPanel stats, //Take 8 panels full of fragile GUI state and concatenate them into one cursed SQL statement.
				SpellsPanel spells, PricingPanel price, FlagsPanel flags, UseRestrictionPanel useRestrictionPanel, 
				LootAndMiscPanel lootAndMiscPanel, PropertiesPanel props)
			{
				StringBuilder sql = new StringBuilder();

				int entry = basic.getEntryID();
				int itemClass = basic.getItemClass();
				int subclass = basic.getItemSubClass();
				String name = basic.getName();
				String displayId = basic.getDisplayID();
				int Quality = basic.getQuality();
				int Flags = flags.getSelectedFlagsValue();
				int BuyCount = price.getBuyCount();
				int BuyPrice = price.getBuyPrice();
				int SellPrice = price.getSellPrice();
				int InventoryType = isEquipable ? basic.getInventoryTypeId() : 0;
				int AllowableClass = AllowedClassesWindow.getClassMask();
				int AllowableRace = AllowedRacesWindow.getRaceMask();
				int ItemLevel = lootAndMiscPanel.getItemLevel();
				int RequiredLevel = req.getRequiredLvl();
				int RequiredSkill = req.reqSkillChk.isSelected() && req.skillBox.getSelectedItem() != null
						? ((SkillEntry) req.skillBox.getSelectedItem()).id
						: 0;
				int RequiredSkillRank = req.reqSkillChk.isSelected() ? (int) req.reqSkillSpinner.getValue() : 0;
				int requiredspell = req.getRequiredSpell();
				int requiredhonorrank = req.reqHonorChk.isSelected() ? req.getRequiredHonorRankId() : 0;
				int RequiredReputationFaction = req.getRequiredFaction();
				int RequiredReputationRank = req.reqFactionChk.isSelected() ? req.getRequiredReputationId() : 0;
				int maxcount = basic.getMaxCount();
				int stackable = basic.getStackSize();
				int ContainerSlots = basic.getContainerSlots();
				int[] stat_type = new int[Stats.statTypes];
				int[] stat_value = new int[Stats.statTypes];

				for (int i = 0; i < Stats.statTypes; i++)
					{
						stat_type[i] =  isEquipable ? stats.getStatType(i + 1) : 0;
						stat_value[i] =  isEquipable ? stats.getStatValue(i + 1) : 0;
					}

				int delay = stats.getDelay();
				int[] dmgMin = new int[DamageSchools.damageSchools - 2];
				int[] dmgMax = new int[DamageSchools.damageSchools - 2];
				int[] dmgType = new int[DamageSchools.damageSchools - 2];

				for (int i = 0; i < DamageSchools.damageSchools - 2; i++)
					{
						dmgMin[i] =  isEquipable ? stats.getDamageMin(i + 1) : 0;
						dmgMax[i] =  isEquipable ? stats.getDamageMax(i + 1) : 0;
						dmgType[i] =  isEquipable ? stats.getDamageType(i + 1) : 0;
					}
				int ammo_type = (itemClass == ItemClass.WEAPON)
						? (subclass == ItemSubClass.GUN ? 1 : (subclass == ItemSubClass.BOW ? 2 : 0))
						: 0;
				double RangedModRange = stats.getRangeMod();

				SpellEffect[] effects = spells.getAllSpellEffects();

				for (int i = 0; i < effects.length; i++)
					{
						SpellEffect e = effects[i];
						System.out.printf(
								"spell_%d SQL values:\n spellid_%d = %d,\n spelltrigger_%d = %d,\n charges_%d = %d,\n ppmRate_%d = %.2f,\n cooldown_%d = %d,\n category_%d = %d,\n categoryCooldown_%d = %d\n",
								i, i, e.spellId, i, e.trigger, i, e.charges, i, (double) e.ppmRate, i, e.cooldown, i,
								e.category, i, e.categoryCooldown);

					}
				int bonding = basic.getBonding();
				String desc = basic.getDescription();
				int LanguageID = props.getLanguage();
				int PageMaterial = props.getPageMat();
				int startquest = lootAndMiscPanel.getStartQuestID();
				int lockid = useRestrictionPanel.getLockID();
				int Material = basic.getMaterial();
				int sheath = basic.getSheathType();
				int block = stats.getBlockValue();
				int MaxDurability = basic.getMaxDuribility();
				int area = useRestrictionPanel.getAreaID();
				int Map = useRestrictionPanel.getMapID();
				int BagFamily = basic.getBagFamily();
				String ScriptName = props.getScriptName();
				int DisenchantID = req.getDisenchantTable();
				int FoodType = lootAndMiscPanel.getFoodType();
				int minMoneyLoot = lootAndMiscPanel.getMinHeldGold();
				int maxMoneyLoot = lootAndMiscPanel.getMaxHeldGold();
				int Duration = lootAndMiscPanel.getDuration();
				int armor = stats.getResValue("armor");
				int holy = stats.getResValue("holy");
				int fire = stats.getResValue("fire");
				int nature = stats.getResValue("nature");
				int frost = stats.getResValue("frost");
				int shadow = stats.getResValue("shadow");
				int arcane = stats.getResValue("arcane");
				int extra_flags = Duration > 0 ? 2 : 0;

				//StringBuilder debug = new StringBuilder();
				
				// SQLLogger.log("Item Template SQL Generation", debug.toString());

				sql.append("INSERT INTO item_template (");
				sql.append(getConfigValue("entry")).append(", ").append(getConfigValue("itemClass")).append(", ")
						.append(getConfigValue("subclass")).append(", ").append(getConfigValue("name")).append(", ")
						.append(getConfigValue("displayId")).append(", ").append(getConfigValue("Quality")).append(", ")
						.append(getConfigValue("Flags")).append(", ").append(getConfigValue("BuyCount")).append(", ")
						.append(getConfigValue("BuyPrice")).append(", ").append(getConfigValue("SellPrice"))
						.append(", ").append(getConfigValue("InventoryType")).append(", ")
						.append(getConfigValue("AllowableClass")).append(", ").append(getConfigValue("AllowableRace"))
						.append(", ").append(getConfigValue("ItemLevel")).append(", ")
						.append(getConfigValue("RequiredLevel")).append(", ").append(getConfigValue("RequiredSkill"))
						.append(", ").append(getConfigValue("RequiredSkillRank")).append(", ")
						.append(getConfigValue("requiredspell")).append(", ")
						.append(getConfigValue("requiredhonorrank")).append(", ")
						.append(getConfigValue("RequiredReputationFaction")).append(", ")
						.append(getConfigValue("RequiredReputationRank")).append(", ")
						.append(getConfigValue("maxcount")).append(", ").append(getConfigValue("stackable"))
						.append(", ").append(getConfigValue("ContainerSlots")).append(", ");

				for (int i = 1; i <= 10; i++)
					{
						sql.append(getConfigValue("stat_type" + i)).append(", ")
								.append(getConfigValue("stat_value" + i)).append(", ");
					}

				for (int i = 1; i <= 5; i++)
					{
						sql.append(getConfigValue("dmgMin" + i)).append(", ").append(getConfigValue("dmgMax" + i))
								.append(", ").append(getConfigValue("dmgType" + i)).append(", ");
					}

				sql.append(getConfigValue("ammo_type")).append(", ").append(getConfigValue("RangedModRange"))
				.append(", ").append(getConfigValue("delay"))
				.append(", ");

				for (int i = 1; i <= 5; i++)
					{
						sql.append(getConfigValue("spellid_" + i)).append(", ")
								.append(getConfigValue("spelltrigger_" + i)).append(", ")
								.append(getConfigValue("spellcharges_" + i)).append(", ")
								.append(getConfigValue("spellppmrate_" + i)).append(", ")
								.append(getConfigValue("spellcooldown_" + i)).append(", ")
								.append(getConfigValue("spellcategory_" + i)).append(", ")
								.append(getConfigValue("spellcategorycooldown_" + i)).append(", ");
					}

				sql.append(getConfigValue("bonding")).append(", ").append(getConfigValue("desc")).append(", ")
						.append(getConfigValue("LanguageID")).append(", ").append(getConfigValue("PageMaterial"))
						.append(", ").append(getConfigValue("startquest")).append(", ").append(getConfigValue("lockid"))
						.append(", ").append(getConfigValue("Material")).append(", ").append(getConfigValue("sheath"))
						.append(", ")

						.append(getConfigValue("block")).append(", ").append(getConfigValue("armor")).append(", ")
						.append(getConfigValue("holy_res")).append(", ").append(getConfigValue("fire_res")).append(", ")
						.append(getConfigValue("nature_res")).append(", ").append(getConfigValue("frost_res"))
						.append(", ").append(getConfigValue("shadow_res")).append(", ")
						.append(getConfigValue("arcane_res")).append(", ")

						.append(getConfigValue("MaxDurability")).append(", ").append(getConfigValue("area"))
						.append(", ").append(getConfigValue("Map")).append(", ").append(getConfigValue("BagFamily"))
						.append(", ").append(getConfigValue("ScriptName")).append(", ")
						.append(getConfigValue("DisenchantID")).append(", ").append(getConfigValue("FoodType"))
						.append(", ").append(getConfigValue("minMoneyLoot")).append(", ")
						.append(getConfigValue("maxMoneyLoot")).append(", ").append(getConfigValue("Duration"))
						.append(", ").append(getConfigValue("extra_flags"));

				sql.append(") VALUES (");

				sql.append(entry).append(", ").append(itemClass).append(", ").append(subclass).append(", '")
						.append(name).append("', ").append(displayId).append(", ").append(Quality).append(", ")
						.append(Flags).append(", ").append(BuyCount).append(", ").append(BuyPrice).append(", ")
						.append(SellPrice).append(", ").append(InventoryType).append(", ").append(AllowableClass)
						.append(", ").append(AllowableRace).append(", ").append(ItemLevel).append(", ")
						.append(RequiredLevel).append(", ").append(RequiredSkill).append(", ").append(RequiredSkillRank)
						.append(", ").append(requiredspell).append(", ").append(requiredhonorrank).append(", ")
						.append(RequiredReputationFaction).append(", ").append(RequiredReputationRank).append(", ")
						.append(maxcount).append(", ").append(stackable).append(", ").append(ContainerSlots)
						.append(", ");

				for (int i = 0; i < 10; i++)
					{
						sql.append(stat_type[i]).append(", ").append(stat_value[i]).append(", ");
					}

				for (int i = 0; i < 5; i++)
					{
						sql.append(dmgMin[i]).append(", ").append(dmgMax[i]).append(", ").append(dmgType[i])
								.append(", ");
					}

				sql.append(ammo_type).append(", ").append(RangedModRange).append(", ").append(delay).append(", ");

				for (int i = 0; i < 5; i++)
					{
						SpellEffect e = effects[i];
						sql.append(e.spellId).append(", ").append(e.trigger).append(", ").append(e.charges).append(", ")
								.append(String.format("%.2f", e.ppmRate)).append(", ").append(e.cooldown).append(", ")
								.append(e.category).append(", ").append(e.categoryCooldown).append(", ");
					}

				sql.append(bonding).append(", '").append(desc).append("', ").append(LanguageID).append(", ")
						.append(PageMaterial).append(", ").append(startquest).append(", ").append(lockid).append(", ")
						.append(Material).append(", ").append(sheath).append(", ")

						.append(block).append(", ").append(armor).append(", ").append(holy).append(", ").append(fire)
						.append(", ").append(nature).append(", ").append(frost).append(", ").append(shadow).append(", ")
						.append(arcane).append(", ")

						.append(MaxDurability).append(", ").append(area).append(", ").append(Map).append(", ")
						.append(BagFamily).append(", '").append(ScriptName).append("', ").append(DisenchantID)
						.append(", ").append(FoodType).append(", ").append(minMoneyLoot).append(", ")
						.append(maxMoneyLoot).append(", ").append(Duration).append(", ").append(extra_flags)
						.append(");");

				System.out.println(sql.toString());

				return sql.toString();

			}

		public static String sanitizeString(Textbox textbox)
			{
				return textbox.getText().trim().replace("'", "''");
			}

		public static int getInt(Textbox textbox)
			{
				if (textbox == null || textbox.getText() == null || textbox.getText().equals(textbox.getPlaceholder()))
					{
						return 0;
					}

				String placeholder = textbox.getPlaceholder();

				return sanitizeString(textbox).equalsIgnoreCase(placeholder) ? 0
						: Integer.parseInt(textbox.getText().trim());

			}

		private static String getConfigValue(String key)
			{
				System.out.println(key + "/" + ConfigurationProcessor.getValue(key));
				return ConfigurationProcessor.getValue(key) != null ? ConfigurationProcessor.getValue(key)
						: "COULD NOT PARSE CONFIG VALUE FOR: " + key + "!";

			}
	}
