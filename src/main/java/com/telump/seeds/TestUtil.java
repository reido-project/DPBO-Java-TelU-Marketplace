package com.telump.seeds;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
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
}