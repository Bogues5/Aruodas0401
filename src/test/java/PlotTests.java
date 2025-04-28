
/*
package org.example.tests;
import org.example.models.Plot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class PlotTests {
    public WebDriver driver;
    public WebDriverWait wait;

    @BeforeClass
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.aruodas.lt/ideti-skelbima/?obj=11&offer_type=1");
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Paspaudžiame slapukų sutikimo mygtuką
        try {
            WebElement acceptBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-reject-all-handler")));
            acceptBtn.click();
        } catch (Exception e) {
            System.out.println("Slapukų sutikimo mygtukas nerastas arba jau paspaustas: " + e.getMessage());
        }
    }

    @Test
    public void testPlotCreation() {
        List<String> purposes = Arrays.asList("property", "manufacturingland", "farm", "garden", "forest",
                "factory", "storage", "comm", "recr", "none");
        List<Integer> specialFeatures = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 11, 501);
        String notesLt = "Sklypas mane tenkina ir norėčiau daugiau galimybių rinktis apmokėjimo būdui";
        String notesEn = "The plot meets my requirements and I would like more options for selecting the payment method";
        String notesRu = "Участок мне подходит, и я хотел бы иметь больше возможностей для выбора способа оплаты.";
        String video = "https://www.youtube.com/embed/sklypaiExample";
        String tour3d = "https://www.example.com/3d-tour/sklypas";
        int price = 35000;
        String phone = "+37060000000";
        String email = "example@example.com";
        boolean dontShowInAds = false;
        boolean dontWantChat = true;
        int accountType = 1;
        boolean agreeToRules = true;
        String unsanitizedRc = "1234-5678-ABC9012";
        String expectedRc = "123456789012";

        // Sukuriame naują Plot objektą naudodami pavyzdinius duomenis
        Plot plot = new Plot(driver, 461, "Vilnius", 1, "Vilniaus m.", 2, "Balsiai",
                21862, "A. Jakšto g.", 5, true,
                unsanitizedRc, true,
                200.0, purposes,
                true, specialFeatures,
                true, false,
                notesLt, notesEn, notesRu,
                video, tour3d,
                price, phone, email,
                dontShowInAds, dontWantChat,
                accountType, agreeToRules);

        // Asercijos tikrina, ar kiekvienas laukas atitinka lūkesčius
       Assert.assertEquals(plot.regionCode, 461);
       Assert.assertEquals(plot.regionName, "Vilnius");

    }

    @Test
    public void testRcNumberSanitizationStaticMethod() {
        String unsanitized = "12ab34CD56";
        String expected = "123456";
        Assert.assertEquals(expected, Plot.sanitizeRcNumber(unsanitized));
    }

    @Test
    public void testAruodasSkelbimas() {
        // Sukuriame Plot objektą
//        Plot plot = new Plot(driver, wait);

        // Iškviečiame metodą, kuris suranda dropdown laukus pagal label tekstą ir simuliuoja ENTER paspaudimus
//        plot.selectLocationFields(driver);

        // Patikriname, ar URL vis dar atitinka lūkesčius
        Assert.assertTrue(driver.getCurrentUrl().contains("aruodas.lt/ideti-skelbima"),
                "URL turėtų turėti 'aruodas.lt/ideti-skelbima'");
    }

    @Test
    public void testPlotToString() {
        List<String> purposes = Arrays.asList("property", "manufacturingland", "farm", "garden", "forest",
                "factory", "storage", "comm", "recr", "none");
        List<Integer> specialFeatures = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 11, 501);
        String notesLt = "Sklypas mane tenkina ir norėčiau daugiau galimybių rinktis apmokėjimo būdui";
        String notesEn = "The plot meets my requirements and I would like more options for selecting the payment method";
        String notesRu = "Участок мне подходит, и я хотел бы иметь больше возможностей для выбора способа оплаты.";
        String video = "https://www.youtube.com/embed/sklypaiExample";
        String tour3d = "https://www.example.com/3d-tour/sklypas";
        int price = 35000;
        String phone = "+37060000000";
        String email = "example@example.com";
        boolean dontShowInAds = false;
        boolean dontWantChat = true;
        int accountType = 1;
        boolean agreeToRules = true;
        String unsanitizedRc = "1234-5678-9012";

        Plot plot = new Plot(driver, 461, "Vilnius", 1, "Vilniaus m.", 2, "Bajorai",
                21862, "A. Jakšto g.", 5, true,
                unsanitizedRc, true,
                200.0, purposes,
                true, specialFeatures,
                true, false,
                notesLt, notesEn, notesRu,
                video, tour3d,
                price, phone, email,
                dontShowInAds, dontWantChat,
                accountType, agreeToRules);
        String plotString = plot.toString();
        System.out.println(plot);
        plot.fill();
    }
*/

