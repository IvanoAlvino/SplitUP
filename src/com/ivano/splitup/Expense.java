package com.ivano.splitup;

import java.util.ArrayList;

class Expense extends Transaction {

  /**
   * The list of users that share this expense.
   * All the contributors will share an equal part of the expense.
   * If empty, the expense will be only payed by his {@link Expense#payer}.
   */
  private ArrayList<User> contributors;

  /**
   * The person who payed for this expense.
   */
  private User payer;

  Expense(User payer, double amount) {
    this.payer = payer;
    this.contributors = new ArrayList<>();
    this.amount = amount;
  }

  /**
   * Add a new contributor to {@link Expense#contributors}.
   * @param contributor The user to add to the contributors list
   */
  void addContributor(User contributor) {
    this.contributors.add(contributor);
  }

  ArrayList<User> getContributors() {
    return this.contributors;
  }

  User getPayer() {
    return this.payer;
  }

  /**
   * Returns the share for every contributor.
   */
  Double getShare() {
    return this.amount / this.contributors.size();
  }

  /**
   * Check if the {@link Expense#contributors} list already contains the given contributor.
   * @param contributor The contributor to look up for in the list
   * @return A boolean value saying if the contributor is already in the list
   */
  boolean containsContributor(User contributor) {
    return contributors.contains(contributor);
  }
}
