package com.ivano.splitup;

import java.util.ArrayList;
import java.util.Scanner;

public class SplitUP {

    private static Scanner scanner = new Scanner(System.in);

    /**
     * The list of all registered expenses.
     */
    private static ArrayList<Expense> listExpenses = new ArrayList<Expense>();

    /**
     * The list of all debts.
     */
    private static ArrayList<Debit> listDebits = new ArrayList<Debit>();

    /**
     * The list of all users.
     */
    private static ArrayList<User> listUsers = new ArrayList<User>();

    /**
     * Ask either to create a new user or to select an already existing one from {@link SplitUP#listUsers}.
     *
     * @return A User, either new or selected from existing one
     */
    private static User chooseUserFromListOrCreateNewUser() {
        printChooseUserORCreateNewMenu();

        while (true) {
            int choice = readInt();
            switch (choice) {
                case 1:
                    return chooseUserFromList();
                case 2:
                    return createNewUser();
                default:
                    printChooseUserORCreateNewMenu();
                    break;
            }
        }
    }

    /**
     * Print a small menu used in {@link SplitUP#chooseUserFromListOrCreateNewUser}.
     */
    private static void printChooseUserORCreateNewMenu() {
        System.out.println("1 - Choose user from list");
        System.out.println("2 - Create new user");
      }

    /**
     * Select an existing user from {@link SplitUP#listUsers}.
     * @return A User
     */
    private static User chooseUserFromList() {
        int selectedUser = -1;
        while (selectedUser < 0 || selectedUser > listUsers.size() -1) {
            printUserList();
            selectedUser = readInt();
        }
        return listUsers.get(selectedUser);
    }

  /**
   * This method will safely read an int value.
   * In case something different from an int is introduced, ask again for an int.
   * @return A Integer value
   */
  private static int readInt() {
    while (!scanner.hasNextInt()) {
        System.out.println("Please insert a number");
        scanner.next();
    }
    return scanner.nextInt();
  }

  /**
     * Print all the users from {@link SplitUP#listUsers}.
     */
    private static void printUserList() {
        for (int i=0; i < listUsers.size(); i++) {
            System.out.println(Integer.toString(i) + " " + listUsers.get(i).toString());
        }
    }

    /**
     * Create a new User and adds it to {@link SplitUP#listUsers}.
     * @return The newly created user
     */
    private static User createNewUser() {
        System.out.println("Insert name of the user:");
        String payerName = scanner.next();
        User user = new User(payerName);
        listUsers.add(user);
        return user;
    }


    /**
     * Display a simple message.
     * Used every iteration of the main loop in main method.
     */
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

    /**
     * This method will create and add a new expense to {@link SplitUP#listExpenses}.<br>
     * It asks to specify the paying <b>User</b>, the <b>Amount</b> that is being payed and the
     * <b>Contributors</b> that will share the expense.<br>
     * The paying user and the contributors can either be created on the fly, or selected from a list of
     * already existing users.
     */
    private static void addExpense() {
        User payer;
        Double amount;
        System.out.println("Who is paying?");

        if (listUsers.isEmpty()) {
            payer = createNewUser();
        }
        else {
            payer = chooseUserFromListOrCreateNewUser();
        }

        amount = readDoubleAmount();
        //TODO who is he sharing with?
        listExpenses.add(new Expense(payer, amount));
    }

    /**
     * This method will safely read a double amount of money.
     * In case something different from a double is introduced, ask again for a double.
     * @return A Double representing an amount of money
     */
    private static Double readDoubleAmount() {
        System.out.println("How much?");
        while (!scanner.hasNextDouble()) {
            System.out.println("Please insert only digits");
            System.out.println("How much?");
            scanner.next();
        }
        return scanner.nextDouble();
    }

    public static void main(String[] args) {

        String choice;
        Boolean finished = false;

        printMenu();
        while (!finished) {
            displayChoiceMessage();
            choice = scanner.next();

            switch (choice) {
                case "a":
                    addExpense();
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

                default:
                    displayChoiceMessage();
                    break;
            }
        }
        System.out.println("Bye bye!");
    }
}
