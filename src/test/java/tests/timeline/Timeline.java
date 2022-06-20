package tests.timeline;

import config.ClientPropInterface;
import config.LinkPropInterface;
import config.TechPropInterface;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import preRequest.PreRequestToken;
import tests.TestBase;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;
import static tests.spec.Specs.response200;

@Tag("timeline")
public class Timeline extends TestBase {


    ClientPropInterface clientConfig = ConfigFactory.create(ClientPropInterface.class);
    TechPropInterface techConfig = ConfigFactory.create(TechPropInterface.class);

    @Test
    @DisplayName("Просмотр Таймлайна (Администратор)")
    void restTimelineAdmin(){
given()
        .filter(withCustomTemplates())
        .header("Authorization", PreRequestToken.getTokenAdmin())
        .queryParam("userId", techConfig.idTechUser())
        .queryParam("clientId", clientConfig.idClient())
        .contentType(JSON)
        .when()
        .get("/rest/timeline")
        .then().log().all()
        .spec(response200)
        .body("status", is("SUCCESS"))
        .body("data", notNullValue())
        .body("data", hasKey("calls"))
        .body("data", hasKey("sms"))
        .body("data", hasKey("statusChanges"));

    }

    @Test
    @DisplayName("Просмотр Таймлайна (Менеджер)")
    void restTimelineManager(){
given()
        .filter(withCustomTemplates())
        .header("Authorization", PreRequestToken.getTokenManager())
        .queryParam("userId", techConfig.idTechUser())
        .queryParam("clientId", clientConfig.idClient())
        .contentType(JSON)
        .when()
        .get("/rest/timeline")
        .then().log().all()
        .spec(response200)
        .body("status", is("SUCCESS"))
        .body("data", notNullValue())
        .body("data", hasKey("calls"))
        .body("data", hasKey("sms"))
        .body("data", hasKey("statusChanges"));

    }


    @Disabled("Этот тест возвращает 200, а должен 4ХХ")
    @Test
    @DisplayName("Негативная проверка: Просмотр Таймлайна (Техник)")
    void restTimelineTech(){
given()
        .filter(withCustomTemplates())
        .header("Authorization", PreRequestToken.getTokenTech())
        .queryParam("userId", techConfig.idTechUser()+7)
        .queryParam("clientId", clientConfig.idClient())
        .contentType(JSON)
        .when()
        .get("/rest/timeline")
        .then().log().all()
        .spec(response200)
        .body("status", is("SUCCESS"))
        .body("data", notNullValue())
        .body("data", hasKey("calls"))
        .body("data", hasKey("sms"))
        .body("data", hasKey("statusChanges"));

    }


}
