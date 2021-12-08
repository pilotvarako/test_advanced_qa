package org.raiffaisendgtl.test.extended.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

import java.util.List;

public class BasketPage extends Page {

    @FindBy(className = "dataTable")
    private List<WebElement> table;

    @FindBy(name = "remove_cart_item")
    private WebElement removeProductButton;

    public BasketPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public BasketPage open() {
        driver.navigate().to("http://localhost/litecart/en/checkout");
        return this;
    }

    public void removeProducts(int countProducts) {
        for (int product = 1; product <= countProducts; product++) {
            removeProduct();
            if (table.isEmpty()) {
                break;
            }
        }
    }

    public void removeProduct() {
        removeProductButton.click();
        wait.until(stalenessOf(table.get(0)));
    }

}
