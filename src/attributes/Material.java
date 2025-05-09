package attributes;

import javax.swing.JComboBox;

import com.tenyar97.util.Identifiable;

public class Material implements Identifiable
	{
		public int id;
		public String name;

		private static final Material[] MATERIALS = { new Material(1, "Metal"), new Material(2, "Wood"),
				new Material(3, "Liquid"), new Material(4, "Jewelry"), new Material(5, "Chain"),
				new Material(6, "Plate"), new Material(7, "Cloth"), new Material(8, "Leather") };

		public Material(int id, String name)
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
				if (!(o instanceof Material))
					return false;
				Material other = (Material) o;
				return this.id == other.id && this.name.equals(other.name);
			}

		@Override
		public int hashCode()
			{
				return id * 31 + name.hashCode();
			}

		public static JComboBox<Material> createMaterialBox()
			{
				JComboBox<Material> materialBox = new JComboBox<>(MATERIALS);
				materialBox.setSelectedIndex(3); // Default: Jewelry
				return materialBox;
			}

		public static String getNameById(int id)
			{
				for (Material material : MATERIALS)
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
