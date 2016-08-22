package com.ivano.splitup;

import java.util.ArrayList;

class UserManager {

    private static final String END_STRING = "end";

    /**
     * The list of all users.
     */
    private static ArrayList<User> listUsers = new ArrayList<>();

    /**
     * Retrieve a User.
     * If the user is already existing in {@link UserManager#listUsers}, the user is returned.
     * If the user is not in the list, create a new one.
     * A user cannot be called {@link UserManager#END_STRING}.
     *
     * @param userName The user to retrive or create
     * @return The User, either found in the list or newly created
     * @throws UsernameNotValidException Throws an exception if Username is equal to {@link UserManager#END_STRING} or
     * its name is composed only by digits.
     */
    static User retrieveUserOrCreateNew(String userName) throws UsernameNotValidException {
        if (UserManager.isValid(userName)) {
            User user;
            if ((user = retrieveUser(userName)) != null) {
                // user found
                return user;
            } else {
                // user not found
                return createNewUser(userName);
            }
        }
        else {
            throw new UsernameNotValidException();
        }
    }

    /**
     * Search for a user in the {@link UserManager#listUsers}.
     *
     * @param userName The name of the user to retrieve
     *
     * @return  The user if found, null otherwise
     */
    private static User retrieveUser(String userName) {
        for (User user : listUsers) {
            if ( userName.equalsIgnoreCase(user.toString()) ) {
                return user;
            }
        }
        return null;
    }

    /**
     * Create a new User and adds it to {@link UserManager#listUsers}.
     * The parameter userName needs to be sanitized, ie it is not possible for it to be equal to
     * {@link UserManager#END_STRING}.
     *
     * @param userName
     *         The new user name
     *
     * @return The newly created user.
     *         Null if userName is empty or null
     */
    private static User createNewUser(String userName) {
        if (userName != null && !userName.isEmpty()) {
            User user = new User(userName);
            listUsers.add(user);
            return user;
        }
        return null;
    }

    static ArrayList<User> getListUsers() {
        return listUsers;
    }

    /**
     * Print an error message for username not valid.
     */
    static void usernameNotAllowedErrorMessage() {
        System.out.println("!! A user cannot be named " + END_STRING + " or containing only digits !!");
    }

    /**
     * Checks username validity.
     * In case it is equal to {@link UserManager#END_STRING} or composed only by digits, it is not valid.
     * @param userName The username to check
     * @return The validity of the username
     */
    static boolean isValid(String userName) {
        return !(userName.equalsIgnoreCase(END_STRING) || userName.matches("[0-9]+"));
    }

    /**
     * Will read a username from stdin, check its validity and if valid return a User.
     * @return A user, either created or returned from the {@link UserManager#listUsers}.
     * @throws UsernameNotValidException Throws an exception if Username is equal to {@link UserManager#END_STRING} or
     * its name is composed only by digits.
     */
    static User obtainUser() throws UsernameNotValidException {
        String userName = IOManager.readString();
        if (!UserManager.isValid(userName)) {
            throw new UsernameNotValidException();
        }
        return UserManager.retrieveUserOrCreateNew(userName);
    }

    /**
     * Calculate and update the {@link User#result} for every user.<br>
     * The result is the difference between {@link User#toPay} and {@link User#payed}.
     */
    private static void calculateTotal() {
        for (User u : UserManager.getListUsers()) {
            u.computeResult();
        }
    }

    /**
     * Loop through {@link ExpenseManager#listExpenses}, calculate the share and update field
     * {@link User#toPay} for every contributor.
     */
    private static void updateToPayForEveryUser() {
        for (Expense e : ExpenseManager.getListExpenses()) {
            for (User u : e.getContributors()) {
                u.updateToPay(e.getShare());
            }
        }
    }

    /**
     * Calculate and print the {@link User#result} for every user.
     */
    static void calculateAndShowResults() {
        resetToPayForEveryUser();
        updateToPayForEveryUser();
        calculateTotal();
        IOManager.printStatusMessage("Total");
        IOManager.printResults();
    }

    /**
     * Initializes the {@link User#toPay} field for every user to 0.
     */
    private static void resetToPayForEveryUser() {
        for (User u : UserManager.getListUsers()) {
            u.setToPay(0.0);
        }
    }
}
