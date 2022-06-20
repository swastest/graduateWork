package tests.restNote;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import preRequest.PreRequestCreateNote;
import preRequest.PreRequestToken;
import tests.TestBase;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static tests.spec.Specs.response200;

@Tag("note")
@DisplayName("Удаление заметок")
public class DeleteRestNote extends TestBase {
    @Disabled("Уточнение требований - сейчас Админ не может удалить заметку, которую создал сам")
    @Test
    @DisplayName("Админ удаляет заметку Админа")
    void AdminDeleteNoteFromAdmin() {
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenAdmin())
                .when().log().all()
                .delete("/rest/notes/" + PreRequestCreateNote.getIdNewNoteTeamFromAdmin())
                .then().log().all();
    }

    @Test
    @DisplayName("Негативная проверка: Менеджер удаляет заметку Админа")
    void MangerDeleteNoteFromAdmin() {
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenManager())
                .when()
                .delete("/rest/notes/" + PreRequestCreateNote.getIdNewNoteTeamFromAdmin())
                .then().log().all()
                .spec(response200)
                .body("message", is("You are not allowed to delete this note"))
                .body("status", is("ERROR"))
                .body("data", nullValue());
    }


    @Disabled("Уточнение требований - сейчас Менеджер не может удалить заметку, которую создал Менеджер")
    @Test
    @DisplayName("Менеджер удаляет заметку Менеджера")
    void MangerDeleteNoteFromManager() {
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenManager())
                .when()
                .delete("/rest/notes/" + PreRequestCreateNote.getIdNewNoteTeamFromManager())
                .then().log().all();
    }

    @Disabled("Уточнение требований - сейчас Техник не может удалить заметку, которую создал сам Техник")
    @Test
    @DisplayName("Техник удаляет свою заметку")
    void TechDeleteYourNote() {
        given()
                .filter(withCustomTemplates())
                .header("Authorization", PreRequestToken.getTokenTech())
                .when()
                .delete("/rest/notes/" + PreRequestCreateNote.getIdNewNoteTechToSelf())
                .then().log().all();
    }
}
