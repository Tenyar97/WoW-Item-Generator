package com.tenyar97.util;

import javax.swing.*;

import com.tenyar97.Main;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class MruImageManager
	{
		private final List<ImageIconFile> mruList = new ArrayList<>();
		private final int limit = Main.mruLimit;

		public MruImageManager()
			{
			}

		public void update(ImageIconFile selected)
			{
				mruList.remove(selected);
				mruList.add(0, selected);
				if (mruList.size() > limit)
					{
						mruList.remove(limit);
					}
			}

		public JPanel createPanel(JDialog dialog, Consumer<ImageIconFile> onSelect)
			{
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

				DefaultListModel<ImageIconFile> model = new DefaultListModel<>();
				mruList.forEach(model::addElement);

				JList<ImageIconFile> list = new JList<>(model);
				list.setCellRenderer(new ImageIconFileRenderer());
				list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				list.addListSelectionListener(e ->
				{
					if (!e.getValueIsAdjusting())
						{
							ImageIconFile selected = list.getSelectedValue();
							if (selected != null)
								{
									onSelect.accept(selected);
									dialog.dispose();
								}
						}
				});

				JScrollPane scrollPane = new JScrollPane(list);
				scrollPane.setPreferredSize(new Dimension(120, 300));
				panel.add(scrollPane);
				return panel;
			}
	}
