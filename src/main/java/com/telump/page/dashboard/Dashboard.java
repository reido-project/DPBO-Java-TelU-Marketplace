package com.telump.page.dashboard;

import java.util.List;
import java.util.Set;

import com.telump.AppController;
import com.telump.catalog.Ad;
import com.telump.catalog.CatalogItem;
import com.telump.catalog.Item;
import com.telump.common.AuthenticationService;
import com.telump.common.SearchService;
import com.telump.common.State;
import com.telump.common.Util;
import com.telump.database.AdTable;
import com.telump.database.ItemTable;
import com.telump.database.ReviewTable;
import com.telump.database.UserTable;
import com.telump.page.AppInfo;
import com.telump.page.catalog.Catalog;
import com.telump.page.profile.Profile;
import com.telump.page.profile.UpdateAd;
import com.telump.page.profile.UpdateItem;
import com.telump.page.profile.UpdateProfile;
import com.telump.review.Review;
import com.telump.user.User;

public class Dashboard {
    public AppInfo dashboard(){
        String errorMessage = "";
        State appState = State.Dashboard;

        while(true){
            CatalogItem selectedItem = null;

            System.out.printf("""
                1. See Catalog
                2. Search Item or User
                3. View Profile
                4. Edit Profile
                5. Sell Item
                6. Register Ad
                7. See Your Active Items
                8. See Your Active Ads
                9. Logout
                0. Exit
                %s
                """, errorMessage);
            switch(Util.integerInput("Pilih opsi: ")) {
                case 1:
                    selectedItem = openCatalog(SearchService.globalSearch(null));
                    printItemInfo(selectedItem, "Input y Untuk Keluar: ");
                    continue;
                case 2:
                    String query = Util.stringInput("Cari: ");
                    selectedItem = openCatalog(SearchService.globalSearch(query));
                    printItemInfo(selectedItem, "Input y Untuk Keluar: ");
                    continue;
                case 3:
                    Profile.showProfile();
                    continue;
                case 4:
                    UpdateProfile.showUpdateProfileMenu();
                    continue;
                case 5:
                    AppInfo registerItemInfo = new RegisterItem().registerItem();
                    errorMessage = registerItemInfo.getErrorMessage();
                    appState = registerItemInfo.getAppState();
                    continue;
                case 6:
                    AppInfo registerAdInfo = new RegisterAdPage().registerAd();
                    errorMessage = registerAdInfo.getErrorMessage();
                    appState = registerAdInfo.getAppState();
                    continue;
                case 7:
                    selectedItem = openCatalog(SearchService.searchItemsByUsername(AppController.getSession().getUsername()));
                    if(printItemInfo(selectedItem, "Edit Item? (y/n): ")){
                        UpdateItem.updateItem(selectedItem.getId());
                    }
                    continue;
                case 8:
                    selectedItem = openCatalog(SearchService.searchAdsByUsername(AppController.getSession().getUsername()));
                    if(printItemInfo(selectedItem, "Edit Ad? (y/n): ")){
                        UpdateAd.updateAd(selectedItem.getId());
                    }
                    continue;
                case 9:
                    // Logout user dan hapus sesi
                    AuthenticationService.logout();
                    errorMessage = "Logout berhasil!";
                    appState = State.Start;
                    break;
                case 0:
                    errorMessage = null;
                    appState = State.Exit;
                    break;
            }

            return new AppInfo(errorMessage, appState);
        }
    }

