package junitdemo.versiontwo;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ToDoAppPage extends WebPage {
    public ToDoAppPage(WebDriver driver, WebDriverWait webDriverWait, Actions actions) {
        super(driver, webDriverWait, actions);
    }

    @Override
    protected String getUrl() {
        return "https://todomvc.com/";
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
        getActions().click(todoInput).sendKeys(Keys.ENTER).perform();
    }
}
