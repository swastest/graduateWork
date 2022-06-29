package preRequest;

import modelPojo.RestTasks;
import tests.TestBase;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static tests.spec.Specs.response200;

public class PreRequestTasks extends TestBase {

    static public Integer getIdTask() {
        Long epoch = System.currentTimeMillis();
        Long epochPlus = System.currentTimeMillis() + 3600000;

        RestTasks body = new RestTasks(epochPlus, 14743, epoch, 0, epochPlus,
                "Test text", 2, epoch, 128);
        Integer response = given()
                .header("Authorization", PreRequestToken.getTokenAdmin())
                .body(body)
                .queryParam("on_hold", false)
                .contentType(JSON)
                .when()
                .put("/rest/tasks")
                .then()
                .extract().path("data.id");
        System.out.println("ЗАДАЧА НОМЕР === " +response);
        return response;
    }

    static public void deleteTask(Integer value) {
        given()
                .header("Authorization", PreRequestToken.getTokenAdmin())
                .pathParam("id", value)
                .contentType(JSON)
                .when()
                .delete("/rest/tasks/{id}")
                .then().log().all()
                .spec(response200);
    }

}
