import api.WallService;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AuthPage;
import pages.FeedPage;


public class FinalTest extends BaseTest {

    private String textPost = "My first api post";

    @Test
    public void vkTest() {
        new AuthPage()
                .signIn();

        new WallService()
                .postMessageOnTheWall();

        String s = new FeedPage()
                .goingToProfile()
                .getTextFromPost();

        Assert.assertTrue(textPost.contains(s));
    }

//    @Test
//    public void deletePost() {
//        new AuthPage()
//                .signIn()
//                .getIDandDeleted()
//                .goingToProfile();
//    }
//
//    @Test
//    public void postPictureOnTheWall() {
//        new AuthPage()
//                .signIn()
//                .getWallUploadServer();
//    }
}
