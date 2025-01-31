package tests;

import api.ApiSteps;
import io.restassured.response.Response;
import models.AddBookModel;
import models.Isbn;
import pages.BookStorePage;
import org.junit.jupiter.api.Test;
import java.util.List;
import static api.ApiSteps.login;
import static data.TestData.*;
public class CollectionTests extends TestBase {
    String bookIsbn = testBookIsbn;
    @Test
    void deleteBookFromList() {
        Response responseLogin = login(bookStoreLogin, bookStorePassword);
        String token = responseLogin.path("token");
        String userId = responseLogin.path("userId");
        String expires = responseLogin.path("expires");
        ApiSteps apiSteps = new ApiSteps();
        apiSteps.clearListOfUserBooks(token, userId);
        Isbn isbn = new Isbn(bookIsbn);
        List<Isbn> listIsbn = List.of(isbn);
        AddBookModel bookData = new AddBookModel(userId, listIsbn);
        apiSteps.addBooks(token, bookData);
        BookStorePage pageObject = new BookStorePage();
        pageObject.openUserBooksPage(userId, expires, token);
        pageObject.findBookByName(bookName);
        pageObject.deleteBookByName(bookName);
        pageObject.findNotBookByName(bookName);
    }
}