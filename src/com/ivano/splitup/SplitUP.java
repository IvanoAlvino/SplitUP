package com.ivano.splitup;

import java.util.ArrayList;

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

        printMenu();

    }

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

    }
}
