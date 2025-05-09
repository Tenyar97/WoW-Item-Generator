package com.tenyar97.panels;

import javax.swing.*;

import com.tenyar97.util.UIBuilder;

import attributes.ItemFlag;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.EnumMap;
import java.util.Map;

public class FlagsPanel extends JPanel
	{
		private final Map<ItemFlag, JCheckBox> checkBoxes = new EnumMap<>(ItemFlag.class);
		private final JLabel valueLabel = new JLabel();

		public FlagsPanel()
			{
				setLayout(new BorderLayout());

				JPanel checkBoxPanel = new JPanel(new GridLayout(0, 2, 4, 2));

				for (ItemFlag flag : ItemFlag.values())
					{
						JCheckBox checkBox = new JCheckBox(flag.label);
						checkBox.setName(flag.label);
						checkBox.setToolTipText(flag.tooltip);
						checkBox.addItemListener(this::onFlagChanged);
						checkBoxes.put(flag, checkBox);
						checkBoxPanel.add(checkBox);
					}

				add(new JScrollPane(checkBoxPanel), BorderLayout.CENTER);
				add(valueLabel, BorderLayout.SOUTH);
				updateLabel();

			}

		private void onFlagChanged(ItemEvent e)
			{
				updateLabel();
			}

		private void updateLabel()
			{
				int flags = getSelectedFlagsValue();
				valueLabel.setText(String.format("Flags: %d (0x%X)", flags, flags));
			}

		public int getSelectedFlagsValue()
			{
				return checkBoxes.entrySet().stream().filter(entry -> entry.getValue().isSelected())
						.mapToInt(entry -> entry.getKey().value).reduce(0, (a, b) -> a | b);
			}

		public void setFlagsValue(int flagsValue)
			{
				checkBoxes.forEach((flag, box) -> box.setSelected((flagsValue & flag.value) != 0));
				updateLabel();
			}

	}
