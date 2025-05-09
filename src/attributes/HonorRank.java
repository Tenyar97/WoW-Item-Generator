package attributes;

import com.tenyar97.util.Identifiable;

public class HonorRank implements Identifiable
	{
		public int id;
		public String name;

		public HonorRank(int id, String name)
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
				if (!(o instanceof HonorRank))
					return false;
				HonorRank other = (HonorRank) o;
				return this.id == other.id && this.name.equals(other.name);
			}

		@Override
		public int hashCode()
			{
				return id * 31 + name.hashCode();
			}

		@Override
		public int getId()
			{
				return id;
			}
	}
