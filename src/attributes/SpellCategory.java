package attributes;

import javax.swing.JComboBox;

import com.tenyar97.util.Identifiable;

public class SpellCategory implements Identifiable

	{
		public int id;
		public String name;
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if (obj == null || getClass() != obj.getClass()) return false;
			SpellCategory other = (SpellCategory) obj;
			return this.id == other.id;
		}

		@Override
		public int hashCode() {
			return Integer.hashCode(id);
		}
		
		@Override
		public int getId()
			{
				return id;
			}

		private static final SpellCategory[] spellCategories = { new SpellCategory(0, "None"),
				new SpellCategory(1, "Mage Armor"), new SpellCategory(2, "Inner Fire"),
				new SpellCategory(3, "Inner Fire - Duplicate"), new SpellCategory(4, "Holy Shield"),
				new SpellCategory(5, "Shield Block"), new SpellCategory(6, "Stealth"),
				new SpellCategory(7, "Stealth Detection"), new SpellCategory(8, "Ghost Wolf"),
				new SpellCategory(9, "Battle Stance"), new SpellCategory(10, "Defensive Stance"),
				new SpellCategory(11, "Potions"), new SpellCategory(12, "Charge"), new SpellCategory(13, "Retaliation"),
				new SpellCategory(14, "Shield Wall"), new SpellCategory(15, "Last Stand"),
				new SpellCategory(21, "Berserker Stance"), new SpellCategory(22, "Divinity"),
				new SpellCategory(23, "Tracking - Beasts"), new SpellCategory(24, "Tracking - Undead"),
				new SpellCategory(25, "Tracking - Demons"), new SpellCategory(26, "Tracking - Elementals"),
				new SpellCategory(27, "Tracking - Giants"), new SpellCategory(28, "Tracking - Dragonkin"),
				new SpellCategory(29, "Tracking - Humans"), new SpellCategory(30, "Bandages"),
				new SpellCategory(31, "First Aid Channel"), new SpellCategory(32, "Engineering Explosives"),
				new SpellCategory(33, "Hearthstones"), new SpellCategory(34, "Aspect of the Cheetah"),
				new SpellCategory(35, "Aspect of the Pack"), new SpellCategory(36, "Combat Potions"),
				new SpellCategory(37, "Nature Resist Totem"), new SpellCategory(38, "Frost Resist Totem"),
				new SpellCategory(39, "Fire Resist Totem"), new SpellCategory(40, "Restoration Potions"),
				new SpellCategory(41, "Combat Mana Potions"), new SpellCategory(42, "Invisibility Potions"),
				new SpellCategory(43, "Elixirs"), new SpellCategory(44, "Mana Gem"),
				new SpellCategory(45, "Unending Breath"), new SpellCategory(46, "Soulstone Resurrection"),
				new SpellCategory(47, "Healthstone Usage"), new SpellCategory(48, "Create Healthstone"),
				new SpellCategory(49, "Create Soulstone"), new SpellCategory(50, "Create Firestone"),
				new SpellCategory(51, "Create Spellstone"), new SpellCategory(52, "Engineering Trinkets"),
				new SpellCategory(53, "Goblin Engineering Items"), new SpellCategory(54, "Gnomish Engineering Items"),
				new SpellCategory(55, "Scrolls"), new SpellCategory(56, "Mana Oils"),
				new SpellCategory(57, "Wizard Oils"), new SpellCategory(58, "Weapon Oils"),
				new SpellCategory(59, "Lockpicking"), new SpellCategory(60, "Pickpocket"),
				new SpellCategory(61, "Throwing Weapons"), new SpellCategory(62, "Fishing Pole Buffs"),
				new SpellCategory(63, "Hunter Traps"), new SpellCategory(64, "Hunter Aspects"),
				new SpellCategory(65, "Hunter Beast Abilities"), new SpellCategory(66, "Find Minerals"),
				new SpellCategory(67, "Find Herbs"), new SpellCategory(68, "Find Treasure"),
				new SpellCategory(69, "Tracking Hidden"), new SpellCategory(70, "Eye of Kilrogg"),
				new SpellCategory(71, "Detect Invisibility"), new SpellCategory(72, "Teleports"),
				new SpellCategory(73, "Portals"), new SpellCategory(74, "Reincarnation"),
				new SpellCategory(75, "Divine Intervention"), new SpellCategory(76, "Death Knight Ghoul (Unused)"),
				new SpellCategory(77, "Charge Stun"), new SpellCategory(78, "Improved Sprint"),
				new SpellCategory(79, "Shapeshift Cat Form"), new SpellCategory(80, "Shapeshift Bear Form"),
				new SpellCategory(81, "Shapeshift Travel Form"), new SpellCategory(82, "Shapeshift Aquatic Form"),
				new SpellCategory(83, "Shapeshift Moonkin Form"), new SpellCategory(84, "Divine Shield"),
				new SpellCategory(85, "Blessing of Protection"), new SpellCategory(86, "Blessing of Freedom"),
				new SpellCategory(87, "Blessing of Sacrifice"), new SpellCategory(88, "Shadowform"),
				new SpellCategory(89, "Deterrence"), new SpellCategory(90, "Turn Undead"),
				new SpellCategory(91, "Fear"), new SpellCategory(92, "Counterspell"), new SpellCategory(93, "Mounts"),
				new SpellCategory(94, "Sprint"), new SpellCategory(95, "Evocation"),
				new SpellCategory(96, "Tranquility"), new SpellCategory(97, "Engineering Devices"),
				new SpellCategory(98, "Ice Block"), new SpellCategory(99, "Divine Favor"),
				new SpellCategory(100, "Nature's Swiftness"), new SpellCategory(101, "Presence of Mind"),
				new SpellCategory(102, "Arcane Power"), new SpellCategory(103, "Elemental Mastery"),
				new SpellCategory(104, "Spirit of Redemption"), new SpellCategory(105, "Free Action Potions"),
				new SpellCategory(106, "Limited Invulnerability Potions"), new SpellCategory(107, "Magic Dust"),
				new SpellCategory(108, "Lesser Invisibility"), new SpellCategory(109, "Greater Invisibility"),
				new SpellCategory(110, "Detect Magic"), new SpellCategory(111, "Food and Water"),
				new SpellCategory(112, "Non-Combat Pet Summon"), new SpellCategory(113, "Scrolls - Combat Related"),
				new SpellCategory(114, "Scrolls - Agility Boost"), new SpellCategory(115, "Scrolls - Strength Boost"),
				new SpellCategory(116, "Scrolls - Stamina Boost"), new SpellCategory(117, "Scrolls - Intellect Boost"),
				new SpellCategory(118, "Scrolls - Spirit Boost"),
				new SpellCategory(119, "Heavy Runecloth Bandage Cooldown"),
				new SpellCategory(120, "Healthstone Usage (alt)"), new SpellCategory(121, "Battleground Potions"),
				new SpellCategory(125, "Crystal Consumables (Silithus)"),
				new SpellCategory(133, "Healthstones (Alternative)"), new SpellCategory(134, "Spellstones"),
				new SpellCategory(136, "Destruction Potions"),
				new SpellCategory(139, "Inscription Scrolls (Combat boosts)"),
				new SpellCategory(140, "Inscription Offhands"), new SpellCategory(141, "Inscription Armor Scrolls"),
				new SpellCategory(142, "Inscription Weapon Scrolls") };

		public SpellCategory(int id, String name)
			{
				this.id = id;
				this.name = name;
			}

		@Override
		public String toString()
			{
				return name;
			}


		public static JComboBox<SpellCategory> createSpellCategoryBox()
			{
				return new JComboBox<>(spellCategories);
			}

		public static String getNameById(int id)
			{
				for (SpellCategory s : spellCategories)
					{
						if (s.id == id)
							{
								return s.name;
							}
					}
				return "Unknown";
			}
	}
