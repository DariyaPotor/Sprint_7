package apisteps;

import POJO.CourierData;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class CourierApi {

    private static final String COURIER_PATH = "/api/v1/courier";
    private static final String LOGIN_PATH = "/api/v1/courier/login";

    public Response createCourier(CourierData courierData) {
        return given()
                .contentType("application/json")
                .body(courierData)
                .when()
                .post(COURIER_PATH);
    }

    public Response deleteCourier(int courierId) {
        return given()
                .contentType("application/json")
                .body("{\"id\": \"" + courierId + "\"}")
                .when()
                .delete(COURIER_PATH + "/" + courierId);
    }

    public Response loginCourier(CourierData courierData) {
        return given()
                .contentType("application/json")
                .body(courierData)
                .when()
                .post(LOGIN_PATH);
    }
}