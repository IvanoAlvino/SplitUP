package com.ivano.splitup;

import java.text.DecimalFormat;
import java.util.Scanner;

class IOManager {

    private static Scanner scanner = new Scanner(System.in);

    static String readString() {
        return scanner.next();
    }

    static Menu getChoice() {
        Menu.displayChoiceMessage();
        String choice = scanner.next();
        return Menu.valueOf(choice.toUpperCase());
    }

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
    public static void printResults() {
        DecimalFormat f = new DecimalFormat("##.00");
        for (User u : UserManager.getListUsers()) {
            System.out.println(u.toString() + " : " + f.format(u.getResult()));
        }
    }
}
