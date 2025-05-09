package com.tenyar97.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import com.tenyar97.util.ConfigurationProcessor;
import com.tenyar97.util.SQLGenerator;
import com.tenyar97.util.UIBuilder;

public class OutputPanel extends JPanel
	{
		private final JTextArea sqlArea;

		public OutputPanel(BasicInfoPanel basic, RequirementsPanel req, StatsPanel stats, SpellsPanel spells,
				PricingPanel price, FlagsPanel flags, UseRestrictionPanel useRestrictionPanel,
				LootAndMiscPanel lootAndMiscPanel, PropertiesPanel props)
			{
				setLayout(new BorderLayout());
				sqlArea = new JTextArea(25, 70);
				sqlArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
				sqlArea.setEditable(false);

				JButton generateButton = new JButton("Generate SQL");
				generateButton.addActionListener(e ->
				{
					String sql = SQLGenerator.generateSQL(basic, req, stats, spells, price, flags, useRestrictionPanel,
							lootAndMiscPanel, props);
					sqlArea.setText(sql);

				});

				JButton copyButton = new JButton("Copy to Clipboard");
				copyButton.addActionListener(e ->
				{
					StringSelection selection = new StringSelection(sqlArea.getText());
					Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
					clipboard.setContents(selection, null);
				});

				JButton exportButton = new JButton("Export to .sql File");
				exportButton.addActionListener(e ->
				{
					JFileChooser fileChooser = new JFileChooser();
					fileChooser.setSelectedFile(new File(basic.getName() + ".sql"));
					int userSelection = fileChooser.showSaveDialog(this);
					if (userSelection == JFileChooser.APPROVE_OPTION)
						{
							File fileToSave = fileChooser.getSelectedFile();
							try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileToSave)))
								{
									writer.write(sqlArea.getText());
									JOptionPane.showMessageDialog(this, "SQL file saved successfully.");
								}
							catch (IOException ex)
								{
									JOptionPane.showMessageDialog(this, "Error saving file: " + ex.getMessage(),
											"Error", JOptionPane.ERROR_MESSAGE);
								}
						}
				});

				JPanel buttonPanel = new JPanel();
				buttonPanel.add(generateButton);
				buttonPanel.add(copyButton);
				buttonPanel.add(exportButton);

				add(new JScrollPane(sqlArea), BorderLayout.CENTER);
				add(buttonPanel, BorderLayout.SOUTH);

				UIBuilder.autoNameFields(this);
			}
	}
