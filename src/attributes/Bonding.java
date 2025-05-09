package attributes;

import javax.swing.JComboBox;

import com.tenyar97.util.Identifiable;

public class Bonding implements Identifiable
	{
		public int id;
		public String name;

		private static final Bonding[] bondingTypes = { new Bonding(0, "No binding"), new Bonding(1, "Bind on pickup"),
				new Bonding(2, "Bind on equip"), new Bonding(3, "Bind on use"), new Bonding(4, "Quest item") };

		public Bonding(int id, String name)
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
				if (!(o instanceof Bonding))
					return false;
				Bonding other = (Bonding) o;
				return this.id == other.id && this.name.equals(other.name);
			}

		@Override
		public int hashCode()
			{
				return id * 31 + name.hashCode();
			}

		public static JComboBox<Bonding> createBondingBox()
			{
				return new JComboBox<>(bondingTypes);
			}

		public static String getNameById(int id)
			{
				for (Bonding b : bondingTypes)
					{
						if (b.id == id)
							{
								return b.name;
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
