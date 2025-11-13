package com.allureReport;

import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;

public final class AllureAttachments {

    private AllureAttachments() {
    }

    public static void attachScreenshot(byte[] screenshot, String name) {
        Allure.addAttachment(
                name,
                "image/png",
                new ByteArrayInputStream(screenshot),
                ".png"
        );
    }

    public static void attachText(String name, String content) {
        Allure.addAttachment(
                name,
                "text/plain",
                content
        );
    }

    public static void attachJson(String name, Object json) {
        Allure.addAttachment(
                name,
                "application/json",
                (String) json
        );
    }
}