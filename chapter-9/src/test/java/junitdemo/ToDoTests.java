package junitdemo;

import com.google.common.base.Strings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

public class ToDoTests {
    private final int WAIT_FOR_ELEMENT_TIMEOUT = 30;
    private WebDriver driver;
    private WebDriverWait webDriverWait;
    private Actions actions;

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT));
        actions = new Actions(driver);
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
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
