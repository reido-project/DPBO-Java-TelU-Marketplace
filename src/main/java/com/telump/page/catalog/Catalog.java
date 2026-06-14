package com.telump.page.catalog;

import com.telump.catalog.Ad;
import com.telump.catalog.CatalogItem;
import com.telump.catalog.Item;
import com.telump.page.catalog.layout.WrapLayout;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Catalog extends JFrame {
    private CatalogItem selectedItem;

    public CatalogItem getSelectedItem() {
        return selectedItem;
    }

    public Catalog(List<CatalogItem> items){
        super("Tel-U Marketplace");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1350, 800);
        setResizable(false);
        setLocationRelativeTo(null);

        // Layer 0
        JPanel window = new JPanel(new BorderLayout());
        window.setBackground(Color.BLACK);

        // Layer 1
        JPanel banner = new JPanel(new GridBagLayout());
        banner.setBackground(Color.RED);
        banner.setPreferredSize(new Dimension(100, 60));

        // Ini Isian Banner
        JLabel title = new JLabel("Tel-U Marketplace");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        banner.add(title);

        // Layer 2
        JPanel catalog = new JPanel(new WrapLayout(25, 25));
        catalog.setBackground(Color.BLACK);
        catalog.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Ngisi Item
        for(CatalogItem item : items){
            catalog.add(createCard(item));
        }

        // Layer 1
        JScrollPane scrollPane = new JScrollPane(catalog);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        window.add(banner, BorderLayout.NORTH);
        window.add(scrollPane, BorderLayout.CENTER);
        setContentPane(window);
    }

    protected void setClickable(Component c, CatalogItem item){
        c.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                selectedItem = item;
                dispose();
            }
        });
    }

    // Layer 3
    public JPanel createCard(CatalogItem item) {
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(220, 300));
        card.setBackground(Color.DARK_GRAY);
        card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        card.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Layer 4
        JLabel image = new JLabel("No Image");
        image.setHorizontalAlignment(SwingConstants.CENTER);
        image.setForeground(Color.GRAY);

        try{
            String imagePath = "com/telump/asset/noImage.png";
            if (item instanceof Ad) {
                imagePath = "com/telump/asset/ad/" + item.getId() + ".png";
            } else {
                imagePath = "com/telump/asset/listing/" + item.getId() + ".png";
            }
            URL imgUrl = ClassLoader.getSystemResource(imagePath);

            if(imgUrl != null){
                ImageIcon icon = new ImageIcon(imgUrl);
                Image scaledImage = icon.getImage().getScaledInstance(180, 150, Image.SCALE_SMOOTH);
                image.setIcon(new ImageIcon(scaledImage));
                image.setText("");
            }
        } catch(Exception e){
            System.out.println("Error loading image");
        }

        card.add(image, BorderLayout.CENTER);

        JPanel info = getInfo(item);
        card.add(info, BorderLayout.SOUTH);

        setClickable(card, item);

        return card;
    }

    // Layer 4
    public JPanel getInfo(CatalogItem item) {
        JPanel info = new JPanel(new GridLayout(2, 1));
        info.setOpaque(false);

        JLabel title = new JLabel(item.getTitle());
        title.setForeground(Color.WHITE);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel price = new JLabel(item.getDescription());
        
        price.setForeground(Color.GREEN);
        price.setHorizontalAlignment(SwingConstants.CENTER);

        info.add(title);
        info.add(price);
        info.setBorder(BorderFactory.createEmptyBorder(10,5,10,5));

        return info;
    }
}
