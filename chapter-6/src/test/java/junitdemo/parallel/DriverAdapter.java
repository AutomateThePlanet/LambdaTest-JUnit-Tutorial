package junitdemo.parallel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DriverAdapter {
    private final int WAIT_FOR_ELEMENT_TIMEOUT = 30;
    private ThreadLocal<WebDriver> webDriver;
    private ThreadLocal<WebDriverWait> webDriverWait;
    private ThreadLocal<Actions> actions;

    public void start(Browser browser) {
        webDriver = new ThreadLocal<>();
        webDriverWait = new ThreadLocal<>();
        actions = new ThreadLocal<>();

        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                webDriver.set(new ChromeDriver());;
                break;
            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                webDriver.set(new FirefoxDriver());
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                webDriver.set(new EdgeDriver());
                break;
            case OPERA:
                WebDriverManager.operadriver().setup();
                webDriver.set(new OperaDriver());
                break;
            case SAFARI:
                webDriver.set(new SafariDriver());
                break;
            default:
                throw new IllegalArgumentException(browser.name());
        }

        actions.set(new Actions(webDriver.get()));
        webDriverWait.set(new WebDriverWait(webDriver.get(), Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT)));
    }

    public void quit() {
        webDriver.get().quit();
    }

    public void goToUrl(String url) {
        webDriver.get().navigate().to(url);
    }

    public void validateInnerTextIs(WebElement resultElement, String expectedText){
        webDriverWait.get().until(ExpectedConditions.textToBePresentInElement(resultElement, expectedText));
    }

    public WebElement findElement(By locator){
        return webDriverWait.get().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public Actions getActions() {
        return actions.get();
    }
}
