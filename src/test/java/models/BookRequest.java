package models;

import java.util.List;

public class BookRequest {
    private String userId;
    private List<Book> collectionOfIsbns;

    public static class Book {
        private String isbn;

        public Book(String isbn) {
            this.isbn = isbn;
        }

        public String getIsbn() {
            return isbn;
        }

        public void setIsbn(String isbn) {
            this.isbn = isbn;
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Book> getCollectionOfIsbns() {
        return collectionOfIsbns;
    }

    public void setCollectionOfIsbns(List<Book> collectionOfIsbns) {
        this.collectionOfIsbns = collectionOfIsbns;
    }
}
