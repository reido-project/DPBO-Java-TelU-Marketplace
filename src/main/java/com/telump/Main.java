package com.telump;

import java.util.List;

import com.telump.catalog.Ad;
import com.telump.catalog.Item;
import com.telump.database.AdTable;
import com.telump.database.CategoryTable;
import com.telump.database.ItemTable;
import com.telump.database.UserTable;
import com.telump.seeds.AdSeeds;
import com.telump.seeds.CategorySeeds;
import com.telump.seeds.ReviewSeeds;
import com.telump.seeds.ItemSeeds;
import com.telump.seeds.ReviewPair;
import com.telump.seeds.TestUtil;
import com.telump.user.Admin;
import com.telump.user.RegularUser;
import com.telump.user.User;
import com.telump.category.*;
import com.telump.review.*;
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
        seedReview();
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
            new Admin("ambatukam", "leleAmbaGacor123", "Mas Amba", "masambatukam@student.telkomuniversity.ac.id", "Kumis tipis, sungut lele merintis."),
            new Admin("masfaiz", "leleDombaGoyang", "Mas Faiz", "masfaiz@student.telkomuniversity.ac.id", "Bukan sembarang lele, ini lele premium."),
            new Admin("mashambali", "kumisLeleHambali99", "Mas Hambali", "mashambali@student.telkomuniversity.ac.id", "Kapten sungut lele regional Bojongsoang."),
            new RegularUser("masrozik", "umpanCacingSutra", "Mas Rozik", "08134567890", "Hati-hati ketusuk patil lele."),
            new RegularUser("ruddiularsawah", "sungutLeleSawah45", "Mas Ruddi Ular Sawah", "08126942067", "Sawah boleh becek, sungut lele tetep pede."),
            new RegularUser("satriosulsel", "sungutGetarMaut", "Mas Satrio", "08571234567", "Mancing mania mantap, sungut lele siap."),
            new RegularUser("masfuad", "pecelLeleGarisKeras", "Mas Fu'ad", "masfuad@student.telkomuniversity.ac.id", "Pecinta pecel lele tanpa kubis."),
            new RegularUser("rehanwangsaf", "kumisBapackLele", "Mas Rehan", "08998765432", "Sungut lele adalah jalan ninjaku."),
            new RegularUser("masrusdi", "rusdiGacorNgawi", "Mas Rusdi", "masrusdi@student.telkomuniversity.ac.id", "Sepuh pangkas rambut Ngawi, pelopor tren sungut lele sejagat raya."),
            new RegularUser("ironi", "ironiGoyangAmba", "Mr. Ironi", "ironi@student.telkomuniversity.ac.id", "Misteri kegelapan Bojongsoang, sungut lele bergetar melihat masa depan."),
            new RegularUser("gatot", "gatotKacaPatil", "Mas Gatot", "gatot@student.telkomuniversity.ac.id", "Otot kawat tulang besi, sungut lele siap membelah badai."),
            new RegularUser("narji", "narjiCagurLele", "Mas Narji", "narji@student.telkomuniversity.ac.id", "Melawak lewat getaran sungut, bukan lewat kata-kata."),
            new RegularUser("azril", "azrilSutradaraGhoib", "Mas Azril", "azrilaz@student.telkomuniversity.ac.id", "Dokumentator resmi ritual pemanggilan lele raksasa Situ Tekno."),
            new RegularUser("nasir", "nasirPecelMaut", "Mas Nasir", "nasir@student.telkomuniversity.ac.id", "Sungut lele meliuk-liuk, tanda pesanan pecel lele sudah matang."),
            new RegularUser("rian", "rianGoyangKarawang", "Mas Rian", "rian@student.telkomuniversity.ac.id", "Sungut lele minimalis, tapi daya sengat patilnya maksimal."),
            new RegularUser("ambatron", "ambatukanRobot99", "Ambatron", "ambatron@student.telkomuniversity.ac.id", "Cyborg buatan Ngawi dengan teknologi core bertenaga sungut lele nuklir."),
            new RegularUser("hardin", "hardinPawangSitu", "Mas Hardin", "hardin@student.telkomuniversity.ac.id", "Pawang lele raksasa Bojongsoang. Sekali siul, sungut langsung tegak."),
            new RegularUser("eko", "ekoPatrioGacor", "Mas Eko", "eko@student.telkomuniversity.ac.id", "Masuk Pak Eko! Sungut lele bergoyang, penonton pun tegang."),
            new RegularUser("ricardomilos", "milosGoyangSeksi", "Ricardo Milos", "ricardomilos@student.telkomuniversity.ac.id", "Duta sungut lele internasional. Sekali goyang, kolam langsung bergolak."),
            new RegularUser("supri", "supriGacorPol", "Mas Supri", "supri_lele@student.telkomuniversity.ac.id", "Kumis boleh bapack-bapack, tapi sungut lele tetap nomor satu.")
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
                13
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
            ),
            new ItemSeeds(
                "Setup Gaming",
                "Setup gaming kursi dan meja",
                6500000,
                10
            ),
            new ItemSeeds(
                "Indomie Rasa Kulit Manggis",
                "Indomie dengan rasa kulit manggis",
                12000,
                12
            ),
            new ItemSeeds(
                "Ryzen RTX 4070",
                "Kartu grafis dengan performa tinggi untuk gaming dan produktivitas",
                6500000,
                2
            ),
            new ItemSeeds(
                "Sertifikat Sigma Male",
                "Sertifikat yang menunjukkan keanggotaan di organisasi Sigma Male",
                450000,
                13
            ),
            new ItemSeeds(
                "Hairdryer Second Mulus",
                "Hairdryer dengan kualitas tinggi dan harga terjangkau",
                150000,
                2
            ),
            new ItemSeeds(
                "Samsung Note 20 Ultra 5G",
                "Smartphone dengan layar besar dan kamera tinggi",
                15000000,
                2
            ),
            new ItemSeeds(
                "Parfume Mekah",
                "Parfume dengan aroma khas Mekah",
                100000,
                3
            ),
            new ItemSeeds(
                "Catok Rambut 2 in 1",
                "Catok rambut dengan fitur 2 dalam 1",
                150000,
                2
            ),
            new ItemSeeds(
                "Kursi Kantor Ergonomis",
                "Kursi kantor yang ergonomis untuk kenyamanan maksimal",
                200000,
                10
            ),
            new ItemSeeds(
                "Paket Galon",
                "Paket 3 galon air minum",
                70000,
                10
            ),
            new ItemSeeds(
                "Vicks Inhaler",
                "Vicks Inhaler untuk meredam batuk dan pilek (baru beli)",
                15000,
                8
            ),
            new ItemSeeds(
                "Gitar Akustik Yamaha",
                "Gitar akustik Yamaha dengan kualitas tinggi (like new)",
                360000,
                13
            ),
            new ItemSeeds(
                "Parfum Velixir",
                "(Eau de parfume) Parfum Velixir dengan aroma yang tahan lama",
                450000,
                3
            ),
            new ItemSeeds(
                "Honda Civic 2010",
                "Mobil Honda Civic 2010 dengan kondisi baik",
                150000000,
                4
            ),
            new ItemSeeds(
                "Helmet Full Face",
                "Helmet full face untuk keamanan berkendara",
                500000,
                4
            ),
            new ItemSeeds(
                "Keyboard Mechanical VortexSeries",
                "Keyboard mekanik dengan performa tinggi untuk gaming",
                600000,
                2
            ),
            new ItemSeeds(
                "Microphone Condenser Maono",
                "Microphone condenser dengan kualitas suara tinggi",
                1000000,
                2
            ),
            new ItemSeeds(
                "Sepatu Nike Air Max",
                "Sepatu olahraga dengan teknologi air max untuk kenyamanan maksimal",
                2000000,
                3
            ),
            new ItemSeeds(
                "Asus TUF F15",
                "Intel Core i7, RAM 16GB, SSD 512GB, RTX 3060, layar 144Hz",
                13000000,
                2
            ),
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
            new CategorySeeds(12, "Makanan & Minuman"),
            new CategorySeeds(13, "Lainnya")
        };

        for (CategorySeeds categorySeed : categories) {
            if (CategoryTable.getCategoryById(categorySeed.getId()) == null) {
                new Category(categorySeed.getId(), categorySeed.getName());
            }
        }
    }

    private static void seedReview() {
        ReviewSeeds[] reviews = {
            new ReviewSeeds("Deal cepet gak pake rapat koordinasi segala.", 5),
            new ReviewSeeds("Salah satu user paling waras yg pernah gw temuin.", 5),
            new ReviewSeeds("Transaksi mulus kek jalanan kosong tengah malem.", 5),
            new ReviewSeeds("Dateng pas sesuai lokasi, gak nyasar ke server lain.", 5),
            new ReviewSeeds("Gak perlu negosiasi alot tingkat PBB.", 5),
            new ReviewSeeds("Fast respon, admin grup pasti bangga.", 5),
            new ReviewSeeds("COD sukses gak pake bikin grup panitia segala.", 5),
            new ReviewSeeds("Gak perlu nge-spam 'jadi kagak mas?' berkali-kali.", 5),
            new ReviewSeeds("Transaksi lancar, server kehidupan stabil.", 5),
            new ReviewSeeds("Transaksi kelar sebelum sempet overthinking.", 5),
            new ReviewSeeds("Koordinasi lancar, gak kek nyari party ranked.", 5),
            new ReviewSeeds("Moga rejekinya selancar proses COD tadi.", 5),
            new ReviewSeeds("Lebih on-time drpd temen-temen gw.", 5),
            new ReviewSeeds("Proses transaksinya sat-set parah.", 5),
            new ReviewSeeds("Kalo semua user begini mah hidup lebih damai.", 5),
            new ReviewSeeds("Gak PHP, langsung gas transaksi.", 5),
            new ReviewSeeds("Nego masuk akal, gak bikin sakit hati kedua pihak.", 5),
            new ReviewSeeds("Dateng tepat waktu, fenomena langka nih.", 5),
            new ReviewSeeds("COD sesuai janjian, mantap betul.", 5),
            new ReviewSeeds("Awalnya waswas, jebulnya orgnya baek bgt.", 5),
            new ReviewSeeds("Orgnya amanah, transaksi lancar jaya.", 5),
            new ReviewSeeds("Fast respon, diajak ngobrol juga asik.", 5),
            new ReviewSeeds("Dateng duluan sebelum gw nyampe.", 5),
            new ReviewSeeds("Mantap, kelar dlm hitungan menit.", 5),
            new ReviewSeeds("Gak banyak drama, langsung deal.", 5),
            new ReviewSeeds("User recommended parah.", 5),
            new ReviewSeeds("Gak ngeghosting, dapet nilai plus gede.", 5),
            new ReviewSeeds("Orgnya chill, transaksi nyaman bgt.", 5),
            new ReviewSeeds("Gak main cancel sepihak, salut.", 5),
            new ReviewSeeds("Enak diajak transaksi, gak ribet sama sekali.", 5),

            new ReviewSeeds("Balesnya agak slow dikit, tp tetep amanah kok.", 4),
            new ReviewSeeds("Transaksi lancar, cuma sempet nyasar dikit grg maps.", 4),
            new ReviewSeeds("Overall oke dan gampang dikontak.", 4),
            new ReviewSeeds("Nego lumayan alot, tp tetep sopan.", 4),
            new ReviewSeeds("Ngaret dikit, tp seenggaknya ngabarin.", 4),
            new ReviewSeeds("Balesnya rada lambat, tp tetep tanggung jawab.", 4),
            new ReviewSeeds("Sempet mundur jadwal, untung lgsg ngabarin.", 4),
            new ReviewSeeds("Transaksi oke, cuma nyari lokasi agak lama dikit.", 4),
            new ReviewSeeds("Overall memuaskan lah buat diajak deal.", 4),
            new ReviewSeeds("Ngaret dikit tp tetep dateng kok.", 4),
            new ReviewSeeds("Komunikasi lumayan oke pas proses COD.", 4),
            new ReviewSeeds("Nego mayan alot, tp tetep santun kok.", 4),
            new ReviewSeeds("Ada miskom dikit, tp beres dlm damai.", 4),
            new ReviewSeeds("Butuh di-ping sekali dua kali, selebihnya aman.", 4),
            new ReviewSeeds("Gak sempurna-sempurna bgt, tp tetep recommended.", 4),
            new ReviewSeeds("Otw-nya beneran otw, walau nunggunya bikin haus.", 4),
            new ReviewSeeds("Orgnya baek, cuma pas chat berasa balesnya pake mikir dulu.", 4),
            new ReviewSeeds("Ketemuan lancar, walau titik COD digeser dikit.", 4),
            new ReviewSeeds("Agak slow respon pas malem, tp siang langsung gercep.", 4),
            new ReviewSeeds("Nego agak ketat kek ikat pinggang kekecilan, tp tetep deal.", 4),
            new ReviewSeeds("Nunggu sekitar 10 menit, untung orgnya ramah pas ketemu.", 4),
            new ReviewSeeds("Chat lancar, pas COD agak canggung tp semua aman.", 4),
            new ReviewSeeds("Lokasi ketemuan agak terpencil, tp transaksi sukses.", 4),
            new ReviewSeeds("Respon lumayan, transaksi gak pake drama berkepanjangan.", 4),
            new ReviewSeeds("Pelayanan oke, cuma balesnya irit kata bgt.", 4),
            new ReviewSeeds("Jam COD meleset dikit dr estimasi, overall aman.", 4),
            new ReviewSeeds("Udah deal, pas ketemu masih ditanya-tanya dikit tp ttp lancar.", 4),
            new ReviewSeeds("Komunikasi lancar jaya walau rada malem responnya.", 4),
            new ReviewSeeds("Recommended lah, walau kudu sabar nunggu balesan chat.", 4),

            new ReviewSeeds("Transaksi gol walau sempet gonta-ganti jadwal.", 3),
            new ReviewSeeds("Orgnya baek, cuma bales chatnya suka nunggu bertapa dulu.", 3),
            new ReviewSeeds("Deal sih jadi, cuma kudu modal sabar dikit.", 3),
            new ReviewSeeds("Masih oke lah buat diajak transaksi lagi.", 3),
            new ReviewSeeds("Bales chatnya bisa dicepetin lagi kali ya.", 3),
            new ReviewSeeds("Ngaretnya lumayan, untung barangnya sesuai ekspektasi.", 3),
            new ReviewSeeds("Komunikasi agak seret, untung gak sampe batal.", 3),
            new ReviewSeeds("Balesnya nunggu ganti hari, bikin jantungan.", 3),
            new ReviewSeeds("COD jadi, tp nunggu di lokasi berasa nunggu jodoh, lama bgt.", 3),
            new ReviewSeeds("Nego alot bgt kek ban fuso, untung nemu jalan tengah.", 3),
            new ReviewSeeds("Bales chat irit bgt kek lagi hemat kuota.", 3),
            new ReviewSeeds("Ditinggal tidur pas lagi seru-serunya nego, tp bsknya kelar.", 3),
            new ReviewSeeds("Janji jam 2 dateng jam 3, untung ngabarin walau telat.", 3),
            new ReviewSeeds("Transaksi sukses walau penuh dgn drama miskomunikasi.", 3),
            new ReviewSeeds("Responnya lemot kek hp ram 1gb, tp untung amanah.", 3),
            new ReviewSeeds("Kurang sat-set, kebanyakan mikir pas diajak deal.", 3),
            new ReviewSeeds("Udah nyampe lokasi baru dikasih tau telat, rada kesel.", 3),
            new ReviewSeeds("Balesnya nunggu di-P berkali-kali, rada bikin emosi.", 3),
            new ReviewSeeds("Biasa aja, proses transaksinya bertele-tele.", 3),

            new ReviewSeeds("Bales chatnya seharian sekali, dikira minum obat apa.", 2),
            new ReviewSeeds("Ngaret parah gak ada kabar, pas ditanya baru bales otw.", 2),
            new ReviewSeeds("Udah nunggu sejam di lokasi, pas dateng gak ngerasa bersalah.", 2),
            new ReviewSeeds("Nego sadis bgt pas ketemu, padahal di chat udh deal.", 2),
            new ReviewSeeds("Komunikasi buruk, bikin capek batin ngadepinnya.", 2),
            new ReviewSeeds("Dighosting seharian, pas mau dibatalin baru muncul.", 2),
            new ReviewSeeds("Banyak alesan pas diajak ketemuan, bikin males.", 2),
            new ReviewSeeds("Gak tepat waktu, respon lambat, kapok transaksi.", 2),
            new ReviewSeeds("Barang ada minus dikit gak dibilang dr awal, untung ketauan.", 2),
            new ReviewSeeds("Nge-cancel sepihak pas pertama, pas kedua ngaret lagi.", 2),
            new ReviewSeeds("Chat cuma dibaca doang, balesnya pas mood aja kali.", 2),
            new ReviewSeeds("Gak niat jualan/beli keknya, slow respon parah.", 2),
            new ReviewSeeds("Ribet bgt nentuin lokasi, pas ketemuan malah telat.", 2),
            new ReviewSeeds("Ketikan di chat ketus bgt, pas ketemu juga cemberut.", 2),
            new ReviewSeeds("Nungguin dia COD berasa nunggu kepastian, digantung mulu.", 2),
            new ReviewSeeds("Bikin emosi, udh nunggu lama pas dateng malah buru-buru bgt.", 2),
            new ReviewSeeds("Suka ganti jadwal dadakan, gak konsisten bgt.", 2),
            new ReviewSeeds("Respon kek siput, bikin transaksi ketunda berhari-hari.", 2),
            new ReviewSeeds("Gak rekomend buat yg pengen transaksi cepet.", 2),
            new ReviewSeeds("Setengah niat setengah kagak, bikin buang waktu.", 2),

            new ReviewSeeds("Fix kapok, orangnya egois dan banyak drama.", 1),
            new ReviewSeeds("Udah di lokasi malah dighosting, untung gak nunggu seharian.", 1),
            new ReviewSeeds("Gak ada sopan santunnya, dichat kasar pas ketemu ngeselin.", 1),
            new ReviewSeeds("Nge-cancel dadakan pas gw udh otw, parah bgt.", 1),
            new ReviewSeeds("Ngerusak mood seharian, transaksi paling zonk.", 1),
            new ReviewSeeds("Udah ngaret 2 jam, pas ketemu malah minta diskon lagi.", 1),
            new ReviewSeeds("Komunikasi nol besar, gak usah transaksi ama org ini.", 1),
            new ReviewSeeds("PHP parah, bilang otw taunya ketiduran gak ada kabar.", 1),
            new ReviewSeeds("Mending cari user lain, yang ini bikin darah tinggi.", 1),
            new ReviewSeeds("Gak profesional sama sekali, kapok kuadrat.", 1),
            new ReviewSeeds("Barang gak sesuai omongan dah gitu orgnya gamau tanggung jawab bensin.", 1),
            new ReviewSeeds("Buang-buang bensin, waktu, dan energi. Sangat tidak rekomend.", 1),
        };

        List<ReviewPair> pairs = TestUtil.generateReviewPairs();

        for (int i = 0; i < reviews.length; i++) {
            ReviewPair pair = pairs.get(i);
            new Review(
                pair.getReviewer(),
                pair.getTarget(),
                reviews[i].getComment(),
                reviews[i].getRating()
            );
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
