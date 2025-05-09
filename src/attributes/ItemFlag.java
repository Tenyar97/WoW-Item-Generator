package attributes;

public enum ItemFlag
	{
	RESERVED_0("Reserved 0", 0x00000001, "Reserved for future use"),
	CONJURED("Conjured", 0x00000002, "Item is conjured (created by spell)"),
	LOOTABLE("Lootable", 0x00000004, "Right-click to open (non-container)"),
	WRAPPED("Wrapped", 0x00000008, "Used in later expansions for wrapped items"),
	DEPRECATED("Deprecated", 0x00000010, "Item cannot be equipped; deprecated"),
	INDESTRUCTIBLE("Indestructible", 0x00000020, "Item can't be destroyed except by spell"),
	USABLE("Usable", 0x00000040, "Right-click to use"),
	NO_EQUIP_COOLDOWN("No Equip Cooldown", 0x00000080, "Item has no equip cooldown"),
	RESERVED_1("Reserved 1", 0x00000100, "Reserved for future use"),
	WRAPPER("Wrapper", 0x00000200, "Gift wrapper item"),
	STACKABLE("Stackable", 0x00000400, "Can be stacked in inventory"),
	PARTY_LOOT("Party Loot", 0x00000800, "Lootable by all party members"),
	RESERVED_2("Reserved 2", 0x00001000, "Reserved for future use"),
	CHARTER("Charter", 0x00002000, "Guild charter item"), LETTER("Letter", 0x00004000, "Readable item (letter/scroll)"),
	PVP_REWARD("PvP Reward", 0x00008000, "Rewarded for PvP ranks or honor"),
	UNK16("Unknown 16", 0x00010000, "Unknown use (many items have this)"),
	UNK17("Unknown 17", 0x00020000, "Last flag used in 1.12.1; unclear purpose");

		public final String label;
		public final int value;
		public final String tooltip;

		ItemFlag(String label, int value, String tooltip)
			{
				this.label = label;
				this.value = value;
				this.tooltip = tooltip;
			}
	}
