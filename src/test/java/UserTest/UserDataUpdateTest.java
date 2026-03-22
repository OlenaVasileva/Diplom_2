package UserTest;

import io.restassured.response.Response;
import model.user.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static model.user.UserCreds.credsFrom;
import static model.user.UserGenegator.randomUser;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserDataUpdateTest {
    private ApiUser apiUser = new ApiUser();
    private String accessToken;

    @Test
    @DisplayName("Изменение данных с авторизацией")
    public void existingUserLoginTest() {
        User user = randomUser();
        Response createResponse = apiUser.createNewUserStep(user);
        assertEquals(200, createResponse.statusCode());

        UserCreds creds = credsFrom(user);
        Response loginResponse = apiUser.loginUserStep(creds);
        String accessToken = loginResponse.as(UserLoginResponse.class).getAccessToken();
        assertEquals(200, loginResponse.statusCode());

        UpdateUserRequest updatedUser = new UpdateUserRequest("yyyttt@mail.ru", "fgvhjь");
        Response updateResponse = apiUser.updateUserStep(updatedUser, accessToken);
        assertEquals(200, updateResponse.statusCode());

    }

    @Test
    @DisplayName("Изменение данных без авторизации")
    public void existingUserLoginNotAuthorizationTest()
    {
        User user = randomUser();
        Response createResponse = apiUser.createNewUserStep(user);
        assertEquals(200, createResponse.statusCode());

        UserCreds creds = credsFrom(user);
        Response loginResponse = apiUser.loginUserStep(creds);
        String accessToken = loginResponse.as(UserLoginResponse.class).getAccessToken();
        assertEquals(200, loginResponse.statusCode());

        UpdateUserRequest updatedUser = new UpdateUserRequest("qbcg12o@mail.ru", "fgvhjь");
        Response updateResponse = apiUser.updateUserNotAuthorizationStep(updatedUser, accessToken);
        assertEquals(401, updateResponse.statusCode());

    }

    @AfterEach
    public void tearDown() {
        apiUser.deleteUserStep(accessToken);
    }
}