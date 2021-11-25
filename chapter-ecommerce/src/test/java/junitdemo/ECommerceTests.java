package junitdemo;

import com.google.common.base.Strings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chromium.ChromiumDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v85.emulation.Emulation;
import org.openqa.selenium.devtools.v95.network.Network;
import org.openqa.selenium.devtools.v95.network.model.ConnectionType;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

public class ECommerceTests {
    private final int WAIT_FOR_ELEMENT_TIMEOUT = 30;
    private WebDriver driver;
    private WebDriverWait webDriverWait;
    private Actions actions;

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
    }

    @ParameterizedTest(name = "{index}. buy product = {0}")
    @CsvSource(value = {
            "Chrome,720,540,.NET,T-Shirt,.NET Foundation Sweatshirt,12.00",
            "Firefox,720,540,.NET,T-Shirt,.NET Foundation Sweatshirt,12.00",
            "Chrome,720,540,.NET,T-Shirt,.NET Bot Black Sweatshirt,19.50"
    })
    public void testBuyProduct(String browser, int height, int width, String brandName, String itemType, String itemName, String expectedTotal) throws MalformedURLException {
        startBrowser(browser, height, width);

        login("demouser@microsoft.com", "Pass@word1", false);

        filterItems(brandName, itemType);

        addItemToCart(itemName);

        //assertTotal(expectedTotal);

        proceedToCheckout();

        completePurchase(new CheckoutDetails(), new PaymentDetails());

        // login and assert completed order
    }

    private void startBrowser(String browser, int height, int width) throws MalformedURLException {
        if (browser.equals("Chrome")){
            driver = new ChromeDriver();
        } else if (browser.equals("Firefox")) {
            driver = new FirefoxDriver();
        }

        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT));
        actions = new Actions(driver);
        driver.manage().window().setSize(new Dimension(width, height));

        driver.manage().deleteAllCookies();
    }

    private void startBrowser(String browser, String browserVersion, int height, int width, String locationCode, String networkType, String timeZone) throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("browserName", browser);
        capabilities.setCapability("browserVersion", browserVersion);
        var ltOptions = new HashMap<String, Object>();
        ltOptions.put("user", "yourUserName");
        ltOptions.put("accessKey", "accesskey");
        ltOptions.put("build", "your build name");
        ltOptions.put("name", "your test name");
        ltOptions.put("platformName", "Windows 10");
        ltOptions.put("browserName", browser);
        ltOptions.put("network", true);

        ltOptions.put("timezone",timeZone);
        ltOptions.put("geoLocation",locationCode);

        capabilities.setCapability("LT:Options", ltOptions);

        driver = new RemoteWebDriver(new URL("https://yourUserName:acessKey@hub.lambdatest.com/wd/hub"), capabilities);
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_FOR_ELEMENT_TIMEOUT));
        actions = new Actions(driver);
        driver.manage().window().setSize(new Dimension(width, height));

        driver.manage().deleteAllCookies();
    }

    private void login(String email, String password, Boolean shouldRememberMe) {
        driver.navigate().to("https://eshop-onweb-webinar-demo2.azurewebsites.net/Identity/Account/Login");

        var emailInput = waitAndFindElement(By.id("Input_Email"));
        var passwordInput = waitAndFindElement(By.id("Input_Password"));
        emailInput.sendKeys(email);
        passwordInput.sendKeys(password);
        if (shouldRememberMe) {
            var rememberMeCheckbox = waitAndFindElement(By.id("Input_RememberMe"));
            rememberMeCheckbox.click();
        }

        var loginButton = waitAndFindElement(By.tagName("button"));
        loginButton.click();
    }

    private void filterItems(String brandName, String itemType) {
        var brandInput = new Select(waitAndFindElement(By.id("CatalogModel_BrandFilterApplied")));
        if (brandName != null || !brandName.isEmpty()) {
            brandInput.selectByVisibleText(brandName);
        } else
        {
            brandInput.selectByVisibleText("All");
        }

        var itemTypeInput = new Select(waitAndFindElement(By.id("CatalogModel_TypesFilterApplied")));
        if (itemType != null || !itemType.isEmpty()) {
            itemTypeInput.selectByVisibleText(itemType);
        } else
        {
            itemTypeInput.selectByVisibleText("All");
        }

        var filterButton = waitAndFindElement(By.className("esh-catalog-send"));
        filterButton.click();
    }

    private void addItemToCart(String itemName) {
        String addToBasketXpath = String.format("//span[text()='%s']//parent::div//preceding-sibling::input", itemName);
        var addToBasketButton = waitAndFindElement(By.xpath(addToBasketXpath));
        addToBasketButton.click();
    }

    private void assertTotal(String expectedTotal) {
        var totalSection = waitAndFindElement(By.xpath("//section[text()='Total']//parent::article//following-sibling::article/section[2]"));
        validateInnerTextIs(totalSection, String.format("$ %s", expectedTotal));
    }

    private void proceedToCheckout() {
        var basketButton = waitAndFindElement(By.xpath("//input[@value='[ Checkout ]']"));
        basketButton.click();
    }

    private void completePurchase(CheckoutDetails checkoutDetails, PaymentDetails paymentDetails) {
        fillCheckoutDetails(checkoutDetails);
        fillPaymentDetails(paymentDetails);

        var submitButton = waitAndFindElement(By.xpath("//button[text()='[ Complete Purchase ]']"));
        submitButton.click();
    }

    private void fillCheckoutDetails(CheckoutDetails checkoutDetails) {
        var streetInput = waitAndFindElement(By.id("ShippingAddress_Street"));
        var cityInput = waitAndFindElement(By.id("ShippingAddress_City"));
        var stateInput = waitAndFindElement(By.id("ShippingAddress_State"));
        var countryInput = waitAndFindElement(By.id("ShippingAddress_Country"));
        var zipCodeInput = waitAndFindElement(By.id("ShippingAddress_ZipCode"));

        streetInput.sendKeys(checkoutDetails.getStreet());
        cityInput.sendKeys(checkoutDetails.getCity());
        stateInput.sendKeys(checkoutDetails.getState());
        countryInput.sendKeys(checkoutDetails.getCountry());
        zipCodeInput.sendKeys(checkoutDetails.getZipCode());
    }

    private void fillPaymentDetails(PaymentDetails paymentDetails) {
        var brandInput = new Select(waitAndFindElement(By.id("PaymentDetails_Brand")));
        var cardNumberInput = waitAndFindElement(By.id("PaymentDetails_CardNumber"));
        var cardHolderNameInput = waitAndFindElement(By.id("PaymentDetails_CardHolderName"));
        var expirationMonthInput = waitAndFindElement(By.id("PaymentDetails_ExperationMonth"));
        var expirationYearInput = waitAndFindElement(By.id("PaymentDetails_ExperationYear"));
        var cvcInput = waitAndFindElement(By.id("PaymentDetails_CVC"));

        brandInput.selectByVisibleText(paymentDetails.getCardBrand().getBrandName());
        cardNumberInput.sendKeys(paymentDetails.getCardNumber());
        cardHolderNameInput.sendKeys(paymentDetails.getCardHolderName());
        expirationMonthInput.sendKeys(paymentDetails.getExpirationMonth());
        expirationYearInput.sendKeys(paymentDetails.getExpirationYear());
        cvcInput.sendKeys(paymentDetails.getCvc());
    }

    private void validateInnerTextIs(WebElement resultElement, String expectedText){
        webDriverWait.until(ExpectedConditions.textToBePresentInElement(resultElement, expectedText));
    }

    private WebElement waitAndFindElement(By locator){
        var element = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", element);
        return element;
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
