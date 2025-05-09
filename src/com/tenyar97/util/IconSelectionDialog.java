package com.tenyar97.util;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class IconSelectionDialog
	{
		private static final Map<File, String> metadataTitleCache = new ConcurrentHashMap<>();

		public static void show(Component parent, String iconDirectory, List<ImageIconFile> iconCache,
				MruImageManager mruManager, Consumer<ImageIconFile> onSelect)
			{
				JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(parent));
				dialog.setSize(400, 425);
				dialog.setLayout(new BorderLayout());

				JPanel loadingPanel = new JPanel(new BorderLayout());
				JProgressBar progressBar = new JProgressBar();
				progressBar.setIndeterminate(true);
				loadingPanel.add(progressBar, BorderLayout.CENTER);
				dialog.add(loadingPanel, BorderLayout.NORTH);

				new Thread(() ->
				{
					if (iconCache.isEmpty())
						{
							iconCache.addAll(IconLoaderUtil.loadIconsFromDirectory(iconDirectory));
						}
					SwingUtilities.invokeLater(() -> setupUI(dialog, loadingPanel, iconCache, mruManager, onSelect));
				}).start();

				dialog.setVisible(true);
			}

		private static void setupUI(JDialog dialog, JPanel loadingPanel, List<ImageIconFile> iconCache,
				MruImageManager mruManager, Consumer<ImageIconFile> onSelect)
			{
				dialog.remove(loadingPanel);

				DefaultListModel<ImageIconFile> model = new DefaultListModel<>();
				iconCache.stream().sorted(Comparator.comparingInt(IconSelectionDialog::extractNumericPrefix))
						.forEach(model::addElement);

				JList<ImageIconFile> imageList = new JList<>(model);
				imageList.setCellRenderer(new ImageIconFileRenderer());
				imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				imageList.addListSelectionListener(e ->
				{
					if (!e.getValueIsAdjusting())
						{
							ImageIconFile selected = imageList.getSelectedValue();
							if (selected != null)
								{
									onSelect.accept(selected);
									mruManager.update(selected);
									dialog.dispose();
								}
						}
				});

				JPanel filterPanel = new JPanel();
				JTextField filterField = new JTextField(10);
				filterPanel.add(new JLabel("Filter by Name:"));
				filterPanel.add(filterField);
				filterField.getDocument().addDocumentListener(new DocumentListener()
					{
						public void insertUpdate(DocumentEvent e)
							{
								filter(model, iconCache, filterField.getText());
							}

						public void removeUpdate(DocumentEvent e)
							{
								filter(model, iconCache, filterField.getText());
							}

						public void changedUpdate(DocumentEvent e)
							{
								filter(model, iconCache, filterField.getText());
							}
					});

				dialog.add(filterPanel, BorderLayout.NORTH);
				dialog.add(new JScrollPane(imageList), BorderLayout.CENTER);
				dialog.add(mruManager.createPanel(dialog, onSelect), BorderLayout.EAST);
				dialog.revalidate();
				dialog.repaint();
			}

		private static void filter(DefaultListModel<ImageIconFile> model, List<ImageIconFile> source, String text)
			{
				model.clear();
				String lowerText = text.toLowerCase();

				source.stream().filter(icon ->
				{
					if (icon.getName().toLowerCase().contains(lowerText))
						return true;

					File file = icon.getFile();
					if (!file.getName().toLowerCase().endsWith(".png"))
						return false;

					String cached = metadataTitleCache.get(file);
					if (cached != null)
						{
							return cached.toLowerCase().contains(lowerText);
						}

					try
						{
							String comment = PngMetadataReader.readComment(file);
							if (comment != null)
								{
									metadataTitleCache.put(file, comment);
									return comment.toLowerCase().contains(lowerText);
								}
							else
								{
									metadataTitleCache.put(file, "");
								}
						}
					catch (IOException e)
						{
							metadataTitleCache.put(file, "");
						}

					return false;
				}).sorted(Comparator.comparingInt(IconSelectionDialog::extractNumericPrefix))
						.forEach(model::addElement);
			}

		private static int extractNumericPrefix(ImageIconFile file)
			{
				try
					{
						String name = file.getName().replaceAll("[^0-9]", "");
						return Integer.parseInt(name);
					}
				catch (Exception e)
					{
						return Integer.MAX_VALUE;
					}
			}
	}
