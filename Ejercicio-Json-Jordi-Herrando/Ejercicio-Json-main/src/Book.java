import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Book {
    private final String isbn;
    private final String title;
    private final String author;
    private final int numPages;
    private final int publicationYear;

    @JsonCreator
    public Book(
            @JsonProperty("isbn") String isbn,
            @JsonProperty("title") String title,
            @JsonProperty("author") String author,
            @JsonProperty("numPages") int numPages,
            @JsonProperty("publicationYear") int publicationYear
    ) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.numPages = numPages;
        this.publicationYear = publicationYear;
    }
}
