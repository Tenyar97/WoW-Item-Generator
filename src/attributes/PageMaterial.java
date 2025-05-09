package attributes;

import com.tenyar97.util.Identifiable;

public class PageMaterial implements Identifiable
	{
		public int id;
		public String name;

		private static final PageMaterial[] MATERIALS = { new PageMaterial(1, "Parchment"),
				new PageMaterial(2, "Stone"), new PageMaterial(3, "Marble"), new PageMaterial(4, "Silver"),
				new PageMaterial(5, "Bronze"), new PageMaterial(6, "Valentine") };

		public PageMaterial(int id, String name)
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
				if (!(o instanceof PageMaterial))
					return false;
				PageMaterial other = (PageMaterial) o;
				return this.id == other.id && this.name.equals(other.name);
			}

		@Override
		public int hashCode()
			{
				return id * 31 + name.hashCode();
			}

		public static PageMaterial[] getAllPageMaterials()
			{
				return MATERIALS;
			}

		public static String getNameById(int id)
			{
				for (PageMaterial material : MATERIALS)
					{
						if (material.id == id)
							{
								return material.name;
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
