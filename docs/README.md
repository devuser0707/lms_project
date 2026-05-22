# Java Library Management - Project Documentation

## Project Overview

This small Java project demonstrates a simple library management system using plain Java classes. It includes basic models for books and users, a `Library` to manage them, and a `Main` class to run the program. The code is intentionally simple so beginners can learn object-oriented programming concepts.

## Features

| Feature                 | Description                                                               |
| ----------------------- | ------------------------------------------------------------------------- |
| Add books               | Add new `Book` objects to the library                                     |
| List books              | Show all books currently in the library                                   |
| Borrow/Return (example) | Demonstrates how a `User` might borrow and return a book (if implemented) |

## Folder Structure

The repository has a small layout:

| Path   | Description                      |
| ------ | -------------------------------- |
| `bin/` | Compiled .class files (optional) |
| `src/` | Java source files                |

## Class Explanation

This project contains these main classes (found in the `src/` folder):

- `Book` — Represents a book (title, author, id).
- `User` — Represents a library user (name, id).
- `Library` — Holds a collection of `Book` objects and methods to add or list books.
- `Main` — Example program entry point that creates a `Library`, adds books, and prints output.

See the source files:

- [src/model/Book.java](src/model/Book.java)
- [src/model/User.java](src/model/User.java)
- [src/service/LibraryService.java](src/service/LibraryService.java)
- [src/utility/Main.java](src/utility/Main.java)

## How the Project Works

1. `Main` creates an instance of `Library`.
2. `Main` creates several `Book` objects and adds them to the `Library` using `library.addBook(...)`.
3. The `Library` stores books in an internal list and provides methods to retrieve or display them.
4. `Main` prints the current books to the console to demonstrate the behavior.

## Execution / Setup Steps

Follow these steps to compile and run the project from the command line (Windows):

1. Open a terminal in the project root (the folder that contains `src/`).
2. Compile the Java sources (project uses packages):

```bash
javac -d bin src/model/*.java src/service/*.java src/utility/*.java
```

3. Run the program:

```bash
java -cp bin utility.Main
```

Notes:

- If your Java sources use packages, adjust commands to reflect package paths.
- You can also build and run inside an IDE (IntelliJ IDEA, Eclipse, VS Code with Java extensions).

## Example Outputs

Here are simple example outputs you might see when running `Main`:

```
Library initialized.
Added book: "Effective Java" by Joshua Bloch (ID: 1)
Added book: "Clean Code" by Robert C. Martin (ID: 2)
Current books in library:
1: Effective Java — Joshua Bloch
2: Clean Code — Robert C. Martin
```

Actual output depends on the implementation in `Main.java` and `Library.java`.

## Technologies Used

| Technology    | Purpose              |
| ------------- | -------------------- |
| Java (JDK 8+) | Programming language |
| Command line  | Compile & run        |

## Future Improvements

- Add persistence (save books to a file or database).
- Implement borrow/return functionality and track due dates.
- Add a simple text-based or GUI menu for user interaction.
- Add unit tests for each class.

---

Created to help beginners understand the project and run it locally.
