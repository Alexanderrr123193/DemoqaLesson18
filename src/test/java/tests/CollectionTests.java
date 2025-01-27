package tests;
import io.restassured.response.Response;
import models.AddBookModel;
import models.Isbn;
import pages.PageObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static api.ApiSteps.*;
public class CollectionTests extends TestBase {

    @DisplayName("Удаление книги из списка")
    @Test
    void deleteBookFromList() {
        Response responseLogin = login(testData.bookStoreLogin, testData.bookStorePassword);
        String token = responseLogin.path("token");
        String userId = responseLogin.path("userId");
        String expires = responseLogin.path("expires");
        clearListOfUserBooks(token, userId);
        Isbn isbn = new Isbn();
        isbn.setIsbn(testData.isbn);
        List<Isbn> listIsbns = List.of(isbn);
        AddBookModel bookData = new AddBookModel(userId, listIsbns);
        addBooks(token, bookData);
        PageObject.openUserBooksPage(userId, expires, token);
        PageObject.findBookByName(testData.bookName);
        PageObject.deleteBookByName(testData.bookName);
        PageObject.findNotBookByName(testData.bookName);
    }
}