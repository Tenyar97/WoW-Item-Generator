package attributes;

public class SpellEffect
	{
		public int spellId;
		public int trigger;
		public int charges;
		public float ppmRate;
		public int cooldown;
		public int category;
		public int categoryCooldown;

		public SpellEffect(SpellEffect other)
			{
				this.spellId = other.spellId;
				this.trigger = other.trigger;
				this.charges = other.charges;
				this.ppmRate = other.ppmRate;
				this.cooldown = other.cooldown;
				this.category = other.category;
				this.categoryCooldown = other.categoryCooldown;
			}

		public int getSpellID()
			{
				return spellId;
			}

		public SpellEffect()
			{
				// Future Copy method
			}
	}
