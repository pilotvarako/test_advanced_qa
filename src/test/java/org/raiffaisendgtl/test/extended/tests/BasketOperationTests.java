package org.raiffaisendgtl.test.extended.tests;

import org.junit.jupiter.api.Test;

public class BasketOperationTests extends TestBase {

    @Test
    public void basketOperationTest() {
        int countProducts = 3;
        app.addProductsToBasket(countProducts);
        app.deleteProductsFromTheBasket();
    }

}
