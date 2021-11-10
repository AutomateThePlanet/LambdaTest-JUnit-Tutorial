package junitdemo.parallel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;

public class ToDoTestsCloud {
    private final int WAIT_FOR_ELEMENT_TIMEOUT = 30;
    private WebDriver driver;
    private WebDriverWait webDriverWait;
    private Actions actions;
    private String status = "failed";

    @BeforeEach
    public void setUp(TestInfo testInfo) throws MalformedURLException {
        var capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", "Chrome");
        capabilities.setCapability("browserVersion", "95.0");

        HashMap<String, Object> ltOptions = new HashMap<String, Object>();

        String username = System.getenv("LT_USERNAME");
        String accessKey = System.getenv("LT_ACCESS_KEY");

        ltOptions.put("user",  username);
        ltOptions.put("accessKey",accessKey);

        ltOptions.put("build", "First Run in the Cloud");

        ltOptions.put("name", testInfo.getDisplayName());

        ltOptions.put("platformName", "Windows 10");
        ltOptions.put("browserName", "Chrome");
        ltOptions.put("browserVersion","95.0");
        ltOptions.put("console","true");
        ltOptions.put("network",true);
        ltOptions.put("visual",true);
        ltOptions.put("timezone","UTC+02:00");
        capabilities.setCapability("LT:Options", ltOptions);

        try {
            String url = String.format("https://%s:%s@hub.lambdatest.com/wd/hub", username, accessKey);
            driver = new RemoteWebDriver(new URL(url), capabilities);
            webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT));
            actions = new Actions(driver);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @ParameterizedTest(name = "{index}. verify todo list created successfully when technology = {0}")
    @ValueSource(strings = {
            "Backbone.js",
            "AngularJS",
            "React",
            "Vue.js",
            "CanJS",
            "Ember.js",
            "KnockoutJS",
            "Marionette.js",
            "Polymer",
            "Angular 2.0",
            "Dart",
            "Elm",
            "Closure",
            "Vanilla JS",
            "jQuery",
            "cujoJS",
            "Spine",
            "Dojo",
            "Mithril",
            "Kotlin + React",
            "Firebase + AngularJS",
            "Vanilla ES6"
    })
    public void verifyToDoListCreatedSuccessfully_withParams(String technology){
        driver.navigate().to("https://todomvc.com/");
        openTechnologyApp(technology);
        addNewToDoItem("Clean the car");
        addNewToDoItem("Clean the house");
        addNewToDoItem("Buy Ketchup");
        getItemCheckbox("Buy Ketchup").click();

        assertLeftItems(2);
    }

    private void assertLeftItems(int expectedCount){
        var resultSpan = waitAndFindElement(By.xpath("//footer/*/span | //footer/span"));
        if (expectedCount == 1){
            var expectedText = String.format("%d item left", expectedCount);
            validateInnerTextIs(resultSpan, expectedText);
        } else {
            var expectedText = String.format("%d items left", expectedCount);
            validateInnerTextIs(resultSpan, expectedText);
        }
    }

    private void validateInnerTextIs(WebElement resultElement, String expectedText){
        webDriverWait.until(ExpectedConditions.textToBePresentInElement(resultElement, expectedText));
    }

    private WebElement getItemCheckbox(String todoItem){
        var xpathLocator = String.format("//label[text()='%s']/preceding-sibling::input", todoItem);
        return waitAndFindElement(By.xpath(xpathLocator));
    }

    private void openTechnologyApp(String technologyName){
        var technologyLink = waitAndFindElement(By.linkText(technologyName));
        technologyLink.click();
    }

    private void addNewToDoItem(String todoItem){
        var todoInput = waitAndFindElement(By.xpath("//input[@placeholder='What needs to be done?']"));
        todoInput.sendKeys(todoItem);
        actions.click(todoInput).sendKeys(Keys.ENTER).perform();
    }

    private WebElement waitAndFindElement(By locator){
        return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    @AfterEach
    public void tearDown(TestInfo testInfo) {
        if (driver != null) {
            driver.quit();
        }
    }
}
