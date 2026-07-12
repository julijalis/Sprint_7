import org.junit.Test;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;
import static steps.ScooterApiSteps.getOrderList;

public class GetOrderListTest extends BaseApiTest {

    @Test
    public void shouldGetOrderList() {
        getOrderList()
                .then()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders.size()", greaterThan(0));
    }
}
