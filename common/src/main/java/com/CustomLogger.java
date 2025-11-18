package com;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.utilities.Ansi.*;

public class CustomLogger {
    private final Logger log;

    private CustomLogger(Class<?> clazz) {
        this.log = LoggerFactory.getLogger(clazz);
    }

    public static CustomLogger getLogger(Class<?> clazz) {
        return new CustomLogger(clazz);
    }

    public void info(String msg) {
        log.info(GREEN + "[INFO] " + msg + RESET);
    }

    public void warn(String msg) {
        log.warn(YELLOW + "[WARN] " + msg + RESET);
    }

    public void error(String msg) {
        log.error(RED + "[ERROR] " + msg + RESET);
    }

    public void step(String msg) {
        log.info(BLUE + "➡ " + msg + RESET);
    }
}
