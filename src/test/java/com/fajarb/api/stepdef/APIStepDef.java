package com.fajarb.api.stepdef;

import com.fajarb.BaseTest;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIStepDef extends BaseTest {
    private static APIStepDef instance;
    private RequestSpecification requestSpecification;
    private ValidatableResponse validatableResponse;
    private Response response;
    private Headers headers;
    private final Map<String, Object> dynamicVariables;

    public APIStepDef() {
        dynamicVariables = new HashMap<>();
    }

    public static APIStepDef getInstance() {
        if (instance == null) {
            instance = new APIStepDef();
        }
        return instance;
    }

    public void setVariable(String key, Object value) {
        dynamicVariables.put(key, value);
    }

    public Object getVariable(String key) {
        return dynamicVariables.get(key);
    }

    @DataTableType
    public Header headerEntry(Map<String, String> entry) {
        String key = entry.get("Key");
        String value = entry.get("Value");
        return new Header(key, value);
    }

    @Given("the request header is set to:")
    public void theRequestHeaderIsSetTo(DataTable dataTable) {
        List<Header> headerList = dataTable.asList(Header.class);
        headers = new Headers(headerList);
        requestSpecification = RestAssured.given().headers(headers);
//        System.out.println(headers);
    }

    @And("the request query parameter is set to:")
    public void theRequestQueryParameterIsSetTo(DataTable dataTable) {
        List<Map<String, String>> bodyList = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> bodyItem : bodyList) {
            String key = bodyItem.get("Key");
            String value = bodyItem.get("Value");
            requestSpecification.queryParam(key, value);
        }
    }

    @When("a {string} request is sent to {string}")
    public void aRequestIsSentTo(String method, String endpoint) {
        if (headers != null) {
            requestSpecification = requestSpecification.when();
        } else {
            requestSpecification = (RequestSpecification) RestAssured.when();
        }

        switch (method) {
            case "GET":
                response = requestSpecification.when().get(endpoint);
                break;
            case "POST":
                response = requestSpecification.when().post(endpoint);
                break;
            case "PUT":
                response = requestSpecification.when().put(endpoint);
                break;
        }

//        System.out.println(response.getBody().asString());
    }

    @When("a {string} request is sent to {string} with path parameter from {string} variable")
    public void aRequestIsSentToWithPathParameterFromVariable(String method, String endpoint, String variableName) {
        APIStepDef dynamicVars = APIStepDef.getInstance();
        Object retrievedValue = dynamicVars.getVariable(variableName);

        if (headers != null) {
            requestSpecification = requestSpecification.when();
        } else {
            requestSpecification = (RequestSpecification) RestAssured.when();
        }

        response = requestSpecification.when().get(endpoint + retrievedValue);
        response = RestAssured.when().get(endpoint + retrievedValue);

        switch (method) {
            case "GET":
                response = requestSpecification.when().get(endpoint + retrievedValue);
                break;
            case "PUT":
                response = requestSpecification.when().put(endpoint + retrievedValue);
                break;
            case "DELETE":
                response = requestSpecification.when().delete(endpoint + retrievedValue);
                break;
        }

//        System.out.println(response.getBody().asString());
    }

    @Then("the status code is {int}")
    public void theStatusCodeIs(int statusCode) {
        validatableResponse = response.then().assertThat().statusCode(statusCode);
//        validatableResponse.log().all();
    }

    @And("the response header should contains:")
    public void theResponseHeaderShouldContains(DataTable dataTable) {
        List<Header> headerList = dataTable.asList(Header.class);
        for (Header headerItem : headerList) {
            Assert.assertEquals(response.getHeader(headerItem.getName()), headerItem.getValue());
        }
    }

    @And("the response body should contains:")
    public void theResponseBodyShouldContains(DataTable dataTable) {
        List<Map<String, String>> bodyList = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> bodyItem : bodyList) {
            String key = bodyItem.get("Key");
            String value = bodyItem.get("Value");
            validatableResponse.assertThat().body(key, Matchers.hasToString(value));
        }
    }

    @And("the response should match the JSON schema {string}")
    public void theResponseShouldMatchTheJSONSchema(String schema) {
        File schemaFile = getJsonSchemaFile(schema);
        validatableResponse.assertThat().body(JsonSchemaValidator.matchesJsonSchema(schemaFile));
    }

    @And("the request body is set to:")
    public void theRequestBodyIsSetTo(DataTable dataTable) {
        JSONObject bodyObject = new JSONObject();
        List<Map<String, String>> bodyList = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> bodyItem : bodyList) {
            String key = bodyItem.get("Key");
            String value = bodyItem.get("Value");
            bodyObject.put(key, value);
        }
        requestSpecification.body(bodyObject.toString());
    }

    @And("response body {string} is saved in variable {string} for next scenario testing")
    public void responseBodyIsSavedInVariableForNextScenarioTesting(String responseBodyKey, String variableName) {
        JSONObject jsonResponse = new JSONObject(validatableResponse.extract().asString());
        String responseBodyValue = jsonResponse.getString(responseBodyKey);

        APIStepDef dynamicVars = APIStepDef.getInstance();
        dynamicVars.setVariable(variableName, responseBodyValue);
//        Object retrievedValue = dynamicVars.getVariable(variableName);
//        System.out.println(variableName + " : " + retrievedValue);

    }
}
