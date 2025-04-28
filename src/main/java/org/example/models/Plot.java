package org.example.models;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Plot {
    private WebDriver driver;
    private WebDriverWait wait;

    // Regiono ir adreso susiję laukai
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

    // RC numerio laukai
    public String rcNumber;
    public boolean rcCheckboxSelected;

    // Ploto ir paskirties laukai
    public double area;
    public List<String> purposes;
    public boolean showAttributes;
    public List<Integer> specialFeatures;

    // Papildomi nustatymai
    public boolean interestedChange;
    public boolean auction;

    // Aprašymo laukai įvairiomis kalbomis
    public String notesLt;
    public String notesEn;
    public String notesRu;

    // Media nuorodos
    public String video;
    public String tour3d;

    // Kontaktinė informacija ir kaina
    public int price;
    public String phone;
    public String email;

    // Papildomi kontaktų nustatymai
    public boolean dontShowInAds;
    public boolean dontWantChat;

    // Vartotojo tipo ir taisyklių sutikimo laukai
    public int accountType;
    public boolean agreeToRules;

    public Plot() {
    }
    public Plot( WebDriver driver,
                 int regionCode, String regionName,
                 int districtCode, String districtName,
                 int quartalCode, String quartalName,
                 int streetCode, String streetName,
                 int houseNumber, boolean checkboxSelected,
                 String rcNumber, boolean rcCheckboxSelected,
                 double area, List<String> purposes,
                 boolean showAttributes, List<Integer> specialFeatures,
                 boolean interestedChange, boolean auction,
                 String notesLt, String notesEn, String notesRu,
                 String video, String tour3d,
                 int price, String phone, String email,
                 boolean dontShowInAds, boolean dontWantChat,
                 int accountType, boolean agreeToRules
    ) {
        this.wait = new WebDriverWait(driver, Duration.ofMillis(1000));
        this.driver = driver;
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
        this.rcNumber = sanitizeRcNumber(rcNumber);
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


    public void fill() {
        fillRegion();
        fillDistrict();
        fillQuartal();
        fillStreet();

    }
    public static String sanitizeRcNumber(String rc) {
        if (rc == null) return "";
        return rc.replaceAll("[^0-9]", "");
    }
    private void fillStreet() {
        this.driver.findElements(By.className("dropdown-input-value-title")).get(3).click();
        wait(200);
        this.driver.findElement(By.xpath("//*[@id=\"streets_1\"]/li[1]/input")).sendKeys(this.streetName);//veliau reikes korekciju
        wait(300);
        this.driver.findElement(By.xpath("//*[@id=\"streets_1\"]/li[1]/input")).sendKeys(Keys.ENTER);
    }
    private void fillQuartal() {
        this.driver.findElements(By.className("dropdown-input-value-title")).get(2).click();
        this.driver.findElements(By.className("dropdown-input-search-value")).get(1).sendKeys(this.quartalName);//veliau reikes korekciju
        wait(2000);
        this.driver.findElements(By.className("dropdown-input-search-value")).get(1).sendKeys(Keys.ENTER);
    }
    private void fillDistrict() { //padaryti veliau
        //        this.driver.findElements(By.className("dropdown-input-value-title")).get(1).click();
        //        this.driver.findElement(By.className("dropdown-input-search-value")).sendKeys(this.region);
        //        this.driver.findElement(By.className("dropdown-input-search-value")).sendKeys(Keys.ENTER);
    }
    private void fillRegion() {
        this.driver.findElements(By.className("dropdown-input-value-title")).get(0).click();
        this.driver.findElement(By.className("dropdown-input-search-value")).sendKeys(this.regionName);
        wait(300);
        this.driver.findElement(By.className("dropdown-input-search-value")).sendKeys(Keys.ENTER);
    }

    public void wait(int time){
        try{
            Thread.sleep(time);
        }catch (Exception e){}
    }

}

