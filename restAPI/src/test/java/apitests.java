
import com.utilities.BaseApiTest;
import com.utilities.Endpoints;
import com.utilities.JsonUtils;
import io.qameta.allure.*;
import org.testng.annotations.Test;


@Epic("BACKEND")
@Feature("Home Page")
public class apitests extends BaseApiTest {

    @Severity(SeverityLevel.NORMAL)
    @Owner("Rony Levi")
    @Issue("JIRA-123")
    @Test(description = "GET Product List")
    public void testGetProductList() {
        String response = api.get(Endpoints.PRODUCT_LIST, 200);
        System.out.println("Product List Response: ");
        JsonUtils.print(response);
    }
}