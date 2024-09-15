package couriertests;

import POJO.CourierData;
import apisteps.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;

public class CourierLoginTest {

    private static final String BASE_URI = "https://qa-scooter.praktikum-services.ru";
    private CourierApi courierApi;
    private int courierId;

    @Before
    public void setUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.filters(new AllureRestAssured());
        courierApi = new CourierApi();
        createCourier();
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            courierApi.deleteCourier(courierId);
        }
    }

    private void createCourier() {
        CourierData courierData = new CourierData("dvvdv", "12rr", "sasked");
        courierApi.createCourier(courierData);
    }

    @Test
    @Description("Курьер может авторизоваться")
    public void testCourierCanLogin() {
        CourierData courierData = new CourierData("dvvdv", "12rr", null);
        Response response = courierApi.loginCourier(courierData);

        response.then()
                .statusCode(200)
                .body("id", notNullValue());

        courierId = response.then().extract().path("id");
    }

    @Test
    @Description("Авторизация без логина возвращает ошибку")
    public void testLoginWithoutLogin() {
        CourierData notLoginData = new CourierData(null, "12rr", null);
        courierApi.loginCourier(notLoginData)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Авторизация без пароля возвращает ошибку")
    public void testLoginWithoutPassword() {
        CourierData notPasswordData = new CourierData("dvvdv", null, null);
        courierApi.loginCourier(notPasswordData)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @Description("Авторизация с неверными данными возвращает ошибку")
    public void testLoginWithIncorrectCredentials() {
        CourierData incorrectData = new CourierData("ccmfjfj", "jigjtjti", null);
        courierApi.loginCourier(incorrectData)
                .then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
}