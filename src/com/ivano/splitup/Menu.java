package com.ivano.splitup;

enum Menu {
  A("Add shared expense"),
  R("Remove shared expense"),
  M("Modify shared expense"),
  D("Add a debit"),
  B("Remove a debit"),
  P("Print all shared expenses"),
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
    IOManager.printOperationMessage("Menu");
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
    IOManager.printInputRequestMessage("Enter choice");
  }

  /**
   * Perform a given Menu operation.
   *
   * @param choice
   *         The given operation to perform
   *
   * @return A boolean value corresponding to true if exit operation is performed
   */
  public static boolean performOperation(Menu choice) {
    boolean finished = false;

    switch (choice) {
    case A:
      try {
        ExpenseManager.addExpense();
      }
      catch (UsernameNotValidException e) {
        UserManager.usernameNotAllowedErrorMessage();
        IOManager.printStatusMessage("Expense creation interrupted");
      }
      break;

    case R:
      System.out.println("Remove a shared expense");
      break;

    case M:
      System.out.println("Modify a shared expense");
      break;

    case D:
      try {
        ExpenseManager.addDebit();
      }
      catch (UsernameNotValidException e) {
        UserManager.usernameNotAllowedErrorMessage();
        IOManager.printStatusMessage("Debit creation interrupted");
      }
      break;

    case B:
      System.out.println("Remove a debit");
      break;

    case P:
      IOManager.printSharedExpenses();
      break;

    case C:
      UserManager.calculateAndShowResults();
      break;

    case E:
      IOManager.printStatusMessage("Bye Bye");
      finished = true;

    default:
      break;
    }

    return finished;
  }
}
