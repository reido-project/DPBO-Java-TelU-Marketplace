package com.telump.page.catalog;

import com.telump.catalog.CatalogItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public abstract class Catalog extends JFrame {
    private CatalogItem selectedItem;

    public CatalogItem getSelectedItem() {
        return selectedItem;
    }

    public Catalog(List<CatalogItem> items){
        super("Tel-U-Marketplace");

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
        JLabel title = new JLabel("Tel-U-Marketplace");
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

    protected abstract JPanel createCard(CatalogItem item);
    protected abstract JPanel getInfo(CatalogItem item);
}
