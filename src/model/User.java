package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User {
    private int userId;
    private String userName;
    private final List<Book> borrowedBooks;

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.borrowedBooks = new ArrayList<>();
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Book> getBorrowedBooks() {
        return Collections.unmodifiableList(new ArrayList<>(borrowedBooks));
    }

    /**
     * Attempts to borrow the given book. Returns true on success.
     */
    public boolean borrowBook(Book book) {
        if (book == null) {
            return false;
        }
        if (borrowedBooks.contains(book)) {
            return false; // already borrowed by this user
        }
        if (!book.isAvailable()) {
            return false; // book not available
        }
        borrowedBooks.add(book);
        book.setAvailable(false);
        return true;
    }

    /**
     * Attempts to return the given book. Returns true on success.
     */
    public boolean returnBook(Book book) {
        if (book == null) {
            return false;
        }
        if (!borrowedBooks.contains(book)) {
            return false; // user didn't borrow this book
        }
        borrowedBooks.remove(book);
        book.setAvailable(true);
        return true;
    }

    /**
     * Prints borrowed books for this user to the console.
     */
    public void displayBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println(userName + " has no borrowed books.");
            return;
        }
        System.out.println("Borrowed books for " + userName + ":");
        for (Book b : borrowedBooks) {
            b.displayBookInfo();
            System.out.println("-----");
        }
    }
}
