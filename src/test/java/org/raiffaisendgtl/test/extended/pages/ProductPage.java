package org.raiffaisendgtl.test.extended.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class ProductPage extends Page {

    @FindBy(name = "options[Size]")
    private List<WebElement> selectSize;

    @FindBy(name = "add_cart_product")
    private WebElement addToBasketButton;

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public ProductPage open(WebElement product) {
        product.click();
        return this;
    }

    public void addToBasket() {
        if (!selectSize.isEmpty()) {
            Select elementSelect = new Select(selectSize.get(0));
            elementSelect.selectByIndex(1);
        }
        addToBasketButton.click();
    }

}
