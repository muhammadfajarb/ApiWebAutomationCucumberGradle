package com.fajarb.web.page;

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

public class CheckoutPage {
    WebDriver driver;
    By placeOrderButton = By.xpath("//button[text()=\"Place Order\"]");
    By modalCheckoutForm = By.xpath("//*[@id=\"orderModalLabel\"]");
    By purchaseButton = By.xpath("//button[text()=\"Purchase\"]");
    private By inputForm(String id) {
        return By.cssSelector("input#"+id);
    }
    By textAlert = By.xpath("//div[@class=\"sweet-alert  showSweetAlert visible\"]/h2");

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickPlaceOrderButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement placeOrderButtonElement = wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton));
        placeOrderButtonElement.click();
    }

    public void formIsDisplayed() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement modalElement = wait.until(ExpectedConditions.visibilityOfElementLocated(modalCheckoutForm));
        assertTrue(modalElement.isDisplayed());
    }

    public void fillFormData(List<Map<String, String>> formData) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        for (Map<String, String> input : formData) {
            String id = input.get("input_id");
            String value = input.get("value");
            WebElement inputElement = wait.until(ExpectedConditions.elementToBeClickable(inputForm(id)));
            inputElement.sendKeys(value);
        }
    }

    public void clickPurchaseButton() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement purchaseButtonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(purchaseButton));
        purchaseButtonElement.click();
    }

    public void checkIfTransactionSuccess() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement textAlertElement = wait.until(ExpectedConditions.visibilityOfElementLocated(textAlert));
        assertEquals("Thank you for your purchase!", textAlertElement.getText());
    }
}
