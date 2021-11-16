package junitdemo.versionone;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ToDoAppPage {
    private final WebDriver driver;
    private final WebDriverWait webDriverWait;
    private final Actions actions;

    public ToDoAppPage(WebDriver driver, WebDriverWait webDriverWait, Actions actions) {
        this.driver = driver;
        this.webDriverWait = webDriverWait;
        this.actions = actions;
    }

    public void open(){
        driver.navigate().to("https://todomvc.com/");
    }

    public void assertLeftItems(int expectedCount){
        var resultSpan = waitAndFindElement(By.xpath("//footer/*/span | //footer/span"));
        if (expectedCount == 1){
            var expectedText = String.format("%d item left", expectedCount);
            validateInnerTextIs(resultSpan, expectedText);
        } else {
            var expectedText = String.format("%d items left", expectedCount);
            validateInnerTextIs(resultSpan, expectedText);
        }
    }

    public WebElement getItemCheckbox(String todoItem){
        var xpathLocator = String.format("//label[text()='%s']/preceding-sibling::input", todoItem);
        return waitAndFindElement(By.xpath(xpathLocator));
    }

    public void openTechnologyApp(String technologyName){
        var technologyLink = waitAndFindElement(By.linkText(technologyName));
        technologyLink.click();
    }

    public void addNewToDoItem(String todoItem){
        var todoInput = waitAndFindElement(By.xpath("//input[@placeholder='What needs to be done?']"));
        todoInput.sendKeys(todoItem);
        actions.click(todoInput).sendKeys(Keys.ENTER).perform();
    }

    private void validateInnerTextIs(WebElement resultElement, String expectedText){
        webDriverWait.until(ExpectedConditions.textToBePresentInElement(resultElement, expectedText));
    }

    private WebElement waitAndFindElement(By locator){
        return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}
