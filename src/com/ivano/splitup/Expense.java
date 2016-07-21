package com.ivano.splitup;

import java.util.ArrayList;

class Expense extends Transaction {

  /**
   * The list of users that share this expense.
   * All the contributors will share an equal part of the expense.
   * If empty, the expense will be only payed by his {@link Expense#payer}.
   */
  private ArrayList<User> contributors;

  private int contributorsNumber;

  /**
   * The person who payed for this expense.
   */
  private User payer;

  public Expense(User payer, double amount) {
    this.payer = payer;
    this.contributors = new ArrayList<User>();
    this.amount = amount;
    this.contributorsNumber = 0;
  }

  /**
   * Add a new contributor to {@link Expense#contributors}.
   * @param contributor The user to add to the contributors list
   */
  public void addContributor(User contributor) {
    this.contributors.add(contributor);
    this.contributorsNumber++;
  }

  public ArrayList<User> getContributors() {
    return this.contributors;
  }

  public String getPayerName() {
    return this.payer.toString();
  }

  /**
   * Returns the share for every contributor.
   */
  public Double getShare() {
    return this.amount / this.contributorsNumber;
  }
}
