/*
KODO LOGIKOS APRAŠYMAS

1. Klasės struktūra ir duomenų saugojimas:
   - Plot klasė centralizuotai saugo visą informaciją apie sklypą.
   - Laukai apima regiono, gyvenvietės, mikrorajono, gatvės ir namo numerio duomenis.
   - RC numeris yra specialiai „sanitizuojamas“ – naudojamas statinis metodas sanitizeRcNumber,
     kuris pašalina visus ne-skaitmenis, užtikrindamas, kad į RC lauką pateikia tik skaitmenis.
   - Kiti laukai apima sklypo plotą, paskirties variantus, specialių ypatybių sąrašą,
     papildomus nustatymus (pvz., "Domina keitimas", "Varžytynės/aukcionas"),
     aprašymus įvairiomis kalbomis (LT, EN, RU) bei media nuorodas (YouTube, 3D turas).
   - Taip pat yra kontaktinė informacija – sklypo kaina, telefono numeris, el. paštas,
     ir nustatymai, ar išjungti el. pašto kontaktavimą bei „chat“ funkciją.
   - Galutinis laukas accountType nurodo, koks yra vartotojo tipas (pvz., 1 – Privatus asmuo).

2. Statinis metodas RC numerio sanitarizacijai:
   - Metodas sanitizeRcNumber(String rc) pašalina visus neatitinkančius skaitmenų simbolius.
   - Tai reiškia, kad jei unikaliame RC numeryje bus raidžių (ar kitų netinkamų simbolių),
     metodas grąžins tik skaitmenis, užtikrindamas duomenų vientisumą.

3. Konstruktorius:
   - Konstruktoriumi naudojame this. nuorodą, kad tiksliai priskirtume kiekvieną parametrą
     atitinkamam klasės laukui.
   - RC numeris yra paverčiamas panaudojant sanitizeRcNumber metodą, todėl galutiniame objekto
     lauke lieka tik skaitmeninė reikšmė.

4. Testavimo logika su TestNG ir Selenium:
   - TestNG naudojamas vienetiniams testams atlikti: tikrinamos reikšmės atitinka lūkesčius.
   - Assertions tikrina, kad kiekvienas laukas (pvz., regiono kodas, RC numerio sanitarizacija ir kt.)
     yra teisingai inicializuotas.
   - Selenium naudojamas sukurti naršyklės pagrindu vykdomus testus, pavyzdžiui:
       • Vienas testas atidaro Google ir tikrina, ar puslapio title turi "Google".
       • Kitas testas atidaro aruodas.lt nuorodą ir tikrina, ar URL atitinka lūkesčius.

5. Kodų organizacija:
   - Visi laukai yra vieši (public), todėl jie yra tiesiogiai pasiekiami.
   - Overridden toString() metodas leidžia vienoje eilutėje peržiūrėti visus objekto duomenis,
     kas padeda testavimo metu tikrinti, ar visi laukai tinkamai priskirti.
   - Testuose naudojami assert metodai (TestNG Assert) užtikrinti, kad objekto būsena atitinka
     numatytas reikšmes, įskaitant RC numerio sanitarizaciją.

Šis išsamus aprašymas turi padėti suprasti, kaip mūsų kodo logika buvo sukonstruota.

*/

// //     Selenium testas, kuris atidaro Google virš naršyklės, laukia, kol puslapio title pasikeis, ir tikrina.
//        Pasilikau kaip pavizdį.
//        @Test
//         public void testSeleniumExample() {
//        // Nustatome chromedriver vykdymo kelią (pakeiskite "path/to/chromedriver" į tikrą kelią).
//        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
//        WebDriver driver = new ChromeDriver(); // Sukuriame naują Chrome naršyklės instanciją.
//        driver.get("https://www.google.com");    // Atidarome Google puslapį.
//        driver.manage().window().maximize();       // Išdidiname naršyklės langą.
//        // Naudojame explicit wait, kad palauktume, kol title turės žodį "Google".
//        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//        wait.until(ExpectedConditions.titleContains("Google")); // Laukiame, kol title bus pakeistas.
//        Assert.assertTrue(driver.getTitle().contains("Google"), "Puslapio title turėtų turėti 'Google'");
//        driver.quit(); // Uždarykite naršyklę.


