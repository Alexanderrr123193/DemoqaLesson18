package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import specifications.ApiSpecification;
import models.AuthRequest;
import models.BookRequest;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.hasItem;
import org.openqa.selenium.Cookie;
import static com.codeborne.selenide.Selenide.*;
import java.util.List;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;

public class ApiSteps {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Step("Авторизация с использованием имени пользователя {userName}")
    public static Response authenticate(String userName, String password) throws JsonProcessingException {
        AuthRequest authRequest = new AuthRequest();
        authRequest.setUserName(userName);
        authRequest.setPassword(password);
        String authRequestJson = objectMapper.writeValueAsString(authRequest);

        return ApiSpecification.getAuthRequest(authRequestJson)
                .when()
                .post("/Account/v1/Login");
    }

    @Step("Очистка коллекции книг для пользователя")
    public static void clearBookCollection(String token, String userId) {
        ApiSpecification.getBookStoreRequest(token, userId, "")
                .when()
                .delete("/BookStore/v1/Books");
    }

    @Step("Добавление книги с ISBN {isbn} в коллекцию пользователя")
    public static void addBookToCollection(String token, String userId, String isbn) throws JsonProcessingException {
        BookRequest bookRequest = new BookRequest();
        bookRequest.setUserId(userId);
        bookRequest.setCollectionOfIsbns(List.of(new BookRequest.Book(isbn)));
        String bookRequestJson = objectMapper.writeValueAsString(bookRequest);

        ApiSpecification.getBookStoreRequest(token, userId, bookRequestJson)
                .when()
                .post("/BookStore/v1/Books");
    }

    @Step("Проверка профиля пользователя")
    public static Response checkProfile(String token) {
        return given()
                .log().uri()
                .log().method()
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/profile");
    }

    @Step("Проверка, что книга с ISBN {isbn} отсутствует в профиле пользователя")
    public static void verifyBookNotInProfile(String token, String isbn) {
        checkProfile(token)
                .then()
                .body("books.isbn", not(hasItem(isbn)));
    }

    @Step("Установка cookies для пользователя с userId {userId}")
    public static void setCookiesAndRefresh(String userId, String expires, String token) {
        // Устанавливаем cookies
        getWebDriver().manage().addCookie(new Cookie("userID", userId));
        getWebDriver().manage().addCookie(new Cookie("expires", expires));
        getWebDriver().manage().addCookie(new Cookie("token", token));
        refresh();
    }
}
