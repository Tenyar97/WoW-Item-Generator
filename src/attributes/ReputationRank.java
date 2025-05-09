package attributes;

import com.tenyar97.util.Identifiable;

public class ReputationRank implements Identifiable
	{
		public int id;
		public String name;

		private static final ReputationRank[] RANKS = { new ReputationRank(0, "Hated"),
				new ReputationRank(1, "Hostile"), new ReputationRank(2, "Unfriendly"), new ReputationRank(3, "Neutral"),
				new ReputationRank(4, "Friendly"), new ReputationRank(5, "Honored"), new ReputationRank(6, "Revered"),
				new ReputationRank(7, "Exalted") };

		public ReputationRank(int id, String name)
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
				if (!(o instanceof ReputationRank))
					return false;
				ReputationRank other = (ReputationRank) o;
				return this.id == other.id && this.name.equals(other.name);
			}

		@Override
		public int hashCode()
			{
				return id * 31 + name.hashCode();
			}

		public static ReputationRank[] getAllRanks()
			{
				return RANKS;
			}

		public static String getNameById(int id)
			{
				for (ReputationRank rank : RANKS)
					{
						if (rank.id == id)
							{
								return rank.name;
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
