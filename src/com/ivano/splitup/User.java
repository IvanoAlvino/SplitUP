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
     * A positive value means this usser will pay money.
     * A negative value means this user will receive money.
     */
    private Double toPay;

    /**
     * The amount this user has already payed.
     */
    private Double payed;

    public User(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
