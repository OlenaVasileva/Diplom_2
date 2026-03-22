package UserTest;

import io.restassured.response.Response;
import model.user.ApiUser;
import model.user.User;
import model.user.UserLoginResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static model.user.UserCreds.credsFrom;
import static model.user.UserGenegator.randomUser;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreateUserTest {
    private ApiUser apiUser = new ApiUser();
    private String accessToken;

    @Test
    @DisplayName("Создание пользователя")
    public void createNewUserTest() {

        User user = randomUser();
        Response response = apiUser.createNewUserStep(user);
        assertEquals(200, response.statusCode());
        Response loginResponse = apiUser.loginUserStep(credsFrom(user));
        accessToken = loginResponse.as(UserLoginResponse.class).getAccessToken();
       assertEquals(200, response.statusCode());
    }
    @Test
    @DisplayName("Создание пользователя ранее созданного")
    public void createUserTest() {

        User user = new User("fghj@mail.ru", "qwerty", "Ваня");
        Response response = apiUser.createNewUserStep(user);
        assertEquals(403, response.statusCode());
    }
    @Test
    @DisplayName("Создание пользователя без пароля")
    public void createUserWithoutPasswordTest() {

        User user = new User("fghj@mail.ru", null, "Ваня");
        Response response = apiUser.createNewUserStep(user);
        assertEquals(403, response.statusCode());
    }
    @AfterEach
    public void tearDown() {
        apiUser.deleteUserStep(accessToken);
    }
}

