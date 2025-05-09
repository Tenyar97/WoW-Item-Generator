package com.tenyar97.components;

import java.awt.Component;

import javax.swing.*;
import javax.swing.text.*;

public class DocFilter extends DocumentFilter {
    private final Component parentComponent;

    public DocFilter(Component parentComponent) {
        this.parentComponent = parentComponent;
    }

    private boolean isNumeric(String text) {
        return text.matches("\\d*"); // allow empty for backspace/delete
    }

    private void showPopup(String message) {
        JOptionPane.showMessageDialog(parentComponent, message, "Invalid EntryID Input", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
        throws BadLocationException {
        if (isNumeric(string)) {
            super.insertString(fb, offset, string, attr);
        } else {
            showPopup("Only numbers are allowed.");
        }
    }

    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
        throws BadLocationException {
        if (isNumeric(text)) {
            super.replace(fb, offset, length, text, attrs);
        } else {
            showPopup("Only numbers are allowed.");
        }
    }
}
