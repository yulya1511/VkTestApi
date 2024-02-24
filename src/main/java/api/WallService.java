package api;

import io.restassured.http.ContentType;
import pages.FeedPage;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;

public class WallService {

    private String BASE_URI = "https://api.vk.com/method/";
    private String GET = "wall.get?";
    private String owner_id = "owner_id";
    private String post_id = "post_id";
    private String OWNER_ID = "673888630";
    private String access_token = "access_token";
    private String TOKEN = "vk1.a.SfUDWWTzE08-nGed9CgF77M2xkBe3O0yT4tHESmaJwdxD9y_UhDF45DU6DAa80vm98Y2XctjkB3NS3l9QVSRIErqruev9iJP00VemVDsO6Zoa3cv5nIUZvs9JsW2eix7aGTkc3yQ16Xle7dERr5zt6SfIY_9FCMbXE6DxglREOQhPrTut7-p3jt_ED43N58dX1xCOTILWUrr50dufQ-daw";
    private String message = "message";
    private String MESSAGE = "My first api post";
    private String v = "v";
    private String V = "5.131";

    public void getWallUploadServer (){
        String s =  given()
                .baseUri(BASE_URI)
                .basePath("photos.getWallUploadServer?")
                .queryParam(owner_id, OWNER_ID)
                .queryParam(access_token, TOKEN)
                .queryParam(v, V)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK)
                .extract().body().asString();
        System.out.println(s);
    }

    public FeedPage postMessageOnTheWall() {
        given()
                .baseUri(BASE_URI)
                .basePath(ApiConstants.POST.getMethod())
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

    public FeedPage getIDandDeleted() {
        List<Integer> ids = given()
                .baseUri(BASE_URI)
                .basePath(GET)
                .queryParam(owner_id, OWNER_ID)
                .queryParam(access_token, TOKEN)
                .queryParam(v, V)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK)
                .extract().jsonPath().getList("response.items.id");

        given()
                .baseUri(BASE_URI)
                .basePath(ApiConstants.DELETE.getMethod())
                .queryParam(owner_id, OWNER_ID)
                .queryParam(post_id, ids.get(0))
                .queryParam(access_token, TOKEN)
                .queryParam(v, V)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK)
                .extract().jsonPath();
        return new FeedPage();
    }
}
