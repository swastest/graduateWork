package tests;

import config.LinkPropInterface;
import io.restassured.RestAssured;
import org.aeonbits.owner.ConfigFactory;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {
    @BeforeAll
    static void setUp() {

        LinkPropInterface linkConfig = ConfigFactory.create(LinkPropInterface.class);
        RestAssured.baseURI = linkConfig.baseUrl();
    }
}
