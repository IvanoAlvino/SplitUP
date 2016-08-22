package com.ivano.splitup;

import java.text.DecimalFormat;
import java.util.Scanner;

class IOManager {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * Read a string from stdin
     * @return The read string
     */
    static String readString() {
        return scanner.next();
    }

    /**
     * Read a choice from the user and return the corresponding Menu enum constant.
     * @return The enum constant choice
     */
    static Menu getChoice() {
        Menu.displayChoiceMessage();
        String choice = scanner.next();
        return Menu.valueOf(choice.toUpperCase());
    }

    /**
     * Print the initial menu.
     */
    static void printMenu() {
        Menu.printMenu();
    }

    /**
     * This method will safely read a double amount of money.
     * In case something different from a double is introduced, ask again for a double.
     *
     * @return A Double representing an amount of money
     */
    static Double readDoubleAmount() {
        System.out.print("How much: ");
        while (!scanner.hasNextDouble()) {
            System.out.println("Please insert only digits");
            System.out.print("How much: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    /**
     * Print the results for every user in {@link UserManager#listUsers}.
     * The value in euro is rounded to two decimals.
     */
    static void printResults() {
        DecimalFormat f = new DecimalFormat("##.00");
        for (User u : UserManager.getListUsers()) {
            System.out.println(u.toString() + " : " + f.format(u.getResult()));
        }
    }

    /**
     * Print a provided message in a formatted way.
     * @param message The mesage to print
     */
    static void printStatusMessage(String message) {
        System.out.print("-- ");
        System.out.print(message);
        System.out.println(" --");
    }

}