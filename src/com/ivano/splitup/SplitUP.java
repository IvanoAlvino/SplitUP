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
    System.out.print("Who is paying: ");
    User payer = UserManager.obtainUser();

    Double amount = IOManager.readDoubleAmount();
    payer.updatePayedAmount(amount);

    Expense expense = new Expense(payer, amount);
    expense.addContributor(payer);        // The payer is always added as contributor
    addContributorsToExpense(expense);    // Add other contributors

    IOManager.printStatusMessage("Expense created");
  }

  /**
   * Add contributors to a provided expense.<br>
   * It is not possible to insert an expense with no contributors beside the original payer.
   *
   * @param expense The expense to which contributors will be added
   * @throws UsernameNotValidException
   */
  private static void addContributorsToExpense(Expense expense) throws UsernameNotValidException {
    IOManager.printStatusMessage("Add contributors");

    Boolean finished = false;
    while (!finished) {
      String username = IOManager.readString();

      if (UserManager.isValid(username)) {
        User contributor = UserManager.retrieveUserOrCreateNew(username);

        if ( !contributor.toString().equals(expense.getPayerName()) ) {
          expense.addContributor(contributor);
        }
        else {
          IOManager.printStatusMessage("It is not possible to add the payer as contributor.");
        }
      }
      else {
        // if there are no other contributors besides the payer, do not permit to finish
        if (expense.getContributors().size() > 1) {
          finished = true;
        }
        else {
          IOManager.printStatusMessage("Enter at least one contributor.");
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
    IOManager.printStatusMessage("Total");
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
    System.out.print("Who is the debtor: ");
    User debtor = UserManager.obtainUser();

    // While introducing creditor, check it is different than debtor
    int i = 0;
    User creditor = debtor;
    while (creditor.toString().equalsIgnoreCase(debtor.toString())) {
      if (i++ > 0) {
        IOManager.printStatusMessage("Not possible to have same user as debtor and creditor");
      }
      System.out.print("Who is the creditor: ");
      creditor = UserManager.obtainUser();
    }

    Double amount = IOManager.readDoubleAmount();

    Debit debit = new Debit(debtor, creditor, amount);
    debtor.addDebit(debit);
    creditor.addCredit(debit);

    IOManager.printStatusMessage("Debit created");
  }

  public static void main(String[] args) {
    IOManager.printMenu();

    Boolean finished = false;
    while (!finished) {

      try {
        Menu choice = IOManager.getChoice();
        switch (choice) {
          case A:
            try {
              addExpense();
            } catch (UsernameNotValidException e) {
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
              addDebit();
            } catch (UsernameNotValidException e) {
              UserManager.usernameNotAllowedErrorMessage();
              IOManager.printStatusMessage("Debit creation interrupted");
            }
            break;

          case B:
            System.out.println("Remove a debit");
            break;

          case C:
            calculateAndShowResults();
            break;

          case E:
            IOManager.printStatusMessage("Bye Bye");
            finished = true;
            break;

          default:
            break;
        }
      }
      catch (IllegalArgumentException e) {
        IOManager.printStatusMessage("Retry");
      }

    }
  }

}
