import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AuthPage;


public class FinalTests extends BaseTest {

    private String textPost = "My first api post";

    @Test
    public void vkTest() {
        String s = new AuthPage()
                .signIn()
                .postMessageOnTheWall()
                .goingToProfile()
                .getTextFromPost();

        Assert.assertTrue(textPost.contains(s));
    }

    @Test
    public void deletePost(){
        new AuthPage()
                .signIn()
                .getIDandDeleted()
                .goingToProfile();
    }

    @Test
    public void postPictureOnTheWall(){
        new AuthPage()
                .signIn()
                .getWallUploadServer();
    }
}