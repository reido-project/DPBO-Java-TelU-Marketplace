package com.telump.seeds;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.telump.common.ItemType;
import com.telump.database.UserTable;
import com.telump.user.Admin;
import com.telump.user.User;

public final class TestUtil {

    private static final Map<ItemType, Integer> imageCounters = new EnumMap<>(ItemType.class);

    static {
        imageCounters.put(ItemType.Ad, 1);
        imageCounters.put(ItemType.Item, 1);
    }

    private TestUtil() {
        
    }

    public static String handleImageUploadTest(String id, ItemType type) {
        String resourceFolder;
        String targetFolderSubPath;

        switch (type) {
            case Ad:
                resourceFolder = "com/telump/seeds/testImages/ad/";
                targetFolderSubPath = "com/telump/asset/ad/";
                break;

            case Item:
                resourceFolder = "com/telump/seeds/testImages/item/";
                targetFolderSubPath = "com/telump/asset/listing/";
                break;

            default:
                return null;
        }

        int currentIndex = imageCounters.getOrDefault(type, 1);
        
        String[] supportedExtensions = {".png", ".jpg", ".jpeg"};
        InputStream in = null;
        String detectedExtension = null;

        for (String ext : supportedExtensions) {
            String testPath = resourceFolder + currentIndex + ext;
            in = TestUtil.class.getClassLoader().getResourceAsStream(testPath);
            if (in != null) {
                detectedExtension = ext;
                break;
            }
        }

        if (in == null) {
            return null;
        }

        try (InputStream autocloseIn = in) {

            String projectRoot = System.getProperty("user.dir");
            Path targetDir = Paths.get(projectRoot, "src", "main", "java", targetFolderSubPath);

            Files.createDirectories(targetDir);

            Path destination = targetDir.resolve(id + detectedExtension);

            Files.copy(
                    autocloseIn,
                    destination,
                    StandardCopyOption.REPLACE_EXISTING);

            imageCounters.put(type, currentIndex + 1);

            return targetFolderSubPath + id + detectedExtension;

        } catch (IOException e) {
            return null;
        }
    }

    public static void cleanAsset(String mavenRoot) throws IOException {
        Path[] targets = {
            Paths.get(mavenRoot, "src/main/java/com/telump/asset/ad"),
            Paths.get(mavenRoot, "src/main/java/com/telump/asset/listing")
        };

        for (Path target : targets) {
            if (Files.exists(target)) {
                Files.walk(target)
                     .filter(path -> !path.equals(target))
                     .sorted(Comparator.reverseOrder())
                     .forEach(path -> {
                         try {
                             Files.delete(path);
                         } catch (IOException ignored) {
                         }
                     });
            }
        }
    }

    public static List<ReviewPair> generateReviewPairs() {
        List<ReviewPair> pairs = new ArrayList<>();
        List<User> users = UserTable.getAll();

        for (User reviewer : users) {
            if (reviewer instanceof Admin)
                continue;

            if (reviewer.getUsername().equals("admin"))
                continue;

            if (reviewer.getUsername().equals("user"))
                continue;

            for (User target : users) {

                if (target instanceof Admin)
                    continue;

                if (target.getUsername().equals("admin"))
                    continue;

                if (target.getUsername().equals("user"))
                    continue;

                if (reviewer.equals(target))
                    continue;

                pairs.add(
                    new ReviewPair(
                        reviewer.getUsername(),
                        target.getUsername()
                    )
                );
            }
        }

        Collections.shuffle(pairs);

        return pairs;
    }
}