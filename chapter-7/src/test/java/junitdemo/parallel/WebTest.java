package junitdemo.parallel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(TestResultListener.class)
@ExtendWith(TestResultLoggerExtension.class)
public abstract class WebTest {
    private static DriverAdapter driver;

    @BeforeEach
    public void setUp(TestInfo testInfo) {
        initializeDriver(testInfo.getDisplayName());
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    public static DriverAdapter getDriver() {
        if (driver == null){
            driver = new DriverAdapter();
        }
        return driver;
    }

    protected abstract void initializeDriver(String testName);
}
