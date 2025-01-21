package tests;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static api.ApiSteps.*;
import static pages.PageObject.*;
import static com.codeborne.selenide.Selenide.*;
import utils.testData;

public class CollectionTests extends TestBase {

    @Test
    public void addBookToCollectionWithDeleteAllBookTests2() throws Exception {
        String userName = testData.USER_NAME;
        String password = testData.PASSWORD;
        String bookName = testData.BOOK_NAME;
        String isbn = testData.ISBN;

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