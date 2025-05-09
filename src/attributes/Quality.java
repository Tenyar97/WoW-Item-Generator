package attributes;

import javax.swing.JComboBox;

import com.tenyar97.util.Identifiable;

public class Quality implements Identifiable
	{
		public int id;
		public String name;

		public Quality(int id, String name)
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
				if (!(o instanceof Quality))
					return false;
				Quality other = (Quality) o;
				return this.id == other.id && this.name.equals(other.name);
			}

		@Override
		public int hashCode()
			{
				return id * 31 + name.hashCode();
			}

		public static JComboBox<Quality> createQualityBox()
			{
				Quality[] qualities = { new Quality(0, "Poor"), new Quality(1, "Common"), new Quality(2, "Uncommon"),
						new Quality(3, "Rare"), new Quality(4, "Epic"), new Quality(5, "Legendary") };
				return new JComboBox<>(qualities);
			}

		public static String getNameByID(int id)
			{
				switch (id)
				{
				case 0:
					return "Poor";
				case 1:
					return "Common";
				case 2:
					return "Uncommon";
				case 3:
					return "Rare";
				case 4:
					return "Epic";
				case 5:
					return "Legendary";
				default:
					return "Unknown";
				}
			}

		@Override
		public int getId()
			{
				return id;
			}

	}
