package tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;

public class LoginTests extends TestBase {

    private static final String LOGIN = "user001";
    private static final String PASSWORD = "vzsGDGE5egq34rfqwdERGEefw4fq3EG!";

    @Test
    public void successfulLoginUITest() {
        step("Open login page", () -> {
            open("/login");
        });

        step("Fill login form", () -> {
            $("#userName").setValue(LOGIN);
            $("#password").setValue(PASSWORD).pressEnter();
        });

        step("Check if log out button appears", () -> {
            $("#submit").shouldBe(visible);
        });
    }

    @Test
    public void successfulLoginAPITest() {
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
        open("/favicon.ico");

        getWebDriver().manage().addCookie(new Cookie("userID", authResponse.path("userId")));
        getWebDriver().manage().addCookie(new Cookie("expires", authResponse.path("expires")));
        getWebDriver().manage().addCookie(new Cookie("token", authResponse.path("token")));

        open("/profile");
        $("#userName-value").shouldHave(text(LOGIN));
    }

}
