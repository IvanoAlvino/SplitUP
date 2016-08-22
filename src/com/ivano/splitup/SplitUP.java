package com.ivano.splitup;

public class SplitUP {

  public static void main(String[] args) {
    IOManager.printMenu();

    Boolean finished = false;
    while (!finished) {

      try {
        Menu choice = IOManager.getChoice();
        switch (choice) {
          case A:
            try {
              ExpenseManager.addExpense();
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
              ExpenseManager.addDebit();
            } catch (UsernameNotValidException e) {
              UserManager.usernameNotAllowedErrorMessage();
              IOManager.printStatusMessage("Debit creation interrupted");
            }
            break;

          case B:
            System.out.println("Remove a debit");
            break;

          case C:
            UserManager.calculateAndShowResults();
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
