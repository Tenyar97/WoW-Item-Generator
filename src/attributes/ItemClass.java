package attributes;

import javax.swing.JComboBox;

import com.tenyar97.util.Identifiable;

public class ItemClass implements Identifiable
	{
		public int id;
		public String name;

		public static final int WEAPON = 2;
		public static final int CONTAINER = 1;

		private static final ItemClass[] ITEM_CLASSES = { new ItemClass(0, "Consumable"), new ItemClass(1, "Container"),
				new ItemClass(2, "Weapon"), new ItemClass(4, "Armor"), new ItemClass(5, "Reagent"),
				new ItemClass(6, "Projectile"), new ItemClass(7, "Trade Goods"), new ItemClass(9, "Recipe"),
				new ItemClass(11, "Quiver"), new ItemClass(12, "Quest"), new ItemClass(13, "Key"),
				new ItemClass(15, "Miscellaneous") };

		public ItemClass(int id, String name)
			{
				this.id = id;
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
				if (!(o instanceof ItemClass))
					return false;
				ItemClass other = (ItemClass) o;
				return this.id == other.id && this.name.equals(other.name);
			}

		@Override
		public int hashCode()
			{
				return id * 31 + name.hashCode();
			}

		public static JComboBox<ItemClass> createItemClassBox()
			{
				JComboBox<ItemClass> box = new JComboBox<>(ITEM_CLASSES);
				box.setSelectedIndex(3); // Default: Armor
				return box;
			}

		public static String getNameById(int id)
			{
				for (ItemClass itemClass : ITEM_CLASSES)
					{
						if (itemClass.id == id)
							{
								return itemClass.name;
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
