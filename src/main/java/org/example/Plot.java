

//package org.example;
//
//import org.openqa.selenium.*;
//import org.openqa.selenium.interactions.Actions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import java.time.Duration;
//import java.util.Arrays;
//import java.util.List;
//
///*
// * Plot klasė saugo visą informaciją apie sklypą, įskaitant regiono, adreso,
// * RC numerio (sanitizuojamo), ploto, paskirties, kontaktinės ir kitokios informacijos laukus.
// * Visi laukai yra public, todėl jie tiesiogiai prieinami.
// */
//public class Plot {
//    private WebDriver driver;
//    private WebDriverWait wait;
//
//    // Regiono ir adreso susiję laukai
//    public int regionCode;
//    public String regionName;
//    public int districtCode;
//    public String districtName;
//    public int quartalCode;
//    public String quartalName;
//    public int streetCode;
//    public String streetName;
//    public int houseNumber;
//    public boolean checkboxSelected;
//
//    // RC numerio laukai
//    public String rcNumber;
//    public boolean rcCheckboxSelected;
//
//    // Ploto ir paskirties laukai
//    public double area;
//    public List<String> purposes;
//    public boolean showAttributes;
//    public List<Integer> specialFeatures;
//
//    // Papildomi nustatymai
//    public boolean interestedChange;
//    public boolean auction;
//
//    // Aprašymo laukai įvairiomis kalbomis
//    public String notesLt;
//    public String notesEn;
//    public String notesRu;
//
//    // Media nuorodos
//    public String video;
//    public String tour3d;
//
//    // Kontaktinė informacija ir kaina
//    public int price;
//    public String phone;
//    public String email;
//
//    // Papildomi kontaktų nustatymai
//    public boolean dontShowInAds;
//    public boolean dontWantChat;
//
//    // Vartotojo tipo ir taisyklių sutikimo laukai
//    public int accountType;
//    public boolean agreeToRules;
//
//    public Plot(WebDriver driver, WebDriverWait wait) {
//        this.driver = driver;
//        this.wait = wait;
//    }
//
//    public Plot( WebDriver driver,
//            int regionCode, String regionName,
//            int districtCode, String districtName,
//            int quartalCode, String quartalName,
//            int streetCode, String streetName,
//            int houseNumber, boolean checkboxSelected,
//            String rcNumber, boolean rcCheckboxSelected,
//            double area, List<String> purposes,
//            boolean showAttributes, List<Integer> specialFeatures,
//            boolean interestedChange, boolean auction,
//            String notesLt, String notesEn, String notesRu,
//            String video, String tour3d,
//            int price, String phone, String email,
//            boolean dontShowInAds, boolean dontWantChat,
//            int accountType, boolean agreeToRules
//    ) {
//        this.wait = new WebDriverWait(driver, Duration.ofMillis(1000));
//        this.driver = driver;
//        this.regionCode = regionCode;
//        this.regionName = regionName;
//        this.districtCode = districtCode;
//        this.districtName = districtName;
//        this.quartalCode = quartalCode;
//        this.quartalName = quartalName;
//        this.streetCode = streetCode;
//        this.streetName = streetName;
//        this.houseNumber = houseNumber;
//        this.checkboxSelected = checkboxSelected;
//        this.rcNumber = sanitizeRcNumber(rcNumber);
//        this.rcCheckboxSelected = rcCheckboxSelected;
//        this.area = area;
//        this.purposes = purposes;
//        this.showAttributes = showAttributes;
//        this.specialFeatures = specialFeatures;
//        this.interestedChange = interestedChange;
//        this.auction = auction;
//        this.notesLt = notesLt;
//        this.notesEn = notesEn;
//        this.notesRu = notesRu;
//        this.video = video;
//        this.tour3d = tour3d;
//        this.price = price;
//        this.phone = phone;
//        this.email = email;
//        this.dontShowInAds = dontShowInAds;
//        this.dontWantChat = dontWantChat;
//        this.accountType = accountType;
//        this.agreeToRules = agreeToRules;
//    }
//
//    public static String sanitizeRcNumber(String rc) {
//        if (rc == null) return "";
//        return rc.replaceAll("[^0-9]", "");
//    }
//
//    @Override
//    public String toString() {
//        return "Plot{" +
//                "regionCode=" + regionCode +
//                ", regionName='" + regionName + '\'' +
//                ", districtCode=" + districtCode +
//                ", districtName='" + districtName + '\'' +
//                ", quartalCode=" + quartalCode +
//                ", quartalName='" + quartalName + '\'' +
//                ", streetCode=" + streetCode +
//                ", streetName='" + streetName + '\'' +
//                ", houseNumber=" + houseNumber +
//                ", checkboxSelected=" + checkboxSelected +
//                ", rcNumber='" + rcNumber + '\'' +
//                ", rcCheckboxSelected=" + rcCheckboxSelected +
//                ", area=" + area +
//                ", purposes=" + purposes +
//                ", showAttributes=" + showAttributes +
//                ", specialFeatures=" + specialFeatures +
//                ", interestedChange=" + interestedChange +
//                ", auction=" + auction +
//                ", notesLt='" + notesLt + '\'' +
//                ", notesEn='" + notesEn + '\'' +
//                ", notesRu='" + notesRu + '\'' +
//                ", video='" + video + '\'' +
//                ", tour3d='" + tour3d + '\'' +
//                ", price=" + price +
//                ", phone='" + phone + '\'' +
//                ", email='" + email + '\'' +
//                ", dontShowInAds=" + dontShowInAds +
//                ", dontWantChat=" + dontWantChat +
//                ", accountType=" + accountType +
//                ", agreeToRules=" + agreeToRules +
//                '}';
//    }
//
//    public void fill() {
//        String[] dropdowns = {regionName, districtName, quartalName, streetName};
//        for (int i = 0; i < dropdowns.length; i++) fillDropdown(i, dropdowns[i], wait);
//        String[] fields = {String.valueOf(houseNumber), rcNumber, String.valueOf(area), String.valueOf(interestedChange), notesLt, notesEn, notesRu, video, tour3d, String.valueOf(price), phone, email};
//        String[] names = {"objNo", "rcNo", "plotSize", "interestedChange", "notes_lt", "notes_en", "notes_ru", "video", "tour3d", "price", "phone", "email"};
//        for (int i = 0; i < fields.length; i++) fillTextField(names[i], fields[i], wait);
//        boolean[] checks = {auction, agreeToRules, dontShowInAds, dontWantChat};
//        String[] checkNames = {"auction", "agreeToRules", "dontShowInAds", "dontWantChat"};
//        for (int i = 0; i < checks.length; i++) if (checks[i]) toggleCheckbox(checkNames[i], wait);
//    }
//
//    public void fillDropdown(int i, String val, WebDriverWait wait) {
//        if (val != null && !val.isEmpty()) {
//            List<WebElement> drops = driver.findElements(By.className("dropdown-input-value-title"));
//            if (i < drops.size()) {
//                drops.get(i).click();
//                String xp = i == 3 ? "//*[@id=\"streets_1\"]/li[1]/input" : "//*[@id=\"input_" + (i + 1) + "\"]/li[1]/input";
//                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xp))).sendKeys(val + Keys.ENTER);
//            }
//        }
//    }
//
//    public void fillTextField(String name, String val, WebDriverWait wait) {
//        if (val != null && !val.isEmpty()) {
//            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.name(name)));
//            element.clear();
//            element.sendKeys(val);
//        }
//    }
//
//    public void toggleCheckbox(String name, WebDriverWait wait) {
//        WebElement cb = wait.until(ExpectedConditions.elementToBeClickable(By.name(name)));
//        if (!cb.isSelected()) cb.click();
//    }
//
//    public void selectLocationFields(WebDriver driver) {
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        Actions actions = new Actions(driver);
//
//        // 1. Select "Savivaldybė"
//        WebElement savivaldybeDropdown = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//label[normalize-space()='Savivaldybė']/following-sibling::span[contains(@class, 'input-style-dropdown')]")
//        ));
//        savivaldybeDropdown.click();
//        WebElement savivaldybeOption = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//li[contains(@class, 'selected-dropdown-elem')]")
//        ));
//        savivaldybeOption.click();
//
//        // 2. Select "Gyvenvietė"
//        WebElement gyvenvieteDropdown = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//label[normalize-space()='Gyvenvietė']/following-sibling::span[contains(@class, 'input-style-dropdown')]")
//        ));
//        gyvenvieteDropdown.click();
//        WebElement gyvenvieteOption = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//li[contains(@class, 'selected-dropdown-elem')]")
//        ));
//        gyvenvieteOption.click();
//
//
//
//
//

//
//// Scroll into view before clicking
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", mikrorajonasElement);
//        mikrorajonasElement.click();
//        wait(2000);  // Wait for dropdown expansion
//
//
//// Simulate ENTER press to confirm selection
//        actions.sendKeys(Keys.RETURN).perform();
//
//
//// Expand the "Gatvė" dropdown list
//        WebElement gatveDropdown = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//label[normalize-space()='Gatvė']/following-sibling::*")
//        ));
//        gatveDropdown.click();  // Click to open the dropdown
//        wait(2000);  // Wait for dropdown expansion
//
//// Select the "A. Jakšto g." option using its onclick attribute
//        WebElement gatveElement = wait.until(ExpectedConditions.elementToBeClickable(
//                By.xpath("//li[contains(@onclick, \"selectDropdownValue(this, 'A. Jakšto g.')\")]")
//        ));
//
//// Scroll into view before clicking
//        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", gatveElement);
//        gatveElement.click();
//        wait(2000);  // Wait for dropdown expansion
//
//// Simulate ENTER press to confirm selection
//        actions.sendKeys(Keys.RETURN).perform();
//
//
