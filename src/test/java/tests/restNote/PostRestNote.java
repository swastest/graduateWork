package tests.restNote;

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
import preRequest.PreRequestCreateNote;
import preRequest.PreRequestToken;
import tests.TestBase;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static tests.spec.Specs.response200;

@Tag("note")
@DisplayName("Редактирование заметок")
public class PostRestNote extends TestBase {
    AdminPropInterface adminConfig = ConfigFactory.create(AdminPropInterface.class);
    ManagerPropInterface managerConfig = ConfigFactory.create(ManagerPropInterface.class);
    TechPropInterface techConfig = ConfigFactory.create(TechPropInterface.class);
    TeamPropInterface teamConf = ConfigFactory.create(TeamPropInterface.class);
    Long epoch = System.currentTimeMillis();
    String changeTestText = "Change Text";

    @Test
    @DisplayName("Админ обновляет(редактирует) заметку, которую создал Админ")
    void AdminUpdateNoteFromAdmin() {
        CreateOrUpdateNoteDto body = new CreateOrUpdateNoteDto(epoch, teamConf.teamId(),
                PreRequestCreateNote.getIdNewNoteTeamFromAdmin(), changeTestText, 0);
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenAdmin())
                .body(body)
                .contentType(ContentType.JSON)
                .when().log().body()
                .post("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data.team.id", is(teamConf.teamId()))
                .body("data.owner.id", is(adminConfig.idAdminUser()))
                .body("data.text", is(changeTestText));
    }


    @Test
    @DisplayName("Админ обновляет(редактирует) заметку, которую создал Техник")
    void AdminUpdateNoteFromTech() {
        Integer idNote = PreRequestCreateNote.getIdNewNoteTechToSelf();
        CreateOrUpdateNoteDto body = new CreateOrUpdateNoteDto(epoch, teamConf.teamId(),
                idNote, changeTestText, 0);
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenAdmin())
                .body(body)
                .contentType(ContentType.JSON)
                .when().log().body()
                .post("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data.owner.id", is(techConfig.idTechUser()))
                .body("data.id", is(idNote))
                .body("data.text", is(changeTestText));
    }


    @Test
    @DisplayName("Админ обновляет(редактирует) заметку, которую создал Менеджер")
    void AdminUpdateNoteFromManager() {
        CreateOrUpdateNoteDto body = new CreateOrUpdateNoteDto(epoch, teamConf.teamId(),
                PreRequestCreateNote.getIdNewNoteTeamFromManager(), changeTestText, 0);
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenAdmin())
                .body(body)
                .contentType(ContentType.JSON)
                .when().log().body()
                .post("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data.team.id", is(teamConf.teamId()))
                .body("data.owner.id", is(managerConfig.idManagerUser()))
                .body("data.text", is(changeTestText));
    }


    @Test
    @DisplayName("Негативная проверка: Менеджер обновляет(редактирует) заметку, которую создал Админ")
    void updateNoteManager() {
        CreateOrUpdateNoteDto body = new CreateOrUpdateNoteDto(epoch, teamConf.teamId(),
                PreRequestCreateNote.getIdNewNoteTeamFromAdmin(), changeTestText, 0);
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenManager())
                .body(body)
                .contentType(ContentType.JSON)
                .when().log().body()
                .post("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("ERROR"))
                .body("message", is("You are not allowed to edit this note"));
    }

    @Test
    @DisplayName("Негативная проверка: Техник обновляет(редактирует) заметку, которую создал Админ")
    void updateNoteTech() {
        CreateOrUpdateNoteDto body = new CreateOrUpdateNoteDto(epoch, teamConf.teamId(),
                PreRequestCreateNote.getIdNewNoteTeamFromAdmin(), changeTestText, 0);
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenTech())
                .body(body)
                .contentType(ContentType.JSON)
                .when().log().body()
                .post("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("ERROR"))
                .body("message", is("You are not allowed to edit this note"));
    }

    @Test
    @DisplayName("Негативная проверка: Техник обновляет(редактирует) заметку, которую создал Менеджер")
    void updateNoteTechFromManager() {
        CreateOrUpdateNoteDto body = new CreateOrUpdateNoteDto(epoch, teamConf.teamId(),
                PreRequestCreateNote.getIdNewNoteTeamFromManager(), changeTestText, 0);
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenTech())
                .body(body)
                .contentType(ContentType.JSON)
                .when().log().body()
                .post("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("ERROR"))
                .body("message", is("You are not allowed to edit this note"));
    }

    @Test
    @DisplayName("Негативная проверка: Техник обновляет(редактирует) заметку, которую создал Сам себе")
    void updateNoteTechFromSelf() {
        Integer idNote = PreRequestCreateNote.getIdNewNoteTechToSelf();
        CreateOrUpdateNoteDto body = new CreateOrUpdateNoteDto(epoch, 0, idNote, changeTestText, 0);
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenTech())
                .body(body)
                .contentType(ContentType.JSON)
                .when().log().body()
                .post("/rest/notes")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data", notNullValue())
                .body("data.id", is(idNote))
                .body("data.text", is(changeTestText))
                .body("data.owner.id", is(techConfig.idTechUser()));

    }

}
