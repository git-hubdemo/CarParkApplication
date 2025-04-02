package org.carpark.util;

import java.util.Scanner;

public class InputValidator {

    private static Scanner scanner = new Scanner(System.in);


    public static String getYesOrNoInput(String prompt) {
        System.out.println(prompt);
        String input = scanner.nextLine();
        while(true){
            if(input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("no")){
                return input;
            }
            System.out.println("Please enter either yes or no");
            input = scanner.nextLine(); //Take the user input again
        }
    }

    public static int validateNumber(String input){

        int number = -1;
        boolean isValidNumber = false;

        while(!isValidNumber){
            try{
                number = Integer.parseInt(input);
                isValidNumber = true;

            }catch (NumberFormatException e){
                System.out.println("Invalid number! try again");
                input = scanner.nextLine();
            }
        }
        return number;
    }

    public static int getNumberInput(String prompt){
        System.out.println(prompt);
        return validateNumber(scanner.nextLine());

    }

    public static String getStringInput(String prompt){
        System.out.println(prompt);
        while(true){
            String input = scanner.nextLine();
            if(!input.equalsIgnoreCase("")){
                return input;
            }
            System.out.println("Please enter a value>");

        }
    }
}
