package org.raiffaisendgtl.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LiteCartSortingTest extends TestBase {

    private ChromeDriver chromeDriver;

    @BeforeEach
    public void setUp() {
        super.setUp();
        chromeDriver = getDriver();
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        chromeDriver.navigate().to("http://localhost/litecart/admin/");
        chromeDriver.findElement(By.name("username")).sendKeys("admin");
        chromeDriver.findElement(By.name("password")).sendKeys("admin");
        chromeDriver.findElement(By.name("login")).click();
    }

    @Test
    public void sortingCountriesInAdminPanel() {
        String urlSectionCountries = "http://localhost/litecart/admin/?app=countries&doc=countries";
        chromeDriver.navigate().to(urlSectionCountries);

        int positionNameCountryInTable = findPositionColumn("Name");
        int positionZonesInTable = findPositionColumn("Zones");

        List<String> nameCountries = new LinkedList<>();
        List<WebElement> rowCountries = getOrUpdateRowCountries();
        int countRows = rowCountries.size();
        for (int row = 0; row < countRows; row++) {
            String nameCountry = rowCountries.get(row)
                    .findElement(By.cssSelector("td:nth-child(" + positionNameCountryInTable + ")"))
                    .getText();
            nameCountries.add(nameCountry);
            if (zonesAreNotEmpty(rowCountries.get(row), positionZonesInTable)) {
                assertTrue(isSortingCountryZones(rowCountries.get(row), positionNameCountryInTable));
                chromeDriver.navigate().to(urlSectionCountries);
                rowCountries = getOrUpdateRowCountries();
            }
        }

        assertTrue(isSortingList(nameCountries));
    }

    @Test
    public void sortingGeoZonesInAdminPanel() {
        String urlSectionGeoZones = "http://localhost/litecart/admin/?app=geo_zones&doc=geo_zones";
        chromeDriver.navigate().to(urlSectionGeoZones);

        int positionNameCountryInTable = findPositionColumn("Name");

        List<WebElement> rowCountries = getOrUpdateRowCountries();
        int countRows = rowCountries.size();
        for (int row = 0; row < countRows; row++) {
            assertTrue(isSortingCountryGeoZones(rowCountries.get(row), positionNameCountryInTable));
            chromeDriver.navigate().to(urlSectionGeoZones);
            rowCountries = getOrUpdateRowCountries();
        }
    }

    @AfterEach
    public void tearDown() {
        chromeDriver.close();
    }

    public int findPositionColumn(String nameColumn) {
        int position = -1;
        List<WebElement> columnTable = chromeDriver.findElements(By.cssSelector("table.dataTable tr.header > th"));
        for (int index = 0; index < columnTable.size(); index++) {
            if (columnTable.get(index).getText().equals(nameColumn)) {
                position = index + 1;
                return position;
            }
        }
        return position;
    }

    public List<WebElement> getOrUpdateRowCountries() {
        return chromeDriver.findElements(By.cssSelector("table.dataTable tr.row"));
    }

    public boolean zonesAreNotEmpty(WebElement country, int position) {
        return getCountZone(country, position) > 0;
    }

    public int getCountZone(WebElement country, int position) {
        String countZoneStr = country.findElement(By.cssSelector("td:nth-child(" + position + ")"))
                .getText();
        return Integer.parseInt(countZoneStr);
    }

    public boolean isSortingCountryZones(WebElement country, int position) {
        country.findElement(By.cssSelector("td:nth-child(" + position + ") > a")).click();

        int positionNameZoneInTable = findPositionColumn("Name");

        List<String> nameZones = new LinkedList<>();

        List<WebElement> zones = findColumnsInTable(positionNameZoneInTable);
        for (WebElement zone : zones) {
            String nameZone = zone.getText();
            if (!nameZone.isEmpty()) {
                nameZones.add(nameZone);
            }
        }

        return isSortingList(nameZones);
    }

    public List<WebElement> findColumnsInTable(int position) {
        return chromeDriver.findElements(By.cssSelector("table.dataTable tr >" +
                "td:nth-child(" + position + ")"));
    }

    public boolean isSortingList(List<String> list) {
        List<String> sortedList = new LinkedList<>(list);
        Collections.sort(sortedList);
        return sortedList.equals(list);
    }

    public boolean isSortingCountryGeoZones(WebElement country, int position) {
        country.findElement(By.cssSelector("td:nth-child(" + position + ") > a")).click();

        int positionZoneInTable = findPositionColumn("Zone");

        List<String> nameZones = new LinkedList<>();

        List<WebElement> zones = findColumnsInTable(positionZoneInTable);
        for (WebElement zone : zones) {
            String nameZone = getSelectedZone(zone);
            if (!nameZone.isEmpty()) {
                nameZones.add(nameZone);
            }
        }

        return isSortingList(nameZones);
    }

    public String getSelectedZone(WebElement zone) {
        return zone.findElement(By.cssSelector("select > option[selected]")).getText();
    }

}
