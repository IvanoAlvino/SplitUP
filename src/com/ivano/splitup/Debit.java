package com.ivano.splitup;

class Debit extends Transaction {

  /**
   * The user towards which this debit is created.
   * The creditor needs to receive an amount of money indicated by {@link Transaction#amount}.
   */
  private User creditor;
}
