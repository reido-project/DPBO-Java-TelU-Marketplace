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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SpinnerNumberModel;

import com.telump.Main;
import com.telump.common.AuthenticationService;
import com.telump.database.ReviewTable;
import com.telump.review.Review;
import com.telump.user.User;

public class UserDetail extends JFrame {
    private User user;
    private boolean reviewSubmitted = false;

    public UserDetail(User user) {
        super("User Detail - Tel-U Marketplace");
        this.user = user;

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

        JLabel title = new JLabel("User Detail");
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

        // User info section
        JPanel userPanel = createUserPanel();
        content.add(userPanel);
        content.add(Box.createVerticalStrut(20));

        // Rating section
        JPanel ratingPanel = createRatingPanel();
        content.add(ratingPanel);
        content.add(Box.createVerticalStrut(20));

        // Review list section
        JPanel reviewsPanel = createReviewsPanel();
        content.add(reviewsPanel);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        return scrollPane;
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Username
        JLabel usernameLabel = new JLabel("Username: @" + user.getUsername());
        usernameLabel.setForeground(Color.YELLOW);
        usernameLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(usernameLabel);
        panel.add(Box.createVerticalStrut(8));

        // Name
        JLabel nameLabel = new JLabel("Nama: " + user.getProfilname());
        nameLabel.setForeground(Color.WHITE);
        panel.add(nameLabel);
        panel.add(Box.createVerticalStrut(8));

        // Role
        JLabel roleLabel = new JLabel("Role: " + user.getRole().toString());
        roleLabel.setForeground(Color.CYAN);
        panel.add(roleLabel);
        panel.add(Box.createVerticalStrut(8));

        // Bio
        JLabel bioTitleLabel = new JLabel("Bio:");
        bioTitleLabel.setForeground(Color.WHITE);
        panel.add(bioTitleLabel);

        JTextArea bioArea = new JTextArea(user.getBio());
        bioArea.setForeground(Color.LIGHT_GRAY);
        bioArea.setBackground(new Color(50, 50, 50));
        bioArea.setFont(new Font("SansSerif", Font.PLAIN, 11));
        bioArea.setEditable(false);
        bioArea.setLineWrap(true);
        bioArea.setWrapStyleWord(true);
        bioArea.setRows(2);
        panel.add(bioArea);
        panel.add(Box.createVerticalStrut(8));

        // Kontak
        JLabel kontakLabel = new JLabel("Kontak: " + user.getKontak());
        kontakLabel.setForeground(Color.LIGHT_GRAY);
        panel.add(kontakLabel);

        return panel;
    }

    private JPanel createRatingPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        double avgRating = ReviewTable.getAverageRating(user.getUsername());
        List<Review> reviews = ReviewTable.getByTargetUsername(user.getUsername());

        JLabel ratingLabel = new JLabel(String.format("Rating: ★%.1f/5.0 (%d review)", avgRating, reviews.size()));
        ratingLabel.setForeground(new Color(255, 200, 0));
        ratingLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        panel.add(ratingLabel);

