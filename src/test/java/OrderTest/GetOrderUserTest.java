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
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class GetOrderUserTest {

    private OrderApi orderApi = new OrderApi();
    private ApiUser apiUser = new ApiUser();
    private String accessToken;
    private User user;


    @Test
    @DisplayName("Получение заказа неавторизованнного пользователя")
    public void getOrderUser() {
        Response response = orderApi.getOrderUserNotAuthStep();
        response.then()
                .assertThat()
                .body("message", notNullValue())
                .statusCode(401);
        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("Получение заказа авторизованнного пользователя")
    public void getOrderUserAuth() {

        this.user=UserGenerator.createRandom();
        Response responseCreate = (Response) apiUser.createNewUserStep(this.user);
        assertEquals(200, responseCreate.statusCode(), "Не удалось создать пользователя через API");

        Response loginResponse = apiUser.loginUserStep(credsFrom(user));
        accessToken = loginResponse.as(UserLoginResponse.class).getAccessToken();
        assertEquals( 200, loginResponse.statusCode());

        Order order = new Order("61c0c5a71d1f82001bdaaa70");
        Response orderResponse = orderApi.createOrderWithAuthStep(accessToken, order);
        assertEquals(200, orderResponse.statusCode());


        Response response = orderApi.getOrderUserAuthStep(accessToken);
        response.then()
                .assertThat()
                .body("orders", notNullValue())
                .statusCode(200);
        System.out.println(response.body().asString());
    }
    @AfterEach
    public void tearDown() {
        apiUser.deleteUserStep(accessToken);
    }
}

