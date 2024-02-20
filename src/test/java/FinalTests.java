import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AuthPage;


public class FinalTests extends BaseTest {

    private String textPost = "My first api post";

    @Test
    public void VkTest() {
        String s = new AuthPage()
                .signIn()
                .postOnTheWall()
                .goingToProfile()
                .getTextFromPost();

        Assert.assertTrue(textPost.contains(s));
    }
}