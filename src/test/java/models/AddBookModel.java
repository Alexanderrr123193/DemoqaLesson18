package models;

import java.util.List;
import lombok.Data;

@Data
public class AddBookModel {
    private final String userId;
    private final List<Isbn> collectionOfIsbns;
}
