package apisteps;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import POJO.CreateOrderData;

public class OrderApi {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";

    public OrderApi() {
        RestAssured.baseURI = BASE_URI;
    }

    public Response createOrder(CreateOrderData orderData) {
        return RestAssured.given()
                .contentType(ContentType.JSON)
                .body(orderData)
                .when()
                .post("/api/v1/orders");
    }

    public Response getOrders() {
        return RestAssured.given()
                .when()
                .get("/api/v1/orders");
    }

    public Response getOrdersWithCourierId(int courierId) {
        return RestAssured.given()
                .queryParam("courierId", courierId)
                .when()
                .get("/api/v1/orders");
    }
}