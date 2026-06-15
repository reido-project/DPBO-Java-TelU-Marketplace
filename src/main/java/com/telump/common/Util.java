package com.telump.common;

import java.util.Scanner;
import java.util.UUID;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.NumberFormat;
import java.util.Locale;

public class Util {
    private static final Scanner input = new Scanner(System.in);

    public static int integerInput(String message){
        while(true){
            System.out.print(message);
            String stringOutput = input.nextLine();

            try{
                return Integer.parseInt(stringOutput);
            }catch(Exception e){
                System.out.println("[Error] Mohon masukkan angka.");
            }
        }
    }

    public static String stringInput(String message){
        System.out.print(message);
        return input.nextLine();
    }

    public static boolean confirmation(String message){
        while(true){
            String confirmation = stringInput(message).trim().toLowerCase();
            if(confirmation.startsWith("y")){
                return true;
            }
            else if(confirmation.startsWith("n")){
                return false;
            }
        }
    }

    /**
     * Input password dari user (untuk update profile)
     * @param message pesan yang ditampilkan
     * @return string yang diinput user
     */
    public static String passwordInput(String message){
        System.out.print(message);
        return input.nextLine();
    }

    public static String formatPrice(int price){
        Locale localeID = new Locale("id", "ID");
        
        NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(localeID);
        
        return rupiahFormat.format(price);
    }

    public static String getExtension(String imageName){
        int lastIndexOfDot = imageName.lastIndexOf(".");
        if (lastIndexOfDot == -1) {
            return "";
        }
        return imageName.substring(lastIndexOfDot);
    }

    public static String handleImageUpload(String id, ItemType type){ 
        String storePath = null;

        if(type == ItemType.Ad){
            storePath = "com/telump/asset/ad/";
        }
        else if(type == ItemType.Item){
            storePath = "com/telump/asset/listing/";
        }

        if (storePath == null){
            return null;
        }

        String userInput = stringInput("Enter Image Directory: ").trim();
        if (userInput.startsWith("\"") && userInput.endsWith("\"")) {
            userInput = userInput.substring(1, userInput.length() - 1);
        }

        Path sourcePath = Paths.get(userInput);

        if (!Files.exists(sourcePath) || Files.isDirectory(sourcePath)) {
            System.out.println("Error: Invalid file path. Please ensure it is a file, not a folder.");
            return null;
        }

        String extension = getExtension(sourcePath.getFileName().toString());
        String fullNewName = id + extension;

        try {
            Path targetDir = Paths.get(storePath);
            if (!Files.exists(targetDir)) {
                Files.createDirectories(targetDir);
            }

            Path destinationPath = targetDir.resolve(fullNewName);
            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("\nSuccess!");
        } catch (IOException e) {
            System.out.println("An error occurred during the copy operation: " + e.getMessage());
            return null;
        }

        return storePath + fullNewName; 
    }

    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }
}
