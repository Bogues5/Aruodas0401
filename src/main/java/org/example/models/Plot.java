import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class PlotTests {
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

    public Plot(WebDriver driver, int regionCode, String regionName, int districtCode, String districtName,
                int quartalCode, String quartalName, int streetCode, String streetName, int houseNumber,
                boolean checkboxSelected, String rcNumber, boolean rcCheckboxSelected, double area,
                List<String> purposes, boolean showAttributes, List<Integer> specialFeatures,
                boolean interestedChange, boolean auction, String notesLt, String notesEn, String notesRu,
                String video, String tour3d, int price, String phone, String email, boolean dontShowInAds,
                boolean dontWantChat, int accountType, boolean agreeToRules) {
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
    }

    // Regiono dropdown – spustelėja lauką, įveda regiono pavadinimą ir pasirenka variantą
    public void fillRegion() {
        wait.until(ExpectedConditions.elementToBeClickable(By.className("dropdown-input-value-title")));
        List<WebElement> dropdowns = driver.findElements(By.className("dropdown-input-value-title"));
        dropdowns.get(0).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("dropdown-input-search-value")));
        WebElement searchField = driver.findElement(By.className("dropdown-input-search-value"));
        searchField.sendKeys(this.regionName);
        searchField.sendKeys(Keys.ENTER);
    }

    // Gyvenvietės (district) lauko pildymas
    public void fillDistrict() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("districtTitle")));
        WebElement districtField = driver.findElement(By.id("districtTitle"));
        districtField.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("dropdown-input-search-value")));
        WebElement searchField = driver.findElement(By.className("dropdown-input-search-value"));
        searchField.sendKeys(this.districtName);
        searchField.sendKeys(Keys.ENTER);
    }

    // Mikrorajono (quartal) lauko pildymas
    public void fillQuartal() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("quartalTitle")));
        WebElement quartalField = driver.findElement(By.id("quartalTitle"));
        quartalField.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("dropdown-input-search-value")));
        WebElement searchField = driver.findElement(By.className("dropdown-input-search-value"));
        searchField.sendKeys(this.quartalName);
        searchField.sendKeys(Keys.ENTER);
    }

    // Gatvės lauko pildymas
    public void fillStreet() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("streetTitle")));
        WebElement streetField = driver.findElement(By.id("streetTitle"));
        streetField.click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("dropdown-input-search-value")));
        WebElement searchField = driver.findElement(By.className("dropdown-input-search-value"));
        searchField.sendKeys(this.streetName);
        searchField.sendKeys(Keys.ENTER);
    }

    // Namo numerio lauko pildymas
    public void fillHouseNumber(){
        WebElement houseNumField = wait.until(ExpectedConditions.elementToBeClickable(By.name("FHouseNum")));
        houseNumField.clear();
        houseNumField.sendKeys(String.valueOf(this.houseNumber));
    }

    // Checkbox "Rodyti" prie namo numerio – jei reikia jį įjungti
    public void toggleShowHouseNumberCheckbox() {
        WebElement checkbox = driver.findElement(By.id("cbshow_house_num"));
        if(!checkbox.isSelected()){
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
        WebElement checkbox = driver.findElement(By.id("cbshow_rc_number"));
        if(!checkbox.isSelected()){
            checkbox.click();
        }
    }

    // Ploto lauko pildymas
    public void fillArea() {
        WebElement areaField = driver.findElement(By.name("FAreaOverAll"));
        areaField.clear();
        areaField.sendKeys(String.valueOf(this.area));
    }

    // Paskirties checkbox'ų žymėjimas – iteruojame per duotą paskirčių sąrašą
    public void selectPurposes(){
        for(String purpose : this.purposes){
            String xpath = String.format("//input[@name='FIntendance[]' and @value='%s']", purpose);
            List<WebElement> checkboxes = driver.findElements(By.xpath(xpath));
            if(!checkboxes.isEmpty()){
                WebElement checkbox = checkboxes.get(0);
                if(!checkbox.isSelected()){
                    checkbox.click();
                }
            }
        }
    }

    // Išplėsti ypatybių sekciją paspaudus mygtuką "Žymėti ypatumus"
    public void expandAttributes(){
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id("showMoreFields")));
        button.click();
    }

    // Ypatybių checkbox'ų žymėjimas
    public void selectSpecialFeatures(){
        for (Integer feature : this.specialFeatures){
            String xpath = String.format("//input[@name='Special[]' and @value='%d']", feature);
            List<WebElement> checkboxes = driver.findElements(By.xpath(xpath));
            if(!checkboxes.isEmpty()){
                WebElement checkbox = checkboxes.get(0);
                if(!checkbox.isSelected()){
                    checkbox.click();
                }
            }
        }
    }

    // Checkbox "Domina keitimas"
    public void toggleInterestedChange(){
        WebElement checkbox = driver.findElement(By.id("cbInterestedChange"));
        if(!checkbox.isSelected()){
            checkbox.click();
        }
    }

    // Checkbox "Varžytynės/aukcionas"
    public void toggleAuction(){
        WebElement checkbox = driver.findElement(By.id("cbAuction"));
        if(!checkbox.isSelected()){
            checkbox.click();
        }
    }

    // Aprašymo įvedimas trimis kalbomis: LT, EN, RU
    public void fillDescription(){
        // LT
        WebElement ltRadio = driver.findElement(By.id("langLt"));
        ltRadio.click();
        WebElement notesLtField = driver.findElement(By.name("notes_lt"));
        notesLtField.clear();
        notesLtField.sendKeys(this.notesLt);
        // EN
        WebElement enRadio = driver.findElement(By.id("langEn"));
        enRadio.click();
        WebElement notesEnField = driver.findElement(By.name("notes_en"));
        notesEnField.clear();
        notesEnField.sendKeys(this.notesEn);
        // RU
        WebElement ruRadio = driver.findElement(By.id("langRu"));
        ruRadio.click();
        WebElement notesRuField = driver.findElement(By.name("notes_ru"));
        notesRuField.clear();
        notesRuField.sendKeys(this.notesRu);
    }

    // Youtube nuorodos ir 3D turo lauko pildymas
    public void fillVideoAndTour3D(){
        WebElement videoField = driver.findElement(By.name("Video"));
        videoField.clear();
        videoField.sendKeys(this.video);

        WebElement tour3dField = driver.findElement(By.name("tour_3d"));
        tour3dField.clear();
        tour3dField.sendKeys(this.tour3d);
    }

    // Sklypo kainos lauko pildymas
    public void fillPrice(){
        WebElement priceField = driver.findElement(By.id("priceField"));
        priceField.clear();
        priceField.sendKeys(String.valueOf(this.price));
    }

    // Telefono lauko pildymas
    public void fillPhone(){
        WebElement phoneField = driver.findElement(By.name("phone"));
        phoneField.clear();
        phoneField.sendKeys(this.phone);
    }

    // El. pašto lauko pildymas
    public void fillEmail(){
        WebElement emailField = driver.findElement(By.name("email"));
        emailField.clear();
        emailField.sendKeys(this.email);
    }

    // Checkbox "Išjungti kontaktavimo el. paštu"
    public void toggleDontShowInAds(){
        WebElement checkbox = driver.findElement(By.id("cbdont_show_in_ads"));
        if(!checkbox.isSelected()){
            checkbox.click();
        }
    }

    // Checkbox "Išjungti pokalbių (chat) funkciją"
    public void toggleDontWantChat(){
        WebElement checkbox = driver.findElement(By.id("cbdont_want_chat"));
        if(!checkbox.isSelected()){
            checkbox.click();
        }
    }

    // Vartotojo tipo pasirinkimas – atsižvelgiant į accountType (1 = Privatus asmuo, 2 = Tarpininkas, 3 = Vystytojas/statytojas, 4 = Kitas verslo subjektas)
    public void selectAccountType(){
        List<WebElement> buttons = driver.findElements(By.cssSelector(".input-buttons-wrapper .input-button"));
        for(WebElement btn : buttons){
            String dataValue = btn.getAttribute("data-value");
            if(Integer.parseInt(dataValue) == this.accountType){
                btn.click();
                break;
            }
        }
    }

    // Checkbox "Sutinku su portalo taisyklėmis"
    public void toggleAgreeToRules(){
        WebElement checkbox = driver.findElement(By.id("cbagree_to_rules"));
        if(!checkbox.isSelected()){
            checkbox.click();
        }
    }

    // Nuotraukos įkėlimas: spustelėjimas ant įkėlimo mygtuko ir failo kelio nusiųsti į <input type="file">
    public void uploadPhoto(String filePath){
        WebElement uploadButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("uploadPhotoBtn")));
        uploadButton.click();
        WebElement fileInput = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[type='file']")));
        fileInput.sendKeys(filePath);
    }

    // Skelbimo pateikimas – spustelimas ant "Įvesti skelbimą" mygtuko
    public void submitListing(){
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(., 'Įvesti skelbimą')]")));
        submitButton.click();
    }
}

public void main() {
}
