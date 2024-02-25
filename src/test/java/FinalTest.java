import api.WallService;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AuthPage;
import pages.FeedPage;

import java.util.List;


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

    @Test
    public void deletePost() {
        new AuthPage()
                .signIn();

        new WallService()
                .postMessageOnTheWall();

        new WallService()
                .getIDandDeleted();

        new FeedPage()
                .goingToProfile();

        List<String> posts = new WallService()
                .getPostsText();

        Assert.assertFalse(textPost.contains(posts.get(0)));
    }

    @Test
    public void postPictureOnTheWallAndCheck() {
        new AuthPage()
                .signIn();

        new WallService()
                .postPictureOnTheWall();

        new FeedPage()
                .goingToProfile();

        List<Integer> postsID = new WallService()
                .getPostsID();

        Integer lastID = postsID.get(0);

        Assert.assertTrue(postsID.contains(lastID));
    }
}
