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
   * The amount this user has to pay back.
   * A positive value means this user will pay money.
   * A negative value means this user will receive money.
   */
  private Double toPay;

  /**
   * The amount this user has already payed.
   */
  private Double payed;

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

  public ArrayList<Debit> getDebits() {
    return this.debits;
  }

  public Double getToPay() {
    return this.toPay;
  }

  public Double getPayed() {
    return this.payed;
  }
}
