import client.ApiUser;
import io.restassured.response.Response;
import jdk.jfr.Description;
import model.User;
import org.junit.After;
import org.junit.Test;




import static model.UserGenegator.randomUser;
import static org.junit.Assert.assertEquals;

public class CreateUserTest {
    private ApiUser apiUser = new ApiUser();
    private String accessToken;

    @Test
    @Description("Создание пользователя")
    public void createNewUserTest() {

        User user = randomUser();
        Response response = apiUser.createNewUserStep(user);
        assertEquals(200, response.statusCode());
    }
    @Test
    @Description("Создание пользователя ранее созданного")
    public void createUserTest() {

        User user = new User("fghj@mail.ru", "qwerty", "Ваня");
        Response response = apiUser.createNewUserStep(user);
        assertEquals(403, response.statusCode());
    }
    @Test
    @Description("Создание пользователя без пароля")
    public void createUserWithoutPasswordTest() {

        User user = new User("fghj@mail.ru", null, "Ваня");
        Response response = apiUser.createNewUserStep(user);
        assertEquals(403, response.statusCode());
    }
    @After
    public void tearDown() {
        apiUser.deleteUserStep(accessToken);
    }
}

