package com.tenyar97.components;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import com.tenyar97.Main;
import com.tenyar97.util.ResourceLoader;

public class Textbox extends JTextArea {
    private String placeholder;
    private Clip preloadedClip;
    private static Random rand = new Random();
    private static byte[] liesAudioBytes = null; // Preloaded lies.txt as bytes

    public Textbox(String text) 
    	{
        super(text);
        this.placeholder = text;
        setLineWrap(true);
        setWrapStyleWord(true);
        setAlignmentX(LEFT_ALIGNMENT);
        setAlignmentY(TOP_ALIGNMENT);
        setCaretPosition(0);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (getText().isEmpty() || getText().equals(placeholder)) {
                    setText("");
                    setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    setForeground(Color.GRAY);
                    setText(placeholder);
                }
            }
        });

        if (getText().isEmpty()) {
            setForeground(Color.GRAY);
            setText(placeholder);
        }

        try {
            // Preload the main clip
            File soundFile = new File(ResourceLoader.getResourceDirectoryPath(ResourceLoader.SECRET) + "\\tunes.wav");
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundFile);
            preloadedClip = AudioSystem.getClip();
            preloadedClip.open(audioIn);
        } catch (Exception e) {
            System.err.println("Failed to preload tunes.wav: " + e.getMessage());
            preloadedClip = null;
        }

        try {
            // Preload lies.txt decoding
            String base64Content = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(ResourceLoader.getResourceDirectoryPath(ResourceLoader.SECRET) + "\\lies.txt")));
            liesAudioBytes = Base64.getDecoder().decode(base64Content);
        } catch (Exception e) {
            System.err.println("Failed to preload lies.txt: " + e.getMessage());
        }

        getDocument().addDocumentListener(new DocumentListener() {
            boolean shownTheTruth = false;

            private void doSuperSecret() {
                String text = getText();
                if (!shownTheTruth && (text.toLowerCase().contains("cow level") || text.toLowerCase().contains("cowlevel"))) {
                    shownTheTruth = true;

                    new Thread(() -> {
                        try {
                            if (preloadedClip != null) {
                                long clipLengthMilliseconds = preloadedClip.getMicrosecondLength() / 1000;
                                
                                if (preloadedClip.isRunning()) {
                                    preloadedClip.stop();
                                }
                                preloadedClip.setMicrosecondPosition(2000000);
                                playSound();
                                preloadedClip.start();
                                
                                new Thread(() -> 
                                {
                                    long startTime = System.currentTimeMillis();
                                    while (System.currentTimeMillis() - startTime < clipLengthMilliseconds) {
                                        try {
                                            Thread.sleep(100 + rand.nextInt(9200));
                                            playSound();
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }).start();
                            }
                        } catch (Exception e) {
                            System.err.println("Audio playback error: " + e.getMessage());
                        }
                    }).start();

                    showCowLevelDialog();
                }
            }

            private void showCowLevelDialog() {
                JDialog dialog = new JDialog();
                dialog.setTitle("There is no Cow Level");
                try {
                    BufferedImage cowKing = ImageIO.read(new File(ResourceLoader.getResourceDirectoryPath(ResourceLoader.SECRET) + "\\its_a_lie.png"));
                    double scale = Math.min(1100.0 / cowKing.getWidth(), 1000.0 / cowKing.getHeight());
                    int newWidth = (int) (cowKing.getWidth() * scale);
                    int newHeight = (int) (cowKing.getHeight() * scale);
                    Image scaledImage = cowKing.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                    dialog.getContentPane().add(new JLabel(new ImageIcon(scaledImage)));
                    dialog.pack();
                    dialog.setSize(newWidth + 20, newHeight + 40);
                } catch (Exception ex) {
                    dialog.getContentPane().add(new JLabel("Failed to load cow image. (Because it doesn't exist)"));
                    dialog.pack();
                }
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }

            @Override public void insertUpdate(DocumentEvent e) { doSuperSecret(); }
            @Override public void removeUpdate(DocumentEvent e) { doSuperSecret(); }
            @Override public void changedUpdate(DocumentEvent e) { doSuperSecret(); }
        });
    }

    public static void playSound() {
        if (liesAudioBytes == null) {
            System.err.println("liesAudioBytes not loaded.");
            return;
        }
        try {
            InputStream byteArrayInputStream = new ByteArrayInputStream(liesAudioBytes);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(byteArrayInputStream);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            //System.out.println("MOO");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPlaceholder() {
        return placeholder;
    }
}