/*  PlotTests.java
PASKIRTIS:

Ši klasė skirta testuoti Plot klasės fill metodą, patikrinant, ar forma užpildoma teisingai.

STRUKTŪRA IR KINTAMIEJI:

WebDriver driver ir WebDriverWait wait – naršyklės valdymui ir laukimui.
Metodai:
@BeforeClass setUp: Inicializuoja Chrome naršyklę, atidaro tinklalapį ir paspaudžia slapukų sutikimo mygtuką.
@Test testFillPlotForm: Sukuria Plot objektą su testiniais duomenimis, užpildo formą ir patikrina laukų reikšmes.
@AfterClass tearDown: Uždaro naršyklę po testų.
Logika:

Testas sukuria Plot objektą su iš anksto apibrėžtais duomenimis (pvz., Kaunas, Laisvės al. 5, 200 m² plotas ir kt.).
Po formos užpildymo naudojami Assert metodai, kad patikrintų, ar laukų reikšmės ir checkbox būsenos atitinka įvestus duomenis.
Testas apima visus formos laukus: adresą, RC numerį, plotą, paskirtis, ypatumus, aprašymus, kainą, kontaktus ir nustatymus.
Paaiškinimai
Selenium naudojimas: Abu failai naudoja Selenium WebDriver formos automatizavimui ir testavimui. By.name, By.id, By.xpath selektoriai atitinka tinklalapio HTML struktūrą.
Kintamųjų prieiga: Visi kintamieji yra public, todėl juos galima tiesiogiai pasiekti ir testuoti.
Patikimumas: Kodas turi klaidų tvarkymą (try-catch blokai), kad išvengtų gedimų, jei elementai nerandami.
Testavimo duomenys: Teste naudojami realistiški duomenys, atitinkantys formos laukus, pvz., telefono numeris su šalies kodu, Youtube nuoroda ir kt.

 Čia yra google pavizdys su assert

       WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");
      driver.manage().window().maximize();
      WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       wait.until(ExpectedConditions.titleContains("Google"));
       Assert.assertTrue(driver.getTitle().contains("Google"), "Puslapio title turėtų turėti 'Google'");
      driver.quit();  Uždarykite naršyklę.*/

import org.example.models.Plot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.lang.classfile.TypeAnnotation;
import java.nio.channels.ScatteringByteChannel;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class PlotTests {
    public WebDriver driver;
    public WebDriverWait wait;
    public Plot plot;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://www.aruodas.lt/ideti-skelbima/?obj=11&offer_type=1");
        acceptCookies();

        plot = new Plot(
                driver,
                1, "Vilniaus m. sav.",
                2, "Vilniaus r. sav.",
                3, "Centras",
                4, "Gedimino pr.",
                10, true,
                "123456789", true,
                150.5, Arrays.asList("Gyvenamasis", "Komercinis"),
                true, Arrays.asList(1, 2),
                true, false,
                "Pastaba LT", "Note EN", "Заметка RU",
                "http://video.com", "http://3d.com",
                100000, "+37060000000", "test@example.com",
                false, true,
                1, true
        );
    }

    public void acceptCookies() {
        try {
            WebElement acceptButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
            acceptButton.click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("onetrust-policy-text")));
        } catch (Exception e) {
            System.out.println("Slapukų lentelė nerasta: " + e.getMessage());
        }
    }

    @Test
    public void testPlotFormFilling() {
        plot.fill();

        WebElement regionInput = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("dropdown-input-value-title"))
        );
        Assert.assertEquals("Vilniaus m. sav.", regionInput.getAttribute("value"));

        WebElement houseNum = driver.findElement(By.name("FHouseNum"));
        Assert.assertEquals("10", houseNum.getAttribute("value"));

        WebElement rcNumber = driver.findElement(By.name("RCNumber"));
        Assert.assertEquals("123456789", rcNumber.getAttribute("value"));

        WebElement area = driver.findElement(By.name("FAreaOverAll"));
        Assert.assertEquals("150.5", area.getAttribute("value"));

        List<WebElement> purposeCheckboxes = driver.findElements(By.name("FIntendance[]"));
        Assert.assertTrue(purposeCheckboxes.stream().anyMatch(cb -> cb.getAttribute("value").equals("Gyvenamasis") && cb.isSelected()));
        Assert.assertTrue(purposeCheckboxes.stream().anyMatch(cb -> cb.getAttribute("value").equals("Komercinis") && cb.isSelected()));

        WebElement price = driver.findElement(By.name("price"));
        Assert.assertEquals("100000", price.getAttribute("value"));

        WebElement dontWantChat = driver.findElement(By.name("dont_want_chat"));
        Assert.assertTrue(dontWantChat.isSelected());
    }

    @Test
    public void testDropdownSynchronization() {
        plot.fillRegion();

        WebElement regionDropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.className("dropdown-input-value-title"))
        );
        Assert.assertEquals("Vilniaus m. sav.", regionDropdown.getAttribute("value"));

        plot.fillQuartal();
        List<WebElement> dropdowns = driver.findElements(By.className("dropdown-input-value-title"));
        Assert.assertEquals("Centras", dropdowns.get(2).getAttribute("value"));
    }

    @Test
    public void testCheckboxAndRadioInteraction() {
        plot.fill();

        WebElement dontShowInAds = driver.findElement(By.name("dont_show_in_ads"));
        Assert.assertFalse(dontShowInAds.isSelected());

        WebElement accountTypeRadio = driver.findElement(By.xpath("//input[@name='account_type' and @value='1']"));
        Assert.assertTrue(accountTypeRadio.isSelected());
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}