package UserTest;

import io.restassured.response.Response;
import model.user.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static model.user.UserCreds.credsFrom;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class LoginUserTest {

    private ApiUser apiUser = new ApiUser();
    private String accessToken;
    private User user;

    @Test
    @DisplayName ("Авторизация с существующим логином")
    public void existingUserLoginTest() {
        this.user= UserGenerator.createRandom();
        Response responseCreate = (Response) apiUser.createNewUserStep(this.user);
        assertEquals(200, responseCreate.statusCode(), "Не удалось создать пользователя через API");

    UserCreds creds = credsFrom(user);
    Response loginResponse = apiUser.loginUserStep(creds);
    String accessToken = loginResponse.as(UserLoginResponse.class).getAccessToken();
    assertEquals(200, loginResponse.statusCode());
    assertNotNull(accessToken, "Полученный токен пуст");
}

    @Test
    @DisplayName("Авторизация с неверными данными")
    public void invalidLoginTest() {
        UserCreds creds = new UserCreds("fhfbdfh@mail.ru", "some-password");
        Response loginResponse = apiUser.loginUserStep(creds);
        assertEquals(401, loginResponse.statusCode());
    }

    @AfterEach
    public void tearDown() {
        apiUser.deleteUserStep(accessToken);
    }
}
