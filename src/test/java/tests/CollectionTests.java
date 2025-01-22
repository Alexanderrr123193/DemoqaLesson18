package tests;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static api.ApiSteps.*;
import static pages.PageObject.*;
import static com.codeborne.selenide.Selenide.*;

public class CollectionTests extends TestBase {

    @Test
    public void addBookToCollectionWithDeleteAllBookTests() throws Exception {
        String userName = data.TestData.USER_NAME;
        String password = data.TestData.PASSWORD;
        String bookName = data.TestData.BOOK_NAME;
        String isbn = data.TestData.ISBN;

        Response authResponse = authenticate(userName, password);
        clearBookCollection(authResponse.path("token"), authResponse.path("userId"));
        addBookToCollection(authResponse.path("token"), authResponse.path("userId"), isbn);
        open("/profile");
        setCookiesAndRefresh(
                authResponse.path("userId"),
                authResponse.path("expires"),
                authResponse.path("token")
        );
        checkUserNameOnProfile(userName);
        checkBookInCollection(bookName);
        deleteBookFromCollection(bookName);
        checkBookNotInCollection(bookName);
        verifyBookNotInProfile(authResponse.path("token"), isbn);
    }
}