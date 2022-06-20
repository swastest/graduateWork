package tests.sendReminder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import preRequest.PreRequestToken;
import tests.TestBase;

import java.util.stream.Stream;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static tests.spec.Specs.response200;

@Tag("sendReminder")
@DisplayName("Отослать напоминание")
public class SendReminder extends TestBase {
    static Stream<Arguments> preRequestParamTokenAdminManagerTech(){
        return Stream.of
                (Arguments.of(PreRequestToken.getTokenAdmin(), "Админ"),
                        Arguments.of(PreRequestToken.getTokenManager(), "Менеджер"),
                        Arguments.of(PreRequestToken.getTokenTech(), "Техник")
                );
    }

    @ParameterizedTest( name = "{1} отсылает напоминание по id задачи")
    @MethodSource(value = "preRequestParamTokenAdminManagerTech")
        void idSendReminder(String value, String forNameTest){
        given()
                .filter(withCustomTemplates())
                .header("Authorization", value)
                .contentType(JSON)
                .pathParam("id", 2000)
                .when()
                .log().all()
                .post("/rest/tasks/{id}/send_reminder")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"));

    }
}
