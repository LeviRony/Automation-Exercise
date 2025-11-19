package com.configurations;


public class BaseUri {

    public static final String ENV_TYPE =
            System.getProperty("tests.general.envType", "PROD");
            /// Options are: DEV | QA | STG | PROD

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
