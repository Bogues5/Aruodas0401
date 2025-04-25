package org.example.models;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Plot {
    private WebDriver driver;
    public String region;
    public String district;
    public String quartal;
    public String street;
    public String fHouseNum;
    public String rcNumber;
    public String fAreaOverAll;
    public String[] paskirtys;
    public String[] ypatybes;
    public String interestedChange;
    public boolean auction;
    public String notesLt;
    public String video;
    public String tour3d;
    public String price;
    public String phone;
    public String email;
    public String cbDontShowInAds;
    public String dontWantChat;
    public String[] datatitle;
    public String agreeToRules;
    public String submitFormButton;

    // Konstruktorius, kuris priima WebDriver
    public Plot(WebDriver driver) {
        this.driver = driver;
    }

    public Plot() {

    }

    public void fill() {
        fillRegion();
        fillDistrict();
        fillQuartal();
        fillStreet();
        fillAdditionalFields();
    }

    private void fillRegion() {
        WebElement regionDropdown = waitForElementClickable(By.className("dropdown-input-value-title"), 0);
        regionDropdown.click();
        WebElement searchInput = waitForElementVisible(By.className("dropdown-input-search-value"));
        searchInput.sendKeys(this.region);
        waitForShortDuration();
        searchInput.sendKeys(Keys.ENTER);
    }

    private void fillDistrict() {
        WebElement districtDropdown = waitForElementClickable(By.className("dropdown-input-value-title"), 1);
        districtDropdown.click();
        WebElement searchInput = waitForElementVisible(By.className("dropdown-input-search-value"));
        searchInput.sendKeys(this.district);
        waitForShortDuration();
        searchInput.sendKeys(Keys.ENTER);
    }

    private void fillQuartal() {
        WebElement quartalDropdown = waitForElementClickable(By.className("dropdown-input-value-title"), 2);
        quartalDropdown.click();
        List<WebElement> searchInputs = driver.findElements(By.className("dropdown-input-search-value"));
        WebElement quartalInput = searchInputs.size() > 1 ? searchInputs.get(1) : searchInputs.get(0);
        quartalInput.sendKeys(this.quartal);
        waitForLongDuration();
        quartalInput.sendKeys(Keys.ENTER);
    }

    private void fillStreet() {
        WebElement streetDropdown = waitForElementClickable(By.className("dropdown-input-value-title"), 3);
        streetDropdown.click();
        WebElement streetInput = waitForElementVisible(By.xpath("//*[@id='streets_1']/li[1]/input"));
        streetInput.sendKeys(this.street);
        waitForShortDuration();
        streetInput.sendKeys(Keys.ENTER);
    }

    private void fillAdditionalFields() {
        sendKeysToElement(By.name("fHouseNum"), this.fHouseNum);
        sendKeysToElement(By.name("rcNumber"), this.rcNumber);
        sendKeysToElement(By.name("fAreaOverAll"), "100");
        clickElement(By.xpath("//label[@for='cb_FIntendance_property']"));
        for (String paskirtis : this.paskirtys) {
            if (paskirtis != null && !paskirtis.isEmpty()) {
                clickElement(By.xpath("//input[@data-title='" + paskirtis + "']/following-sibling::label"));
            }
        }
        for (String ypatybe : this.ypatybes) {
            if (ypatybe != null && !ypatybe.isEmpty()) {
                clickElement(By.xpath("//input[@data-title='" + ypatybe + "']/following-sibling::label"));
            }
        }
        clickElement(By.xpath("//input[@name='interestedChange']/following-sibling::label"));
        if (this.auction) {
            clickElement(By.xpath("//input[@name='auction']/following-sibling::label"));
        }
        sendKeysToElement(By.name("notes_lt"), "Parduodamas sklypas su elektra.");
        sendKeysToElement(By.name("video"), "https://youtube.com/example");
        sendKeysToElement(By.name("tour_3d"), "https://3dturas.example.com");
        sendKeysToElement(By.name("price"), "50000");
        sendKeysToElement(By.name("phone"), "+37012345678");
        sendKeysToElement(By.name("email"), "test@example.com");
        clickElement(By.xpath("//input[@data-title='Privatus asmuo']/following-sibling::label"));
        clickElement(By.name("agree_to_rules"));
        clickElement(By.name("submitFormButton"));
    }

    // Pagalbiniai metodai
    private WebElement waitForElementClickable(By locator, int index) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        List<WebElement> elements = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        return elements.get(index);
    }

    private WebElement waitForElementVisible(By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void waitForShortDuration() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void waitForLongDuration() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    private void sendKeysToElement(By locator, String value) {
        if (value != null && !value.isEmpty()) {
            WebElement element = waitForElementVisible(locator);
            element.clear();
            element.sendKeys(value);
        }
    }

    private void clickElement(By locator) {
        WebElement element = waitForElementVisible(locator);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(element))
                .click();
    }
}