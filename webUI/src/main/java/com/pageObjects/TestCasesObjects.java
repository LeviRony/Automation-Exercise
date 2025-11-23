package com.pageObjects;

import com.CustomLogger;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.accessibility.AccessibilityUtils;
import io.qameta.allure.Step;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TestCasesObjects {

    private static final CustomLogger log = CustomLogger.getLogger(CustomLogger.class);
    private final Page page;

    public TestCasesObjects(Page page) {
        this.page = page;
    }

    // =========================================================
    ///                   Locators
    // =========================================================

    private Locator pageTitle() {
        return page.locator("b");
    }


    // =========================================================
    ///              Actions / Assertions
    // =========================================================


    @Step("Run accessibility scan on the current page (Axe-core)")
    public TestCasesObjects runAccessibilityScan() {
        AccessibilityUtils.runAxeScan(page);
        return this;
    }

    @Step("Validate that the marketing paragraph (bold text) is visible on the page")
    public void assertMarketingParagraphVisible() {
        Locator paragraph = pageTitle();
        assertThat(paragraph).isVisible();
        log.info(paragraph.textContent());
    }
}