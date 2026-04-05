package OrderTest;

import io.restassured.response.Response;
import model.order.Order;
import model.order.OrderApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.containsString;

public class CreateOrderNegativTest {
    private OrderApi orderApi = new OrderApi();


    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderNotAuthTest() {

        Order order = new Order("61c0c5a71d1f82001bdaaa70");
        Response response = orderApi.createOrderNotAuthStep(order);

        response.then()
                .assertThat()
                .statusCode(401);
        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
    public void createOrderNotIngridientsTest() {

        Order order = new Order(null);
        Response response = orderApi.createOrderNotAuthStep(order);

        response.then()
                .assertThat()
                .statusCode(400)
                .body("message", containsString("Ingredient ids must be provided"));
        System.out.println(response.body().asString());
    }

    @Test
    @DisplayName("Создание заказа с неверным хэшем ингредиентов")
    public void createOrderNotValidIngridientsTest() {

        Order order = new Order("hfjfjgfjgjg");
        Response response = orderApi.createOrderNotAuthStep(order);

        response.then()
                .assertThat()
                .statusCode(500);
        System.out.println(response.body().asString());
    }
}
