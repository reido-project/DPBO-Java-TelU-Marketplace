package com.telump;

import com.telump.catalog.Ad;
import com.telump.catalog.Item;
import com.telump.database.AdTable;
import com.telump.database.CategoryTable;
import com.telump.database.ItemTable;
import com.telump.database.UserTable;
import com.telump.seeds.AdSeeds;
import com.telump.seeds.CategorySeeds;
import com.telump.seeds.ItemSeeds;
import com.telump.seeds.TestUtil;
import com.telump.user.Admin;
import com.telump.user.RegularUser;
import com.telump.user.User;
import com.telump.category.*;
import com.telump.common.ItemType;
import com.telump.common.Util;

public class Main {
    public static void main(String[] args) {
        init();

        AppController appController = new AppController();

        appController.appController();
    }
 
    private static void init(){ 
        try{
            TestUtil.cleanAsset("./");
        }catch(Exception e){
            System.out.println("Error cleaning up");
        }
        
        seedUser();
        seedCategory();
        seedItem();
        seedAd();
    }

    private static void seedUser() {
        User[] users = {
            new Admin(
                "admin", 
                "admin#1234", 
                "Administrator", 
                "admin@telu.ac.id", 
                "Admin account for Tel-U Marketplace"
            ),
            new RegularUser(
                "user", 
                "user123", 
                "Regular User", "user@student.telu.ac.id", 
                "Regular user account for testing"
            ),
            new Admin(
                "ambatukam", 
                "leleAmbaGacor123", 
                "Mas Amba", 
                "masambatukam@student.telkomuniversity.ac.id", 
                "Kumis tipis, sungut lele merintis."
            ),
            new Admin(
                "masfaiz", 
                "leleDombaGoyang", 
                "Mas Faiz", 
                "masfaiz@student.telkomuniversity.ac.id", 
                "Bukan sembarang lele, ini lele premium."
            ),
            new Admin(
                "mashambali", 
                "kumisLeleHambali99", 
                "Mas Hambali", 
                "mashambali@student.telkomuniversity.ac.id", 
                "Kapten sungut lele regional Bojongsoang."
            ),
            new RegularUser(
                "masrozik", 
                "umpanCacingSutra", 
                "Mas Rozik", 
                "08134567890", 
                "Hati-hati ketusuk patil lele."
            ),
            new RegularUser(
                "ruddiularsawah", 
                "sungutLeleSawah45", 
                "Mas Ruddi Ular Sawah", 
                "08126942067", 
                "Sawah boleh becek, sungut lele tetep pede."
            ),
            new RegularUser(
                "satriosulsel", 
                "sungutGetarMaut", 
                "Mas Satrio", 
                "08571234567", 
                "Mancing mania mantap, sungut lele siap."
            ),
            new RegularUser(
                "masfuad", 
                "pecelLeleGarisKeras", 
                "Mas Fu'ad", 
                "masfuad@student.telkomuniversity.ac.id", 
                "Pecinta pecel lele tanpa kubis."
            ),
            new RegularUser(
                "rehanwangsaf", 
                "kumisBapackLele", 
                "Mas Rehan", 
                "08998765432", 
                "Sungut lele adalah jalan ninjaku."
            )
        };

        for(User user : users){
            UserTable.save(user);
        }

        System.out.println("[Init] Default ADMIN account created: admin / admin#1234");
        System.out.println("[Init] Default USER account created: user / user123");
    }

    private static void seedItem() {
        ItemSeeds[] items = {
            new ItemSeeds(
                "1 Dollar", 
                "Selembar 1 Dollar Amerika", 
                18190,
                5
            ),
            new ItemSeeds(
                "Scar Megalodon", 
                "Senjata Legendaris Epep Papale Papale", 
                2000000,
                9
            ),
            new ItemSeeds(
                "Chiaki-chan >//<", 
                "Chiaki-chan wangy wangy >//<", 
                Integer.MAX_VALUE,
                11
            ),
            new ItemSeeds(
                "Ahegao Hoodie", 
                "Tambahkan aura mu +999999 dengan hoodie ini!", 
                250000,
                3
            ),
            new ItemSeeds(
                "Rudal Jawa", 
                "Mas Ruddi Ular Sawah ingin menemuimu!", 
                1,
                10
            ),
            new ItemSeeds(
                "Suara Rakyat", 
                "Ingin menang pemilu? Beli sekarang!", 
                50000,
                12
            ),
            new ItemSeeds(
                "Assasin Cina", 
                "Assasin yang mempelajari ilmu pembunuhan rahasia dari Cina, siap membantu kardiomu!", 
                200000,
                7
            ),
            new ItemSeeds(
                "Gitar Miku", 
                "Gitar berbentuk Miku", 
                15000000,
                2
            ),
            new ItemSeeds(
                "Ulet Bulu Inggris", 
                "Ulet bulu Inggris asal dari Yorkshire, bisa menangani masalah2 medis", 
                3000000,
                8
            ),
            new ItemSeeds(
                "Bass Fretless", 
                "Bass Fretless model JazzMaster", 
                12500000,
                2
            ),
            new ItemSeeds(
                "Kuda Balap Putih", 
                "Kuda balap warna putih, bisa melakukan moonwalk seperti Michael Jackson", 
                200000000,
                4
            )
        };

        for (ItemSeeds item : items) {
            String id = Util.generateUUID();
            String title = item.getTitle();
            String description = item.getDescription();
            int price = item.getPrice();
            User user = UserTable.getRandomUser();

            if(item.getTitle().equals("Rudal Jawa")){
                user = UserTable.findByUsername("ruddiularsawah");
            }

            Category ctg = CategoryTable.getCategoryById(item.getCategory());

            String imagePath = TestUtil.handleImageUploadTest(id, ItemType.Item); 

            ItemTable.create(new Item(id, title, description, price, user, ctg, imagePath));
        }
    }

