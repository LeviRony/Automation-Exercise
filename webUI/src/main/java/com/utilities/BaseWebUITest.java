package com.utilities;

import com.microsoft.playwright.*;
import org.testng.annotations.*;
import static com.configurations.BaseUri.urlAutomationExercise;
import static com.utilities.Ansi.*;

public class BaseWebUITest {

    protected static Playwright playwright;
    protected static Browser browser;
    protected BrowserContext context;
    protected static Page page;

    @BeforeClass(alwaysRun = true)
    public void launchBrowser() {
        System.out.println(BLUE + "<<<  Before class  >>>" + RESET);
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
                new BrowserType.LaunchOptions().setHeadless(false)
        );
    }

    @AfterClass(alwaysRun = true)
    public void closeBrowser() {
        System.out.println(BLUE + "Closing browser" + RESET);
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @BeforeMethod(alwaysRun = true)
    public void createContextAndPage() {
        System.out.println(BLUE + "<<<  Before method >>>" + RESET);
        context = browser.newContext();
        page = context.newPage();
        page.navigate(urlAutomationExercise());
    }

    @AfterMethod(alwaysRun = true)
    public void closeContext() {
        if (context != null) context.close();
    }
}