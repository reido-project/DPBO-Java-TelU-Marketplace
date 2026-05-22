package com.telump.page.catalog;

import com.telump.catalog.Ad;
import com.telump.catalog.CatalogItem;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.List;

public class ItemCatalog extends Catalog {
    public ItemCatalog(List<CatalogItem> items) {
        super(items);
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
            /*if(item instanceof Item){
                imagePath = String.format("com/telump/asset/listing/%s.png", item.getId());

            }*/
            /*else*/ if(item instanceof Ad){
                imagePath = String.format("com/telump/asset/listing/%s.png", item.getId());
            }
            URL imgUrl = getClass().getResource(imagePath);

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

        JLabel price = new JLabel("Price");
        /*if(item instanceof Item){
            price = new JLabel(item.getFormattedPrice());
        }
        else*/ if(item instanceof Ad){
            price = new JLabel(item.getDescription());
        }
        price.setForeground(Color.GREEN);
        price.setHorizontalAlignment(SwingConstants.CENTER);

        info.add(title);
        info.add(price);
        info.setBorder(BorderFactory.createEmptyBorder(10,5,10,5));

        return info;
    }
}
