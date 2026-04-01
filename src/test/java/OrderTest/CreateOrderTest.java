package OrderTest;
import model.user.ApiUser;
import io.restassured.response.Response;
import model.order.Order;
import model.order.OrderApi;
import model.user.User;
import model.user.UserGenerator;
import model.user.UserLoginResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static model.user.UserCreds.credsFrom;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CreateOrderTest {
    private OrderApi orderApi = new OrderApi();
    private String accessToken;
    private ApiUser apiUser = new ApiUser();
    private User user;


    @Test
    @DisplayName("Создание заказа c авторизацией")
    public void createOrderWithAuthTest() {


        this.user=UserGenerator.createRandom();
        Response responseCreate = (Response) apiUser.createNewUserStep(this.user);
        assertEquals(200, responseCreate.statusCode(), "Не удалось создать пользователя через API");

        Response loginResponse = apiUser.loginUserStep(credsFrom(user));
        accessToken = loginResponse.as(UserLoginResponse.class).getAccessToken();
        assertEquals(200, loginResponse.statusCode());

        Order order = new Order("61c0c5a71d1f82001bdaaa70");
        Response response = orderApi.createOrderWithAuthStep(accessToken, order);
        assertEquals(200, response.statusCode());
        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderNotAuthTest() {

        Order order = new Order("61c0c5a71d1f82001bdaaa70");
        Response response = orderApi.createOrderNotAuthStep(order);
        assertEquals(401, response.statusCode());
        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderNotIngridientsTest() {

        Order order = new Order(null);
        Response response = orderApi.createOrderNotAuthStep(order);
        assertEquals(400, response.statusCode());
        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("Создание заказа с неверным хэшем ингредиентов")
    public void createOrderNotValidIngridientsTest() {

        Order order = new Order("hfjfjgfjgjg");
        Response response = orderApi.createOrderNotAuthStep(order);
        assertEquals(500, response.statusCode());
        System.out.println(response.body().asString());
    }
    @AfterEach
    public void tearDown() {
        apiUser.deleteUserStep(accessToken);
    }
}
