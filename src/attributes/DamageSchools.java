package attributes;

import com.tenyar97.util.Identifiable;

public class DamageSchools implements Identifiable
	{
		public static final int damageSchools = 7;

		public int id;
		public String name;

		private static final DamageSchools[] schools = { new DamageSchools(0, "Physical"), new DamageSchools(1, "Holy"),
				new DamageSchools(2, "Fire"), new DamageSchools(3, "Nature"), new DamageSchools(4, "Frost"),
				new DamageSchools(5, "Shadow"), new DamageSchools(6, "Arcane") };

		public DamageSchools(int id, String name)
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
				if (!(o instanceof DamageSchools))
					return false;
				DamageSchools other = (DamageSchools) o;
				return this.id == other.id && this.name.equals(other.name);
			}

		@Override
		public int hashCode()
			{
				return id * 31 + name.hashCode();
			}

		public static DamageSchools[] getAllSchools()
			{
				return schools;
			}

		public static String getNameById(int id)
			{
				for (DamageSchools school : schools)
					{
						if (school.id == id)
							{
								return school.name;
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
