package com.fajarb.web.stepdef;

import com.fajarb.BaseTest;
import com.fajarb.web.page.CartPage;
import com.fajarb.web.page.HomePage;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.util.List;
import java.util.Map;

public class CartStepDef extends BaseTest {

    HomePage homePage;
    CartPage cartPage;

    @And("user go to cart page")
    public void userGoToCartPage() {
        cartPage = new CartPage(driver);
        cartPage.goToCartPage();
    }

    @When("user click add to cart button on following items:")
    public void userClickAddToCartButtonOnFollowingItems(DataTable dataTable) {
        List<Map<String, String>> productItems = dataTable.asMaps(String.class, String.class);
        cartPage = new CartPage(driver);
        cartPage.addProductToCart(productItems);
    }

    @Then("cart should contain the following items:")
    public void cartShouldContainTheFollowingItems(DataTable dataTable) {
        List<Map<String, String>> productItems = dataTable.asMaps(String.class, String.class);
        cartPage = new CartPage(driver);
        cartPage.checkIfContainItem(productItems);
    }

    @And("total price cart is correct")
    public void totalPriceCartIsCorrect() {
        cartPage.validateCartTotalPrice();
    }
}
