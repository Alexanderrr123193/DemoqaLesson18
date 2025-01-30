package models;
import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class Isbn {
    private String isbn;
    public Isbn() {
    }

    public Isbn(String isbn) {
        this.isbn = isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}
