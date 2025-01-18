package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.lang.String.format;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;

public class CollectionTests2 extends TestBase {

    @Test
    public void addBookToCollectionWithDeleteAllBookTests2() {
        // Авторизация по API
        String authData = "{\"userName\":\"user001\", \"password\":\"vzsGDGE5egq34rfqwdERGEefw4fq3EG!\"}";
        Response authResponse = given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body(authData)
                .when()
                .post("/Account/v1/Login")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .extract().response();

        // Очистка коллекции книг по API
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .header("Authorization", "Bearer " + authResponse.path("token"))
                .queryParams("UserId", authResponse.path("userId"))
                .when()
                .delete("/BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);

        // Добавление книги в коллекцию по API
        String isbn = "9781449365035";
        String bookData = format("{\"userId\":\"%s\",\"collectionOfIsbns\":[{\"isbn\":\"%s\"}]}",
                authResponse.path("userId"), isbn);

        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .header("Authorization", "Bearer " + authResponse.path("token"))
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .log().status()
                .log().body()
                .statusCode(201);

        // Открытие страницы профиля
        open("/profile");

        // Добавление cookies после открытия страницы
        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));

        // Перезагружаем страницу после добавления cookies
        refresh();

        // Ожидание загрузки элементов страницы (например, проверки имени пользователя)
        $("#userName-value").shouldBe(visible).shouldHave(text("user001"));

        // Ожидание появления книги в UI
        $(".ReactTable").shouldBe(visible).shouldHave(text("Speaking JavaScript"));

        // Удаление книги через UI
        $("#delete-record-undefined").click();

        // Если появляется модальное окно с подтверждением, кликаем на кнопку "OK"
        $("#closeSmallModal-ok").click();

        // Добавляем явное ожидание, чтобы убедиться, что книга удалена
        $(".ReactTable").shouldNotHave(text("Speaking JavaScript"));

        // Дополнительное ожидание для синхронизации данных на сервере
        // Например, 1-2 секунды, чтобы данные успели обновиться на сервере
        sleep(2000); // Убедитесь, что добавили задержку в 2 секунды

        // Дополнительная проверка через API, чтобы убедиться, что книга действительно удалена
        given()
                .log().uri()
                .log().method()
                .header("Authorization", "Bearer " + authResponse.path("token"))
                .when()
                .get("/profile")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("books.isbn", not(hasItem(isbn))); // Проверка, что книги больше нет в списке
    }
}
