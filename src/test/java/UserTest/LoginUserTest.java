package UserTest;

import io.restassured.response.Response;
import model.user.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static model.user.UserCreds.credsFrom;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class LoginUserTest {

    private ApiUser apiUser = new ApiUser();
    private User user;
    private String accessToken;

    @Test
    @DisplayName("Авторизация с существующим логином")
    public void existingUserLoginTest() {

        this.user = UserGenerator.createRandom();
        Response responseCreate = apiUser.createNewUserStep(this.user);
        assertEquals(200, responseCreate.statusCode(), "Не удалось создать пользователя через API");

        UserCreds creds = credsFrom(user);
        Response loginResponse = apiUser.loginUserStep(creds);
        accessToken = loginResponse.as(UserLoginResponse.class).getAccessToken();

        loginResponse.then()
                .assertThat()
                .statusCode(200)
                .body( "accessToken", notNullValue());
        System.out.println(loginResponse.body().asString());
    }

    @Test
    @DisplayName("Авторизация с неверными данными")
    public void invalidLoginTest() {
        UserCreds creds = new UserCreds("fhfbdfh@mail.ru", "some-password");
        Response loginResponse = apiUser.loginUserStep(creds);

        loginResponse.then()
                .assertThat()
                .statusCode(401)
                .body("message", equalTo("email or password are incorrect"));
        System.out.println(loginResponse.body().asString());
    }

    @AfterEach
    public void tearDown() {
        if (accessToken != null) {
            apiUser.deleteUserStep(accessToken);
        }
    }
}
