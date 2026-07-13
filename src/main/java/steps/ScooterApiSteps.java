package steps;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierLoginModel;
import model.CourierModel;
import static data.ApiData.*;
import static io.restassured.RestAssured.given;
import io.qameta.allure.Step;
import model.OrderModel;

public class ScooterApiSteps {

    @Step("Создание курьера")
    public static Response createCourier(CourierModel courier) {
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(COURIER_CREATE_PATH)
                .then()
                .extract().response();
    }

    @Step("Логин курьера в системе")
    public static Response courierInSystemLogin(CourierLoginModel courier) {
        return given()
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post(LOGIN_IN_SYSTEM_PATH)
                .then()
                .extract().response();
    }

    @Step("Создание заказа")
        public static Response createOrder(OrderModel order) {
        return given()
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post(CREATE_ORDER_PATH)
                .then()
                .extract().response();
    }

    @Step("Получение списка заказов")
        public static Response getOrderList() {
        return given()
                .get(GET_ORDERS_LIST_PATH)
                .then()
                .extract().response();
    }

    @Step("Логин и получение ID курьера")
    public static int loginAndGetCourierId(CourierLoginModel courier) {
        return courierInSystemLogin(courier)
                .then()
                .extract()
                .path("id");
    }

    @Step("Удаление курьера")
        public static Response deleteCourier(int courierId) {
        return given()
                .when()
                .delete(DELETE_COURIER_PATH + courierId)
                .then()
                .extract()
                .response();
    }

}
