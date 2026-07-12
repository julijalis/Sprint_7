import model.CourierLoginModel;
import model.CourierModel;
import org.junit.After;
import org.junit.Test;
import static data.TestData.*;
import static org.hamcrest.Matchers.equalTo;
import static steps.ScooterApiSteps.*;

public class CreateCourierTest extends BaseApiTest {
    private Integer courierId;

    @Test
    public void createCourierSuccess() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        CourierLoginModel loginData = new CourierLoginModel(LOGIN, PASSWORD);

        createCourier(courier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));

        courierId = loginAndGetCourierId(loginData);
    }


    @Test
    public void cannotCreateSameCouriers() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        CourierLoginModel loginData = new CourierLoginModel(LOGIN, PASSWORD);

        createCourier(courier);
        courierId = loginAndGetCourierId(loginData);

        createCourier(courier)
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    public void cannotCreateCouriersWithSameLogin() {
        CourierModel courier1 = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        CourierLoginModel loginData = new CourierLoginModel(LOGIN, PASSWORD);
        CourierModel courier2 = new CourierModel(LOGIN, "7490", "Мария");

        createCourier(courier1);
        courierId = loginAndGetCourierId(loginData);

        createCourier(courier2)
                .then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    public void shouldNotCreateCourierWithoutLogin() {
        CourierModel courier = new CourierModel(null, PASSWORD, FIRSTNAME);
        createCourier(courier)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    public void shouldNotCreateCourierWithoutPassword() {
        CourierModel courier = new CourierModel(LOGIN, null, FIRSTNAME);
        createCourier(courier)
                .then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void cleanUp () {
        if (courierId != null) {
            deleteCourier(courierId);
        }
    }
}
