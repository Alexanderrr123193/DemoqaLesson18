package specs;

import helpers.CustomAllureListener;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.*;
import static io.restassured.http.ContentType.JSON;

public class GeneralSpec {
    public static final RequestSpecification requestSpecification = with()
            .filter(CustomAllureListener.withCustomTemplates())
            .header("accept", "application/json")
            .header("Content-Type", "application/json")
            .log().all()
            .contentType(JSON);

    public static final ResponseSpecification responseSpecification200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(ALL)
            .build();

    public static final ResponseSpecification responseSpecification201 = new ResponseSpecBuilder()
            .expectStatusCode(201)
            .log(ALL)
            .build();

    public static final ResponseSpecification responseSpecification204 = new ResponseSpecBuilder()
            .expectStatusCode(204)
            .log(ALL)
            .build();

    public static final ResponseSpecification responseSpecification400 = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(ALL)
            .build();
}