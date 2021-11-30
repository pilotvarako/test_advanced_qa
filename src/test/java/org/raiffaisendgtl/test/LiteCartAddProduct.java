package org.raiffaisendgtl.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.time.Duration;

public class LiteCartAddProduct extends TestBase {

    @BeforeEach
    public void setUp() {
        super.setUp();
        WebDriver driver = getDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.navigate().to("http://localhost/litecart/admin/");
        driver.findElement(By.name("username")).sendKeys("admin");
        driver.findElement(By.name("password")).sendKeys("admin");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void addNewProduct() {
        String urlPageCatalog = "http://localhost/litecart/admin/?app=catalog&doc=catalog";
        WebDriver driver = getDriver();
        driver.navigate().to(urlPageCatalog);

        driver.findElement(By.cssSelector(".button:last-child")).click();
        String nameProduct = "Black Sofa";
        fillFieldsGeneralProduct(nameProduct);

        driver.findElement(By.xpath("//*[contains(@class, 'index')]//a[contains(text(),'Information')]")).click();
        fillFieldsInformationProduct();

        driver.findElement(By.xpath("//*[contains(@class, 'index')]//a[contains(text(),'Data')]")).click();
        fillFieldsDataProduct();

        driver.findElement(By.cssSelector("[name='save']")).click();
        driver.findElement(By.xpath("//a[contains(text(),'" + nameProduct + "')]"));
    }

    public void fillFieldsGeneralProduct(String nameProduct) {
        WebDriver driver = getDriver();

        driver.findElement(By.cssSelector("[name='status'][value='1']")).click();
        driver.findElement(By.cssSelector("[name='name[en]']")).sendKeys(nameProduct);
        driver.findElement(By.cssSelector("[name='code']")).sendKeys("Sofa001");
        driver.findElement(By.cssSelector("[name='quantity']")).clear();
        driver.findElement(By.cssSelector("[name='quantity']")).sendKeys("10");

        Select fieldSelect = new Select(driver.findElement(By.cssSelector("[name='sold_out_status_id']")));
        fieldSelect.selectByVisibleText("Temporary sold out");

        File file = new File("src/test/resources/products/sofa1.png");
        driver.findElement(By.cssSelector("[name='new_images[]']")).sendKeys(file.getAbsolutePath());
    }

    public void fillFieldsInformationProduct() {
        WebDriver driver = getDriver();

        Select fieldSelect = new Select(driver.findElement(By.cssSelector("[name='manufacturer_id']")));
        fieldSelect.selectByVisibleText("ACME Corp.");

        driver.findElement(By.cssSelector("[name='short_description[en]']")).sendKeys("test short input");
        driver.findElement(By.cssSelector(".trumbowyg-editor")).sendKeys("test textarea");
    }

    public void fillFieldsDataProduct() {
        WebDriver driver = getDriver();
        WebElement number;

        driver.findElement(By.cssSelector("[name='sku']")).sendKeys("TEST_SOFA");
        driver.findElement(By.cssSelector("[name='weight']")).sendKeys("Sofa001");

        number = driver.findElement(By.cssSelector("[name='weight']"));
        number.clear();
        number.sendKeys("50");

        number = driver.findElement(By.cssSelector("[name='dim_x']"));
        number.clear();
        number.sendKeys("7");

        number = driver.findElement(By.cssSelector("[name='dim_y']"));
        number.clear();
        number.sendKeys("7");

        number = driver.findElement(By.cssSelector("[name='dim_z']"));
        number.clear();
        number.sendKeys("7");

        driver.findElement(By.cssSelector("[name='attributes[en]']")).sendKeys("test attributes");
    }

}
