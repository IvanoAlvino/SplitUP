package com.ivano.splitup;

import java.util.ArrayList;

class ExpenseManager {

  /**
   * The list of all registered expenses.
   */
  private static ArrayList<Expense> listExpenses = new ArrayList<>();


  static ArrayList<Expense> getListExpenses() {
    return listExpenses;
  }

  /**
   * This method will create and add a new expense to {@link ExpenseManager#listExpenses}.<br>
   * It asks to specify the paying <b>User</b>, the <b>Amount</b> that is being payed and the
   * <b>Contributors</b> that will share the expense.<br>
   */
  static void addExpense() throws UsernameNotValidException {
    IOManager.printInputRequestMessage("Who is paying");
    User payer = UserManager.obtainUser();
    Double amount = IOManager.readDoubleAmount();
    Expense expense = new Expense(payer, amount);

    payer.addExpense(expense);
    expense.addContributor(payer);        // The payer is always added as contributor
    addContributorsToExpense(expense);    // Add other contributors

    IOManager.printStatusMessage("Expense created");
  }

  /**
   * Add contributors to a provided expense.<br>
   * It is not possible to insert an expense with no contributors beside the original payer.
   *
   * @param expense The expense to which contributors will be added
   * @throws UsernameNotValidException Throws an exception if Username is equal to {@link UserManager#END_STRING} or
   * its name is composed only by digits.
   */
  private static void addContributorsToExpense(Expense expense) throws UsernameNotValidException {
    IOManager.printOperationMessage("Add contributors");

    Boolean finished = false;
    while (!finished) {
      String username = IOManager.readString();

      if (UserManager.isValid(username)) {
        User contributor = UserManager.retrieveUserOrCreateNew(username);
        if (canBeAdded(expense, contributor)) {
          expense.addContributor(contributor);
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
   * Check if a contributor can be added to an {@link Expense}.
   * If it has not already been added previously and he is not the original expense payer, it can be added.
   *
   * @param expense The expense where to add the contributor
   * @param contributor The contributor to add in the expense
   * @return A boolean value saying if the contributor can be added to the expense
   */
  private static boolean canBeAdded(Expense expense, User contributor) {
    if ( contributor.equals(expense.getPayer()) ) {
      IOManager.printStatusMessage("It is not possible to add the payer as contributor.");
      return false;
    }

    if (expense.containsContributor(contributor)) {
      IOManager.printStatusMessage("Contributor " + contributor.toString() + " already inserted.");
      return false;
    }

    return true;
  }

  /**
   * Adds a debit from a debtor towards a creditor.
   * Debits will simply be added to the amount a user needs to pay when calculating results.
   */
  static void addDebit() throws UsernameNotValidException {
    IOManager.printInputRequestMessage("Who is the debtor");
    User debtor = UserManager.obtainUser();

    // While introducing creditor, check it is different than debtor
    int i = 0;
    User creditor = debtor;

    while (creditor.equals(debtor)) {
      if (i++ > 0) {
        IOManager.printStatusMessage("Not possible to have same user as debtor and creditor");
      }
      IOManager.printInputRequestMessage("Who is the creditor");
      creditor = UserManager.obtainUser();
    }

    Double amount = IOManager.readDoubleAmount();

    Debit debit = new Debit(debtor, creditor, amount);
    debtor.addDebit(debit);
    creditor.addCredit(debit);

    IOManager.printStatusMessage("Debit created");
  }
}
