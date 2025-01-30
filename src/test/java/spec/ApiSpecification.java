package spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.ALL;
import static io.restassured.http.ContentType.JSON;

public class ApiSpecification {

    public static RequestSpecification loginRequestSpecification = with()
            .filter(withCustomTemplates())
            .contentType(JSON)
            .log().all();

    public static ResponseSpecification loginResponseSpecification = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .build();

    public static ResponseSpecification deleteBookResponseSpecification = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(ALL)
            .build();

    public static ResponseSpecification addBookResponseSpecification = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(ALL)
            .build();
}