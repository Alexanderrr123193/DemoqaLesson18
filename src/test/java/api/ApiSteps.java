package api;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.AddBookModel;
import models.LoginRequestModel;
import static io.restassured.RestAssured.given;
import static spec.ApiSpecification.*;
public class ApiSteps {
    @Step("Авторизация в книжном магазине")
    public static Response login(String userName, String password) {
        LoginRequestModel authData = new LoginRequestModel(userName, password);
        Response authResponse = given(loginRequestSpecification)
                .body(authData)
                .when().post("/Account/v1/Login")
                .then().spec(loginResponseSpecification)
                .extract().response();
        return authResponse;
    }
    @Step("Очистка коллекции книг пользователя")
    public Response clearListOfUserBooks(String token, String userId) {
        Response deletionResponse = given(loginRequestSpecification)
                .header("Authorization", "Bearer " + token)
                .queryParams("UserId", userId)
                .when().delete("/BookStore/v1/Books")
                .then().spec(deleteBookResponseSpecification)
                .extract().response();
        return deletionResponse;
    }
    @Step("Добавление книги в коллекцию пользователя")
    public void addBooks(String token, AddBookModel bookData) {
        given(loginRequestSpecification)
                .header("Authorization", "Bearer " + token)
                .body(bookData)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .spec(addBookResponseSpecification);
    }
}