package com.telump.page.detail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.telump.Main;
import com.telump.catalog.Ad;

public class AdDetail extends JFrame {
    private Ad ad;

    public AdDetail(Ad ad) {
        super("Ad Detail - Tel-U Marketplace");
        this.ad = ad;

        URL imgURL = Main.class.getResource("/com/telump/asset/emblem.png");
        if (imgURL != null) {
            Image icon = new ImageIcon(imgURL).getImage();
            setIconImage(icon);
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        // Header
        JPanel header = createHeader();
        mainPanel.add(header, BorderLayout.NORTH);

        // Content area dengan scroll
        JScrollPane scrollPane = createContentArea();
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Footer dengan button
        JPanel footer = createFooter();
        mainPanel.add(footer, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new GridBagLayout());
        header.setBackground(new Color(200, 0, 0));
        header.setPreferredSize(new Dimension(100, 60));

        JLabel title = new JLabel("Iklan Detail");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        header.add(title);

        return header;
    }

    private JScrollPane createContentArea() {
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(Color.BLACK);
        content.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Image section
        JPanel imagePanel = createImagePanel();
        content.add(imagePanel);
        content.add(Box.createVerticalStrut(20));

        // Info section
        JPanel infoPanel = createInfoPanel();
        content.add(infoPanel);
        content.add(Box.createVerticalStrut(20));

        // Registrant info section
        JPanel registrantPanel = createRegistrantPanel();
        content.add(registrantPanel);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        return scrollPane;
    }

    private JPanel createImagePanel() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel image = new JLabel("No Image");
        image.setHorizontalAlignment(SwingConstants.CENTER);
        image.setForeground(Color.GRAY);

        try {
            String imagePath = "com/telump/asset/noImage.png";
            if (ad.getImagePath() != null && !ad.getImagePath().trim().isBlank()) {
                imagePath = ad.getImagePath();
            }

            URL imgUrl = ClassLoader.getSystemResource(imagePath);
            if (imgUrl != null) {
                ImageIcon icon = new ImageIcon(imgUrl);
                Image scaledImage = icon.getImage().getScaledInstance(300, 250, Image.SCALE_SMOOTH);
                image.setIcon(new ImageIcon(scaledImage));
                image.setText("");
            }
        } catch (Exception e) {
            System.out.println("Error loading image");
        }

        panel.add(image);
        return panel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Title
        JLabel titleLabel = new JLabel("Judul: " + ad.getTitle());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        panel.add(titleLabel);
        panel.add(Box.createVerticalStrut(10));

        // Description
        JLabel descLabel = new JLabel("Deskripsi:");
        descLabel.setForeground(Color.WHITE);
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panel.add(descLabel);

        JTextArea descArea = new JTextArea(ad.getDescription());
        descArea.setForeground(Color.LIGHT_GRAY);
        descArea.setBackground(new Color(50, 50, 50));
        descArea.setFont(new Font("SansSerif", Font.PLAIN, 11));
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setRows(4);
        panel.add(descArea);
        panel.add(Box.createVerticalStrut(10));

        // Target URL
        if (ad.getTargetUrl() != null && !ad.getTargetUrl().isBlank()) {
            JLabel urlLabel = new JLabel("Pelajari Lebih Lanjut:");
            urlLabel.setForeground(Color.WHITE);
            panel.add(urlLabel);

            JLabel linkLabel = new JLabel("<html><u>" + ad.getTargetUrl() + "</u></html>");
            linkLabel.setForeground(new Color(0, 100, 255));
            linkLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            linkLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    openLink(ad.getTargetUrl());
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    linkLabel.setForeground(new Color(100, 150, 255));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    linkLabel.setForeground(new Color(0, 100, 255));
                }
            });
            panel.add(linkLabel);
        }

        return panel;
    }

    private JPanel createRegistrantPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Registrant title
        JLabel regTitle = new JLabel("Informasi Pengiklan");
        regTitle.setForeground(Color.WHITE);
        regTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(regTitle);
        panel.add(Box.createVerticalStrut(10));

        // Registrant info
        JLabel regNameLabel = new JLabel("Nama: " + ad.getRegistrant().getProfilname());
        regNameLabel.setForeground(Color.WHITE);
        panel.add(regNameLabel);

        JLabel regUsernameLabel = new JLabel("Username: @" + ad.getRegistrant().getUsername());
        regUsernameLabel.setForeground(Color.YELLOW);
        panel.add(regUsernameLabel);

        JLabel regKontakLabel = new JLabel("Kontak: " + ad.getRegistrant().getKontak());
        regKontakLabel.setForeground(Color.LIGHT_GRAY);
        panel.add(regKontakLabel);

        return panel;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel();
        footer.setBackground(new Color(40, 40, 40));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton closeButton = new JButton("Tutup");
        closeButton.setBackground(new Color(100, 100, 100));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> dispose());

        footer.add(closeButton);

        return footer;
    }

    private void openLink(String url) {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new java.net.URI(url));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Tidak bisa membuka link: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void display() {
        setVisible(true);
    }
}
