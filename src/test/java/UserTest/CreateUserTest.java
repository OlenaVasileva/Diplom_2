package UserTest;

import io.restassured.response.Response;
import model.user.ApiUser;
import model.user.User;
import model.user.UserGenerator;
import model.user.UserLoginResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static model.user.UserCreds.credsFrom;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreateUserTest {
    private ApiUser apiUser = new ApiUser();
    private String accessToken;
    private User user;

    @Test
    @DisplayName("Создание пользователя")
    public void createNewUserTest() {

        this.user = UserGenerator.createRandom();
        Response responseCreate = (Response) apiUser.createNewUserStep(this.user);
        assertEquals(200, responseCreate.statusCode(), "Не удалось создать пользователя через API");
        
        Response loginResponse = apiUser.loginUserStep(credsFrom(user));
        accessToken = loginResponse.as(UserLoginResponse.class).getAccessToken();
       assertEquals(200, loginResponse.statusCode());
    }
    @Test
    @DisplayName("Создание пользователя ранее созданного")
    public void createUserTest() {

        User userToCreate = User.builder()
                .email("fghj@mail.ru")
                .password("qwerty")
                .name("Ваня")
                .build();
        Response response = apiUser.createNewUserStep(userToCreate);
        assertEquals(403, response.statusCode());
    }
    @Test
    @DisplayName("Создание пользователя без пароля")
    public void createUserWithoutPasswordTest() {
        User userToCreate = User.builder()
                .email("fghj@mail.ru")
                .password(null)
                .name("Ваня")
                .build();
        Response response = apiUser.createNewUserStep(userToCreate);
        assertEquals(403, response.statusCode());
    }
    @AfterEach
    public void tearDown() {
        apiUser.deleteUserStep(accessToken);
    }
}

