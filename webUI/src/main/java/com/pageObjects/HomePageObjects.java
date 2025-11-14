package com.pageObjects;

import com.allureReport.AllureLogger;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import com.accessibility.AccessibilityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class HomePageObjects {

    private static final Logger log = LoggerFactory.getLogger(HomePageObjects.class);
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
    // ---------- Actions / Assertions ----------

    /** Run accessibility scan on this page */
    public HomePageObjects runAccessibilityScan() {
        AccessibilityUtils.runAxeScan(page);
        return this;
    }

    /** Assert the marketing paragraph is visible and print its text */
    public HomePageObjects assertMarketingParagraphVisible() {
        Locator paragraph = marketingParagraph();
        assertThat(paragraph).isVisible();
        AllureLogger.step(paragraph.textContent());
        log.info(paragraph.textContent());
        return this;
    }

    /** Click on "Test Cases" button */
    public HomePageObjects goToTestCases() {
        testCasesButton().click();
        return this;
    }
}