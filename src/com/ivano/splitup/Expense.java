package com.ivano.splitup;

import java.util.ArrayList;

class Expense extends Transaction {

    /**
     * The list of users that share this expense.
     * All the contributors will share an equal part of the expense.
     */
    private ArrayList<User> contributors;
}
