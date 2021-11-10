package junitdemo.parallel;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.util.ArrayList;
import java.util.Optional;

public class TestResultLoggerExtension implements TestWatcher, AfterAllCallback {
    @Override
    public void afterAll(ExtensionContext context) throws Exception {
    }

    @Override
    public void testDisabled(ExtensionContext context, Optional<String> reason) {
        TestWatcher.super.testDisabled(context, reason);
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        TestWatcher.super.testSuccessful(context);
    }

    @Override
    public void testAborted(ExtensionContext context, Throwable cause) {
        TestWatcher.super.testAborted(context, cause);
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        var exceptionCapture = new ArrayList<>();
        exceptionCapture.add(cause.getMessage());
        WebTest.getDriver().executeScript("lambda-exceptions", exceptionCapture);
    }
}
