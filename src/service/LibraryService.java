package service;

import model.Book;
import model.User;
import model.Admin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Queue;

public class LibraryService {
    private final List<Book> books;
    private final Map<Integer, Queue<User>> reservations;

    public LibraryService() {
        this.books = new ArrayList<>();
        this.reservations = new HashMap<>();
    }

    /**
     * Adds a book to the library if it is non-null and its id is not already
     * present.
     */
    public boolean addBook(Book book) {
        if (book == null) {
            return false;
        }
        if (findBookById(book.getId()).isPresent()) {
            return false; // duplicate id
        }
        return books.add(book);
    }

    /**
     * Returns a list of books whose titles contain the given query
     * (case-insensitive).
     */
    public List<Book> findBooksByTitle(String titleQuery) {
        if (isNullOrEmpty(titleQuery)) {
            return Collections.emptyList();
        }
        String q = titleQuery.toLowerCase();
        List<Book> results = new ArrayList<>();
        for (Book b : books) {
            String t = b.getTitle();
            if (t != null && t.toLowerCase().contains(q)) {
                results.add(b);
            }
        }
        return results;
    }

    // Backwards-compatible alias
    public List<Book> searchBookByTitle(String title) {
        return findBooksByTitle(title);
    }

    /**
     * Returns an unmodifiable list of all books.
     */
    public List<Book> getAllBooks() {
        return Collections.unmodifiableList(new ArrayList<>(books));
    }

