package com.ivano.splitup;

import java.util.ArrayList;

public class SplitUP {

  /**
   * The list of all registered expenses.
   */
  private static ArrayList<Expense> listExpenses = new ArrayList<>();

  /**
   * This method will create and add a new expense to {@link SplitUP#listExpenses}.<br>
   * It asks to specify the paying <b>User</b>, the <b>Amount</b> that is being payed and the
   * <b>Contributors</b> that will share the expense.<br>
   */
  private static void addExpense() throws UsernameNotValidException {
    User payer;
    Double amount;
    Expense expense;

    // Get payer User
    System.out.print("Who is paying: ");
    payer = UserManager.obtainUser();

    amount = IOManager.readDoubleAmount();
    payer.updatePayedAmount(amount);

    expense = new Expense(payer, amount);

    expense.addContributor(payer);        // The payer is always added as contributor
    addContributorsToExpense(expense);    // Add other contributors

    System.out.println("-- Expense created --");
  }

  /**
   * Add contributors to a provided expense.<br>
   * It is not possible to insert an expense with no contributors beside the original payer.
   *
   * @param expense The expense to which contributors will be added
   */
  private static void addContributorsToExpense(Expense expense) {
    User contributor;
    Boolean finished = false;

    System.out.println("** Add contributors");
    while (!finished) {
      String username = IOManager.readString();

      if (UserManager.isValid(username)) {
        contributor = UserManager.retrieveUserOrCreateNew(username);

        if ( !contributor.toString().equals(expense.getPayerName()) ) {
          expense.addContributor(contributor);
        }
        else {
          System.out.println("It is not possible to add the payer as contributor.");
        }
      }
      else {
        // if there are no other contributors besides the payer, do not permit to finish
        if (expense.getContributors().size() > 1) {
          finished = true;
        }
        else {
          System.out.println("Enter at least one contributor.");
        }
      }

    }
    listExpenses.add(expense);
  }

  /**
   * Calculate and update the {@link User#result} for every user.<br>
   * The result is the difference between {@link User#toPay} and {@link User#payed}.
   */
  private static void calculateTotal() {
    for (User u : UserManager.getListUsers()) {
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
    resetToPayForEveryUser();
    updateToPayForEveryUser();
    calculateTotal();
    System.out.println("-- Total --");
    IOManager.printResults();
  }

  /**
   * Initializes the {@link User#toPay} field for every user to 0.
   */
  private static void resetToPayForEveryUser() {
    for (User u : UserManager.getListUsers()) {
      u.setToPay(0.0);
    }
  }


  /**
   * Adds a debit from a debtor towards a creditor.
   * Debits will simply be added to the amount a user needs to pay when calculating results.
   */
  private static void addDebit() throws UsernameNotValidException {
    User debtor, creditor;
    Double amount;

    System.out.print("Who is the debtor: ");
    debtor = UserManager.obtainUser();

    // While introducing creditor, check it is different than debtor
    int i = 0;
    creditor = debtor;
    while (creditor.toString().equalsIgnoreCase(debtor.toString())) {
      if (i++ > 0) {
        System.out.println("Not possible to have same user as debtor and creditor");
      }
      System.out.print("Who is the creditor: ");
      creditor = UserManager.obtainUser();
    }

    amount = IOManager.readDoubleAmount();

    Debit debit = new Debit(debtor, creditor, amount);
    debtor.addDebit(debit);
    creditor.addCredit(debit);

    System.out.println("-- Debit created --");
  }

  public static void main(String[] args) {
    Menu choice;
    Boolean finished = false;

    IOManager.printMenu();
    while (!finished) {

      choice = IOManager.getChoice();

      switch (choice) {
        case A:
          try {
            addExpense();
          } catch (UsernameNotValidException e) {
            UserManager.usernameNotAllowedErrorMessage();
            System.out.println("-- Expense creation interrupted --");
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
            addDebit();
          } catch (UsernameNotValidException e) {
            UserManager.usernameNotAllowedErrorMessage();
            System.out.println("-- Debit creation interrupted --");
          }
          break;

        case B:
          System.out.println("Remove a debit");
          break;

        case C:
          calculateAndShowResults();
          break;

        case E:
          System.out.println("-- Bye bye --");
          finished = true;
          break;

        default:
          break;
      }
    }
  }

}
