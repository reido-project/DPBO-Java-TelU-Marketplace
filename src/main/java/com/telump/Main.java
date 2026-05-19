package com.telump;

import com.telump.database.AdTable;
import com.telump.item.Ad;

public class Main {
    public static void main(String[] args) {
        AppController.appController();
    }

    public static void init(){ // Ini method buat testing
        AdTable.create(new Ad("Example001", "Tel-U Marketplace!", "Tel-U Marketplace, Platform JB Terbaik Untuk Mahasiswa Telkom University!", "", "Reido"));
    }
}