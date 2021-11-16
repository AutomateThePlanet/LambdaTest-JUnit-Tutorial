package junitdemo.versionone;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ToDoPageObjectsTests {
    private final int WAIT_FOR_ELEMENT_TIMEOUT = 30;
    private WebDriver driver;
    private WebDriverWait webDriverWait;
    private Actions actions;
    private MainPage mainPage;
    private ToDoAppPage toDoAppPage;

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT));
        actions = new Actions(driver);
        mainPage = new MainPage(driver, webDriverWait);
        toDoAppPage = new ToDoAppPage(driver, webDriverWait, actions);
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
    public void verifyToDoListCreatedSuccessfully_withParamsWithPageObjects(String technology){
        mainPage.open();
        mainPage.openTechnologyApp(technology);

        toDoAppPage.addNewToDoItem("Clean the car");
        toDoAppPage.addNewToDoItem("Clean the house");
        toDoAppPage.addNewToDoItem("Buy Ketchup");
        toDoAppPage.getItemCheckbox("Buy Ketchup").click();

        toDoAppPage.assertLeftItems(2);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
