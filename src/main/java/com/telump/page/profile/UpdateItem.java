package com.telump.page.profile;

import com.telump.category.Category;
import com.telump.common.Util;
import com.telump.database.CategoryTable;
import com.telump.database.ItemTable;
import com.telump.catalog.Item;

public class UpdateItem {
    public static void updateItem(String id){
        
        Item selectItem = ItemTable.getItemById(id);

         while(true){
            System.out.println("""
                =================================

                1. Edit Item
                2. Delete Item
                0. Kembali

                =================================
            """);
         int opsi = Util.integerInput("Pilih Opsi: ");
            switch(opsi){
                case 1:
                    editItem(selectItem);
                    System.out.println("-----------------------------\nberhasil diupdate\n-----------------------------");
                    continue;
                case 2:
                    deleteItem(selectItem);
                    System.out.println("-----------------------------\nberhasil dihapus\n-----------------------------");
                    return;
                case 0:
                    return;
                default:
                    continue;
            }
        }
    }

    private static void editItem(Item selectItem){
            
            int pilihan = 9;

            while(pilihan != 0){
                System.out.println("\n========== EDIT ITEM ==========");
                System.out.println("1. Title");
                System.out.println("2. Price");
                System.out.println("3. Description");
                System.out.println("4. Category");
                System.out.println("0. Kembali");
                System.out.println("=================================");

                pilihan = Util.integerInput("Field yang ingin di update: ");

                switch (pilihan){
                    case 1:
                        String title = Util.stringInput("New Title: ");
                        selectItem.setTitle(title);
                        break;
                    case 2:
                        int price = Util.integerInput("New Price: ");
                        selectItem.setPrice(price);
                        break;
                    case 3:
                        String description = Util.stringInput("New Description: ");
                        selectItem.setDescription(description);
                        break;
                    case 4:
                        String categoryName = Util.stringInput("New Category: ");
                        String cleanedCategoryName = categoryName.trim().toLowerCase();
                        Category category = CategoryTable.getCategoryByName(cleanedCategoryName);
        
                        if (category == null) {
                            int nextId = CategoryTable.getCategoryList().size() + 1;

                            category = new Category(nextId, cleanedCategoryName);
                        }
                        selectItem.setCategory(category);
                        break;
                    case 0:
                        return;
                    default:
                        return;

            }
        }
    }
    private static String deleteItem(Item selectItem){
        return ItemTable.delete(selectItem.getId());
    }
}
