package com.ivano.splitup;

import java.util.ArrayList;

class Expense extends Transaction {

    public Expense(User payer, double amount) {
        this.payer = payer;
        this.contributors = new ArrayList<User>();
        this.amount = amount;
    }

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
}