        return panel;
    }

    private JPanel createReviewsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel reviewsTitle = new JLabel("=== Review Terbaru ===");
        reviewsTitle.setForeground(Color.WHITE);
        reviewsTitle.setFont(new Font("SansSerif", Font.BOLD, 12));
        panel.add(reviewsTitle);
        panel.add(Box.createVerticalStrut(10));

        List<Review> reviews = ReviewTable.getByTargetUsername(user.getUsername());

        if (reviews.isEmpty()) {
            JLabel emptyLabel = new JLabel("Belum ada review untuk user ini.");
            emptyLabel.setForeground(Color.LIGHT_GRAY);
            panel.add(emptyLabel);
        } else {
            // Tampilkan maksimal 5 review terbaru
            int startIdx = Math.max(0, reviews.size() - 5);
            for (int i = reviews.size() - 1; i >= startIdx; i--) {
                Review r = reviews.get(i);
                JPanel reviewItem = createReviewItem(r);
                panel.add(reviewItem);
                panel.add(Box.createVerticalStrut(8));
            }
        }

        return panel;
    }

    private JPanel createReviewItem(Review review) {
        JPanel item = new JPanel();
        item.setLayout(new BoxLayout(item, BoxLayout.Y_AXIS));
        item.setBackground(new Color(50, 50, 50));
        item.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        item.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String rating = "★".repeat(review.getRating()) + "☆".repeat(5 - review.getRating());

        JLabel ratingLabel = new JLabel(rating + " | @" + review.getReviewer());
        ratingLabel.setForeground(new Color(255, 200, 0));
        ratingLabel.setFont(new Font("SansSerif", Font.BOLD, 11));
        item.add(ratingLabel);

        JLabel commentLabel = new JLabel(review.getComment());
        commentLabel.setForeground(Color.WHITE);
        commentLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
        item.add(commentLabel);

        JLabel dateLabel = new JLabel(review.getCreatedAt().toString());
        dateLabel.setForeground(Color.GRAY);
        dateLabel.setFont(new Font("SansSerif", Font.ITALIC, 9));
        item.add(dateLabel);

        return item;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel();
        footer.setBackground(new Color(40, 40, 40));
        footer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Cek apakah user sedang login dan bukan diri sendiri
        User currentUser = AuthenticationService.getCurrentUser();
        boolean canReview = currentUser != null && !currentUser.getUsername().equals(user.getUsername());

        if (canReview) {
            JButton reviewButton = new JButton("Beri Review");
            reviewButton.setBackground(new Color(0, 150, 0));
            reviewButton.setForeground(Color.WHITE);
            reviewButton.setFocusPainted(false);
            reviewButton.addActionListener(e -> {
                ReviewDialog dialog = new ReviewDialog(this, user.getUsername());
                dialog.setVisible(true);
                if (dialog.isSubmitted()) {
                    reviewSubmitted = true;
                    // Refresh window
                    dispose();
                    new UserDetail(user).display();
                }
            });
            footer.add(reviewButton);
            footer.add(Box.createHorizontalStrut(10));
        }

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

    /**
     * Modal dialog untuk input review
     */
    private static class ReviewDialog extends JDialog {
        private boolean submitted = false;
        private String targetUsername;

        ReviewDialog(JFrame parent, String targetUsername) {
            super(parent, "Beri Review", true);
            this.targetUsername = targetUsername;

            setSize(400, 300);
            setResizable(false);
            setLocationRelativeTo(parent);

            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(new Color(40, 40, 40));
            panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

            // Rating
            JLabel ratingLabel = new JLabel("Rating (1-5):");
            ratingLabel.setForeground(Color.WHITE);
            panel.add(ratingLabel);

            JSpinner ratingSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 5, 1));
            panel.add(ratingSpinner);
            panel.add(Box.createVerticalStrut(10));

            // Comment
            JLabel commentLabel = new JLabel("Komentar:");
            commentLabel.setForeground(Color.WHITE);
            panel.add(commentLabel);

            JTextArea commentArea = new JTextArea(5, 25);
            commentArea.setForeground(Color.BLACK);
            commentArea.setFont(new Font("SansSerif", Font.PLAIN, 11));
            commentArea.setLineWrap(true);
            commentArea.setWrapStyleWord(true);
            panel.add(new JScrollPane(commentArea));
            panel.add(Box.createVerticalStrut(10));

            // Buttons
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(new Color(40, 40, 40));

            JButton submitButton = new JButton("Submit");
            submitButton.setBackground(new Color(0, 150, 0));
            submitButton.setForeground(Color.WHITE);
            submitButton.setFocusPainted(false);
            submitButton.addActionListener(e -> {
                String comment = commentArea.getText().trim();
                if (comment.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Komentar tidak boleh kosong!", "Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int rating = (Integer) ratingSpinner.getValue();
                User currentUser = AuthenticationService.getCurrentUser();

                // Cek duplikat
                Review existingReview = ReviewTable.findReviewByReviewerAndTarget(currentUser.getUsername(), targetUsername);
                if (existingReview != null) {
                    int result = JOptionPane.showConfirmDialog(this,
                        "Anda sudah pernah review user ini.\nReview lama: ★" + existingReview.getRating() + " - " + existingReview.getComment() + "\n\nApakah ingin update?",
                        "Duplikat Review", JOptionPane.YES_NO_OPTION);

                    if (result == JOptionPane.YES_OPTION) {
                        existingReview.setRating(rating);
                        existingReview.setComment(comment);
                        existingReview.setCreatedAt(java.time.LocalDateTime.now());
                        ReviewTable.update(existingReview);
                        JOptionPane.showMessageDialog(this, "Review berhasil diupdate!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    new Review(currentUser.getUsername(), targetUsername, comment, rating);
                    JOptionPane.showMessageDialog(this, "Review berhasil disimpan!", "Success", JOptionPane.INFORMATION_MESSAGE);
                }

                submitted = true;
                dispose();
            });

            JButton cancelButton = new JButton("Batal");
            cancelButton.setBackground(new Color(100, 100, 100));
            cancelButton.setForeground(Color.WHITE);
            cancelButton.setFocusPainted(false);
            cancelButton.addActionListener(e -> dispose());

            buttonPanel.add(submitButton);
            buttonPanel.add(Box.createHorizontalStrut(10));
            buttonPanel.add(cancelButton);

            panel.add(buttonPanel);

            setContentPane(panel);
        }

        public boolean isSubmitted() {
            return submitted;
        }
    }
}
