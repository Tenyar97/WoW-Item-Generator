package attributes;

import com.tenyar97.util.Identifiable;

public class Stats implements Identifiable
	{
		public static final int statTypes = 10;

		public int id;
		public String name;

		public Stats(int id, String name)
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
				if (!(o instanceof Stats))
					return false;
				Stats other = (Stats) o;
				return this.id == other.id && this.name.equals(other.name);
			}

		@Override
		public int hashCode()
			{
				return id * 31 + name.hashCode();
			}

		public static Stats[] getAllStats()
			{
				return new Stats[] { new Stats(1, "Health"), new Stats(3, "Agility"), new Stats(4, "Strength"),
						new Stats(5, "Intellect"), new Stats(6, "Spirit"), new Stats(7, "Stamina") };
			}

		public static String getNameById(int id)
			{
				for (Stats stat : getAllStats())
					{
						if (stat.id == id)
							{
								return stat.name;
							}
					}
				return "Empty";
			}

		@Override
		public int getId()
			{
				return id;
			}

	}