    /**
     * Prints information for every book in the library.
     */
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
            return;
        }
        for (Book b : books) {
            b.displayBookInfo();
            System.out.println("-----");
        }
    }

    /**
     * Attempts to borrow a book by id. Returns true if successful.
     */
    public boolean borrowBookById(int id) {
        Optional<Book> opt = findBookById(id);
        if (!opt.isPresent()) {
            return false;
        }
        Book b = opt.get();
        if (!b.isAvailable()) {
            return false;
        }
        b.setAvailable(false);
        return true;
    }

    // Backwards-compatible alias
    public boolean borrowBook(int id) {
        return borrowBookById(id);
    }

    /**
     * Attempts to return a book by id. Returns true if successful.
     */
    public boolean returnBookById(int id) {
        Optional<Book> opt = findBookById(id);
        if (!opt.isPresent()) {
            return false;
        }
        Book b = opt.get();
        if (b.isAvailable()) {
            return false;
        }
        b.setAvailable(true);
        return true;
    }

    /**
     * User-aware borrow: attempts to let a user borrow a book by id.
     * Prints validation messages and returns true on success.
     */
    public boolean borrowBook(User user, int id) {
        if (user == null) {
            System.out.println("Invalid user.");
            return false;
        }
        Optional<Book> opt = findBookById(id);
        if (!opt.isPresent()) {
            System.out.println("Book with ID " + id + " not found.");
            return false;
        }
        Book book = opt.get();
        if (!book.isAvailable()) {
            System.out.println("Book '" + book.getTitle() + "' is currently not available.");
            System.out.println("You can reserve it to be notified when it becomes available.");
            return false;
        }
        if (user.getBorrowedBooks().contains(book)) {
            System.out.println(user.getUserName() + " already borrowed '" + book.getTitle() + "'.");
            return false;
        }
        boolean ok = user.borrowBook(book);
        if (ok) {
            // If the user had a reservation for this book, remove it
            removeReservationIfExists(id, user);
            System.out.println(user.getUserName() + " successfully borrowed '" + book.getTitle() + "'.");
            return true;
        }
        System.out.println("Failed to borrow '" + book.getTitle() + "'.");
        return false;
    }

    /**
     * User-aware return: attempts to let a user return a book by id.
     * Prints validation messages and returns true on success.
     */
    public boolean returnBook(User user, int id) {
        if (user == null) {
            System.out.println("Invalid user.");
            return false;
        }
        Optional<Book> opt = findBookById(id);
        if (!opt.isPresent()) {
            System.out.println("Book with ID " + id + " not found.");
            return false;
        }
        Book book = opt.get();
        if (book.isAvailable()) {
            System.out.println("Book '" + book.getTitle() + "' is not currently borrowed.");
            return false;
        }
        if (!user.getBorrowedBooks().contains(book)) {
            System.out.println(user.getUserName() + " did not borrow '" + book.getTitle() + "'.");
            return false;
        }
        boolean ok = user.returnBook(book);
        if (ok) {
            System.out.println(user.getUserName() + " successfully returned '" + book.getTitle() + "'.");
            // After return, check reservation queue and auto-assign if someone is waiting
            Queue<User> q = reservations.get(id);
            if (q != null && !q.isEmpty()) {
                User next = q.poll();
                // remove queue entry if empty now
                if (q.isEmpty()) {
                    reservations.remove(id);
                }
                boolean assigned = next.borrowBook(book);
                if (assigned) {
                    System.out.println("Book '" + book.getTitle()
                            + "' has been automatically assigned to reserved user: " + next.getUserName());
                } else {
                    // if assign failed, make the book available
                    book.setAvailable(true);
                    System.out.println(
                            "Unable to assign '" + book.getTitle() + "' to reserved user: " + next.getUserName());
                }
            }
            return true;
        }
        System.out.println("Failed to return '" + book.getTitle() + "'.");
        return false;
    }

    // Backwards-compatible alias
    public boolean returnBook(int id) {
        return returnBookById(id);
    }

    /**
     * Reserve a book for a user. Only allowed when the book is not available.
     */
    public boolean reserveBook(User user, int id) {
        if (user == null) {
            System.out.println("Invalid user.");
            return false;
        }
        Optional<Book> opt = findBookById(id);
        if (!opt.isPresent()) {
            System.out.println("Book with ID " + id + " not found.");
            return false;
        }
        Book book = opt.get();
        if (book.isAvailable()) {
            System.out.println("Book '" + book.getTitle() + "' is available now — you can borrow it immediately.");
            return false;
        }
        Queue<User> q = reservations.computeIfAbsent(id, k -> new LinkedList<>());
        if (q.contains(user)) {
            System.out.println(user.getUserName() + " already has a reservation for '" + book.getTitle() + "'.");
            return false;
        }
        q.add(user);
        System.out.println(user.getUserName() + " reserved '" + book.getTitle() + "'. Position in queue: " + q.size());
        return true;
    }

    private void removeReservationIfExists(int bookId, User user) {
        Queue<User> q = reservations.get(bookId);
        if (q == null)
            return;
        // remove the user if present
        q.remove(user);
        if (q.isEmpty()) {
            reservations.remove(bookId);
        }
    }

    /**
     * Admin-only: remove a book entirely from the library.
     */
    public boolean removeBookById(int id, User requestedBy) {
        if (!(requestedBy instanceof Admin)) {
            System.out.println("Permission denied: admin only.");
            return false;
        }
        Optional<Book> opt = findBookById(id);
        if (!opt.isPresent()) {
            System.out.println("Book with ID " + id + " not found.");
            return false;
        }
        books.remove(opt.get());
        reservations.remove(id);
        System.out.println("Book with ID " + id + " removed by admin.");
        return true;
    }

    /**
     * Admin-only: view reservation queue for a book.
     */
    public List<User> viewReservationQueue(int id, User requestedBy) {
        if (!(requestedBy instanceof Admin)) {
            System.out.println("Permission denied: admin only.");
            return Collections.emptyList();
        }
        Queue<User> q = reservations.get(id);
        if (q == null || q.isEmpty()) {
            System.out.println("No reservations for book ID " + id + ".");
            return Collections.emptyList();
        }
        return new ArrayList<>(q);
    }

    /**
     * Admin-only: cancel a reservation by userId for a book.
     */
    public boolean cancelReservation(int bookId, int targetUserId, User requestedBy) {
        if (!(requestedBy instanceof Admin)) {
            System.out.println("Permission denied: admin only.");
            return false;
        }
        Queue<User> q = reservations.get(bookId);
        if (q == null || q.isEmpty()) {
            System.out.println("No reservations for book ID " + bookId + ".");
            return false;
        }
        User toRemove = null;
        for (User u : q) {
            if (u.getUserId() == targetUserId) {
                toRemove = u;
                break;
            }
        }
        if (toRemove == null) {
            System.out.println("User with ID " + targetUserId + " not found in reservation queue.");
            return false;
        }
        q.remove(toRemove);
        if (q.isEmpty())
            reservations.remove(bookId);
        System.out.println("Removed reservation for user " + targetUserId + " on book " + bookId + ".");
        return true;
    }

    /**
     * Helper: find a book by id.
     */
    private Optional<Book> findBookById(int id) {
        for (Book b : books) {
            if (b.getId() == id) {
                return Optional.of(b);
            }
        }
        return Optional.empty();
    }

    private boolean isNullOrEmpty(String s) {
        return s == null || s.trim().isEmpty();
    }
}
