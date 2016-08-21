package com.ivano.splitup;

import java.util.ArrayList;

public class UserManager {

    private static final String END_STRING = "end";

    /**
     * The list of all users.
     */
    private static ArrayList<User> listUsers = new ArrayList<>();

    /**
     * Enter the name of a user.
     * If the user is already existing in {@link UserManager#listUsers}, the user is returned.
     * If the user is not in the list, create a new one.
     * If the string {@link UserManager#END_STRING} is entered, null is returned.
     *
     * A user cannot be called {@link UserManager#END_STRING} and this is guaranteed at user creation time.
     *
     * @return A User, either new or selected from existing one
     *         Null if the string {@link UserManager#END_STRING} is entered
     */
    static User retrieveUserOrCreateNew(String userName) {
        User user;
        if ( ( user = retrieveUser(userName)) != null ) {
            // user found
            return user;
        }
        else {
            // user not found
            return createNewUser(userName);
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

    static void usernameNotAllowedErrorMessage() {
        System.out.println("A user cannot be named " + END_STRING + " or containing only digits");
    }

    static boolean isValid(String userName) {
        return !(userName.equalsIgnoreCase(END_STRING) || userName.matches("[0-9]+"));
    }

    static User obtainUser() throws UsernameNotValidException {
        String userName = IOManager.readString();
        if (!UserManager.isValid(userName)) {
            throw new UsernameNotValidException();
        }
        return UserManager.retrieveUserOrCreateNew(userName);
    }
}
