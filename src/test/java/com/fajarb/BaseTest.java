package com.fajarb;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;

public class BaseTest {
    protected static WebDriver driver;

    protected void getApiDriver() {
        RestAssured.baseURI = "https://dummyapi.io/data/v1/";
    }

    protected void getWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
    }

    public static File getJsonSchemaFile(String jsonSchemaFilename) {
        String jsonSchemaDir = "src/test/resources/jsonSchema/";
        return new File(jsonSchemaDir + jsonSchemaFilename);
    }
}
