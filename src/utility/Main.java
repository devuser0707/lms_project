package utility;

import model.Book;
import model.User;
import model.Admin;
import service.LibraryService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryService library = new LibraryService();
        try {
            System.out.print("Enter your user id: ");
            int userId = readInt(scanner);
            System.out.print("Enter your name: ");
            String userName = scanner.nextLine().trim();
            if (userName.isEmpty()) {
                userName = "User" + userId;
            }
            System.out.print("Are you an admin? (y/N): ");
            String isAdmin = scanner.nextLine().trim();
            User user;
            if ("y".equalsIgnoreCase(isAdmin)) {
                user = new Admin(userId, userName);
            } else {
                user = new User(userId, userName);
            }

            boolean running = true;
            while (running) {
                printMenu();
                System.out.print("Choose an option: ");
                String opt = scanner.nextLine().trim();
                switch (opt) {
                    case "1": // Add Book
                        System.out.print("Book ID: ");
                        int id = readInt(scanner);
                        System.out.print("Title: ");
                        String title = scanner.nextLine().trim();
                        System.out.print("Author: ");
                        String author = scanner.nextLine().trim();
                        Book b = new Book(id, title, author);
                        boolean added = library.addBook(b);
                        if (added) {
                            System.out.println("Book added.\n");
                        } else {
                            System.out.println("Failed to add book. A book with ID " + id + " may already exist.\n");
                        }
                        break;
                    case "2": // View Books
                        library.displayAllBooks();
                        break;
                    case "3": // Search Book
                        System.out.print("Search title: ");
                        String q = scanner.nextLine().trim();
                        List<Book> results = library.findBooksByTitle(q);
                        if (results.isEmpty()) {
                            System.out.println("No books found matching '" + q + "'.\n");
                        } else {
                            for (Book r : results) {
                                r.displayBookInfo();
                                System.out.println("-----");
                            }
                        }
                        break;
                    case "4": // Borrow Book
                        System.out.print("Enter book ID to borrow: ");
                        int borrowId = readInt(scanner);
                        library.borrowBook(user, borrowId);
                        break;
                    case "5": // Return Book
                        System.out.print("Enter book ID to return: ");
                        int returnId = readInt(scanner);
                        library.returnBook(user, returnId);
                        break;
                    case "6": // Reserve Book
                        System.out.print("Enter book ID to reserve: ");
                        int reserveId = readInt(scanner);
                        library.reserveBook(user, reserveId);
                        break;
                    case "7": // Exit
                        running = false;
                        break;
                    case "8": // Remove Book (admin)
                        System.out.print("Enter book ID to remove: ");
                        int removeId = readInt(scanner);
                        library.removeBookById(removeId, user);
                        break;
                    case "9": // View Reservations (admin)
                        System.out.print("Enter book ID to view reservations: ");
                        int viewId = readInt(scanner);
                        List<User> queue = library.viewReservationQueue(viewId, user);
                        if (!queue.isEmpty()) {
                            System.out.println("Reservation queue for book " + viewId + ":");
                            for (User u : queue) {
                                System.out.println("- " + u.getUserName() + " (ID: " + u.getUserId() + ")");
                            }
                        }
                        break;
                    case "10": // Cancel Reservation (admin)
                        System.out.print("Enter book ID: ");
                        int bookId = readInt(scanner);
                        System.out.print("Enter user ID to cancel reservation for: ");
                        int targetUserId = readInt(scanner);
                        library.cancelReservation(bookId, targetUserId, user);
                        break;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }
        } catch (java.util.NoSuchElementException e) {
            System.out.println("\nInput closed. Exiting.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }

        System.out.println("Goodbye.");
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("Menu");
        System.out.println("1. Add Book");
        System.out.println("2. View Books");
        System.out.println("3. Search Book");
        System.out.println("4. Borrow Book");
        System.out.println("5. Return Book");
        System.out.println("6. Reserve Book");
        System.out.println("7. Exit");
        System.out.println("8. Remove Book (admin)");
        System.out.println("9. View Reservations (admin)");
        System.out.println("10. Cancel Reservation (admin)");
    }

    private static int readInt(Scanner scanner) {
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid integer: ");
            }
        }
    }
}
