package junitdemo.parallel;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static junitdemo.parallel.WebTest.getDriver;

public class TestResultListener implements AfterTestExecutionCallback {
    private static final ThreadLocal<TestResult> CURRENT_TEST_RESULT = new ThreadLocal<>();

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        if (context.getExecutionException().isPresent()) {
            CURRENT_TEST_RESULT.set(TestResult.FAILURE);
        } else {
            CURRENT_TEST_RESULT.set(TestResult.SUCCESS);
        }

        getDriver().executeScript("lambda-status=" + CURRENT_TEST_RESULT.get().getResult());
    }
}
