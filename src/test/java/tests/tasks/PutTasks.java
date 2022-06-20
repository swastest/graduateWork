package tests.tasks;

import config.TeamPropInterface;
import config.TechPropInterface;
import modelPojo.RestTasks;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import preRequest.PreRequestTasks;
import preRequest.PreRequestToken;
import tests.TestBase;

import java.util.stream.Stream;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static tests.spec.Specs.*;

@Tag("task")
@DisplayName("Создать задачу")
public class PutTasks extends TestBase {
    Long epoch = System.currentTimeMillis();
    Long epochPlus = System.currentTimeMillis() + 3600000;
    TechPropInterface configTech = ConfigFactory.create(TechPropInterface.class);
    TeamPropInterface configTeam = ConfigFactory.create(TeamPropInterface.class);

    static Stream<Arguments> preRequestParamTokenAdminManager() {
        return Stream.of
                (Arguments.of(PreRequestToken.getTokenAdmin(), "Админ"),
                        Arguments.of(PreRequestToken.getTokenManager(), "Менеджер")
                );
    }

    @ParameterizedTest(name = "(on_hold=false): {1} назначает задание технику ")
    @MethodSource(value = "preRequestParamTokenAdminManager")
    void restTasksToTech(String value, String forNameTest) {
        RestTasks body = new RestTasks(epochPlus, 14743, epoch, 0, epochPlus,
                "Test text", 0, epoch, 11);
        Integer response = given()
                .filter(withCustomTemplates())
                .header("Authorization", value)
                .body(body)
                .queryParam("on_hold", false)
                .contentType(JSON)
                .when().log().all()
                .put("/rest/tasks")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data", notNullValue())
                .body("data.id", notNullValue())
                .body("data.client.id", is(14743))
                .body("data.description", is("Test text"))
                .extract().path("data.id");
        PreRequestTasks.deleteTask(response);
    }

    @ParameterizedTest(name = "(on_hold=true): {1} назначает задание на команду ")
    @MethodSource(value = "preRequestParamTokenAdminManager")
    void restTasksToTeam(String value, String forNameTest) {
        RestTasks body = new RestTasks(epochPlus, 14743, epoch, configTeam.teamId(), epochPlus,
                "Test text", 0, epoch, 0);
        Integer response = given()
                .filter(withCustomTemplates())
                .header("Authorization", value)
                .body(body)
                .queryParam("on_hold", true)
                .contentType(JSON)
                .when().log().all()
                .put("/rest/tasks")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data", notNullValue())
                .body("data.id", notNullValue())
                .body("data.client.id", is(14743))
                .body("data.description", is("Test text"))
                .extract().path("data.id");
        PreRequestTasks.deleteTask(response);
    }

    @ParameterizedTest(name = "(on_hold=true): {1} назначает задание технику")
    @MethodSource(value = "preRequestParamTokenAdminManager")
    void restTasksToTechNegative(String value, String forNameTest) {
        RestTasks body = new RestTasks(epochPlus, 14743, epoch, 0,
                epochPlus, "Test text", 0, epoch, 6);
        Integer response = given()
                .filter(withCustomTemplates())
                .header("Authorization", value)
                .body(body)
                .queryParam("on_hold", true)
                .contentType(JSON)
                .when().log().all()
                .put("/rest/tasks")
                .then().log().all()
                .spec(response200)
                .extract().path("data.id");
        PreRequestTasks.deleteTask(response);
    }

    @ParameterizedTest(name = "Негативная проверка (on_hold=false): {1} назначает задание на команду")
    @MethodSource(value = "preRequestParamTokenAdminManager")
    void restTasksToTeamNegative(String value, String forNameTest) {
        RestTasks body = new RestTasks(epochPlus, 14743, epoch, configTeam.teamId(),
                epochPlus, "Test text", 0, epoch, 0);
        given()
                .filter(withCustomTemplates())
                .header("Authorization", value)
                .body(body)
                .queryParam("on_hold", false)
                .contentType(JSON)
                .when().log().all()
                .put("/rest/tasks")
                .then().log().all()
                .spec(response405);
    }

    @Test
    @DisplayName("Негативная проверка: Техник назначает задачу самому себе")
    void restTasksTechToTech() {
        RestTasks body = new RestTasks(epochPlus, 14743, epoch, 0, epochPlus,
                "Test text", 0, epoch, configTech.idTechUser());
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenTech())
                .body(body)
                .queryParam("on_hold", false)
                .contentType(JSON)
                .when().log().all()
                .put("/rest/tasks")
                .then().log().all()
                .spec(response403);

    }

    @Test
    @DisplayName("Негативная проверка: Техник назначает задачу на команду")
    void restTasksTechToTeam() {
        RestTasks body = new RestTasks(epochPlus, 14743, epoch, configTeam.teamId(), epochPlus,
                "Test text", 0, epoch, 0);
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenTech())
                .body(body)
                .queryParam("on_hold", true)
                .contentType(JSON)
                .when().log().all()
                .put("/rest/tasks")
                .then().log().all()
                .spec(response403);

    }


}
