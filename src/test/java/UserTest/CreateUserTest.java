package UserTest;

import io.restassured.response.Response;
import model.user.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;


public class CreateUserTest {
    private ApiUser apiUser = new ApiUser();
    private String accessToken;
    private User user;

    @Test
    @DisplayName("Создание пользователя")
    public void createNewUserTest() {

        this.user = UserGenerator.createRandom();
        Response createResponse = apiUser.createNewUserStep(this.user);

        createResponse.then()
                .assertThat()
                .statusCode(200)
                .body("user", notNullValue());

        this.accessToken = createResponse.jsonPath().getString("accessToken");

        System.out.println(createResponse.body().asString());

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

        response.then()
                .assertThat()
                .statusCode(403)
                .body("message", equalTo("User already exists"));
        System.out.println(response.body().asString());
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

        response.then()
                .assertThat()
                .statusCode(403)
                .body("message", equalTo("Email, password and name are required fields"));
        System.out.println(response.body().asString());
    }

    @AfterEach
    public void tearDown() {
        if (accessToken != null) {
            apiUser.deleteUserStep(accessToken);
        }
    }
}

