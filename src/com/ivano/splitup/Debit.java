package com.ivano.splitup;

class Debit extends Transaction {

  /**
   * The user towards which this debit is created.
   * The creditor needs to receive an amount of money indicated by {@link Transaction#amount}.
   */
  private User creditor;

  /**
   * The user who owns this debt: the debtor.
   * The debtor needs to give an amount of money indicated by {@link Transaction#amount}.
   */
  private User debtor;

  Debit(User debtor, User creditor, Double amount) {
    this.debtor = debtor;
    this.creditor = creditor;
    this.amount = amount;
  }
}
