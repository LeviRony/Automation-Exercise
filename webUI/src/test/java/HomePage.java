import com.pageObjects.HomePageObjects;
import com.pageObjects.TestCasesObjects;
import com.utilities.BaseWebUITest;
import io.qameta.allure.*;
import org.testng.annotations.Test;


@Epic("Web")
@Feature("Home Page")
public class HomePage extends BaseWebUITest {


    @Severity(SeverityLevel.NORMAL)
    @Owner("Rony Levi")
    @Issue("JIRA-123")
    @Test(description = "Search Wikipedia for the term 'playwright'")
    public static void shouldSearchWiki() {
        HomePageObjects home = new HomePageObjects(page);
        TestCasesObjects test = new TestCasesObjects(page);

        home.runAccessibilityScan()
                .assertMarketingParagraphVisible()
                .goToTestCases();

        test.runAccessibilityScan()
                .assertMarketingParagraphVisible();
    }
}