package api;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import pages.FeedPage;
import pojoClasses.Album;
import pojoClasses.SavedPhoto;
import pojoClasses.Server;
import utils.ResourceConstants;

import java.io.File;
import java.util.List;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static utils.ResourcesUtils.getResource;

public class WallService {

    private String BASE_URI = "API_HOST";
    private String owner_id = "owner_id";
    private String post_id = "post_id";
    private String OWNER_ID = "673888630";
    private String access_token = "access_token";
    private String TOKEN = "TOKEN";
    private String message = "message";
    private String MESSAGE = "My first api post";
    private String v = "v";
    private String V = "5.131";
    private String servers = "server";
    private String photos = "photo";
    private String hash = "hash";
    private String attachment = "attachment";

    File photo = new File(System.getProperty("user.dir") + "//src//main//resources//Крош.png");
    Gson gson = new Gson();

    public WallService postMessageOnTheWall() {
        given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.POST.getMethod())
                .queryParam(owner_id, OWNER_ID)
                .queryParam(access_token, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(message, MESSAGE)
                .queryParam(v, V)
                .contentType(ContentType.JSON)
                .when().post()
                .then().statusCode(SC_OK)
                .extract().body().jsonPath();
        return new WallService();
    }

    public List getPostsText() {
        List<String> postsText = given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.WALL_GET.getMethod())
                .queryParam(owner_id, OWNER_ID)
                .queryParam(access_token, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(v, V)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK)
                .extract().jsonPath().getList("response.items.text");
        return postsText;
    }

    public List getPostsID() {
        List<Integer> postsID = given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.WALL_GET.getMethod())
                .queryParam(owner_id, OWNER_ID)
                .queryParam(access_token, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(v, V)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK)
                .extract().jsonPath().getList("response.items.id");
        return postsID;
    }

    public WallService getIDandDeleted() {
        List<Integer> ids = given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.WALL_GET.getMethod())
                .queryParam(owner_id, OWNER_ID)
                .queryParam(access_token, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(v, V)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK)
                .extract().jsonPath().getList("response.items.id");

        given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.DELETE.getMethod())
                .queryParam(owner_id, OWNER_ID)
                .queryParam(post_id, ids.get(0))
                .queryParam(access_token, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(v, V)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK)
                .extract().jsonPath();
        return new WallService();
    }

    public void postPictureOnTheWall() {
        Album album = given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.GET_WALL_UPLOAD_SERVER.getMethod())
                .queryParam(owner_id, OWNER_ID)
                .queryParam(access_token, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(v, V)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK)
                .extract().body().as(Album.class);

        String s = given()
                .multiPart(photo)
                .when().post(album.getResponse().getUploadUrl())
                .then()
                .extract().body().asString();

        Server server = gson.fromJson(s, Server.class);

        String photoId = given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.SAVE_WALL_PHOTO.getMethod())
                .queryParam(servers, server.getServer())
                .queryParam(photos, server.getPhoto())
                .queryParam(hash, server.getHash())
                .queryParam(access_token, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(v, V)
                .when().post()
                .then()
                .extract().asString();

        SavedPhoto savedPhoto = gson.fromJson(photoId, SavedPhoto.class);
//        System.out.println(savedPhoto);
//        System.out.println(savedPhoto.getResponse().get(0).getId());

        s = given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.POST.getMethod())
                .queryParam(owner_id, OWNER_ID)
                .queryParam(access_token, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(message, "Привет, Крош")
                .queryParam(attachment, String.format("photo%s_%d", "673888630", savedPhoto.getResponse().get(0).getId()))
                .queryParam(v, V)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK)
                .extract().body().asString();
        System.out.println(s);
    }
}
