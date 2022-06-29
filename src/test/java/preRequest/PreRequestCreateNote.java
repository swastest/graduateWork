package preRequest;

import com.github.javafaker.Faker;
import config.TeamPropInterface;
import config.TechPropInterface;
import modelPojo.forPreRequest.CreateOrUpdateNoteDto;
import org.aeonbits.owner.ConfigFactory;
import tests.TestBase;

import static io.restassured.RestAssured.given;
import static tests.spec.Specs.request;

public  class PreRequestCreateNote extends TestBase {

    static public Integer getIdNewNoteTeamFromAdmin() {
        TeamPropInterface teamConf = ConfigFactory.create(TeamPropInterface.class);
        Long epoch = System.currentTimeMillis();
        Faker faker = new Faker();
        String testTxt = faker.backToTheFuture().quote();

        CreateOrUpdateNoteDto body = new CreateOrUpdateNoteDto(epoch, teamConf.teamId(), 0, testTxt, 0);
        return  given()
               .spec(request)
                .header("Authorization", PreRequestToken.getTokenAdmin())
                .body(body)
                .when()
                .put( "/notes")
                .then().log().all()
                .extract().jsonPath().get("data.id");
    }
    static public Integer getIdNewNoteTeamFromManager() {
        TeamPropInterface teamConf = ConfigFactory.create(TeamPropInterface.class);
        Long epoch = System.currentTimeMillis();
        Faker faker = new Faker();
        String testTxt = faker.backToTheFuture().quote();

        CreateOrUpdateNoteDto body = new CreateOrUpdateNoteDto(epoch, teamConf.teamId(), 0, testTxt, 0);
        return given()
                .spec(request)
                .header("Authorization", PreRequestToken.getTokenManager())
                .body(body)
                .when()
                .put("/notes")
                .then().log().all()
                .extract().jsonPath().get("data.id");
    }

    static public Integer getIdNewNoteTechToSelf() {
        TechPropInterface techConfig = ConfigFactory.create(TechPropInterface.class);
        Long epoch = System.currentTimeMillis();
        Faker faker = new Faker();
        String testTxt = faker.backToTheFuture().quote();

        CreateOrUpdateNoteDto body = new CreateOrUpdateNoteDto(epoch, 0, 0, testTxt, techConfig.idTechUser());
        return given()
               .spec(request)
                .header("Authorization", PreRequestToken.getTokenTech())
                .body(body)
                .when()
                .put("/notes")
                .then().log().all()
                .extract().jsonPath().get("data.id");
    }

}
