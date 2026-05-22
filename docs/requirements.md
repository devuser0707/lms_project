# Requirements

## Functional Requirements

- Add new books to the library.
- List all books stored in the library.
- (Optional) Allow a `User` to borrow and return books.
- Provide a simple way to show current library state (console output).

## Non-Functional Requirements

- Code should be readable and easy for beginners to follow.
- Programs should run on a standard Java runtime (JDK 8+).
- Response (console output) should be clear and user-friendly.

## System Requirements

- Operating System: Windows, macOS, or Linux
- Java Development Kit (JDK) 8 or later installed
- 50 MB disk space for source & compiled files

## Software Dependencies

This project uses only the Java standard library. No external libraries are required.

| Item       | Version / Note |
| ---------- | -------------- |
| Java (JDK) | 8+             |

## User Roles

- Librarian (developer or user running the program): Adds or lists books.
- Reader (optional role): Borrows and returns books when implemented.

## Use Cases

1. Add Book
   - Actor: Librarian
   - Precondition: Program running
   - Flow: Provide book details -> Program creates `Book` -> Adds to `Library` -> Confirm message

2. List Books
   - Actor: Any user
   - Precondition: Program running
   - Flow: Request list -> Program prints all books to console

3. (Optional) Borrow Book
   - Actor: Reader
   - Precondition: Book available in `Library`
   - Flow: Reader requests borrow -> Library marks book as borrowed -> Confirm message

## Project Scope

- In scope: Simple in-memory library data structures, console-based examples, beginner-friendly code.
- Out of scope: Persistent databases, web interfaces, advanced search, user authentication.

## Assumptions and Limitations

- The project keeps data in memory; restarting the program clears data.
- No concurrent access handling (not designed for multi-user or threaded use).
- Error handling is basic for educational clarity; production-ready validation is not included.
