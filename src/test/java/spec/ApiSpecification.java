package specifications;

import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class ApiSpecification {

    public static RequestSpecification getAuthRequest(String authData) {
        return given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType("application/json")
                .body(authData);
    }

    public static RequestSpecification getBookStoreRequest(String token, String userId, String bookData) {
        return given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType("application/json")
                .header("Authorization", "Bearer " + token)
                .queryParams("UserId", userId)
                .body(bookData);
    }
}
