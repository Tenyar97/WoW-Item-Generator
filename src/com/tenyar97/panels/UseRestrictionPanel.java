package com.tenyar97.panels;

import javax.swing.*;

import com.tenyar97.Main;
import com.tenyar97.components.Textbox;
import com.tenyar97.util.SQLGenerator;
import com.tenyar97.util.UIBuilder;

import java.awt.*;

public class UseRestrictionPanel extends JPanel
	{
		JCheckBox mapChckbx, areaChckbx;
		Textbox mapIDTextbox, areaIDTextbox;
		static Textbox lockIDTextbox;
		public static boolean showLock = false;

		public UseRestrictionPanel()
			{

				setLayout(null);

				mapChckbx = new JCheckBox("Map specific?");
				mapChckbx.setName("mapChckbx");
				mapChckbx.setToolTipText("Only usable in a specific map zone.");
				mapChckbx.setBounds(6, 7, 119, 23);
				mapChckbx.setOpaque(false);
				add(mapChckbx);

				mapIDTextbox = new Textbox("MapID");
				mapIDTextbox.setName("mapIDTextbox");
				mapIDTextbox.setBounds(116, 7, 155, 23);
				mapIDTextbox.setVisible(false);
				add(mapIDTextbox);
				UIBuilder.bindVisibility(mapChckbx, mapIDTextbox);

				areaChckbx = new JCheckBox("Area specific?");
				areaChckbx.setName("areaChckbx");
				areaChckbx.setToolTipText("Only usable in a specific sub-area within a map.");
				areaChckbx.setBounds(6, 33, 119, 23);
				areaChckbx.setOpaque(false);
				add(areaChckbx);

				areaIDTextbox = new Textbox("AreaID");
				areaIDTextbox.setName("areaIDTextbox");
				areaIDTextbox.setBounds(116, 37, 155, 23);
				areaIDTextbox.setVisible(false);
				add(areaIDTextbox);
				UIBuilder.bindVisibility(areaChckbx, areaIDTextbox);

				lockIDTextbox = new Textbox("Lock ID");
				lockIDTextbox.setName("lockIDTextbox");
				lockIDTextbox.setToolTipText("Associated lock for chests or doors that require this item.");
				lockIDTextbox.setBounds(6, 63, 119, 23);
				lockIDTextbox.setVisible(false);
				add(lockIDTextbox);

				UIBuilder.autoNameFields(this);
			}

		public int getLockID()
			{
				return SQLGenerator.getInt(lockIDTextbox);
			}

		public int getMapID()
			{
				return SQLGenerator.getInt(mapIDTextbox);
			}

		public int getAreaID()
			{
				return SQLGenerator.getInt(areaIDTextbox);
			}

		public static void setLockVisibility(boolean isVisible)
			{
				lockIDTextbox.setVisible(isVisible);
			}
	}