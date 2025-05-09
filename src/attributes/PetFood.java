package attributes;

import com.tenyar97.util.Identifiable;

public class PetFood implements Identifiable
	{
		public int id;
		public String name;

		private static final PetFood[] FOODS = { new PetFood(1, "Meat"), new PetFood(2, "Fish"),
				new PetFood(3, "Cheese"), new PetFood(4, "Bread"), new PetFood(5, "Fungus"), new PetFood(6, "Fruit"),
				new PetFood(7, "Raw Meat"), new PetFood(8, "Raw Fish") };

		public PetFood(int id, String name)
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
				if (!(o instanceof PetFood))
					return false;
				PetFood other = (PetFood) o;
				return this.id == other.id && this.name.equals(other.name);
			}

		@Override
		public int hashCode()
			{
				return id * 31 + name.hashCode();
			}

		public static PetFood[] getAllFoods()
			{
				return FOODS;
			}

		public static String getNameById(int id)
			{
				for (PetFood food : FOODS)
					{
						if (food.id == id)
							{
								return food.name;
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
