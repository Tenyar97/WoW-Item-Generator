package com.tenyar97.util;

import javax.swing.ImageIcon;
import java.io.File;

public class ImageIconFile {
    private final ImageIcon icon;
    private final String name;
    private final File file;

    public ImageIconFile(ImageIcon icon, String name, File file) {
        this.icon = icon;
        this.name = name;
        this.file = file;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public File getFile() {
        return file;
    }
}
