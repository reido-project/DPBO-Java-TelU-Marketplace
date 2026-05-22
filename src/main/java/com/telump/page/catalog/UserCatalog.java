package com.telump.page.catalog;

import com.telump.catalog.CatalogItem;

import javax.swing.*;
import java.util.List;

public class UserCatalog extends Catalog{
    public UserCatalog(List<CatalogItem> items) {
        super(items);
    }

    public JPanel createCard(CatalogItem item) {
        return new JPanel();
    }

    public JPanel getInfo(CatalogItem item) {
        return new JPanel();
    }
}
