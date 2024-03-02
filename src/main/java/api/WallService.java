package api;

import com.google.gson.Gson;
import io.restassured.http.ContentType;
import pojo.Album;
import pojo.SavedPhoto;
import pojo.Server;
import utils.ResourceConstants;

import java.io.File;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.hc.core5.http.HttpStatus.SC_OK;
import static utils.ResourcesUtils.getResource;

public class WallService {

    private String BASE_URI = "API_HOST";
    private String ownerId = "owner_id";
    private String postId = "post_id";
    private String OWNER_ID = "673888630";
    private String accessToken = "access_token";
    private String TOKEN = "TOKEN";
    private String messageField = "message";
    private String apiVersion = "v";
    private String API_VERSION = "5.131";
    private String servers = "server";
    private String photos = "photo";
    private String hash = "hash";
    private String attachment = "attachment";

    File photo = new File(System.getProperty("user.dir") + "//src//main//resources//Крош.png");
    Gson gson = new Gson();

    public WallService postMessageOnTheWall(String messageValue, String apiVers) {
        given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.POST.getMethod())
                .queryParam(ownerId, OWNER_ID)
                .queryParam(accessToken, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(messageField, messageValue)
                .queryParam(apiVersion, apiVers)
                .contentType(ContentType.JSON)
                .when().post()
                .then().statusCode(SC_OK)
                .extract().body().jsonPath();
        return new WallService();
    }

    public List<String> getPostsText() {
        List<String> postsText = given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.WALL_GET.getMethod())
                .queryParam(ownerId, OWNER_ID)
                .queryParam(accessToken, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(apiVersion, API_VERSION)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK)
                .extract().jsonPath().getList("response.items.text");
        return postsText;
    }

    public List<Integer> getPostsID() {
        List<Integer> postsID = given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.WALL_GET.getMethod())
                .queryParam(ownerId, OWNER_ID)
                .queryParam(accessToken, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(apiVersion, API_VERSION)
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
                .queryParam(ownerId, OWNER_ID)
                .queryParam(accessToken, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(apiVersion, API_VERSION)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK)
                .extract().jsonPath().getList("response.items.id");

        given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.DELETE.getMethod())
                .queryParam(ownerId, OWNER_ID)
                .queryParam(postId, ids.get(0))
                .queryParam(accessToken, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(apiVersion, API_VERSION)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK)
                .extract().jsonPath();
        return new WallService();
    }

    public void postPictureOnTheWall(String messageValue, String apiVers) {
        Album album = given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.GET_WALL_UPLOAD_SERVER.getMethod())
                .queryParam(ownerId, OWNER_ID)
                .queryParam(accessToken, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(apiVersion, apiVers)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK)
                .extract().body().as(Album.class);

        String postPhoto = given()
                .multiPart(photo)
                .when().post(album.getResponse().getUploadUrl())
                .then()
                .extract().body().asString();

        Server server = gson.fromJson(postPhoto, Server.class);

        String photoId = given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.SAVE_WALL_PHOTO.getMethod())
                .queryParam(servers, server.getServer())
                .queryParam(photos, server.getPhoto())
                .queryParam(hash, server.getHash())
                .queryParam(accessToken, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(apiVersion, apiVers)
                .when().post()
                .then()
                .extract().asString();

        SavedPhoto savedPhoto = gson.fromJson(photoId, SavedPhoto.class);

        postPhoto = given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.POST.getMethod())
                .queryParam(ownerId, OWNER_ID)
                .queryParam(accessToken, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(messageField, messageValue)
                .queryParam(attachment, String.format("photo%s_%d", "673888630", savedPhoto.getResponse().get(0).getId()))
                .queryParam(apiVersion, apiVers)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK)
                .extract().body().asString();
        System.out.println(postPhoto);
    }
}
