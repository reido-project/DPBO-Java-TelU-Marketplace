package com.telump;

import com.telump.catalog.Ad;
import com.telump.catalog.Item;
import com.telump.database.AdTable;
import com.telump.database.ItemTable;
import com.telump.database.UserTable;
import com.telump.user.Admin;
import com.telump.user.RegularUser;
import com.telump.user.User;
import com.telump.category.*;

public class Main {
    public static void main(String[] args) {
        init();

        AppController appController = new AppController();

        appController.appController();
    }
 
    public static void init(){ 
        // Initialize default ads
        AdTable.create(0, new Ad("Example001", "Tel-U Marketplace!", "Tel-U Marketplace, Platform JB Terbaik Untuk Mahasiswa Telkom University!", "", UserTable.findByUsername("user")));

        // Initialize default users for testing (HARDCODED)
        initializeDefaultUsers();
        seedProduct();
        seedAd();
    }

    /**
     * Inisialisasi akun default untuk testing:
     * - 1 akun ADMIN: admin / admin123
     * - 1 akun USER biasa: user / user123
     */
    public static void initializeDefaultUsers() {
        // Create Admin account
        Admin adminUser = new Admin("admin", "admin123", "Administrator", "admin@telu.ac.id", "Admin account for Tel-U Marketplace");
        UserTable.save(adminUser);
        System.out.println("[Init] Default ADMIN account created: admin / admin123");

        // Create Regular User account
        RegularUser regularUser = new RegularUser("user", "user123", "Regular User", "user@student.telu.ac.id", "Regular user account for testing");
        UserTable.save(regularUser);
        System.out.println("[Init] Default USER account created: user / user123");
    }

    public static void seedProduct(){
        User user = UserTable.findByUsername("user");
        Category ctg = new Category(1, "Test");

        ItemTable.create(new Item("1", "1 Dollar", "Selembar 1 Dollar Amerika", 18190, user, "1", ctg));

        ItemTable.create(new Item("2", "Scar Megalodon", "Senjata Legendaris Epep Papale Papale", 2000000, user, "2", ctg));

        ItemTable.create(new Item("3", "Chiaki-chan >//<", "Chiaki-chan wangy wangy >//<", Integer.MAX_VALUE, user, "3", ctg));

        ItemTable.create(new Item("4", "Ahegao Hoodie", "Tambahkan aura mu +999999 dengan hoodie ini!", 250000, user, "4", ctg));

        ItemTable.create(new Item("5", "Rudal Jawa", "Mas Ruddi Ular Sawah ingin menemuimu!", 1, user, "5", ctg));

        ItemTable.create(new Item("6", "Suara Rakyat", "Ingin menang pemilu? Beli sekarang!", 50000, user, "6", ctg));

        ItemTable.create(new Item("7", "Assasin Cina", "Assasin yang mempelajari ilmu pembunuhan rahasia dari Cina", 200000, user, "7", ctg));

        ItemTable.create(new Item("8", "Gitar Miku", "Gitar berbentuk Miku", 15000000, user, "8", ctg));

        ItemTable.create(new Item("9", "Ulet Bulu Inggris", "Ulet bulu Inggris asal dari Yorkshire, bisa menangani masalah2 medis", 3000000, user, "9", ctg));

        ItemTable.create(new Item("10", "Bass Fretless", "Bass Fretless model JazzMaster", 12500000, user, "10", ctg));

        ItemTable.create(new Item("11", "Kuda Balap Putih", "Kuda balap warna putih, bisa melakukan moonwalk seperti Michael Jackson", 200000000, user, "11", ctg));
    }

    public static void seedAd(){
        User user = UserTable.findByUsername("user");

        AdTable.create(new Ad("1", "Alien Spray", "Keep extraterrestrial pests off your lawn! Formula certified to repel Greys, Reptilians, and that one weird neighbor. 100% tinfoil-safe.", "http://www.totallynotascam.net", user));

        AdTable.create(new Ad("2", "Lost Inca Gold Map", "X marks the spot! A totally authentic, absolutely unique map to untold riches. (Disclaimer: Map may resemble a coffee-stained napkin).", "http://www.100percentrealmapnodupes.com", user));

        AdTable.create(new Ad("3", "Mind Control Blocker", "Block government frequencies and late-night psychic invasions. Lightweight, aerodynamic, and fits perfectly under a standard fedora.", "http://www.antigovernmentbrainshield.info", user));

        AdTable.create(new Ad("4", "Age Reversal Pill", "Turn back the clock! Side effects may include sudden urges to watch 90s cartoons, loss of permanent teeth, and temporary toddler tantrums.", "http://www.legitmedicalmiraclecapsules.co", user));
        
        AdTable.create(new Ad("5", "Mysttal Energy Healing", "Premium, ethically sourced divorce rocks. Imbued with the cosmic power of high alimony and fresh starts. Shiny finish guaranteed.", "http://www.buy-my-shiny-divorce-rocks.net", user));

        AdTable.create(new Ad("6", "Instant Psychic Training", "Unlock your third eye in under five minutes! We already know you're going to buy this, because we're psychic. And soon, you will be too.", "http://www.instant-third-eye-activation.info", user));

        AdTable.create(new Ad("7", "Government Conspiracy Documents", "The truth they don't want you to know, printed on highly confidential, easily downloadable PDFs. Warning: Redacted sharpie lines included.", "http://www.top-secret-truth-bombs.biz", user));

        AdTable.create(new Ad("8", "Invisible Cloak Blanket", "Perfect for introverts. Wrap yourself in this premium blanket and become entirely invisible to social obligations. (Results may vary in daylight).", "http://www.buy-invisibility-blankets.com", user));

        AdTable.create(new Ad("9", "Dream Manifestation Helmet", "A state-of-the-art brainwave booster that looks suspiciously like a kitchen colander. Manifest your dreams while draining pasta!", "http://www.strainer-brain-wave-booster.com", user));
    }
}
