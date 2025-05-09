package attributes;

import com.tenyar97.util.Identifiable;

public class SkillEntry implements Identifiable
	{
		public final int id;
		public final String name;

		private static final SkillEntry[] SKILLS = { new SkillEntry(6, "Frost"), new SkillEntry(8, "Fire"),
				new SkillEntry(26, "Arms"), new SkillEntry(38, "Combat"), new SkillEntry(39, "Subtlety"),
				new SkillEntry(40, "Poisons"), new SkillEntry(43, "(Weapon) Swords"),
				new SkillEntry(44, "(Weapon) Axes"), new SkillEntry(45, "(Weapon) Bows"),
				new SkillEntry(46, "(Weapon) Guns"), new SkillEntry(50, "Beast Mastery"),
				new SkillEntry(51, "Survival"), new SkillEntry(54, "(Weapon) Maces"),
				new SkillEntry(55, "(Weapon) 2H Swords"), new SkillEntry(56, "Holy"), new SkillEntry(78, "Shadow"),
				new SkillEntry(95, "Defense"), new SkillEntry(98, "(Language) Common"),
				new SkillEntry(129, "(Profession) First Aid"), new SkillEntry(136, "(Weapon) Staves"),
				new SkillEntry(160, "(Weapon) 2H Maces"), new SkillEntry(162, "(Weapon) Unarmed"),
				new SkillEntry(163, "Marksmanship"), new SkillEntry(164, "(Profession) Blacksmithing"),
				new SkillEntry(165, "(Profession) Leatherworking"), new SkillEntry(171, "(Profession) Alchemy"),
				new SkillEntry(172, "(Weapon) 2H Axes"), new SkillEntry(173, "(Weapon) Daggers"),
				new SkillEntry(176, "(Weapon) Thrown"), new SkillEntry(182, "(Profession) Herbalism"),
				new SkillEntry(185, "(Profession) Cooking"), new SkillEntry(186, "(Profession) Mining"),
				new SkillEntry(197, "(Profession) Tailoring"), new SkillEntry(202, "(Profession) Engineering"),
				new SkillEntry(226, "(Weapon) Crossbows"), new SkillEntry(228, "(Weapon) Wands"),
				new SkillEntry(229, "(Weapon) Polearms"), new SkillEntry(333, "(Profession) Enchanting"),
				new SkillEntry(356, "(Profession) Fishing"), new SkillEntry(393, "(Profession) Skinning"),
				new SkillEntry(413, "Mail"), new SkillEntry(414, "Leather"), new SkillEntry(415, "Cloth"),
				new SkillEntry(433, "(Weapon) Shield"), new SkillEntry(473, "(Weapon) Fist Weapons"),
				new SkillEntry(762, "Riding"), new SkillEntry(754, "(Racial) Human"),
				new SkillEntry(753, "(Racial) Gnome"), new SkillEntry(733, "(Racial) Troll") };

		public SkillEntry(int id, String name)
			{
				this.id = id;
				this.name = name;
			}

		@Override
		public String toString()
			{
				return name;
			}

		public static SkillEntry[] getAllSkills()
			{
				return SKILLS;
			}

		public static String getNameById(int id)
			{
				for (SkillEntry skill : SKILLS)
					{
						if (skill.id == id)
							{
								return skill.name;
							}
					}
				return "Unknown";
			}
		
		@Override
		public int getId()
			{
				return id;
			}
	}
