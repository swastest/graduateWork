package tests.taskIdPayments;

import modelPojo.Body;
import modelPojo.PaymentItem;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import preRequest.PreRequestToken;
import tests.TestBase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static tests.spec.Specs.response200;


@Tag("payment")
@DisplayName("Добавление оплат")
public class PutTaskIdPayment extends TestBase {

    static Stream<Arguments> preRequestParamTokenAdminManagerTech(){
        return Stream.of
                (Arguments.of(PreRequestToken.getTokenAdmin(), "Админ"),
                        Arguments.of(PreRequestToken.getTokenManager(), "Менеджер"),
                        Arguments.of(PreRequestToken.getTokenTech(), "Техник")
                );
    }
    @ParameterizedTest( name = "{1} добавляет оплату")
    @MethodSource(value = "preRequestParamTokenAdminManagerTech")
    void addsPayment(String value, String forNameTest){
    List<PaymentItem> list = new ArrayList<>();
    PaymentItem pyItem1 = new PaymentItem(10,"cash");
    PaymentItem pyItem2 = new PaymentItem(15,"credit");
    list.add(pyItem1);
    list.add(pyItem2);

    Body body = new Body(list);
    given()
            .filter(withCustomTemplates())
            .header("Authorization", value)
            .contentType(JSON)
            .pathParam("id","2001")
            .body(body)
            .when().log().all()
            .put("/rest/tasks/{id}/payment")
            .then().log().all()
            .spec(response200)
            .body("status", is("SUCCESS"));

}
    @ParameterizedTest( name = "{1} обновляет оплату")
    @MethodSource(value = "preRequestParamTokenAdminManagerTech")
    void editsPayment(String value, String forNameTest){
    List<PaymentItem> list = new ArrayList<>();
    PaymentItem pyItem1 = new PaymentItem(10,"cash");
    PaymentItem pyItem2 = new PaymentItem(15,"credit");
    list.add(pyItem1);
    list.add(pyItem2);

    Body body = new Body(list);
    given()
            .filter(withCustomTemplates())
            .header("Authorization", value)
            .contentType(JSON)
            .pathParam("id","2001")
            .body(body)
            .when().log().all()
            .post("/rest/tasks/{id}/payment")
            .then().log().all()
            .spec(response200)
            .body("status", is("SUCCESS"));

}
}
