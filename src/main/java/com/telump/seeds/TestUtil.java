package com.telump.seeds;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

import com.telump.common.ItemType;

public class TestUtil {
    private static final Map<ItemType, Integer> imageCounters = new HashMap<>();

    static {
        imageCounters.put(ItemType.Ad, 1);
        imageCounters.put(ItemType.Item, 1);
    }

    public static String handleImageUploadTest(String id, ItemType type) {
        String storePath = null;
        String seedPathPrefix = null;

        if (type == ItemType.Ad) {
            storePath = "com/telump/asset/ad/";
            seedPathPrefix = "com/telump/seeds/testImages/ad/";
        } else if (type == ItemType.Item) {
            storePath = "com/telump/asset/listing/";
            seedPathPrefix = "com/telump/seeds/testImages/item/";
        }

        if (storePath == null) {
            return null;
        }

        int currentSeedIndex = imageCounters.get(type);
        String extension = ".png";
        Path sourcePath = Paths.get(seedPathPrefix + currentSeedIndex + extension);

        if (!Files.exists(sourcePath) || Files.isDirectory(sourcePath)) {
            return null;
        }

        String fullNewName = id + extension;
        try {
            Path targetDir = Paths.get(storePath);
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
            }

            Path destinationPath = targetDir.resolve(fullNewName);
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            
            imageCounters.put(type, currentSeedIndex + 1);

        } catch (IOException e) {
            return null;
        }

        return storePath + fullNewName;
    }
}
