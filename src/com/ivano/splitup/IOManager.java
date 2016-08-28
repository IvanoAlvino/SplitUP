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
        IOManager.printInputRequestMessage("How much");
        while (!scanner.hasNextDouble()) {
            IOManager.printStatusMessage("Please insert only digits");
            IOManager.printInputRequestMessage("How much");
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
            System.out.print(u.toString());
            if (u.getResult() >= 0) {
                System.out.print(" owes ");
            }
            else {
                System.out.print(" collects ");
            }
            System.out.println(f.format(Math.abs(u.getResult())));
        }
    }

    static void printSharedExpenses() {
        printOperationMessage("List of expenses");
        for (User u : UserManager.getListUsers()) {
            System.out.println(u.toString());
            for (Expense e : u.getListExpenses()) {
                System.out.print("\t" + e.amount);
                System.out.print(" (");
                int i = 0;
                for (User contributor : e.getContributors()) {
                    if (i++ > 0) {
                        System.out.print(", ");
                    }
                    System.out.print(contributor);
                }
                System.out.println(")");
            }
        }
    }

    /**
     * Print a provided input request message in a formatted way.
     * Used to request input from user.
     * @param message The message to print
     */
    static void printInputRequestMessage(String message) {
        System.out.print(">> ");
        System.out.print(message + " : ");
    }

    /**
     * Print a provided operation message in a formatted way.
     * Used to print operations titles.
     * @param message The message to print
     */
    static void printOperationMessage(String message) {
        System.out.print("** ");
        System.out.print(message);
        System.out.println(" **");
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
