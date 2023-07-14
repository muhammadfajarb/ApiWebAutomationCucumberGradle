package com.fajarb;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        glue = {"com.fajarb"},
        features = {"src/test/resources"},
        plugin = {"pretty","html:reports/index.html", "json:reports/index.json"})
public class CucumberTest {
}
