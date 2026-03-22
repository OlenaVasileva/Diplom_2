package OrderTest;


import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.order.Order;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;




public class OrderApi {

    public OrderApi() {
        RestAssured.baseURI = "https://stellarburgers.education-services.ru/";
    }


    @Step("Создание заказа без авторизации")
    public Response createOrderNotAuthStep(Order order) {
        return given()
                .contentType(JSON)
                .body(order)
                .when()
                .post("api/orders");
    }

    @Step("Создание заказа c авторизацией")
    public Response createOrderWithAuthStep(String accessToken, Order order) {
        return given()
                .contentType(JSON)
                .header("Authorization", accessToken)
                .body(order)
                .when()
                .post("api/orders");
    }

    @Step("Получение заказов пользователя без авторизации")
    public Response getOrderUserNotAuthStep() {
        return given()
                .contentType(JSON)
                .when()
                .get("api/orders");
    }
    @Step("Получение заказов пользователя с авторизацией")
    public Response getOrderUserAuthStep(String accessToken) {
        return given()
                .contentType(JSON)
                .header("Authorization", accessToken)
                .when()
                .get("api/orders");
    }
}

