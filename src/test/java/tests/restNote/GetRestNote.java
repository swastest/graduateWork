package tests.restNote;

import config.TeamPropInterface;
import config.TechPropInterface;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import preRequest.PreRequestToken;
import tests.TestBase;

import java.util.stream.Stream;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static tests.spec.Specs.response200;

@Tag("note")
@DisplayName("Запрос на просмотр уже созданных заметок")
public class GetRestNote extends TestBase {
    TechPropInterface techConfig = ConfigFactory.create(TechPropInterface.class);
    TeamPropInterface teamConf = ConfigFactory.create(TeamPropInterface.class);
    Long epoch = System.currentTimeMillis();

    @Test
    @DisplayName("Негативная проверка: Техник запрашивает заметки команды")
    void techRequestTeamNotes() {
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenTech())
                .queryParam("date", epoch)
                .queryParam("teamId", teamConf.teamId())
                .get("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("ERROR"))
                .body("message", is("Access denied"));
    }

    @Test
    @DisplayName("Негативная проверка: Техник запрашивает заметки другого техника")
    void techRequestAnotherTechNotes() {
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenTech())
                .queryParam("date", epoch)
                .queryParam("userId", 67)
                .get("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("ERROR"))
                .body("message", is("Access denied"));
    }

    @Test
    @DisplayName("Техник запрашивает свои заметки за текущий месяц")
    void techRequestYourSelfNotes() {
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenTech())
                .queryParam("date", epoch)
                .get("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data", notNullValue())
                .body("data.text", notNullValue())
                .body("data.user.id", hasItem(techConfig.idTechUser()));
    }

    static Stream<Arguments> preRequestParamTokenAdminManager() {
        return Stream.of
                (Arguments.of(PreRequestToken.getTokenAdmin(), "Админ"),
                        Arguments.of(PreRequestToken.getTokenManager(), "Менеджер")
                );
    }

    @ParameterizedTest(name = "{1} запрашивает заметки по id команды")
    @MethodSource(value = "preRequestParamTokenAdminManager")
    void managerAdminRequestTeamNotes(String value, String forNameTest) {
        given()
                .filter(withCustomTemplates())
                .header("Authorization", value)
                .queryParam("date", epoch)
                .queryParam("teamId", teamConf.teamId())
                .get("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data", notNullValue())
                .body("data.team.id", hasItem(teamConf.teamId()))
                .body("data.text", notNullValue());
    }


    @ParameterizedTest(name = "{1} запрашивает заметки по id техника")
    @MethodSource(value = "preRequestParamTokenAdminManager")
    void managerAdminRequestTechNotes(String value, String forNameTest) {
        given()
                .filter(withCustomTemplates())
                .header("Authorization", value)
                .queryParam("date", epoch)
                .queryParam("userId", techConfig.idTechUser())
                .when().log().all()
                .get("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data", notNullValue())
                .body("data.user.id", hasItem(techConfig.idTechUser()))
                .body("data.text", notNullValue());
    }


}
