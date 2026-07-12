import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierLoginModel;
import model.CourierModel;
import org.junit.After;
import org.junit.Test;
import static data.TestData.*;
import static java.net.HttpURLConnection.*;
import static org.hamcrest.Matchers.equalTo;
import static steps.ScooterApiSteps.*;

public class CreateCourierTest extends BaseApiTest {
    private Integer courierId;
    private CourierModel courier;
    private CourierLoginModel loginData;

    @Test
    @DisplayName("Create courier success")
    @Description("Testing that creating courier is successfully done")
    public void createCourierSuccess() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        CourierLoginModel loginData = new CourierLoginModel(LOGIN, PASSWORD);

        createCourier(courier)
                .then()
                .statusCode(HTTP_CREATED)
                .body("ok", equalTo(true));

        courierId = loginAndGetCourierId(loginData);
    }


    @Test
    @DisplayName("Can't create same couriers")
    @Description("Testing that same couriers can't be created")
    public void cannotCreateSameCouriers() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        CourierLoginModel loginData = new CourierLoginModel(LOGIN, PASSWORD);

        createCourier(courier);
        courierId = loginAndGetCourierId(loginData);

        createCourier(courier)
                .then()
                .statusCode(HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Can't create couriers with same login")
    @Description("Testing that couriers with same login can't be created")
    public void cannotCreateCouriersWithSameLogin() {
        CourierModel courier1 = new CourierModel(LOGIN, PASSWORD, FIRSTNAME);
        CourierLoginModel loginData = new CourierLoginModel(LOGIN, PASSWORD);
        CourierModel courier2 = new CourierModel(LOGIN, "7490", "Мария");

        createCourier(courier1);
        courierId = loginAndGetCourierId(loginData);

        createCourier(courier2)
                .then()
                .statusCode(HTTP_CONFLICT)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Shouldn't create courier without login")
    @Description("Testing that courier can't be created without login")
    public void shouldNotCreateCourierWithoutLogin() {
        CourierModel courier = new CourierModel(null, PASSWORD, FIRSTNAME);
        createCourier(courier)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Shouldn't create courier without password")
    @Description("Testing that courier can't be created without password")
    public void shouldNotCreateCourierWithoutPassword() {
        CourierModel courier = new CourierModel(LOGIN, null, FIRSTNAME);
        createCourier(courier)
                .then()
                .statusCode(HTTP_BAD_REQUEST)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void cleanUp () {
        if (courierId != null) {
            deleteCourier(courierId);
        }
    }
}
