package com.utilities;

import com.CustomLogger;
import com.microsoft.playwright.*;
import com.pageObjects.*;
import org.testng.annotations.*;

import static com.configurations.BaseUri.urlAutomationExercise;
import static com.utilities.Ansi.*;

public class BaseWebUITest {
    protected static final CustomLogger log =
            CustomLogger.getLogger(CustomLogger.class);

    protected Page page;
    protected HomePageObjects home;
    protected TestCasesObjects testCases;
    protected SignupLoginPageObjects signupLogin;

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;

    @Parameters("browserName")
    @BeforeClass(alwaysRun = true)
    public void launchBrowser(@Optional("chromium") String browserName) {
        System.out.println(BLUE + "<<<  Before class  >>> Open Browser: " + browserName + RESET);

        playwright = Playwright.create();
        BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                .setHeadless(false); // set true on CI if needed

        switch (browserName.toLowerCase()) {
            case "firefox":
                browser = playwright.firefox().launch(options);
                break;
            case "webkit":
                browser = playwright.webkit().launch(options);
                break;
            /// >>>   FYI Edge is using Chromium engine
//            case "edge":
//                browser = playwright.chromium().launch(
//                        options.setChannel("msedge")
//                );
//                break;
            case "chromium":
            default:
                browser = playwright.chromium().launch(options);
                break;
        }
    }

    @AfterClass(alwaysRun = true)
    public void closeBrowser() {
        System.out.println(BLUE + "Closing browser " + RESET);

        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @BeforeMethod(alwaysRun = true)
    public void createContextAndPage() {
        System.out.println(BLUE + "<<<  Before method >>>" + RESET);

        context = browser.newContext();
        page = context.newPage();

        home = new HomePageObjects(page);
        testCases = new TestCasesObjects(page);
        signupLogin = new SignupLoginPageObjects(page);

        page.navigate(urlAutomationExercise());
    }

    @AfterMethod(alwaysRun = true)
    public void closeContext() {
        if (context != null) context.close();
    }
}