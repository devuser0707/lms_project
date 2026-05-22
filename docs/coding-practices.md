# Coding Practices (Beginner Friendly)

This document lists simple, practical coding rules and examples to help beginners work with the project.

## Naming Conventions

- Classes: Use PascalCase (each word capitalized).

  Example: `Book`, `Library`, `User`

- Methods and variables: Use camelCase (first word lowercase, following words capitalized).

  Example: `addBook()`, `getBookList`, `userName`

- Constants: Use UPPER_SNAKE_CASE.

  Example: `MAX_BORROW_DAYS`

## Class Design Guidelines

- Keep one responsibility per class. Each class should model one concept (single responsibility).

- Keep fields private and provide public methods when needed.

Example class skeleton:

```java
public class Book {
    private int id;
    private String title;
    private String author;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    public String getTitle() { return title; }
}
```

## Method Writing Best Practices

- Keep methods short and focused (do one thing).
- Use clear parameter names and document unusual behavior.
- Return meaningful values or use void when no value is intended.

Example:

```java
public void addBook(Book book) {
    // adds book to internal list
}
```

## Exception Handling Rules

- Prefer checked exceptions for recoverable errors and unchecked for programming errors.
- Catch exceptions only when you can handle them or add useful context before rethrowing.
- Avoid empty catch blocks.

Example:

```java
try {
    // code that may fail
} catch (IOException e) {
    System.err.println("Unable to read file: " + e.getMessage());
}
```

## Code Formatting Standards

- Use 4 spaces for indentation.
- Put braces on the same line for classes/methods.
- Use blank lines between logical sections of code.

Example:

```java
public void printBooks() {
    for (Book b : books) {
        System.out.println(b.getTitle());
    }
}
```

## Commenting Guidelines

- Use comments to explain why code exists, not what it does (the code should show what).
- Use Javadoc comments for public methods and classes.

Example:

```java
/**
 * Adds a book to the library.
 * @param book the book to add
 */
public void addBook(Book book) { ... }
```

## SOLID Principles Used (Simple Explanation)

- Single Responsibility: Each class has one job (e.g., `Book` holds book data).
- Open/Closed: Code should be written so it is easy to extend (add new features) without changing existing code.
- Liskov Substitution: Subclasses (if used) should behave like their parent classes.
- Interface Segregation: Prefer small, focused interfaces over large ones (not heavily used in small projects).
- Dependency Inversion: Depend on abstractions (interfaces) rather than concrete classes when appropriate.

## Clean Code Principles

- Choose meaningful names.
- Keep functions small.
- Avoid duplication.
- Write tests for core behavior when possible.

## Git Commit Best Practices

- Use short, descriptive commit messages.

  Format recommendation:
  - Title (50 characters max)
  - Blank line
  - Brief description of what and why

- Commit one logical change at a time.
- Run the program / tests before committing.

Example commit message:

```
Add Book model and basic library storage

Introduce `Book` class and add in-memory list handling in `Library`.
```

---

Refer to source files in `src/` when implementing these practices:

- [src/model/Book.java](src/model/Book.java)
- [src/model/User.java](src/model/User.java)
- [src/service/LibraryService.java](src/service/LibraryService.java)

After refactor the main entry is at [src/utility/Main.java](src/utility/Main.java)
