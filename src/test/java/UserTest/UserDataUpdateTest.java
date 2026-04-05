package UserTest;

import io.restassured.response.Response;
import model.user.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static model.user.UserCreds.credsFrom;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.containsString;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class UserDataUpdateTest {
    private ApiUser apiUser = new ApiUser();
    private String accessToken;
    private User user;

    @Test
    @DisplayName("Изменение данных с авторизацией")
    public void updateUserDataWithAuthTest() {
        this.user = UserGenerator.createRandom();
        Response responseCreate = apiUser.createNewUserStep(this.user);
        assertEquals(200, responseCreate.statusCode(), "Не удалось создать пользователя через API");

        UserCreds creds = credsFrom(user);
        Response loginResponse = apiUser.loginUserStep(creds);
        accessToken = loginResponse.as(UserLoginResponse.class).getAccessToken();
        assertEquals(200, loginResponse.statusCode());

        UpdateUserRequest updatedUser = new UpdateUserRequest("dvw1cо2vf@mail.ru", "s7b0g73421");
        Response updateResponse = apiUser.updateUserStep(updatedUser, accessToken);

        updateResponse.then()
                .assertThat()
                .statusCode(200)
                .body("user", notNullValue());
        System.out.println(updateResponse.body().asString());

    }

    @Test
    @DisplayName("Изменение данных без авторизации")
    public void updateUserDataWithNotAuthTest()
    {
        this.user= UserGenerator.createRandom();
        Response responseCreate = apiUser.createNewUserStep(this.user);
        assertEquals(200, responseCreate.statusCode(), "Не удалось создать пользователя через API");

        UpdateUserRequest updatedUser = new UpdateUserRequest("teyty@mail.ru", "fhgj66hh");
        Response updateResponse = apiUser.updateUserNotAuthorizationStep(updatedUser);

        updateResponse.then()
                .assertThat()
                .statusCode(401)
                .body("message", containsString("You should be authorised"));
        System.out.println(updateResponse.body().asString());
    }

    @AfterEach
    public void tearDown() {
        if (accessToken != null) {
            apiUser.deleteUserStep(accessToken);
        }
    }
    }
