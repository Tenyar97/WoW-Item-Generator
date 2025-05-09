package attributes;

import com.tenyar97.util.Identifiable;

public class Languages implements Identifiable
	{
		public int id;
		public String name;

		private static final Languages[] LANGUAGES = { new Languages(1, "Orcish"), new Languages(2, "Darnassian"),
				new Languages(3, "Taurahe"), new Languages(6, "Dwarvish"), new Languages(7, "Common"),
				new Languages(8, "Demonic"), new Languages(9, "Titan"), new Languages(10, "Thalassian"),
				new Languages(11, "Draconic"), new Languages(12, "Kalimag"), new Languages(13, "Gnomish"),
				new Languages(14, "Troll"), new Languages(33, "Gutterspeak") };

		public Languages(int id, String name)
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
				if (!(o instanceof Languages))
					return false;
				Languages other = (Languages) o;
				return this.id == other.id && this.name.equals(other.name);
			}

		@Override
		public int hashCode()
			{
				return id * 31 + name.hashCode();
			}

		public static Languages[] getAllLanguages()
			{
				return LANGUAGES;
			}

		public static String getNameById(int id)
			{
				for (Languages lang : LANGUAGES)
					{
						if (lang.id == id)
							{
								return lang.name;
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
