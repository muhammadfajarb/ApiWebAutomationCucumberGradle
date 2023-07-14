package com.fajarb.web.stepdef;

import com.fajarb.BaseTest;
import com.fajarb.web.page.CartPage;
import com.fajarb.web.page.CheckoutPage;
import com.fajarb.web.page.LoginPage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

public class CheckoutStepDef extends BaseTest {

    CartPage cartPage;
    CheckoutPage checkoutPage;

    @When("user click place order button")
    public void userClickPlaceOrderButton() {
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.clickPlaceOrderButton();
    }

    @And("place order form is displayed")
    public void placeOrderFormIsDisplayed() {
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.formIsDisplayed();
    }

    @And("fill the form with following information:")
    public void fillTheFormWithFollowingInformation(DataTable dataTable) {
        List<Map<String, String>> formData = dataTable.asMaps(String.class, String.class);
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillFormData(formData);
    }

    @And("purchase button is clicked")
    public void purchaseButtonIsClicked() {
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.clickPurchaseButton();
    }

    @Then("the transaction is successful")
    public void theTransactionIsSuccessful() {
        checkoutPage = new CheckoutPage(driver);
        checkoutPage.checkIfTransactionSuccess();
    }
}
