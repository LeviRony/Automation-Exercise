package com.pageObjects;

import com.allureReport.AllureLogger;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.accessibility.AccessibilityUtils;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePageObjects {

    private final Logger log = LoggerFactory.getLogger(HomePageObjects.class);
    private final Page page;

    public HomePageObjects(Page page) {
        this.page = page;
    }

    // ---------- Locators ----------

    private Locator marketingParagraph() {
        return page.getByRole(AriaRole.PARAGRAPH)
                .filter(new Locator.FilterOptions()
                        .setHasText("All QA engineers can use this"));
    }

    private Locator testCasesButton() {
        return page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Test Cases")
        );
    }

    private Locator searchBox() {
        return page.getByRole(
                AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Search an example..."));
    }

    private Locator menuSignupLogin() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(" Signup / Login"));

    }

    private Locator menuLogout() {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(" Logout"));
    }


    // ---------- Actions / Assertions ----------

    @Step("")
    public HomePageObjects logoutVisible() {
        assertThat(menuLogout()).isVisible();
        return this;
    }

    @Step("")
    public HomePageObjects logoutClick() {
        menuLogout().click();
        return this;
    }

    @Step("")
    public HomePageObjects runAccessibilityScan() {
        AccessibilityUtils.runAxeScan(page);
        return this;
    }

    @Step("")
    public HomePageObjects assertMarketingParagraphVisible() {
        Locator paragraph = marketingParagraph();
        assertThat(paragraph).isVisible();
        AllureLogger.step(paragraph.textContent());
        log.info(paragraph.textContent());
        return this;
    }

    @Step("")
    public HomePageObjects goToTestCases() {
        testCasesButton().click();
        return this;
    }

    @Step("")
    public HomePageObjects signupOrLogin() {
        menuSignupLogin().click();
        return this;
    }
}