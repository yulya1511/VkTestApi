import api.WallService;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AuthPage;
import pages.FeedPage;
import utils.ResourceConstants;

import static utils.ResourcesUtils.getResource;

import java.util.List;


public class FinalTest extends BaseTest {

    WallService wallService = new WallService();
    private String textPost = "My first api post";

    @Test
    public void vkTest() {
        new AuthPage()
                .signIn(getResource(ResourceConstants.TEST_DATA.getResource(), "LOGIN"),
                        getResource(ResourceConstants.TEST_DATA.getResource(), "PASSWORD"));

        wallService.postMessageOnTheWall(getResource(ResourceConstants.TEST_DATA.getResource(), "MESSAGE_VALUE"),
                getResource(ResourceConstants.TEST_DATA.getResource(), "API_VERSION"));

        String s = new FeedPage()
                .goingToProfile()
                .getTextFromPost();

        Assert.assertTrue(textPost.contains(s));
    }

    @Test
    public void deletePost() {
        new AuthPage()
                .signIn(getResource(ResourceConstants.TEST_DATA.getResource(), "LOGIN"),
                        getResource(ResourceConstants.TEST_DATA.getResource(), "PASSWORD"));

        wallService.postMessageOnTheWall(getResource(ResourceConstants.TEST_DATA.getResource(), "MESSAGE_VALUE"),
                getResource(ResourceConstants.TEST_DATA.getResource(), "API_VERSION"));

        wallService.getIDandDeleted();

        new FeedPage()
                .goingToProfile();

        List<String> posts = new WallService()
                .getPostsText();

        Assert.assertFalse(textPost.contains(posts.get(0)));
    }

    @Test
    public void postPictureOnTheWallAndCheck() {
        new AuthPage()
                .signIn(getResource(ResourceConstants.TEST_DATA.getResource(), "LOGIN"),
                        getResource(ResourceConstants.TEST_DATA.getResource(), "PASSWORD"));

        wallService.postPictureOnTheWall("Привет, Крош",
                getResource(ResourceConstants.TEST_DATA.getResource(), "API_VERSION"));

        new FeedPage()
                .goingToProfile();

        List<Integer> postsID = new WallService()
                .getPostsID();

        Integer lastID = postsID.get(0);

        Assert.assertTrue(postsID.contains(lastID));
    }
}
