import api.WallService;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AuthPage;
import pages.FeedPage;
import utils.ResourceConstants;

import java.util.List;

import static utils.ResourcesUtils.getResource;


public class FinalTest extends BaseTest {
    private static final String LOGIN = "LOGIN";
    private static final String PASSWORD = "PASSWORD";
    private String ownerId = "673888630";
    private String message = "Привет, Крош";
    private String textPost = "My first api post";
    private WallService wallService = new WallService();

    @Test
    public void vkTest() {
        new AuthPage()
                .signIn(getResource(ResourceConstants.TEST_DATA.getResource(), LOGIN),
                        getResource(ResourceConstants.TEST_DATA.getResource(), PASSWORD));

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
                .signIn(getResource(ResourceConstants.TEST_DATA.getResource(), LOGIN),
                        getResource(ResourceConstants.TEST_DATA.getResource(), PASSWORD));

        wallService.postMessageOnTheWall(getResource(ResourceConstants.TEST_DATA.getResource(), "MESSAGE_VALUE"),
                getResource(ResourceConstants.TEST_DATA.getResource(), "API_VERSION"));

        wallService.getIdAndDelete();

        new FeedPage()
                .goingToProfile();

        List<String> posts = new WallService()
                .getPostsText();

        Assert.assertFalse(textPost.contains(posts.get(0)));
    }

    @Test
    public void postPictureOnTheWallAndCheck() {
        new AuthPage()
                .signIn(getResource(ResourceConstants.TEST_DATA.getResource(), LOGIN),
                        getResource(ResourceConstants.TEST_DATA.getResource(), PASSWORD));

        wallService.postPictureOnTheWall(message, ownerId,
                getResource(ResourceConstants.TEST_DATA.getResource(), "API_VERSION"));

        new FeedPage()
                .goingToProfile();

        List<Integer> postsID = new WallService()
                .getPostsID();

        Integer lastID = postsID.get(0);

        Assert.assertTrue(postsID.contains(lastID));
    }
}
