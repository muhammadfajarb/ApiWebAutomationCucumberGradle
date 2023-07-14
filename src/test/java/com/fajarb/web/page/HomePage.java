package com.fajarb.web.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HomePage {
    WebDriver driver;
    By nameOfUser = By.xpath("//a[@id=\"nameofuser\"]");

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public void validateOnHomePage() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement userName = wait.until(ExpectedConditions.elementToBeClickable(nameOfUser));
        assertTrue(userName.isDisplayed());
        assertEquals("Welcome qwertytest123", userName.getText());
    }

}
