import client.ApiUser;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import model.User;
import model.UserCreds;
import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static model.UserGenegator.randomUser;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginUserTest {
    private ApiUser apiUser = new ApiUser();
    private String accessToken;

    @Test
    @Description ("авторизация с существующим логином")
    public void loginPassLogin()
    {
        User user = randomUser();
        Response response = apiUser.createNewUserStep(user);
        Assert.assertEquals(200, response.statusCode());
        
    }

    @Test
    @Description("Авторизация с неверными данными")
    public void loginFailLogin() {
        UserCreds creds = new UserCreds("fhfbdfh@mail.ru", "some-password");
        Response loginResponse = apiUser.loginUserStep(creds);
        assertEquals(401, loginResponse.statusCode());
    }

    @After
    public void tearDown() {
        apiUser.deleteUserStep(accessToken);
    }
}
