import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Scanner;

@Slf4j
public class App {
    public static void main(String[] args) {
        BookManager bookManager = new BookManager();
        bookManager.loadBooksFromJson();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            log.info("Menu:");
            log.info("1. Add a new book");
            log.info("2. Search for books");
            log.info("3. List all books");
            log.info("4. Exit");
            log.info("Select an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    addNewBook(scanner, bookManager);
                    break;
                case 2:
                    searchBooks(scanner, bookManager);
                    break;
                case 3:
                    listAllBooks(bookManager);
                    break;
                case 4:
                    bookManager.saveBooksToJson();
                    log.info("Goodbye!");
                    System.exit(0);
                default:
                    log.info("Invalid choice. Please try again.");
            }
        }
    }

    private static void addNewBook(Scanner scanner, BookManager bookManager) {
        log.info("Add a new book:");
        log.info("ISBN: ");
        String isbn = scanner.nextLine();
        log.info("Title: ");
        String title = scanner.nextLine();
        log.info("Author: ");
        String author = scanner.nextLine();
        log.info("Number of Pages: ");
        int numPages = scanner.nextInt();
        log.info("Publication Year: ");
        int publicationYear = scanner.nextInt();

        Book newBook = new Book(isbn, title, author, numPages, publicationYear);
        bookManager.addBook(newBook);
        log.info("Book added successfully!");
    }

    private static void searchBooks(Scanner scanner, BookManager bookManager) {
        log.info("Search for books by title or author:");
        log.info("Enter search query: ");
        String query = scanner.nextLine();

        List<Book> result = bookManager.searchBooks(query);

        if (result.isEmpty()) {
            log.info("No matching books found.");
        } else {
            result.forEach(book -> {
                log.info(book.toString());
                log.info("");
            });
        }
    }

    private static void listAllBooks(BookManager bookManager) {
        log.info("List of all books:");
        bookManager.getAllBooks().forEach(book -> {
            log.info(book.toString());
            log.info("");
        });
    }
}
