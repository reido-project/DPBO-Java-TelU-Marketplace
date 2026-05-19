package com.telump.database;

public interface IDatabase {
    static String create(){
        return "Entry created successfully.";
    }

    static String update(){
        return "Entry updated successfully.";
    }

    static String delete(){
        return "Entry deleted successfully.";
    }
}
