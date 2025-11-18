
import com.utilities.BaseWebUITest;
import io.qameta.allure.*;
import org.testng.annotations.Test;


@Epic("Web")
@Feature("Home Page")
public class HomePage extends BaseWebUITest {


    @Severity(SeverityLevel.NORMAL)
    @Owner("Rony Levi")
    @Issue("JIRA-123")
    @Test(description = "Browse to homepage, and login to website'")
    public void browseAndLogin() {
        home.runAccessibilityScan().assertMarketingParagraphVisible()
                .goToTestCases();
        testCases.runAccessibilityScan().assertMarketingParagraphVisible();
        signupLogin.login(home,"rony@mail.com", "1234");
    }

}