import data.TestData;
import io.restassured.response.Response;
import model.OrderModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.ScooterApiSteps;
import java.util.List;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderCreationTest extends BaseApiTest {
    private final List<String> color;

    public OrderCreationTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] getColor() {
        return new Object[][] {
                {List.of("BLACK")},
                {List.of("GREY")},
                {List.of("BLACK", "GREY")},
                {null}
        };
    }

    @Test
    public void shouldCreateOrderWithDifferentColors() {
        OrderModel order = new OrderModel(
                TestData.FIRST_NAME,
                TestData.LAST_NAME,
                TestData.ADDRESS,
                TestData.METRO_STATION,
                TestData.PHONE,
                TestData.RENT_TIME,
                TestData.DELIVERY_DATE,
                TestData.COMMENT,
                color
        );

        Response response = ScooterApiSteps.createOrder(order);

        response.then()
                .statusCode(201)
                .body("track", notNullValue());
    }
}