package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;          // Naudojama sukurti Chrome naršyklės instanciją.
import org.openqa.selenium.interactions.Actions;          // Leidžia simuliuoti klaviatūros ir pelės įvykius.
import org.openqa.selenium.support.ui.ExpectedCondition;  // Leidžia kurti individualias laukimo sąlygas.
import org.openqa.selenium.support.ui.ExpectedConditions; // Naudojama dažniausiai pasitaikančioms laukimo sąlygoms.
import org.openqa.selenium.support.ui.WebDriverWait;        // Leidžia laukti, kol įvyksta tam tikra sąlyga.
import org.testng.annotations.Test;                        // TestNG anotacija testams žymėti.
import org.testng.Assert;                                  // TestNG asercijų metodai tikrinimui.
import java.time.Duration;                                 // Leidžia nustatyti laiko intervalus.
import java.util.Arrays;                                   // Naudojama greitai sukurti masyvus/sąrašus.
import java.util.List;                                     // List duomenų struktūra.

public class PlotTests {

    /**
     * Helper metodas, kuris laukia 4 sekundes naudodamas WebDriverWait.
     */
    private void waitForFourSeconds(WebDriver driver) {
        new WebDriverWait(driver, Duration.ofSeconds(4), Duration.ofMillis(500))
                .until(new ExpectedCondition<Boolean>() {
                    long startTime = System.currentTimeMillis(); // Įrašome laukimo pradžios laiką
                    @Override
                    public Boolean apply(WebDriver d) {
                        // Grąžiname true, kai praėjo bent 4000 ms (4 sekundės) nuo startTime
                        return (System.currentTimeMillis() - startTime) >= 4000;
                    }
                });
    }

