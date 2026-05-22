package model;

/**
 * Simple Admin user. Currently acts as a marker subclass of User.
 */
public class Admin extends User {
    public Admin(int userId, String userName) {
        super(userId, userName);
    }
}
