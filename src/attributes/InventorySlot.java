package attributes;

import javax.swing.JComboBox;

import com.tenyar97.util.Identifiable;

public class InventorySlot implements Identifiable
	{
		public final int id;
		public final String name;

		private static final InventorySlot[] inventorySlots = { new InventorySlot(1, "Head"),
				new InventorySlot(2, "Neck"), new InventorySlot(3, "Shoulders"), new InventorySlot(4, "Body"),
				new InventorySlot(5, "Chest"), new InventorySlot(6, "Waist"), new InventorySlot(7, "Legs"),
				new InventorySlot(8, "Feet"), new InventorySlot(9, "Wrists"), new InventorySlot(10, "Hands"),
				new InventorySlot(11, "Finger"), new InventorySlot(12, "Trinket"), new InventorySlot(13, "Weapon"),
				new InventorySlot(14, "Shield"), new InventorySlot(15, "Ranged (Left)"), new InventorySlot(16, "Cloak"),
				new InventorySlot(17, "2H weapon"), new InventorySlot(18, "Bag"), new InventorySlot(19, "Tabard"),
				new InventorySlot(20, "Robe"), new InventorySlot(21, "Weapon (Main Hand)"),
				new InventorySlot(22, "Weapon (Off Hand)"), new InventorySlot(23, "Holdable"),
				new InventorySlot(24, "Ammo"), new InventorySlot(25, "Thrown"), new InventorySlot(26, "Ranged (Right)"),
				new InventorySlot(27, "Quiver"), new InventorySlot(28, "Relic") };

		public InventorySlot(int id, String name)
			{
				this.id = id;
				this.name = name;
			}

		@Override
		public String toString()
			{
				return name;
			}

		public static JComboBox<InventorySlot> createArmorSlotBox()
			{
				return new JComboBox<>(inventorySlots);
			}

		public static String getNameById(int id)
			{
				for (InventorySlot slot : inventorySlots)
					{
						if (slot.id == id)
							{
								return slot.name;
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
