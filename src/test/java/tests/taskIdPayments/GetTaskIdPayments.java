package tests.taskIdPayments;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import preRequest.PreRequestToken;
import tests.TestBase;

import java.util.stream.Stream;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static tests.spec.Specs.response200;

@DisplayName("Запрос информации об оплате по id задачи")
public class GetTaskIdPayments extends TestBase {

    static Stream<Arguments> preRequestParamTokenAdminManagerTech(){
        return Stream.of
                (Arguments.of(PreRequestToken.getTokenAdmin(), "Админ"),
                        Arguments.of(PreRequestToken.getTokenManager(), "Менеджер"),
                        Arguments.of(PreRequestToken.getTokenTech(), "Техник")
                );
    }

    @ParameterizedTest( name = "{1} запрашивает информацию оплат по id задачи")
    @MethodSource(value = "preRequestParamTokenAdminManagerTech")
    void taskIdPayments(String value, String forNameTest) {
        given()
                .filter(withCustomTemplates())
                .header("Authorization", value)
                .when()
                .get("/rest/tasks/2000/payment")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data.task.id", hasItem(2000));

    }


}
