package com.telump.seeds;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.Map;

import com.telump.common.ItemType;

public final class TestUtil {

    private static final Map<ItemType, Integer> imageCounters =
            new EnumMap<>(ItemType.class);

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

        String fileName = currentIndex + ".png";
        String resourcePath = resourceFolder + fileName;

        try (InputStream in = TestUtil.class.getClassLoader().getResourceAsStream(resourcePath)) {

            if (in == null) {
                return null;
            }

            String projectRoot = System.getProperty("user.dir");
            
            Path targetDir = Paths.get(projectRoot, "src", "main", "java", targetFolderSubPath);

            Files.createDirectories(targetDir);

            Path destination = targetDir.resolve(id + ".png");

            Files.copy(
                    in,
                    destination,
                    StandardCopyOption.REPLACE_EXISTING);

            imageCounters.put(type, currentIndex + 1);

            return targetFolderSubPath + id + ".png";

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
}