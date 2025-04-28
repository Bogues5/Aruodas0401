package org.example.models;
/*
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
        driver.findElement(By.xpath("//*[@id=\"uploadPhotoBtn\"]/input")).sendKeys("C:\\Users\\Halo 5\\IdeaProjects\\Aruodas0401\\photos\\p1.jpeg");
    }
    public static String sanitizeRcNumber(String rc) {
        if (rc == null) return "";
        return rc.replaceAll("[^0-9]", "");
    }
    private void fillStreet() {
        this.driver.findElements(By.className("dropdown-input-value-title")).get(3).click();
        wait(200);
        this.driver.findElement(By.xpath("//*[@id=\"streets_1\"]/li[1]/input")).sendKeys(this.streetName);
        wait(300);
        this.driver.findElement(By.xpath("//*[@id=\"streets_1\"]/li[1]/input")).sendKeys(Keys.ENTER);
    }
    public void fillQuartal() {
        this.driver.findElements(By.className("dropdown-input-value-title")).get(2).click();
        this.driver.findElements(By.className("dropdown-input-search-value")).get(1).sendKeys(this.quartalName);
        wait(2000);
        this.driver.findElements(By.className("dropdown-input-search-value")).get(1).sendKeys(Keys.ENTER);
    }
    public void fillDistrict() {
               this.driver.findElements(By.className("dropdown-input-value-title")).get(1).click();
               this.driver.findElement(By.className("dropdown-input-search-value")).sendKeys(this.regionName);
               this.driver.findElement(By.className("dropdown-input-search-value")).sendKeys(Keys.ENTER);
    }
    public void fillRegion() {
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
 */


/* Aprašymas: Kodo logika, struktūra ir paaiškinimai
Plot.java
 PASKIRTIS:
Ši klasė skirta automatizuotai užpildyti sklypo skelbimo formą tinklalapyje, naudojant Selenium WebDriver.
Visi kintamieji ir metodai yra public.

STRUKTŪRA IR KINTAMIEJI
WebDriver driver ir WebDriverWait wait – naudojami naršyklės valdymui ir laukimui, kol elementai bus pasiekiami.
Adreso laukai: regionCode, regionName, districtCode, districtName, quartalCode, quartalName, streetCode, streetName, houseNumber, checkboxSelected.
RC numerio laukai: rcNumber, rcCheckboxSelected.
Ploto ir paskirties laukai: area, purposes, showAttributes, specialFeatures.
Papildomi nustatymai: interestedChange, auction.
Aprašymai: notesLt, notesEn, notesRu.
Media: video, tour3d.
Kontaktinė informacija: price, phone, email, dontShowInAds, dontWantChat.
Vartotojo tipas ir taisyklės: accountType, agreeToRules.
Konstruktoriai:
Tuščias konstruktorius (public Plot()) – leidžia sukurti objektą be inicializacijos.
Pilnas konstruktorius – priima visus kintamuosius ir inicializuoja objektą, taip pat nustato wait ir išvalo rcNumber.
Metodai:
sanitizeRcNumber: Pašalina ne skaitmeninius simbolius iš RC numerio.
fill: Pagrindinis metodas, kuris kviečia kitus metodus formos užpildymui.
fillRegion, fillDistrict, fillQuartal, fillStreet: Užpildo dropdown tipo laukus (regioną, rajoną, kvartalą, gatvę).
fillTextField: Užpildo tekstinius laukus pagal pavadinimą.
setCheckbox (dvi versijos): Nustato checkbox būseną pagal boolean reikšmę arba pagal reikšmę (value).
selectRadio: Pasirenka radio mygtuką pagal pavadinimą ir reikšmę.
Logika:

Forma užpildoma nuosekliai: pirmiausia adresas (dropdown'ai), tada tekstiniai laukai, checkbox'ai ir radio mygtukai.
Naudojamas WebDriverWait, kad būtų užtikrinta, jog elementai yra pasiekiami prieš juos užpildant.
Checkbox'ai ir radio mygtukai valdomi pagal pateiktas boolean reikšmes arba specifines reikšmes (pvz., "Y", "1").*/

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.List;

public class Plot {
    public WebDriver driver;
    public WebDriverWait wait;
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

    public void fill() {
        fillRegion();
        // Pridėkite kitus metodus pagal jūsų projekto poreikius
    }

    public void fillRegion() {
        wait.until(ExpectedConditions.elementToBeClickable(By.className("dropdown-input-value-title")));
        List<WebElement> dropdowns = driver.findElements(By.className("dropdown-input-value-title"));
        dropdowns.get(0).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("dropdown-input-search-value")));
        driver.findElement(By.className("dropdown-input-search-value")).sendKeys(this.regionName);
        driver.findElement(By.className("dropdown-input-search-value")).sendKeys(Keys.ENTER);
    }


    public void fillQuartal() {


    }
}