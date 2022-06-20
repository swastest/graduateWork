package tests.taskIdPayments;

import config.AdminPropInterface;
import config.ManagerPropInterface;
import io.restassured.http.ContentType;
import modelPojo.RootRequestItem;
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

public class PaymentsTest extends TestBase {

    @Tag("payments")
    @Test
    @DisplayName("Оплата проставлена с аккаунта админа админу")
    void paymentAdminToAdminId() {
        AdminPropInterface config = ConfigFactory.create(AdminPropInterface.class);
        RootRequestItem item = new RootRequestItem();
        item.setDate(1653657301000l);
        item.setDistance(0);
        item.setName("");
        item.setTeamId(0);
        item.setUserId(config.idAdminUser());
        item.setTime(1653657301000l);

        given()
                .filter(withCustomTemplates())
                .contentType(ContentType.JSON)
                .body(new RootRequestItem[]{item})
                .header("Authorization", PreRequestToken.getTokenAdmin())
                .when()
                .post("/rest/payments")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data", notNullValue())
                .body("data.items", notNullValue())
                .body("data.items.collector", contains("Admin"))
                .body("data.items.paymentMethod", contains("cash"));
    }

    @Tag("payments")
    @Test
    @DisplayName("Оплата проставлена с аккаунта админа технику")
    void paymentAdminToTech() {
        RootRequestItem item = new RootRequestItem();
        item.setDate(1653657301000l);
        item.setDistance(0);
        item.setName("");
        item.setTeamId(0);
        item.setUserId(129);
        item.setTime(1653657301000l);

        given()
                .filter(withCustomTemplates())
                .contentType(ContentType.JSON)
                .body(new RootRequestItem[]{item})
                .header("Authorization", PreRequestToken.getTokenAdmin())
                .when()
                .post("/rest/payments")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data", notNullValue())
                .body("data.items", notNullValue());
    }

    @Tag("payments")
    @Test
    @DisplayName("Оплата проставлена с аккаунта менеджера самому себе")
    void paymentManagerToManager() {
        ManagerPropInterface config = ConfigFactory.create(ManagerPropInterface.class);
        RootRequestItem item = new RootRequestItem();
        item.setDate(1653657301000l);
        item.setDistance(0);
        item.setName("");
        item.setTeamId(0);
        item.setUserId(config.idManagerUser());
        item.setTime(1653657301000l);

        given()
                .filter(withCustomTemplates())
                .contentType(ContentType.JSON)
                .body(new RootRequestItem[]{item})
                .header("Authorization", PreRequestToken.getTokenManager())
                .when()
                .post("/rest/payments")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data", notNullValue())
                .body("data.items", notNullValue());
    }


    @Tag("payments")
    @Test
    @DisplayName("Оплата проставлена с аккаунта менеджера самому себе")
    void paymentManagerToTech() {
        RootRequestItem item = new RootRequestItem();
        item.setDate(1653657301000l);
        item.setDistance(0);
        item.setName("");
        item.setTeamId(0);
        item.setUserId(129);
        item.setTime(1653657301000l);

        given()
                .filter(withCustomTemplates())
                .contentType(ContentType.JSON)
                .body(new RootRequestItem[]{item})
                .header("Authorization", PreRequestToken.getTokenManager())
                .when()
                .post("/rest/payments")
                .then().log().all()
                .spec(response200)
                .body("status", is("SUCCESS"))
                .body("data", notNullValue())
                .body("data.items", notNullValue());
    }


    @Tag("payments")
    @Test
    @DisplayName("Негативная проверка: Оплата проставлена с аккаунта техника самому себе")
    void paymentTechnicianToTechnician() {
        RootRequestItem item = new RootRequestItem();
        item.setDate(1653657301000l);
        item.setDistance(0);
        item.setName("");
        item.setTeamId(0);
        item.setUserId(57);
        item.setTime(1653657301000l);


        given()
                .filter(withCustomTemplates())
                .contentType(ContentType.JSON)
                .body(new RootRequestItem[]{item})
                .header("Authorization", PreRequestToken.getTokenTech())
                .when()
                .post("/rest/payments")
                .then().log().all()
                .spec(response200)
                .body("status", is("ERROR"))
                .body("data", nullValue())
                .body("message", equalTo("FORBIDDEN"));
    }

}
