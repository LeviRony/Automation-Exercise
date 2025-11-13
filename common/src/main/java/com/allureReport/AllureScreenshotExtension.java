package com.allureReport;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.lang.reflect.Field;

public class AllureScreenshotExtension implements AfterTestExecutionCallback {

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        boolean failed = context.getExecutionException().isPresent();
        if (!failed) {
            return;
        }

        Object testInstance = context.getRequiredTestInstance();
        Page page = resolvePageField(testInstance);

        if (page != null) {
            try {
                byte[] screenshot = page.screenshot(
                        new Page.ScreenshotOptions().setFullPage(true)
                );
                AllureAttachments.attachScreenshot(
                        screenshot,
                        "Failure screenshot - " + context.getDisplayName()
                );
            } catch (Exception ignored) {
            }
        }
    }


    private Page resolvePageField(Object testInstance) {
        Class<?> clazz = testInstance.getClass();

        while (clazz != null) {
            try {
                Field field = clazz.getDeclaredField("page");
                field.setAccessible(true);
                Object value = field.get(testInstance);
                if (value instanceof Page) {
                    return (Page) value;
                }
            } catch (NoSuchFieldException e) {
                clazz = clazz.getSuperclass();
                continue;
            } catch (IllegalAccessException e) {
                return null;
            }
        }
        return null;
    }
}