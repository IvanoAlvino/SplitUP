package com.ivano.splitup;

import java.util.ArrayList;
import java.util.Scanner;

public class SplitUP {

    /**
     * The list of all registered expenses.
     */
    private ArrayList<Expense> listExpenses;

    /**
     * The list of all debts.
     */
    private ArrayList<Debit> listDebits;

    public static void main(String[] args) {
        String choice;
        Boolean finished = false;
        Scanner scanner = new Scanner(System.in);

        printMenu();
        while (!finished) {
            displayChoiceMessage();
            choice = scanner.next();

            switch (choice) {
                case "a":
                    System.out.println("Add a shared expense");
                    break;

                case "r":
                    System.out.println("Remove a shared expense");
                    break;

                case "m":
                    System.out.println("Modify a shared expense");
                    break;

                case "u":
                    System.out.println("Add a user");
                    break;

                case "x":
                    System.out.println("Remove a user");
                    break;

                case "d":
                    System.out.println("Add a debit");
                    break;

                case "b":
                    System.out.println("Remove a debit");
                    break;

                case "c":
                    System.out.println("Calculate total");
                    break;

                case "e":
                    System.out.println("Exit from SplitUP");
                    finished = true;
                    break;
            }
        }

        System.out.println("Bye bye!");
    }


    private static void displayChoiceMessage() {
        System.out.println();
        System.out.print("Enter choice: ");
        System.out.println();
    }

    /**
     * Print a menu with possible operations.
     */
    private static void printMenu() {
        System.out.println("Menu\n----\n");
        System.out.println("a - Add shared expense");
        System.out.println("r - Remove shared expense");
        System.out.println("m - Modify shared expense");
        System.out.println();
        System.out.println("u - Add a user");
        System.out.println("x - Remove a user");
        System.out.println();
        System.out.println("d - Add a debit");
        System.out.println("b - Remove a debit");
        System.out.println();
        System.out.println("c - Calculate total");
        System.out.println();
        System.out.println("e - Exit from SplitUP");
        System.out.println();
    }
}
