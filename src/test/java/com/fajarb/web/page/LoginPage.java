package com.fajarb.web.page;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginPage {
    WebDriver driver;

    By usernameInputText = By.cssSelector("input#loginusername");
    By passwordInputText = By.cssSelector("input#loginpassword");
    By loginMenu = By.xpath("//a[@id=\"login2\"]");
    By loginButton = By.xpath("//button[text()=\"Log in\"]");

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    public void goToLoginPage() {
        driver.get("https://www.demoblaze.com/");
    }

    public void inputUsername(String username) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement clickableElement = wait.until(ExpectedConditions.elementToBeClickable(usernameInputText));
        clickableElement.sendKeys(username);
    }

    public void inputPassword(String password) {
        driver.findElement(passwordInputText).sendKeys(password);
    }

    public void clickLoginButton() {
        driver.findElement(loginButton).click();
    }

    public void validateErrorAppear(String errorMessage) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());

        String alertText = alert.getText();
        alert.accept();
        assertEquals(errorMessage, alertText);
    }

    public void clickLoginMenu() {
        driver.findElement(loginMenu).click();
    }
}
