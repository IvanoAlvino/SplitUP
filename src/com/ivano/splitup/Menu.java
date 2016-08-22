package com.ivano.splitup;

public enum Menu {
    A("Add shared expense"),
    R("Remove shared expense"),
    M("Modify shared expense"),
    D("Add a debit"),
    B("Remove a debit"),
    C("Calculate total"),
    E("Exit from SplitUp");

    private final String description;

    private static final String SEPARATOR = " - ";

    Menu(String s) {
        this.description = s;
    }

    /**
     * Print a menu with possible operations.
     */
    public static void printMenu() {
        IOManager.printStatusMessage("Menu");
        for (Menu m : Menu.values()) {
            System.out.println(m + SEPARATOR + m.description);
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
