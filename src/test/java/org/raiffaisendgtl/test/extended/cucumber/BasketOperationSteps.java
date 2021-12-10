package org.raiffaisendgtl.test.extended.cucumber;

import io.cucumber.java8.En;

public class BasketOperationSteps extends CucumberTestBase implements En {

    private int countProducts;

    public BasketOperationSteps() {
        Given("number of goods in the amount of {int} units", (Integer count) -> {
            countProducts = count;
        });
        When("add items to basket", () -> {
            app.addProductsToBasket(countProducts);
        });
        And("remove items from the basket", () -> {
            app.deleteProductsFromTheBasket();
        });
        Then("operations completed correctly", () -> {

        });
    }

}
