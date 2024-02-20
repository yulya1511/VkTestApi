package pages;

import io.restassured.http.ContentType;
import org.openqa.selenium.By;

import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static io.restassured.RestAssured.given;
import static utils.WaitUtils.waitUntilPresenceOfElementLocated;


public class FeedPage {

    private String BASE_URI = "https://api.vk.com/method/";
    private String POST = "wall.post?";
    private String owner_id = "owner_id";
    private String OWNER_ID = "673888630";
    private String access_token = "access_token";
    private String TOKEN = "vk1.a.SfUDWWTzE08-nGed9CgF77M2xkBe3O0yT4tHESmaJwdxD9y_UhDF45DU6DAa80vm98Y2XctjkB3NS3l9QVSRIErqruev9iJP00VemVDsO6Zoa3cv5nIUZvs9JsW2eix7aGTkc3yQ16Xle7dERr5zt6SfIY_9FCMbXE6DxglREOQhPrTut7-p3jt_ED43N58dX1xCOTILWUrr50dufQ-daw";
    private String message = "message";
    private String MESSAGE = "My first api post";
    private String v = "v";
    private String V = "5.131";


    public FeedPage postOnTheWall() {
        given()
                .baseUri(BASE_URI)
                .basePath(POST)
                .queryParam(owner_id, OWNER_ID)
                .queryParam(access_token, TOKEN)
                .queryParam(message, MESSAGE)
                .queryParam(v, V)
                .contentType(ContentType.JSON)
                .when().post()
                .then().statusCode(SC_OK)
                .extract().body().jsonPath();
        return new FeedPage();
    }

    public ProfilePage goingToProfile() {
        waitUntilPresenceOfElementLocated(By.xpath("//span[text()='My profile']")).click();
        return new ProfilePage();
    }
}
