package preRequest;

import config.AdminPropInterface;
import config.LinkPropInterface;
import config.ManagerPropInterface;
import config.TechPropInterface;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import tests.TestBase;

import static io.restassured.RestAssured.given;

public class PreRequestToken extends TestBase {

    static public String getTokenAdmin(){
        AdminPropInterface config = ConfigFactory.create(AdminPropInterface.class);
      Response r = given()
                .contentType(ContentType.JSON)
                .queryParam("password", config.passwordAdmin())
                .queryParam("email", config.emailAdmin())
              .post("/rest/auth")
              .then()
              .extract().response();
     String a =  r.getHeader("Auth-Token");
        System.out.println("ТОКЕН АДМИН === "+a);
        return a;
    }

    static  public String getTokenManager(){
        ManagerPropInterface config = ConfigFactory.create(ManagerPropInterface.class);
        Response r = given()
                .contentType(ContentType.JSON)
                .queryParam("password", config.passwordManager())
                .queryParam("email", config.emailManager())
                .post("/rest/auth")
                .then()
                .extract().response();
        String a =  r.getHeader("Auth-Token");
        System.out.println("ТОКЕН МЕНЕДЖЕР === "+a);
        return a;
    }


    static  public String getTokenTech(){
        TechPropInterface config = ConfigFactory.create(TechPropInterface.class);
        Response r = given()
                .contentType(ContentType.JSON)
                .queryParam("password", config.passwordTech())
                .queryParam("email", config.emailTech())
                .post("/rest/auth")
                .then()
                .extract().response();
        String a =  r.getHeader("Auth-Token");
        System.out.println("ТОКЕН ТЕХНИК === "+a);
        return a;
    }


}
