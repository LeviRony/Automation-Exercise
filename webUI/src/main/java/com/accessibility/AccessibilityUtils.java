package com.accessibility;

import com.allureReport.AllureAttachments;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Page.AddScriptTagOptions;
import io.qameta.allure.Step;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static com.configurations.BaseUri.RUN_ACCESSIBILITY_TEST;
import static com.utilities.Ansi.*;

public final class AccessibilityUtils {

    private static final String AXE_RESOURCE = "/axe.min.js";

    private AccessibilityUtils() {
    }

    @Step("Run Axe accessibility scan on current page")
    public static void runAxeScan(Page page) {
        try {
            String axeScript = loadResourceAsString(AXE_RESOURCE);
            page.addScriptTag(new AddScriptTagOptions().setContent(axeScript));
            String resultJson = (String) page.evaluate(
                    "async () => JSON.stringify(await axe.run())"
            );
            AllureAttachments.attachJson("Accessibility Report", resultJson);
            JSONObject report = new JSONObject(resultJson);
            if (!report.has("violations")) {
                AllureAttachments.attachText("Axe raw JSON (no 'violations')", resultJson);
                Assertions.fail("Axe result missing 'violations' key.");
            }

            JSONArray violations = report.getJSONArray("violations");

            int blockingCount = 0;
            StringBuilder summary = new StringBuilder();
            summary.append("Accessibility violations (")
                    .append(violations.length())
                    .append("):\n\n");

            for (int i = 0; i < violations.length(); i++) {
                JSONObject v = violations.getJSONObject(i);

                String id = v.optString("id", "unknown");
                String impact = v.optString("impact", "unknown");
                String desc = v.optString("description");
                String help = v.optString("helpUrl");

                summary.append("[").append(i + 1).append("] ")
                        .append(id)
                        .append(" (impact=")
                        .append(impact)
                        .append(")\n    ")
                        .append(desc)
                        .append("\n    More info: ")
                        .append(help)
                        .append("\n\n");

                if ("serious".equalsIgnoreCase(impact) || "critical".equalsIgnoreCase(impact)) {
                    blockingCount++;
                }
            }
            AllureAttachments.attachText("Accessibility Violations Summary", summary.toString());
            boolean strictMode = Boolean.parseBoolean(System.getProperty("a11y.strict", RUN_ACCESSIBILITY_TEST));

            // ==== CONSOLE OUTPUTS ====
            System.out.println("\n===============================");
            System.out.println(" A11Y Accessibility Report");
            System.out.println("===============================");
            System.out.println("Total violations: " + violations.length());
            System.out.println("Blocking (serious/critical): " + blockingCount);
            System.out.println("Strict mode: " + strictMode);
            System.out.println("===============================\n");

            if (!strictMode && blockingCount > 0) {
                System.out.println(YELLOW + "WARNING: Blocking issues found, but NOT failing (strict=false)" + RESET);
            }

            if (strictMode && blockingCount > 0) {
                System.out.println(RED + "FAILING due to blocking accessibility issues (strict=true)" + RESET);
                Assertions.fail("Blocking accessibility violations (serious/critical): " + blockingCount);
            } else if (strictMode) {
                System.out.println(GREEN + "Strict mode enabled: No blocking issues. Test PASSES." + RESET);
            }

            if (strictMode) {
                if (blockingCount > 0) {
                    Assertions.fail("Blocking accessibility violations (serious/critical): " + blockingCount);
                }
            } else {
                if (blockingCount > 0) {
                    AllureAttachments.attachText(
                            "Accessibility Blocking Count",
                            "Serious/Critical issues found: " + blockingCount +
                                    " (strict mode OFF — test NOT failed)"
                    );
                }
            }

        } catch (Exception e) {
            Assertions.fail("Failed to run accessibility scan: " + e.getMessage(), e);
        }
    }

    private static String loadResourceAsString(String resourcePath) {
        try (InputStream is = AccessibilityUtils.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IllegalStateException("Resource not found: " + resourcePath);
            }
            byte[] bytes = is.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load resource: " + resourcePath, e);
        }
    }
}