package tests.spec;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;

public class Specs {
    public static RequestSpecification request = with()
            .filter(withCustomTemplates())
      //      .baseUri("https://reqres.in")
            .log().all()
            .contentType(ContentType.JSON);


    public static ResponseSpecification response200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .build();

    public static ResponseSpecification response403 = new ResponseSpecBuilder()
            .expectStatusCode(403)
            .build();

    public static ResponseSpecification response405 = new ResponseSpecBuilder()
            .expectStatusCode(405)
            .build();
}
