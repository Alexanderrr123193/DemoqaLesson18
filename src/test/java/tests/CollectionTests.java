package tests;
import io.restassured.response.Response;
import models.AddBookModel;
import models.Isbn;
import pages.PageObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static api.ApiSteps.*;
import static data.TestData.*;
public class CollectionTests extends TestBase {
    String bookIsbn = "9781449365035";
    @DisplayName("Удаление книги")
    @Test
    void deleteBookFromList() {
        Response responseLogin = login(bookStoreLogin, bookStorePassword);
        String token = responseLogin.path("token");
        String userId = responseLogin.path("userId");
        String expires = responseLogin.path("expires");
        clearListOfUserBooks(token, userId);
        Isbn isbn = new Isbn(bookIsbn);
        List<Isbn> listIsbns = List.of(isbn);
        AddBookModel bookData = new AddBookModel(userId, listIsbns);
        addBooks(token, bookData);
        PageObject pageObject = new PageObject();
        pageObject.openUserBooksPage(userId, expires, token);
        pageObject.findBookByName(bookName);
        pageObject.deleteBookByName(bookName);
        pageObject.findNotBookByName(bookName);
    }
}