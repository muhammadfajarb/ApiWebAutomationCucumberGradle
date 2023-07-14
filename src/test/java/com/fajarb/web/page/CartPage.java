package com.fajarb.web.page;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CartPage {
    WebDriver driver;
    private By itemName(String productName) {
        return By.xpath("//a[@class=\"hrefch\" and text()=\""+productName+"\"]");
    }
    By addToCartButton = By.xpath("//a[text()=\"Add to cart\"]");
    By cartMenu = By.xpath("//a[@class=\"nav-link\" and text()=\"Cart\"]");
    private By itemCartName(String productName) {
        return By.xpath("//tr[@class=\"success\"]/td[2][text()=\""+productName+"\"]");
    }
    By priceCollection = By.xpath("//tr[@class=\"success\"]/td[3]");
    By totalPrice = By.xpath("//h3[@id=\"totalp\"]");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public void addProductToCart(List<Map<String, String>> productItems) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        for (Map<String, String> productItem : productItems) {
            driver.get("https://www.demoblaze.com/");
            String item = productItem.get("item_name");

            WebElement productLinkElement = wait.until(ExpectedConditions.elementToBeClickable(itemName(item)));
            productLinkElement.click();

            WebElement addToCartButtonElement = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
            addToCartButtonElement.click();

            wait.until(ExpectedConditions.alertIsPresent());
            Alert alert = driver.switchTo().alert();
            alert.accept();
        }
    }

    public void goToCartPage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement cartMenuElement = wait.until(ExpectedConditions.elementToBeClickable(cartMenu));
        cartMenuElement.click();
    }

    public void checkIfContainItem(List<Map<String, String>> productItems) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        for (Map<String, String> productItem : productItems) {
            String item = productItem.get("item_name");
            WebElement itemCartNameElement = wait.until(ExpectedConditions.elementToBeClickable(itemCartName(item)));
            assertTrue(itemCartNameElement.isDisplayed());
        }
    }

    public void validateCartTotalPrice() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        List<WebElement> priceCollectionElements = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(priceCollection));
        int totalPriceExpected = 0;
        for (WebElement priceElement : priceCollectionElements) {
            totalPriceExpected += Integer.parseInt(priceElement.getText());
        }

        WebElement totalPriceElement = wait.until(ExpectedConditions.elementToBeClickable(totalPrice));
        Integer totalPriceActual = Integer.parseInt(totalPriceElement.getText());

        assertEquals(totalPriceExpected, totalPriceActual);
    }
}
