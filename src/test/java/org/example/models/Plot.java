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
    public WebDriver driver;
    public WebDriverWait wait;

    // Duomenys, kurie bus naudojami formoje
    public int regionCode;
    public String regionName;
    public int districtCode;
    public String districtName;
    public int quartalCode;
    public String quartalName;
    public int streetCode;
    public String streetName;
    public int houseNumber;
    public boolean checkboxSelected;
    public String rcNumber;
    public boolean rcCheckboxSelected;
    public double area;
    public List<String> purposes;
    public boolean showAttributes;
    public List<Integer> specialFeatures;
    public boolean interestedChange;
    public boolean auction;
    public String notesLt;
    public String notesEn;
    public String notesRu;
    public String video;
    public String tour3d;
    public int price;
    public String phone;
    public String email;
    public boolean dontShowInAds;
    public boolean dontWantChat;
    public int accountType;
    public boolean agreeToRules;
    public String photoUrl;

    public Plot(WebDriver driver, int regionCode, String regionName, int districtCode, String districtName,
                int quartalCode, String quartalName, int streetCode, String streetName, int houseNumber,
                boolean checkboxSelected, String rcNumber, boolean rcCheckboxSelected, double area,
                List<String> purposes, boolean showAttributes, List<Integer> specialFeatures,
                boolean interestedChange, boolean auction, String notesLt, String notesEn, String notesRu,
                String video, String tour3d, int price, String phone, String email, boolean dontShowInAds,
                boolean dontWantChat, int accountType, boolean agreeToRules, String photoUrl) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        this.regionCode = regionCode;
        this.regionName = regionName;
        this.districtCode = districtCode;
        this.districtName = districtName;
        this.quartalCode = quartalCode;
        this.quartalName = quartalName;
        this.streetCode = streetCode;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.checkboxSelected = checkboxSelected;
        this.rcNumber = rcNumber;
        this.rcCheckboxSelected = rcCheckboxSelected;
        this.area = area;
        this.purposes = purposes;
        this.showAttributes = showAttributes;
        this.specialFeatures = specialFeatures;
        this.interestedChange = interestedChange;
        this.auction = auction;
        this.notesLt = notesLt;
        this.notesEn = notesEn;
        this.notesRu = notesRu;
        this.video = video;
        this.tour3d = tour3d;
        this.price = price;
        this.phone = phone;
        this.email = email;
        this.dontShowInAds = dontShowInAds;
        this.dontWantChat = dontWantChat;
        this.accountType = accountType;
        this.agreeToRules = agreeToRules;
        this.photoUrl = photoUrl;
    }

    public void fill() {
        fillRegion();
        fillDistrict();
        fillQuartal();
        fillStreet();
        fillHouseNumber();
        toggleShowHouseNumberCheckbox();
        fillRCNumber();
        toggleRCCheckbox();
        fillArea();
        selectPurposes();
        expandAttributes();
        selectSpecialFeatures();
        toggleInterestedChange();
        toggleAuction();
        fillDescription();
        fillVideoAndTour3D();
        fillPrice();
        fillPhone();
        fillEmail();
        toggleDontShowInAds();
        toggleDontWantChat();
        selectAccountType();
        toggleAgreeToRules();
        uploadPhoto();
    }

    public void fillRegion() {
        // First wait for page to fully load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[2]/form/ul/li[3]/span[1]/input[2]")));

        try {
            // Find and click the dropdown trigger element
            WebElement dropdownTrigger = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector("input.dropdown-input-value-title")));
            dropdownTrigger.click();

            // Wait for dropdown to appear
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[2]/form/ul/li[3]/span[1]/ul[2]/li[2]")));

            // Find search field
            WebElement searchField = wait.until(ExpectedConditions.elementToBeClickable(
                    By.cssSelector(".dropdown-input-search-value")));


            // Find and click the matching option
            // Using contains to handle potential case insensitivity
            String xpathSelector = String.format(
                    "//li[contains(@data-search-string, '%s') or contains(text(), '%s')]",
                    this.regionName.toLowerCase(), this.regionName);

            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathSelector)));
            option.click();

            // Verify selection was made
            wait.until(ExpectedConditions.textToBePresentInElementLocated(
                    By.cssSelector("input.dropdown-input-value-title"), this.regionName));

        } catch (Exception e) {
            System.out.println("Error selecting region: " + e.getMessage());
            e.printStackTrace();
        }
    }
    // Gyvenvietės (district) lauko pildymas
    public void fillDistrict() {
        WebElement districtField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[2]/form/ul/li[4]/span[1]/input[2]")));
        districtField.click();
        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/div[1]/div[2]/form/ul/li[4]/span[1]/ul[2]/li[1]" + this.regionCode + " .dropdown-input-search-value")));
        searchField.sendKeys(this.districtName);
        searchField.sendKeys(Keys.ENTER);

    }

    // Mikrorajono (quartal) lauko pildymas
    public void fillQuartal() {
        WebElement quartalField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[2]/form/ul/li[5]/span[1]/input[2]")));
        quartalField.click();
        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#quartals_" + this.districtCode + " .dropdown-input-search-value")));
        searchField.sendKeys(this.quartalName);
        searchField.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[1]/div[2]/form/ul/li[5]/span[1]/ul[2]/li[6]")));
    }

    // Gatvės lauko pildymas
    public void fillStreet() {
        WebElement streetField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div[2]/form/ul/li[6]/span[1]/input[2]")));
        streetField.click();
        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#streets_" + this.quartalCode + " .dropdown-input-search-value")));
        searchField.sendKeys(this.streetName);
        searchField.sendKeys(Keys.ENTER);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("/html/body/div[1]/div[2]/form/ul/li[6]/span[1]/ul[2]/li[5]")));
    }

    // Namo numerio lauko pildymas
    public void fillHouseNumber() {
        WebElement houseNumField = wait.until(ExpectedConditions.elementToBeClickable(By.name("FHouseNum")));
        houseNumField.clear();
        houseNumField.sendKeys(String.valueOf(this.houseNumber));
    }

    // Checkbox "Rodyti" prie namo numerio
    public void toggleShowHouseNumberCheckbox() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("cbshow_house_num")));
        if (this.checkboxSelected != checkbox.isSelected()) {
            checkbox.click();
        }
    }

    // RC numerio lauko pildymas
    public void fillRCNumber() {
        WebElement rcField = wait.until(ExpectedConditions.elementToBeClickable(By.name("RCNumber")));
        rcField.clear();
        rcField.sendKeys(this.rcNumber);
    }

    // Checkbox RC numerio rodyti
    public void toggleRCCheckbox() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("cbshow_rc_number")));
        if (this.rcCheckboxSelected != checkbox.isSelected()) {
            checkbox.click();
        }
    }

    // Ploto lauko pildymas
    public void fillArea() {
        WebElement areaField = wait.until(ExpectedConditions.elementToBeClickable(By.name("FAreaOverAll")));
        areaField.clear();
        areaField.sendKeys(String.valueOf(this.area));
    }

    // Paskirties checkbox'ų žymėjimas
    public void selectPurposes() {
        for (String purpose : this.purposes) {
            String xpath = String.format("//input[@name='FIntendance[]' and @value='%s']", purpose);
            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }

    // Išplėsti ypatybių sekciją
    public void expandAttributes() {
        if (this.showAttributes) {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id("showMoreFields")));
            button.click();
        }
    }

    // Ypatybių checkbox'ų žymėjimas
    public void selectSpecialFeatures() {
        for (Integer feature : this.specialFeatures) {
            String xpath = String.format("//input[@name='Special[]' and @value='%d']", feature);
            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }

    // Checkbox "Domina keitimas"
    public void toggleInterestedChange() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("cbInterestedChange")));
        if (this.interestedChange != checkbox.isSelected()) {
            checkbox.click();
        }
    }

    // Checkbox "Varžytynės/aukcionas"
    public void toggleAuction() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("cbAuction")));
        if (this.auction != checkbox.isSelected()) {
            checkbox.click();
        }
    }

    // Aprašymo įvedimas trimis kalbomis
    public void fillDescription() {
        WebElement ltRadio = wait.until(ExpectedConditions.elementToBeClickable(By.id("langLt")));
        ltRadio.click();
        WebElement notesLtField = wait.until(ExpectedConditions.elementToBeClickable(By.name("notes_lt")));
        notesLtField.clear();
        notesLtField.sendKeys(this.notesLt);

        WebElement enRadio = wait.until(ExpectedConditions.elementToBeClickable(By.id("langEn")));
        enRadio.click();
        WebElement notesEnField = wait.until(ExpectedConditions.elementToBeClickable(By.name("notes_en")));
        notesEnField.clear();
        notesEnField.sendKeys(this.notesEn);

        WebElement ruRadio = wait.until(ExpectedConditions.elementToBeClickable(By.id("langRu")));
        ruRadio.click();
        WebElement notesRuField = wait.until(ExpectedConditions.elementToBeClickable(By.name("notes_ru")));
        notesRuField.clear();
        notesRuField.sendKeys(this.notesRu);
    }

    // Youtube nuorodos ir 3D turo lauko pildymas
    public void fillVideoAndTour3D() {
        WebElement videoField = wait.until(ExpectedConditions.elementToBeClickable(By.name("Video")));
        videoField.clear();
        videoField.sendKeys(this.video);

        WebElement tour3dField = wait.until(ExpectedConditions.elementToBeClickable(By.name("tour_3d")));
        tour3dField.clear();
        tour3dField.sendKeys(this.tour3d);
    }

    // Sklypo kainos lauko pildymas
    public void fillPrice() {
        WebElement priceField = wait.until(ExpectedConditions.elementToBeClickable(By.id("priceField")));
        priceField.clear();
        priceField.sendKeys(String.valueOf(this.price));
    }

    // Telefono lauko pildymas
    public void fillPhone() {
        WebElement phoneField = wait.until(ExpectedConditions.elementToBeClickable(By.name("phone")));
        phoneField.clear();
        phoneField.sendKeys(this.phone);
    }

    // El. pašto lauko pildymas
    public void fillEmail() {
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.name("email")));
        emailField.clear();
        emailField.sendKeys(this.email);
    }

    // Checkbox "Išjungti kontaktavimo el. paštu"
    public void toggleDontShowInAds() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("cbdont_show_in_ads")));
        if (this.dontShowInAds != checkbox.isSelected()) {
            checkbox.click();
        }
    }

    // Checkbox "Išjungti pokalbių (chat) funkciją"
    public void toggleDontWantChat() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("cbdont_want_chat")));
        if (this.dontWantChat != checkbox.isSelected()) {
            checkbox.click();
        }
    }

    // Vartotojo tipo pasirinkimas
    public void selectAccountType() {
        List<WebElement> buttons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector(".input-buttons-wrapper .input-button")));
        for (WebElement btn : buttons) {
            String dataValue = btn.getAttribute("data-value");
            if (Integer.parseInt(dataValue) == this.accountType) {
                btn.click();
                break;
            }
        }
    }

    // Checkbox "Sutinku su portalo taisyklėmis"
    public void toggleAgreeToRules() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.id("cbagree_to_rules")));
        if (this.agreeToRules != checkbox.isSelected()) {
            checkbox.click();
        }
    }

    // Nuotraukos įkėlimas
    public void uploadPhoto() {
        WebElement uploadButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("uploadPhotoBtn")));
        uploadButton.click();
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']")));
        fileInput.sendKeys(this.photoUrl);
    }
}