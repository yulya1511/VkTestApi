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

    private static final String ATTACHMENT_PHOTO_TEMPLATE = "photo%s_%d";
    private static final String BASE_URI = "API_HOST";
    private static final String ownerIdField = "owner_id";
    private static final String postIdField = "post_id";
    private static final String accessTokenField = "access_token";
    private static final String messageField = "message";
    private static final String apiVersionField = "v";
    private static final String serverField = "server";
    private static final String photoField = "photo";
    private static final String attachmentField = "attachment";
    private static final String hashField = "hash";
    private static final String TOKEN = "TOKEN";

    // Отделить тестовые данные от логики
    private String ownerId = "673888630";
    private String apiVersion = "5.131";
    private File photo = new File(System.getProperty("user.dir") + "//src//main//resources//Крош.png");
    private Gson gson = new Gson();

    public WallService postMessageOnTheWall(String message, String apiVers) {
        given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.POST.getMethod())
                .queryParam(ownerIdField, ownerId)
                .queryParam(accessTokenField, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(messageField, message)
                .queryParam(apiVersionField, apiVers)
                .contentType(ContentType.JSON)
                .when().post()
                .then().statusCode(SC_OK);
        return new WallService();
    }

    public List<String> getPostsText() {
        List<String> postsText = given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.WALL_GET.getMethod())
                .queryParam(ownerIdField, ownerId)
                .queryParam(accessTokenField, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(apiVersionField, apiVersion)
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
                .queryParam(ownerIdField, ownerId)
                .queryParam(accessTokenField, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(apiVersionField, apiVersion)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK)
                .extract().jsonPath().getList("response.items.id");
        return postsID;
    }

    public WallService getIdAndDelete() {
        List<Integer> idList = given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.WALL_GET.getMethod())
                .queryParam(ownerIdField, ownerId)
                .queryParam(accessTokenField, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(apiVersionField, apiVersion)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK)
                .extract().jsonPath().getList("response.items.id");

        given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.DELETE.getMethod())
                .queryParam(ownerIdField, ownerId)
                .queryParam(postIdField, idList.get(0))
                .queryParam(accessTokenField, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(apiVersionField, apiVersion)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK);
        return new WallService();
    }

    public void postPictureOnTheWall(String message, String ownerId, String apiVers) {
        Album album = given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.GET_WALL_UPLOAD_SERVER.getMethod())
                .queryParam(ownerIdField, ownerId)
                .queryParam(accessTokenField, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(apiVersionField, apiVers)
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
                .queryParam(serverField, server.getServer())
                .queryParam(photoField, server.getPhoto())
                .queryParam(hashField, server.getHash())
                .queryParam(accessTokenField, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(apiVersionField, apiVers)
                .when().post()
                .then()
                .extract().asString();

        SavedPhoto savedPhoto = gson.fromJson(photoId, SavedPhoto.class);

        postPhoto = given()
                .baseUri(getResource(ResourceConstants.CONFIG.getResource(), BASE_URI))
                .basePath(ApiConstants.POST.getMethod())
                .queryParam(ownerIdField, ownerId)
                .queryParam(accessTokenField, getResource(ResourceConstants.CONFIG.getResource(), TOKEN))
                .queryParam(messageField, message)
                .queryParam(attachmentField,
                        String.format(ATTACHMENT_PHOTO_TEMPLATE, ownerId, savedPhoto.getResponse().get(0).getId()))
                .queryParam(apiVersionField, apiVers)
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(SC_OK)
                .extract().body().asString();
        System.out.println(postPhoto);
    }
}
