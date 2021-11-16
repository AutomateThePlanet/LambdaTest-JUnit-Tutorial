package junitdemo.versionthree.todopage;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Assertions {
    private Map map;
    private WebDriverWait webDriverWait;

    public Assertions(WebDriverWait webDriverWait, Map map) {
        this.webDriverWait = webDriverWait;
        this.map = map;
    }

    public void assertLeftItems(int expectedCount) {
        var resultSpan = map.resultSpan();
        if (expectedCount == 1) {
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
}
