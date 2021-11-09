package junitdemo.parallel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class WebTest {
    private DriverAdapter driver;

    @BeforeEach
    public void setUp() {
        initializeDriver();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    public DriverAdapter getDriver() {
        if (driver == null){
            driver = new DriverAdapter();
        }
        return driver;
    }

    protected abstract void initializeDriver();
}
