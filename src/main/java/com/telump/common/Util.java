package com.telump.common;

import java.util.Scanner;

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
    public static String imageInput(String ImageName){
        System.out.print(ImageName);
        String inputUser = input.nextLine().trim();
    
        if (!inputUser.toLowerCase().endsWith(".png")) {
            inputUser += ".png";
        }
        
        return inputUser;
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
}