    /**
     * Metodas, kuris suranda ir pasirenka savivaldybės, gyvenvietės, mikrorajono ir gatvės laukus.
     * Paieška vykdoma pagal label tekstą (naudojant normalize-space) arba absoliutų XPath, kai reikia.
     * Tarp pasirinkimų laukiam 4 sekundes, po kurių simuliuojamas ENTER paspaudimas.
     */
    private void selectLocationFields(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Actions actions = new Actions(driver);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        // 1. Select "Savivaldybė"
        WebElement savivaldybeDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[normalize-space()='Savivaldybė']/following-sibling::*")
        ));
        savivaldybeDropdown.click();
        waitForFourSeconds(driver);
        actions.sendKeys(Keys.RETURN).perform();

        // 2. Select "Gyvenvietė"
        WebElement gyvenvieteDropdown = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[normalize-space()='Gyvenvietė']/following-sibling::*")
        ));
        gyvenvieteDropdown.click();
        waitForFourSeconds(driver);
        actions.sendKeys(Keys.RETURN).perform();

        // 3. Select "Mikrorajonas" (Ensure dropdown is present)
        WebElement mikrorajonasDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("/html/body/div[2]/div[3]/div/ul")
        ));
        mikrorajonasDropdown.click();

        // Ensure dropdown expands fully
        waitForFourSeconds(driver);

        // Locate "Balsiai" using a stable XPath, ensuring it is interactable
        WebElement mikrorajonasElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[@data-search-string='balsiai']")
        ));

        // Scroll element into view before clicking
        js.executeScript("arguments[0].scrollIntoView(true);", mikrorajonasElement);
        mikrorajonasElement.click();
        waitForFourSeconds(driver);
        actions.sendKeys(Keys.RETURN).perform();

        // 4. Select "Gatvė" (Ensure dropdown exists first)
        WebElement gatveDropdown = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//label[normalize-space()='Gatvė']/following-sibling::*")
        ));
        gatveDropdown.click();
        waitForFourSeconds(driver);

        // Ensure stable selection of street dropdown
        WebElement gatveElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[contains(text(),'Gatvė')]")
        ));

        js.executeScript("arguments[0].scrollIntoView(true);", gatveElement);
        gatveElement.click();
        waitForFourSeconds(driver);
        actions.sendKeys(Keys.RETURN).perform();
    }



    /**
     * Testas, tikrinantis, kad Plot objektas yra teisingai sukurtas ir visi laukai turi tikėtiną reikšmę.
     */
    @Test
    public void testPlotCreation() {
        List<String> purposes = Arrays.asList("property", "manufacturingland", "farm", "garden", "forest",
                "factory", "storage", "comm", "recr", "none"); // Paskirties variantų sąrašas
        List<Integer> specialFeatures = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 11, 501); // Specialiųjų ypatybių sąrašas
        String notesLt = "Sklypas mane tenkina ir norėčiau daugiau galimybių rinktis apmokėjimo būdui"; // Lietuviškas aprašymas
        String notesEn = "The plot meets my requirements and I would like more options for selecting the payment method"; // Angliškas aprašymas
        String notesRu = "Участок мне подходит, и я хотел бы иметь больше возможностей для выбора способа оплаты."; // Rusiškas aprašymas
        String video = "https://www.youtube.com/embed/sklypaiExample"; // Video nuoroda
        String tour3d = "https://www.example.com/3d-tour/sklypas"; // 3D turo nuoroda
        int price = 35000; // Sklypo kaina
        String phone = "+37060000000"; // Telefono numeris
        String email = "example@example.com"; // El. pašto adresas
        boolean dontShowInAds = false; // Kontaktavimo flag (false reiškia – neišjungtas)
        boolean dontWantChat = true;   // Chat flag (true – išjungta)
        int accountType = 1;           // Vartotojo tipas – 1 = Privatus asmuo
        boolean agreeToRules = true;   // Vartotojas sutinka su taisyklėmis

        String unsanitizedRc = "1234-5678-ABC9012"; // RC numeris su nereikalingomis raidėmis
        String expectedRc = "123456789012"; // Tikėtina RC numerio reikšmė po sanitizacijos

        // Sukuriame naują Plot objektą naudodami pavyzdinius duomenis.
        Plot plot = new Plot(461, "Vilnius", 1, "Vilniaus m.", 2, "Balsiai",
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

        // Asercijos tikrina, ar kiekvienas laukas atitinka lūkesčius.
        Assert.assertEquals(plot.regionCode, 461);                           // Tikrina regiono kodą.
        Assert.assertEquals(plot.regionName, "Vilnius");                       // Tikrina regiono pavadinimą.
        Assert.assertEquals(plot.districtCode, 1);                             // Tikrina gyvenvietės kodą.
        Assert.assertEquals(plot.districtName, "Vilniaus m.");                   // Tikrina gyvenvietės pavadinimą.
        Assert.assertEquals(plot.quartalCode, 2);                               // Tikrina mikrorajono kodą.
        Assert.assertEquals(plot.quartalName, "Balsiai");                       // Tikrina mikrorajono pavadinimą.
        Assert.assertEquals(plot.streetCode, 21862);                            // Tikrina gatvės kodą.
        Assert.assertEquals(plot.streetName, "A. Jakšto g.");                    // Tikrina gatvės pavadinimą.
        Assert.assertEquals(plot.houseNumber, 5);                               // Tikrina namo numerį.
        Assert.assertTrue(plot.checkboxSelected);                               // Užtikrina, kad namo numerio rodymo flagas yra true.
        Assert.assertEquals(plot.rcNumber, expectedRc);                         // Užtikrina, kad RC numeris yra teisingai išfiltruotas.
        Assert.assertTrue(plot.rcCheckboxSelected);                             // Tikrina RC rodymo flagą.
        Assert.assertEquals(plot.area, 200.0, 0.001);                             // Tikrina sklypo ploto dydį.
        Assert.assertEquals(plot.purposes, purposes);                             // Tikrina paskirties variantų sąrašą.
        Assert.assertTrue(plot.showAttributes);                                  // Tikrina flagą, ar rodyti ypatumus.
        Assert.assertEquals(plot.specialFeatures, specialFeatures);               // Tikrina specialiųjų ypatybių sąrašą.
        Assert.assertTrue(plot.interestedChange);                                // Tikrina "Domina keitimas" flagą.
        Assert.assertFalse(plot.auction);                                         // Užtikrina, kad aukciono flagas yra false.
        Assert.assertEquals(plot.notesLt, notesLt);                               // Tikrina lietuvišką aprašymą.
        Assert.assertEquals(plot.notesEn, notesEn);                               // Tikrina anglų aprašymą.
        Assert.assertEquals(plot.notesRu, notesRu);                               // Tikrina rusišką aprašymą.
        Assert.assertEquals(plot.video, video);                                   // Tikrina YouTube nuorodą.
        Assert.assertEquals(plot.tour3d, tour3d);                                   // Tikrina 3D turo nuorodą.
        Assert.assertEquals(plot.price, price);                                   // Tikrina sklypo kainą.
        Assert.assertEquals(plot.phone, phone);                                   // Tikrina telefono numerį.
        Assert.assertEquals(plot.email, email);                                   // Tikrina el. pašto adresą.
        Assert.assertEquals(plot.dontShowInAds, dontShowInAds);                   // Tikrina, kad kontaktavimo flagas yra false.
        Assert.assertTrue(plot.dontWantChat);                                     // Tikrina, kad chat funkcija išjungta.
        Assert.assertEquals(plot.accountType, accountType);                       // Tikrina vartotojo tipą.
        Assert.assertTrue(plot.agreeToRules);                                     // Tikrina sutikimą su taisyklėmis.
    }

    /**
     * Testas statiniam metodui, kuris iš RC numerio pašalina netinkamus simbolius.
     */
    @Test
    public void testRcNumberSanitizationStaticMethod() {
        String unsanitized = "12ab34CD56";  // Įvestas RC numeris su nerinktais simboliais.
        String expected = "123456";         // Tikėtina reikšmė su tik skaitmenimis.
        Assert.assertEquals(expected, Plot.sanitizeRcNumber(unsanitized));
    }



    /**
     * Selenium testas, kuris atidaro aruodas.lt skelbimų puslapį, laukiant kol pasirodys slapukų mygtukas,
     * tada laukia elementų pagal label (savivaldybė, gyvenvietė, mikrorajonas, gatvė) išskleidimo ir simuliuoja ENTER paspaudimus.
     */
    @Test
    public void testAruodasSkelbimas() {

        WebDriver driver = new ChromeDriver(); // Atidarome Chrome naršyklę.
        driver.get("https://www.aruodas.lt/ideti-skelbima/?obj=11&offer_type=1"); // Atidarome aruodas.lt skelbimų puslapį.
        driver.manage().window().maximize(); // Išdidiname naršyklės langą.

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Sukuriame explicit wait objektą su 10 sekundžių limitu.
        // Laukiame, kol bus pasiekiamas slapukų priėmimo mygtukas ir jį paspaudžiame.
        wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-reject-all-handler"))).click();
        // Laukiame, kol URL patvirtins, kad esame teisingame puslapyje.
        wait.until(ExpectedConditions.urlContains("aruodas.lt/ideti-skelbima"));

        // Iškviečiame metodą, kuris suranda dropdown laukus pagal label tekstą ir simuliuoja ENTER paspaudimus.
        selectLocationFields(driver);

        // Patikriname, ar URL vis dar atitinka lūkesčius.
        Assert.assertTrue(driver.getCurrentUrl().contains("aruodas.lt/ideti-skelbima"),
                "URL turėtų turėti 'aruodas.lt/ideti-skelbima'");

    }

    /**
     * Selenium testas, tikrinantis Plot.toString() metodo gražų atvaizdavimą.
     */
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

        Plot plot = new Plot(461, "Vilnius", 1, "Vilniaus m.", 2, "Balsiai",
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

        Assert.assertTrue(plotString.contains("Vilnius"), "Plot string turėtų turėti 'Vilnius'");
        Assert.assertTrue(plotString.contains("Vilniaus m."), "Plot string turėtų turėti 'Vilniaus m.'");
        Assert.assertTrue(plotString.contains("Balsiai"), "Plot string turėtų turėti 'Balsiai'");
        Assert.assertTrue(plotString.contains("A. Jakšto g."), "Plot string turėtų turėti 'A. Jakšto g.'");
        Assert.assertTrue(plotString.contains("5"), "Plot string turėtų turėti namo numerį '5'");
        Assert.assertTrue(plotString.contains(Plot.sanitizeRcNumber(unsanitizedRc)),
                "Plot string turėtų turėti sanitizuotą RC numerį");
        Assert.assertTrue(plotString.contains("200.0"), "Plot string turėtų turėti '200.0'");
        Assert.assertTrue(plotString.contains("property"), "Plot string turėtų turėti 'property'");
        Assert.assertTrue(plotString.contains("showAttributes=true"), "Plot string turėtų turėti 'showAttributes=true'");
        Assert.assertTrue(plotString.contains("interestedChange=true"), "Plot string turėtų turėti 'interestedChange=true'");
        Assert.assertTrue(plotString.contains("auction=false"), "Plot string turėtų turėti 'auction=false'");
        Assert.assertTrue(plotString.contains(notesLt), "Plot string turėtų turėti lietuvišką aprašymą");
        Assert.assertTrue(plotString.contains(notesEn), "Plot string turėtų turėti anglų aprašymą");
        Assert.assertTrue(plotString.contains(notesRu), "Plot string turėtų turėti rusišką aprašymą");
        Assert.assertTrue(plotString.contains(video), "Plot string turėtų turėti video nuorodą");
        Assert.assertTrue(plotString.contains(tour3d), "Plot string turėtų turėti 3D turo nuorodą");
        Assert.assertTrue(plotString.contains(String.valueOf(price)), "Plot string turėtų turėti sklypo kainą");
        Assert.assertTrue(plotString.contains(phone), "Plot string turėtų turėti telefono numerį");
        Assert.assertTrue(plotString.contains(email), "Plot string turėtų turėti el. pašto adresą");
        Assert.assertTrue(plotString.contains("dontShowInAds=false"), "Plot string turėtų turėti 'dontShowInAds=false'");
        Assert.assertTrue(plotString.contains("dontWantChat=true"), "Plot string turėtų turėti 'dontWantChat=true'");
        Assert.assertTrue(plotString.contains("accountType=" + accountType), "Plot string turėtų turėti vartotojo tipą");
        Assert.assertTrue(plotString.contains("agreeToRules=true"), "Plot string turėtų turėti 'agreeToRules=true'");
    }
}
