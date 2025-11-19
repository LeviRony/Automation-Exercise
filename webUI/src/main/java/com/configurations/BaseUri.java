package com.configurations;


public class BaseUri {

    // =========================================================
    ///           Options are: true | false
    // =========================================================
    public static final String RUN_ACCESSIBILITY_TEST =
            System.getProperty("tests.general.accessibility", "false");

    // =========================================================
    ///           Options are: DEV | QA | STG | PROD
    // =========================================================
    protected static final String ENV_TYPE =
            System.getProperty("tests.general.envType", "PROD");

    public static String urlAutomationExercise() {
        return switch (ENV_TYPE) {
            case "DEV" -> "https://dev.automationexercise.com/";
            case "QA" -> "";
            case "STG" -> "https://stag.automationexercise.com/";
            case "PROD" -> "https://automationexercise.com/";
            default -> throw new IllegalStateException("Unexpected value: " + ENV_TYPE);
        };
    }
}
