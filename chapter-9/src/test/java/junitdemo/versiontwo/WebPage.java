package junitdemo.versiontwo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class WebPage {
    private final WebDriver driver;
    private final WebDriverWait webDriverWait;
    private final Actions actions;

    public WebPage(WebDriver driver, WebDriverWait webDriverWait, Actions actions) {
        this.driver = driver;
        this.webDriverWait = webDriverWait;
        this.actions = actions;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public WebDriverWait getWebDriverWait() {
        return webDriverWait;
    }

    public Actions getActions() {
        return actions;
    }

    public void open() {
        driver.navigate().to(getUrl());
        waitForPageToLoad();
    }

    protected abstract String getUrl();

    protected void waitForPageToLoad() {
    }

    protected void validateInnerTextIs(WebElement resultElement, String expectedText){
        webDriverWait.until(ExpectedConditions.textToBePresentInElement(resultElement, expectedText));
    }

    protected WebElement waitAndFindElement(By locator){
        return webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}
