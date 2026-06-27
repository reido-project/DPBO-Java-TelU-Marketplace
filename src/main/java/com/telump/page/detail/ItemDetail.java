package com.telump.page.detail;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.net.URL;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import com.telump.Main;
import com.telump.catalog.Item;
import com.telump.database.ReviewTable;
import com.telump.review.Review;

public class ItemDetail extends JFrame {
    private Item item;

    public ItemDetail(Item item) {
        super("Item Detail - Tel-U Marketplace");
        this.item = item;

        URL imgURL = Main.class.getResource("/com/telump/asset/emblem.png");
        if (imgURL != null) {
            Image icon = new ImageIcon(imgURL).getImage();
            setIconImage(icon);
        }

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
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

        JLabel title = new JLabel("Item Detail");
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

        // Seller info section
        JPanel sellerPanel = createSellerPanel();
        content.add(sellerPanel);

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
            if (item.getImagePath() != null && !item.getImagePath().trim().isBlank()) {
                imagePath = item.getImagePath();
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
        JLabel titleLabel = new JLabel("Judul: " + item.getTitle());
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(titleLabel);

        // Description
        JLabel descLabel = new JLabel("Deskripsi:");
        descLabel.setForeground(Color.WHITE);
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(descLabel);

        JTextArea descArea = new JTextArea(item.getDescription());
        descArea.setForeground(Color.LIGHT_GRAY);
        descArea.setBackground(new Color(50, 50, 50));
        descArea.setFont(new Font("SansSerif", Font.PLAIN, 11));
        descArea.setEditable(false);
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setRows(3);
        descArea.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(descArea);

        // Price
        JLabel priceLabel = new JLabel("Harga: " + item.getFormattedPrice());
        priceLabel.setForeground(new Color(0, 255, 0));
        priceLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(priceLabel);

        // Category
        JLabel categoryLabel = new JLabel("Kategori: " + item.getCategory().getName());
        categoryLabel.setForeground(Color.CYAN);
        categoryLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        categoryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(categoryLabel);

        return panel;
    }

    private JPanel createSellerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Seller title
        JLabel sellerTitle = new JLabel("Informasi Penjual");
        sellerTitle.setForeground(Color.WHITE);
        sellerTitle.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(sellerTitle);
        panel.add(Box.createVerticalStrut(10));

        // Seller info
        JLabel sellerNameLabel = new JLabel("Nama: " + item.getSeller().getProfilname());
        sellerNameLabel.setForeground(Color.WHITE);
panel.add(sellerNameLabel);

        JLabel sellerUsernameLabel = new JLabel("Username: @" + item.getSeller().getUsername());
        sellerUsernameLabel.setForeground(Color.YELLOW);
        panel.add(sellerUsernameLabel);

        JLabel sellerBioLabel = new JLabel("Bio: " + item.getSeller().getBio());
        sellerBioLabel.setForeground(Color.LIGHT_GRAY);
        panel.add(sellerBioLabel);

        JLabel sellerKontakLabel = new JLabel("Kontak: " + item.getSeller().getKontak());
        sellerKontakLabel.setForeground(Color.LIGHT_GRAY);
        panel.add(sellerKontakLabel);

        panel.add(Box.createVerticalStrut(8));

        // Seller rating
        double avgRating = ReviewTable.getAverageRating(item.getSeller().getUsername());
        List<Review> reviews = ReviewTable.getByTargetUsername(item.getSeller().getUsername());

        JLabel ratingLabel = new JLabel(String.format("Rating: ★%.1f/5.0 (%d review)", avgRating, reviews.size()));
        ratingLabel.setForeground(new Color(255, 200, 0));
        ratingLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        panel.add(ratingLabel);

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

    public void display() {
        setVisible(true);
    }
}
