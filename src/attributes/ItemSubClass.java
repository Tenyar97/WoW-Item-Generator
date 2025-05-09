package attributes;

import java.util.Arrays;
import java.util.List;

import com.tenyar97.util.Identifiable;

public class ItemSubClass implements Identifiable
	{
		public int id;
		public int subID;
		public String name;

		public static final int BOW = 2;
		public static final int GUN = 3;
		public static final int SHIELD = 6;
		public static final int BAG = 0;
		public static final int THROWN = 16;
		public static final int XBOW = 18;
		public static final int WAND = 19;

		public ItemSubClass(int id, int subID, String name)
			{
				this.id = id;
				this.subID = subID;
				this.name = name;
			}

		@Override
		public String toString()
			{
				return name;
			}

		@Override
		public boolean equals(Object o)
			{
				if (this == o)
					return true;
				if (!(o instanceof ItemSubClass))
					return false;
				ItemSubClass other = (ItemSubClass) o;
				return this.id == other.id && this.subID == other.subID && this.name.equals(other.name);
			}

		@Override
		public int hashCode()
			{
				int result = id;
				result = 31 * result + subID;
				result = 31 * result + name.hashCode();
				return result;
			}

		public static List<ItemSubClass> getAllSubclasses()
			{
				return Arrays.asList(new ItemSubClass(0, 0, "Consumable"), new ItemSubClass(1, 0, "Bag"),
						new ItemSubClass(1, 1, "Soul Bag"), new ItemSubClass(1, 2, "Herb Bag"),
						new ItemSubClass(1, 3, "Enchanting Bag"), new ItemSubClass(1, 4, "Engineering Bag"),
						new ItemSubClass(2, 0, "Axe 1H"), new ItemSubClass(2, 1, "Axe 2H"),
						new ItemSubClass(2, 2, "Bow"), new ItemSubClass(2, 3, "Gun"), new ItemSubClass(2, 4, "Mace 1H"),
						new ItemSubClass(2, 5, "Mace 2H"), new ItemSubClass(2, 6, "Polearm"),
						new ItemSubClass(2, 7, "Sword 1H"), new ItemSubClass(2, 8, "Sword 2H"),
						new ItemSubClass(2, 10, "Staff"), new ItemSubClass(2, 13, "Fist Weapon"),
						new ItemSubClass(2, 14, "Miscellaneous"), new ItemSubClass(2, 15, "Dagger"),
						new ItemSubClass(2, 16, "Thrown"), new ItemSubClass(2, 17, "Spear"),
						new ItemSubClass(2, 18, "Crossbow"), new ItemSubClass(2, 19, "Wand"),
						new ItemSubClass(2, 20, "Fishing Pole"), new ItemSubClass(4, 0, "Miscellaneous"),
						new ItemSubClass(4, 1, "Cloth"), new ItemSubClass(4, 2, "Leather"),
						new ItemSubClass(4, 3, "Mail"), new ItemSubClass(4, 4, "Plate"),
						new ItemSubClass(4, 6, "Shield"), new ItemSubClass(4, 7, "Libram"),
						new ItemSubClass(4, 8, "Idol"), new ItemSubClass(4, 9, "Totem"),
						new ItemSubClass(5, 0, "Reagent"), new ItemSubClass(6, 2, "Arrow"),
						new ItemSubClass(6, 3, "Bullet"), new ItemSubClass(7, 0, "Trade Goods"),
						new ItemSubClass(7, 1, "Parts"), new ItemSubClass(7, 2, "Explosives"),
						new ItemSubClass(7, 3, "Devices"), new ItemSubClass(9, 0, "Book"),
						new ItemSubClass(9, 1, "Leatherworking"), new ItemSubClass(9, 2, "Tailoring"),
						new ItemSubClass(9, 3, "Engineering"), new ItemSubClass(9, 4, "Blacksmithing"),
						new ItemSubClass(9, 5, "Cooking"), new ItemSubClass(9, 6, "Alchemy"),
						new ItemSubClass(9, 7, "First Aid"), new ItemSubClass(9, 8, "Enchanting"),
						new ItemSubClass(9, 9, "Fishing"), new ItemSubClass(11, 2, "Quiver"),
						new ItemSubClass(11, 3, "Ammo Pouch"), new ItemSubClass(12, 0, "Quest"),
						new ItemSubClass(13, 0, "Key"), new ItemSubClass(13, 1, "Lockpick"),
						new ItemSubClass(15, 0, "Junk"));
			}

		public static boolean isShield(ItemSubClass item)
			{
				return item != null && item.subID == SHIELD;
			}

		public static boolean isRanged(ItemSubClass item)
			{
				return item != null && (item.subID == GUN || item.subID == BOW || item.subID == XBOW
						|| item.subID == THROWN || item.subID == WAND);
			}

		public static String getNameById(int id, int subID)
			{
				for (ItemSubClass item : getAllSubclasses())
					{
						if (item.id == id && item.subID == subID)
							{
								return item.name;
							}
					}
				return "Unknown";
			}

		@Override
		public int getId()
			{
				return subID;
			}
	}
