package junitdemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.Duration.ofMinutes;

public class FirstSeleniumTests {
    private WebDriver driver;

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
    }

    @Test
    public void properCheckboxSelected() throws Exception {
        driver.navigate().to("https://lambdatest.github.io/sample-todo-app/");

        LocalDate birthDay = LocalDate.of(1990, 10, 20);
        // us 10/20/1990
        DateTimeFormatter usDateFormat = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String dateToType = usDateFormat.format(birthDay);

        WebElement todoInput = driver.findElement(By.id("sampletodotext"));
        todoInput.sendKeys(dateToType);

        var addButton = driver.findElement(By.id("addbutton"));
        addButton.click();

        var todoCheckboxes = driver.findElements(By.xpath("//li[@ng-repeat]/input"));

        todoCheckboxes.get(2).click();

        var todoInfos = driver.findElements(By.xpath("//li[@ng-repeat]/span"));

        Assertions.assertEquals("20-10-1990", todoInfos.get(5).getText());

        String expectedUrl = "https://lambdatest.github.io/sample-todo-app/";
        Assertions.assertTrue(expectedUrl.equals(driver.getCurrentUrl()), "URL does not match");

        String notExpectedUrl = "https://www.lambdatest.com/";
        Assertions.assertFalse(notExpectedUrl.equals(driver.getCurrentUrl()), "URL match");

        var expectedItems = new String[] {"First Item", "Second Item", "Third Item", "Fourth Item", "Fifth Item", "20-10-1990"};
        var actualToDoInfos  = todoInfos.stream().map(e -> e.getText()).toArray();
        Assertions.assertArrayEquals(expectedItems, actualToDoInfos);

        Exception exception = Assertions.assertThrows(ArithmeticException.class, () -> new Calculator().divide(1, 0));

        Assertions.assertEquals("/ by zero", exception.getMessage());

        Assertions.assertTimeout(ofMinutes(2), () -> {
            // perform your tasks
        });

        Assertions.assertAll(
                () -> Assertions.assertTrue(expectedUrl.equals(driver.getCurrentUrl()), "URL does not match"),
                () -> Assertions.assertFalse(notExpectedUrl.equals(driver.getCurrentUrl()), "URL match"),
                () -> Assertions.assertArrayEquals(expectedItems, actualToDoInfos)
        );

        double actualDoubleValue= 2.999;
        double expectedDoubleValue = 3.000;

        Assertions.assertEquals(expectedDoubleValue, actualDoubleValue, 0.001);

        var currentTime = LocalDateTime.now();
        var currentTimeInPast = LocalDateTime.now().minusMinutes(3);

        DateTimeAssert.assertEquals(currentTime, currentTimeInPast, DateTimeDeltaType.MINUTES, 4);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