    private static void seedCategory() {
        CategorySeeds[] categories = {
            new CategorySeeds(1, "Test"),
            new CategorySeeds(2, "Elektronik"),
            new CategorySeeds(3, "Fashion"),
            new CategorySeeds(4, "Kendaraan"),
            new CategorySeeds(5, "Hobi & Koleksi"),
            new CategorySeeds(6, "Rumah & Taman"),
            new CategorySeeds(7, "Olahraga & Outdoor"),
            new CategorySeeds(8, "Kesehatan & Kecantikan"),
            new CategorySeeds(9, "Mainan & Game"),
            new CategorySeeds(10, "Perlengkapan Rumah"),
            new CategorySeeds(11, "Souvenir & Oleh-oleh"),
            new CategorySeeds(12, "Lainnya")
        };

        for (CategorySeeds categorySeed : categories) {
            if (CategoryTable.getCategoryById(categorySeed.getId()) == null) {
                new Category(categorySeed.getId(), categorySeed.getName());
            }
        }
    }

    private static void seedAd(){
        AdSeeds[] ads = {
            new AdSeeds(
                "Tel-U Marketplace!",
                "Tel-U Marketplace, Platform JB Terbaik Untuk Mahasiswa Telkom University!",
                ""
            ),
            new AdSeeds(
                "Alien Spray", 
                "Keep extraterrestrial pests off your lawn! Formula certified to repel Greys, Reptilians, and that one weird neighbor. 100% tinfoil-safe.", 
                "http://www.totallynotascam.net"
            ),
            new AdSeeds(
                "Lost Inca Gold Map", 
                "X marks the spot! A totally authentic, absolutely unique map to untold riches. (Disclaimer: Map may resemble a coffee-stained napkin).", 
                "http://www.100percentrealmapnodupes.com"
            ),
            new AdSeeds(
                "Mind Control Blocker", 
                "Block government frequencies and late-night psychic invasions. Lightweight, aerodynamic, and fits perfectly under a standard fedora.", 
                "http://www.antigovernmentbrainshield.info"
            ),
            new AdSeeds(
                "Age Reversal Pill", 
                "Turn back the clock! Side effects may include sudden urges to watch 90s cartoons, loss of permanent teeth, and temporary toddler tantrums.", 
                "http://www.legitmedicalmiraclecapsules.co"
            ),
            new AdSeeds(
                "Mysttal Energy Healing", 
                "Premium, ethically sourced divorce rocks. Imbued with the cosmic power of high alimony and fresh starts. Shiny finish guaranteed.", 
                "http://www.buy-my-shiny-divorce-rocks.net"
            ),
            new AdSeeds(
                "Instant Psychic Training", 
                "Unlock your third eye in under five minutes! We already know you're going to buy this, because we're psychic. And soon, you will be too.", 
                "http://www.instant-third-eye-activation.info"
            ),
            new AdSeeds(
                "Government Conspiracy Documents", 
                "The truth they don't want you to know, printed on highly confidential, easily downloadable PDFs. Warning: Redacted sharpie lines included.", 
                "http://www.top-secret-truth-bombs.biz"
            ),
            new AdSeeds(
                "Invisible Cloak Blanket", 
                "Perfect for introverts. Wrap yourself in this premium blanket and become entirely invisible to social obligations. (Results may vary in daylight).", 
                "http://www.buy-invisibility-blankets.com"
            ),
            new AdSeeds(
                "Dream Manifestation Helmet", 
                "A state-of-the-art brainwave booster that looks suspiciously like a kitchen colander. Manifest your dreams while draining pasta!", 
                "http://www.strainer-brain-wave-booster.com"
            )
        };

        for(AdSeeds ad : ads){
            String id = Util.generateUUID();
            String title = ad.getTitle();
            String description = ad.getDescription();
            String targetUrl = ad.getTargetUrl();
        
            String imagePath = TestUtil.handleImageUploadTest(id, ItemType.Ad);
            
            AdTable.create(new Ad(id, title, description, targetUrl, UserTable.getRandomUser(), imagePath));
        }
    }
}
