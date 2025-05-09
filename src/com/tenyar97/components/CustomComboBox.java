package com.tenyar97.components;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.plaf.basic.BasicComboBoxUI;

import com.tenyar97.Main;

public class CustomComboBox<E> extends JComboBox<E> {
	private final ImageIcon defaultIcon = new ImageIcon("C:\\Users\\Brady\\eclipse-workspace\\WoW Item Generator\\CustomIcons\\unSelectedDropDown.png");
	private final ImageIcon hoverIcon = new ImageIcon("C:\\Users\\Brady\\eclipse-workspace\\WoW Item Generator\\CustomIcons\\arrow_hover.png");
	private final ImageIcon pressedIcon = new ImageIcon("C:\\Users\\Brady\\eclipse-workspace\\WoW Item Generator\\CustomIcons\\arrow_pressed.png");

	private ImageIcon currentBackground = defaultIcon;
	private final JLabel selectedLabel = new JLabel();
	public Font font = Main.lifeCraft;

	public CustomComboBox(E[] items) {
		super(items);
		setRenderer(new CustomComboBoxRenderer());

		setUI(new InvisibleComboBoxUI());
		setOpaque(false);
		setBorder(BorderFactory.createEmptyBorder());
		setForeground(Color.WHITE);
		setFont(font);

		// Label to show selected value
		selectedLabel.setFont(font);
		selectedLabel.setForeground(Color.WHITE);
		selectedLabel.setText("Selected: " + getSelectedItem());
		selectedLabel.setBounds(25, 25, 200, 20);
		add(selectedLabel);

		// Update label on selection
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectedLabel.setText("Selected: " + getSelectedItem());
				System.out.println("Selected: " + getSelectedItem());
			}
		});

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				currentBackground = hoverIcon;
				repaint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				currentBackground = defaultIcon;
				repaint();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				currentBackground = pressedIcon;
				repaint();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				currentBackground = hoverIcon;
				repaint();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
	    g.drawImage(currentBackground.getImage(), 0, 0, getWidth(), getHeight(), this);

	    String selected = getSelectedItem().toString();
	    if (selected instanceof String str) 
	    {
	        g.setFont(getFont());
	        g.setColor(getForeground());
	        g.drawString(str, 10, (getHeight() + g.getFontMetrics().getAscent()) / 2 - 2); // vertical centering
	    }
	    
	}




	@Override
	public Dimension getPreferredSize() {
	    Dimension base = super.getPreferredSize();
	    return new Dimension(base.width, base.height + 20); // +20 for the label
	}

	@Override
	public void doLayout() {
	    super.doLayout();
	    int comboHeight = super.getHeight();
	    selectedLabel.setBounds(0, comboHeight, getWidth(), 20); // place label below
	}


	class InvisibleComboBoxUI extends BasicComboBoxUI {
		@Override
		protected JButton createArrowButton() {
			JButton button = new JButton();
			button.setPreferredSize(new Dimension(0, 0));
			button.setMinimumSize(new Dimension(0, 0));
			button.setMaximumSize(new Dimension(0, 0));
			button.setVisible(false);
			return button;
		}

		@Override
		public void paint(Graphics g, JComponent c) {}

		@Override
		public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {}

		@Override
		@SuppressWarnings("unchecked")
		public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
		    ListCellRenderer<? super E> renderer = comboBox.getRenderer();
		    E value = (E) comboBox.getSelectedItem();
		    
		    Component c = renderer.getListCellRendererComponent((JList<? extends E>) comboBox.getAccessibleContext().getAccessibleChild(0),value,-1,false,false );
		    
		    // paint safely within bounds
		    c.setBounds(bounds);
		    c.paint(g.create(bounds.x, bounds.y, bounds.width, bounds.height));
		}


	}
}
