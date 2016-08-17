package com.ivano.splitup;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;


public class SplitUP {

  private static final String END_STRING = "end";

  private static Scanner scanner = new Scanner(System.in);

  /**
   * The list of all registered expenses.
   */
  private static ArrayList<Expense> listExpenses = new ArrayList<>();

  /**
   * The list of all debts.
   */
  private static ArrayList<Debit> listDebits = new ArrayList<>();

  /**
   * The list of all users.
   */
  private static ArrayList<User> listUsers = new ArrayList<>();

  /**
   * Enter the name of a user.
   * If the user is already existing in {@link SplitUP#listUsers}, the user is returned.
   * If the user is not in the list, create a new one.
   * If the string {@link SplitUP#END_STRING} is entered, null is returned.
   *
   * A user cannot be called {@link SplitUP#END_STRING} and this is guaranteed at user creation time.
   *
   * @return A User, either new or selected from existing one
   *         Null if the string {@link SplitUP#END_STRING} is entered
   */
  private static User retrieveUserOrCreateNew() {

    // insert user name
    System.out.println("Insert user name");
    String userName = scanner.next();

    if (userName.equalsIgnoreCase(END_STRING)) {
      return null;
    }

    User user;
    if ( (user = retrieveUser(userName)) != null ) {
      // user found
      return user;
    }
    else {
      // user not found
      return createNewUser(userName);
    }
  }

  /**
   * Search for a user in the {@link SplitUP#listUsers}.
   *
   * @param userName The name of the user to retrieve
   *
   * @return  The user if found, null otherwise
   */
  private static User retrieveUser(String userName) {
    for (User user : listUsers) {
      if ( userName.equalsIgnoreCase(user.toString()) ) {
        return user;
      }
    }
    return null;
  }

  /**
   * Create a new User and adds it to {@link SplitUP#listUsers}.
   * The parameter userName is sanitized, ie it is not possible for it to be equal to {@link SplitUP#END_STRING}.
   * 
   * @param userName
   *         The new user name 
   *
   * @return The newly created user.
   *         Null if userName is empty or null
   */
  private static User createNewUser(String userName) {
    if (userName != null && !userName.isEmpty()) {
      User user = new User(userName);
      listUsers.add(user);
      return user;
    }
    return null;
  }

  /**
   * Create a new User and adds it to {@link SplitUP#listUsers}.
   *
   * @throws UserNameNotAllowedException
   *          If userName entered is {@link SplitUP#END_STRING}
   * @return The newly created user.
   */
  private static User createNewUser() throws UserNameNotAllowedException {
    System.out.println("Insert user name");
    String userName = scanner.next();

    if (userName.equalsIgnoreCase(END_STRING)) {
      throw new UserNameNotAllowedException();
    }

    User user = new User(userName);
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
   */
  private static void addExpense() throws UserNameNotAllowedException {
    User payer;
    Double amount;
    Expense expense;

    // Get payer User
    System.out.println("Who is paying?");
    if (listUsers.isEmpty()) {
      payer = createNewUser();
    }
    else {
      payer = retrieveUserOrCreateNew();
      if (payer == null) {
        throw new UserNameNotAllowedException();
      }
    }

    amount = readDoubleAmount();
    payer.updatePayedAmount(amount);

    expense = new Expense(payer, amount);
    // The payer is always added as contributor
    expense.addContributor(payer);
    // Potentially add other contributors
    addContributorsToExpense(expense);

    System.out.println("Expense created!");
  }

  /**
   * Add contributors to a provided expense.<br>
   * It is possible not to insert any contributor, in this case the expense will be sustained only by the original
   * expense payer.
   *
   * @param expense The expense to which contributors will be added
   */
  private static void addContributorsToExpense(Expense expense) {
    //TODO: it should be not possible to enter zero contributors by application design
    User contributor;
    Boolean finished = false;

    while (!finished) {
      System.out.println("Who is sharing this expense?");
      contributor = retrieveUserOrCreateNew();
      if (contributor != null) {
        expense.addContributor(contributor);
      }
      else {
        finished = true;
      }
    }
    listExpenses.add(expense);
  }

  /**
   * This method will safely read a double amount of money.
   * In case something different from a double is introduced, ask again for a double.
   *
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

  /**
   * Print the results for every user in {@link SplitUP#listUsers}.
   * The value in euro is rounded to two decimals.
   */
  private static void printResults() {
    DecimalFormat f = new DecimalFormat("##.00");
    for (User u : listUsers) {
      System.out.println(u.toString() + " : " + f.format(u.getResult()));
    }
  }

  /**
   * Calculate and update the {@link User#result} for every user.<br>
   * The result is the difference between {@link User#toPay} and {@link User#payed}.
   */
  private static void calculateTotal() {
    for (User u : listUsers) {
      u.computeResult();
    }
  }

  /**
   * Loop through {@link SplitUP#listExpenses}, calculate the share and update field
   * {@link User#toPay} for every contributor.
   */
  private static void updateToPayForEveryUser() {
    for (Expense e : listExpenses) {
      for (User u : e.getContributors()) {
        u.updateToPay(e.getShare());
      }
    }
  }

  /**
   * Calculate and print the {@link User#result} for every user.
   */
  private static void calculateAndShowResults() {
    updateToPayForEveryUser();
    calculateTotal();
    printResults();
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
          try {
            addExpense();
          } catch (UserNameNotAllowedException e) {
            System.out.println("A user cannot be named " + END_STRING);
            System.out.println("Expense creation interrupted");
          }
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
          calculateAndShowResults();
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
