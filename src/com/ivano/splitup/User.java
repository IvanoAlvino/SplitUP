package com.ivano.splitup;

import java.util.ArrayList;

class User {

  /**
   * The name of the user.
   */
  private String name;

  /**
   * The list of debits this user have.
   */
  private ArrayList<Debit> debits;

  /**
   * The sum of the shares this user has to pay.
   */
  private Double toPay;

  /**
   * The amount this user has already payed.
   */
  private Double payed;

  /**
   * The difference between {@link User#toPay} and {@link User#payed}.<br>
   * This value can be:<br>
   *   - Positive -> The user pays money <br>
   *   - Negative -> The user receives money.
   */
  private Double result;

  public User(String name) {
    this.name = name;
    this.debits = new ArrayList<>();
    this.toPay = 0.0;
    this.payed = 0.0;
  }

  @Override
  public String toString() {
    return this.name;
  }

  void updatePayedAmount(double amount) {
    this.payed += amount;
  }

  /**
   * Compute the {@link User#result}.
   */
  void computeResult() {
    this.result = this.toPay - this.payed;
  }

  Double getResult() {
    return this.result;
  }

  void updateToPay(Double share) {
    this.toPay += share;
  }

  void setToPay(Double val) {
    this.toPay = val;
  }

  void addDebit(Debit debit) {
    this.debits.add(debit);
  }
}
