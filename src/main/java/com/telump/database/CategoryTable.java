package com.telump.database;

import com.telump.category.Category;
import java.util.ArrayList;
import java.util.List;

public class CategoryTable implements IDatabase {

    private static final List<Category> categoryTable = new ArrayList<>();

    public static String create(Category category) {

        if (category == null) {
            return "Error: Category cannot be null.";
        }

        for (Category c : categoryTable) {
            if (c.getId() == category.getId()) {
                return "Error: Category with duplicated id found.";
            }
        }

        categoryTable.add(category);
        return "Category created successfully!";
    }

    public static List<Category> getCategoryList() {
        return categoryTable;
    }

    public static Category getCategoryById(int id) {

        for (Category c : categoryTable) {
            if (c.getId() == id) {
                return c;
            }
        }

        return null;
    }

    public static String update(Category category) {

        if (category == null) {
            return "Error: Category cannot be null.";
        }

        for (int i = 0; i < categoryTable.size(); i++) {
            if (categoryTable.get(i).getId() == category.getId()) {

                categoryTable.set(i, category);
                return "Category updated successfully!";
            }
        }

        return "Error: Category not found.";
    }

    public static String delete(int id) {

        for (int i = 0; i < categoryTable.size(); i++) {
            if (categoryTable.get(i).getId() == id) {

                categoryTable.remove(i);
                return "Category deleted successfully!";
            }
        }

        return "Error: Category not found.";
    }
}