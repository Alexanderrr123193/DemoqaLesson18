package tests;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static api.ApiSteps.*;
import static api.UiSteps.*;
import static com.codeborne.selenide.Selenide.*;
import utils.testData;

public class CollectionTests2 extends TestBase {

    @Test
    public void addBookToCollectionWithDeleteAllBookTests2() throws Exception {
        String userName = testData.USER_NAME;
        String password = testData.PASSWORD;
        String bookName = testData.BOOK_NAME;
        String isbn = testData.ISBN;

        // 1. Авторизация через API
        Response authResponse = authenticate(userName, password);
        // 2. Очистка коллекции книг через API
        clearBookCollection(authResponse.path("token"), authResponse.path("userId"));
        // 3. Добавление книги через API
        addBookToCollection(authResponse.path("token"), authResponse.path("userId"), isbn);
        // 4. Открытие страницы профиля и добавление cookies
        open("/profile");
        setCookiesAndRefresh(
                authResponse.path("userId"),
                authResponse.path("expires"),
                authResponse.path("token")
        );
        // 5. Проверки через UI
        checkUserNameOnProfile(userName);
        checkBookInCollection(bookName);
        // 6. Удаление книги через UI
        deleteBookFromCollection(bookName);
        // 7. Проверка, что книга удалена через UI
        checkBookNotInCollection(bookName);
        // 8. Дополнительная проверка через API
        verifyBookNotInProfile(authResponse.path("token"), isbn);
    }
}
