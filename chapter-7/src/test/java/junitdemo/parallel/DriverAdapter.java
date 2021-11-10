package junitdemo.parallel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;

public class DriverAdapter {
    private final int WAIT_FOR_ELEMENT_TIMEOUT = 30;
    private ThreadLocal<WebDriver> webDriver;
    private ThreadLocal<WebDriverWait> webDriverWait;
    private ThreadLocal<Actions> actions;

    public void start(Browser browser, String testName) {
        webDriver = new ThreadLocal<>();
        webDriverWait = new ThreadLocal<>();
        actions = new ThreadLocal<>();

        var capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserVersion", "latest");
        HashMap<String, Object> ltOptions = new HashMap<>();
        String username = System.getenv("LT_USERNAME");
        String accessKey = System.getenv("LT_ACCESS_KEY");
        ltOptions.put("user",  username);
        ltOptions.put("accessKey",accessKey);

        ltOptions.put("build", "your build name");

        ltOptions.put("name", testName);

        ltOptions.put("platformName", "Windows 10");
        ltOptions.put("browserName", "Chrome");
        ltOptions.put("browserVersion","95.0");
        ltOptions.put("console","true");
        ltOptions.put("network",true);
        ltOptions.put("visual",true);
        ltOptions.put("timezone","UTC+02:00");
        capabilities.setCapability("LT:Options", ltOptions);

        switch (browser) {
            case CHROME:
                capabilities.setCapability("browserName", browser.getName());
                break;
            case FIREFOX:
                capabilities.setCapability("browserName", browser.getName());
                break;
            case EDGE:
                capabilities.setCapability("browserName", browser.getName());
                break;
            case OPERA:
                capabilities.setCapability("browserName", browser.getName());
                break;
            case SAFARI:
                capabilities.setCapability("browserName", browser.getName());
                break;
            default:
                throw new IllegalArgumentException(browser.name());
        }

        try {
            String url = String.format("https://%s:%s@hub.lambdatest.com/wd/hub", username, accessKey);
            WebDriver remoteDriver = new RemoteWebDriver(new URL(url), capabilities);
            webDriver.set(remoteDriver);
        } catch (MalformedURLException e) {
            System.out.println("Invalid grid URL");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        actions.set(new Actions(webDriver.get()));
        webDriverWait.set(new WebDriverWait(webDriver.get(), Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT)));
    }

    public void start(Browser browser) {
        webDriver = new ThreadLocal<>();
        webDriverWait = new ThreadLocal<>();
        actions = new ThreadLocal<>();

        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                webDriver.set(new ChromeDriver());
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

    public void executeScript(String script, Object... args) {
        try {
            ((JavascriptExecutor)webDriver.get()).executeScript(script, args);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
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
