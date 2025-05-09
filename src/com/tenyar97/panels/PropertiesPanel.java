package com.tenyar97.panels;

import javax.swing.*;

import com.tenyar97.Main;
import com.tenyar97.components.Textbox;
import com.tenyar97.components.TexturedTextbox;
import com.tenyar97.util.SQLGenerator;
import com.tenyar97.util.UIBuilder;

import attributes.Languages;
import attributes.Material;
import attributes.PageMaterial;
import attributes.Stats;

import java.awt.*;

public class PropertiesPanel extends JPanel
	{
		Textbox scriptNameTxtBx;
		JCheckBox bookChxBx;
		JComboBox<PageMaterial> bookMatBox;
		JComboBox<Languages> languageBox;
		private JCheckBox scriptChxBx;

		public PropertiesPanel()
			{

				setLayout(null);

				bookChxBx = new JCheckBox("Is it a Book");
				bookChxBx.setName("bookChxBx");
				bookChxBx.setFont(Main.lifeCraft);
				bookChxBx.setOpaque(false);
				bookChxBx.setBounds(10, 37, 99, 23);
				add(bookChxBx);

				bookMatBox = new JComboBox<>(PageMaterial.getAllPageMaterials());
				bookMatBox.setName("bookMatBox");
				bookMatBox.setBounds(103, 37, 108, 22);
				bookMatBox.setToolTipText("The style of the pages when this item is a readable book.");
				bookMatBox.setVisible(false);

				add(bookMatBox);
				UIBuilder.bindVisibility(bookChxBx, bookMatBox);

				scriptChxBx = new JCheckBox("Is it scriptable?");
				scriptChxBx.setName("scriptChxBx");
				scriptChxBx.setOpaque(false);
				scriptChxBx.setFont(null);
				scriptChxBx.setBounds(10, 11, 118, 23);
				add(scriptChxBx);

				scriptNameTxtBx = new Textbox("ScriptName");
				scriptNameTxtBx.setName("scriptNameTxtBx");
				scriptNameTxtBx.setToolTipText(
						"Links the item to a C++ or core script that adds custom behavior when the item is used, equipped, or triggers an event.");
				scriptNameTxtBx.setBounds(134, 11, 87, 22);
				scriptNameTxtBx.setVisible(false);
				add(scriptNameTxtBx);

				languageBox = new JComboBox<>(Languages.getAllLanguages());
				languageBox.setName("languageBox");
				languageBox.setBounds(231, 11, 108, 22);
				languageBox.setToolTipText("The language the player speaks when preforming scripted content.");
				languageBox.setVisible(false);
				add(languageBox);

				UIBuilder.bindVisibility(scriptChxBx, scriptNameTxtBx, languageBox);

				UIBuilder.autoNameFields(this);
			}

		public int getLanguage()
			{
				Languages language = (Languages) languageBox.getSelectedItem();
				return scriptChxBx.isSelected() ? language.id : 0;
			}

		public int getPageMat()
			{
				PageMaterial material = (PageMaterial) bookMatBox.getSelectedItem();
				return bookChxBx.isSelected() ? material.id : 0;
			}

		public String getScriptName()
			{
				return scriptChxBx.isSelected() ? SQLGenerator.sanitizeString(scriptNameTxtBx) : "";
			}
	}