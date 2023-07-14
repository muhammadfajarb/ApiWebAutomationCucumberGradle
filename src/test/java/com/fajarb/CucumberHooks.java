package com.fajarb;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.restassured.RestAssured;

public class CucumberHooks extends BaseTest {
    @Before
    public void beforeTest() {
        getApiDriver();
        getWebDriver();
    }

    @After
    public void afterTest() {
        RestAssured.reset();
        driver.close();
    }
}
