package tests.restNote;

import com.github.javafaker.Faker;
import config.AdminPropInterface;
import config.ManagerPropInterface;
import config.TeamPropInterface;
import config.TechPropInterface;
import io.restassured.http.ContentType;
import modelPojo.forPreRequest.CreateOrUpdateNoteDto;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import preRequest.PreRequestToken;
import tests.TestBase;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static tests.spec.Specs.response200;

@Tag("note")
@DisplayName("Создание новой заметки")
public class PutRestNotes extends TestBase {

     AdminPropInterface adminConfig = ConfigFactory.create(AdminPropInterface.class);
     ManagerPropInterface managerConfig = ConfigFactory.create(ManagerPropInterface.class);
     TechPropInterface techConfig = ConfigFactory.create(TechPropInterface.class);
     TeamPropInterface teamConf = ConfigFactory.create(TeamPropInterface.class);
     Long epoch = System.currentTimeMillis();
    Faker faker = new Faker();
    String testTxt = faker.backToTheFuture().quote();

    @Test
    @DisplayName("Админ создает заметку для команды")
    void createNoteAdminForTeam() {
        CreateOrUpdateNoteDto body = new CreateOrUpdateNoteDto(epoch, teamConf.teamId(),
                0, testTxt, 0);
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenAdmin())
                .body(body)
                .contentType(ContentType.JSON)
                .when().log().body()
                .put("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data", notNullValue())
                .body("data.id", notNullValue())
                .body("data.team.id", is(teamConf.teamId()))
                .body("data.owner.id", is(adminConfig.idAdminUser()))
                .body("data.text", is(testTxt));
    }

    @Test
    @DisplayName("Админ создает заметку для техника")
    void createNoteAdminForTech() {
        CreateOrUpdateNoteDto body = new CreateOrUpdateNoteDto(epoch, 0,
                0, testTxt, techConfig.idTechUser());
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenAdmin())
                .body(body)
                .contentType(ContentType.JSON)
                .when().log().body()
                .put("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data", notNullValue())
                .body("data.id", notNullValue())
                .body("data.user.id", is(techConfig.idTechUser()))
                .body("data.owner.id", is(adminConfig.idAdminUser()))
                .body("data.text", is(testTxt));
    }

    @Test
    @DisplayName("Менеджер создает заметку для техника")
    void createNoteManagerForTech() {
        CreateOrUpdateNoteDto body = new CreateOrUpdateNoteDto(epoch, 0,
                0, testTxt, techConfig.idTechUser());
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenManager())
                .body(body)
                .contentType(ContentType.JSON)
                .when().log().body()
                .put("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data", notNullValue())
                .body("data.id", notNullValue())
                .body("data.user.id", is(techConfig.idTechUser()))
                .body("data.owner.id", is(managerConfig.idManagerUser()))
                .body("data.text", is(testTxt));
    }

    @Test
    @DisplayName("Менеджер создает заметку для команды")
    void createNoteManagerForTeam() {
        CreateOrUpdateNoteDto body = new CreateOrUpdateNoteDto(epoch, teamConf.teamId(),
                0, testTxt, 0);
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenManager())
                .body(body)
                .contentType(ContentType.JSON)
                .when().log().body()
                .put("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data", notNullValue())
                .body("data.id", notNullValue())
                .body("data.team.id", is(teamConf.teamId()))
                .body("data.owner.id", is(managerConfig.idManagerUser()))
                .body("data.text", is(testTxt));
    }

    @Test
    @DisplayName("Негативная проверка: Техник создает заметку для команды")
    void createNoteTechForTeam() {
        CreateOrUpdateNoteDto body = new CreateOrUpdateNoteDto(epoch, teamConf.teamId(),
                0, testTxt, 0);
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenTech())
                .body(body)
                .contentType(ContentType.JSON)
                .when().log().body()
                .put("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("ERROR"))
                .body("data", nullValue())
                .body("message", is("Access denied"));
    }

    @Test
    @DisplayName("Техник создает заметку самому себе")
    void createNoteTechToSelf() {
        CreateOrUpdateNoteDto body = new CreateOrUpdateNoteDto(epoch, 0,
                0, testTxt, techConfig.idTechUser());
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenTech())
                .body(body)
                .contentType(ContentType.JSON)
                .when().log().body()
                .put("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data.id", notNullValue())
                .body("data.owner.id", is(techConfig.idTechUser()))
                .body("data.text", is(testTxt));
    }

}
