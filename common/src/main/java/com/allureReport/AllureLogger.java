package com.allureReport;

import io.qameta.allure.Step;

public final class AllureLogger {

    @Step("{message}")
    public static void step(String message, Object... args) {
        if (args != null && args.length > 0) {
            message = java.text.MessageFormat.format(message, args);
        }
        System.out.println("[ALLURE] " + message);
    }

    // Example: AllureLogger.step("User logged in with email: {0}", email);
}
