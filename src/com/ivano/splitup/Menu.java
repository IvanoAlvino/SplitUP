package com.ivano.splitup;

public enum Menu {
    A("Add shared expense"),
    R("Remove shared expense"),
    M("Modify shared expense"),
    D("Add a debit"),
    B("Remove a debit"),
    C("Calculate total"),
    E("Exit from SplitUp");

    private final String text;

    private static final String separator = " - ";

    Menu(String s) {
        this.text = s;
    }

    /**
     * Print a menu with possible operations.
     */
    public static void printMenu() {
        System.out.println("ivoMenu\n----\n");
        for (Menu m : Menu.values()) {
            System.out.println(m + separator + m.text);
        }
    }

    /**
     * Display a simple message.
     * Used every iteration of the main loop in main method.
     */
    public static void displayChoiceMessage() {
        System.out.println();
        System.out.print("Enter choice: ");
    }
}
