import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BookManager {
    private List<Book> books = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();
    private static final String JSON_FILENAME = "books.json";

    public void addBook(Book book) {
        books.add(book);
    }

    public List<Book> searchBooks(String query) {
        Predicate<Book> containsQuery = book -> book.getTitle().contains(query) || book.getAuthor().contains(query);
        return books.stream()
                .filter(containsQuery)
                .collect(Collectors.toList());
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public void loadBooksFromJson() {
        try {
            File jsonFile = new File(JSON_FILENAME);
            if (jsonFile.exists()) {
                books = objectMapper.readValue(jsonFile, new TypeReference<List<Book>>() {});
                System.out.println("Loaded " + books.size() + " books from JSON.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveBooksToJson() {
        try {
            objectMapper.writeValue(new File(JSON_FILENAME), books);
            System.out.println("Saved " + books.size() + " books to JSON.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