    private CatalogItem openCatalog(Set<CatalogItem> items){
        Catalog window = new Catalog(items);
        window.setVisible(true);

        while (window.isDisplayable()) {
            try {
                Thread.sleep(100);
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return window.getSelectedItem();
    }

    private boolean printItemInfo(CatalogItem catalogItem, String message){
        if(catalogItem == null){
            System.out.println("\nNo Item Was Selected.");
            return false;
        }

        switch(catalogItem.getType()){
            case Item:
                Item item = ItemTable.getItemById(catalogItem.getId());
                System.out.printf("""

                        ID: %s
                        Judul: %s
                        Deskripsi: %s
                        Harga: %s
                        Kategori: %s
                        Penjual: @%s

                        """, item.getId(), item.getTitle(), item.getDescription(), item.getFormattedPrice(), item.getCategory().getName(), item.getSeller().getUsername());
                return Util.confirmation(message);
            case Ad:
                Ad ad = AdTable.getAdById(catalogItem.getId());
                System.out.printf("""
                        
                        ID: %s
                        Judul: %s
                        Deskripsi: %s
                        Pelajari Lebih Lanjut: %s
                        Pengiklan: @%s

                        """, ad.getId(), ad.getTitle(), ad.getDescription(), ad.getTargetUrl(), ad.getRegistrant().getUsername());
                return Util.confirmation(message);
            case User:
                User user = UserTable.findByUsername(catalogItem.getId());
                
                // Hitung rating rata-rata
                double avgRating = ReviewTable.getAverageRating(user.getUsername());
                List<Review> reviews = ReviewTable.getByTargetUsername(user.getUsername());
                
                System.out.printf("""
                        
                        Username: @%s
                        Name: %s
                        Bio: %s
                        Kontak: %s
                        Role: %s
                        Rating: %.1f/5.0 (%d review)

                        """, user.getUsername(), user.getProfilname(), user.getBio(), user.getKontak(), 
                        String.valueOf(user.getRole()), avgRating, reviews.size());
                
                // Tampilkan review (maksimal 5 terbaru)
                displayReviewList(user.getUsername());
                
                // Tanyakan apakah ingin memberi review (hanya jika user sedang login dan bukan diri sendiri)
                User currentUser = AuthenticationService.getCurrentUser();
                if (currentUser != null && !currentUser.getUsername().equals(user.getUsername())) {
                    if (Util.confirmation("Berikan review ke user ini? (y/n): ")) {
                        leaveReview(user.getUsername());
                    }
                }
                
                return Util.confirmation(message);
            default:
                System.out.println("\nNo Item Was Selected.");
                return false;
        }
    }
    
    /**
     * Method untuk input dan simpan review ke user tertentu
     * @param targetUsername username user yang akan di-review
     */
    private void leaveReview(String targetUsername) {
        User currentUser = AuthenticationService.getCurrentUser();
        
        // Validasi: cek apakah reviewer sudah pernah review user ini sebelumnya
        Review existingReview = ReviewTable.findReviewByReviewerAndTarget(currentUser.getUsername(), targetUsername);
        
        if (existingReview != null) {
            System.out.println("\nAnda sudah pernah review user ini sebelumnya.");
            System.out.printf("Review lama: ★%d - %s%n", existingReview.getRating(), existingReview.getComment());
            
            if (Util.confirmation("Apakah ingin update review? (y/n): ")) {
                // Update review
                updateExistingReview(existingReview);
            }
            return;
        }
        
        // Input rating
        System.out.println("\n--- Form Beri Review ---");
        int rating = 0;
        while (rating < 1 || rating > 5) {
            rating = Util.integerInput("Rating (1-5): ");
            if (rating < 1 || rating > 5) {
                System.out.println("Rating harus antara 1-5!");
            }
        }
        
        // Input komentar
        String comment = "";
        while (comment.trim().isEmpty()) {
            comment = Util.stringInput("Komentar (tidak boleh kosong): ");
            if (comment.trim().isEmpty()) {
                System.out.println("Komentar tidak boleh kosong!");
            }
        }
        
        // Simpan review baru
        new Review(currentUser.getUsername(), targetUsername, comment, rating);
        System.out.println("\n✓ Review berhasil disimpan!");
        
        // Tampilkan ulang info user dengan review yang sudah ter-updated
        User updatedUser = UserTable.findByUsername(targetUsername);
        double newAvgRating = ReviewTable.getAverageRating(updatedUser.getUsername());
        List<Review> updatedReviews = ReviewTable.getByTargetUsername(updatedUser.getUsername());
        
        System.out.printf("""
                
                Rating terbaru: %.1f/5.0 (%d review)

                """, newAvgRating, updatedReviews.size());
        
        // Tampilkan review list yang sudah ter-update
        displayReviewList(targetUsername);
    }
    
    /**
     * Method untuk update review yang sudah ada
     * @param existingReview review yang akan diupdate
     */
    private void updateExistingReview(Review existingReview) {
        System.out.println("\n--- Update Review ---");
        
        // Input rating baru
        int newRating = 0;
        while (newRating < 1 || newRating > 5) {
            newRating = Util.integerInput("Rating baru (1-5): ");
            if (newRating < 1 || newRating > 5) {
                System.out.println("Rating harus antara 1-5!");
            }
        }
        
        // Input komentar baru
        String newComment = "";
        while (newComment.trim().isEmpty()) {
            newComment = Util.stringInput("Komentar baru (tidak boleh kosong): ");
            if (newComment.trim().isEmpty()) {
                System.out.println("Komentar tidak boleh kosong!");
            }
        }
        
        // Update review dengan setter
        existingReview.setRating(newRating);
        existingReview.setComment(newComment);
        existingReview.setCreatedAt(java.time.LocalDateTime.now());
        ReviewTable.update(existingReview);
        
        System.out.println("\n✓ Review berhasil diupdate!");
        
        // Tampilkan rating terbaru
        double newAvgRating = ReviewTable.getAverageRating(existingReview.getTargetUsername());
        List<Review> updatedReviews = ReviewTable.getByTargetUsername(existingReview.getTargetUsername());
        
        System.out.printf("""
                
                Rating terbaru: %.1f/5.0 (%d review)

                """, newAvgRating, updatedReviews.size());
        
        // Tampilkan review list yang sudah ter-update
        displayReviewList(existingReview.getTargetUsername());
    }
    
    /**
     * Method helper untuk menampilkan review list user
     * @param targetUsername username user yang akan ditampilkan reviewnya
     */
    private void displayReviewList(String targetUsername) {
        List<Review> reviews = ReviewTable.getByTargetUsername(targetUsername);
        
        if (reviews.isEmpty()) {
            System.out.println("Belum ada review untuk user ini.\n");
        } else {
            System.out.println("=== Review Terbaru ===");
            
            // Tampilkan dari belakang (terbaru dulu), maksimal 5
            int startIdx = Math.max(0, reviews.size() - 5);
            for (int i = reviews.size() - 1; i >= startIdx; i--) {
                Review r = reviews.get(i);
                System.out.printf("  ★%d | @%s: %s (%s)%n", 
                    r.getRating(), r.getReviewer(), r.getComment(), r.getCreatedAt());
            }
            System.out.println();
        }
    }
}
