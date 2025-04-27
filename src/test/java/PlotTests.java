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

import org.openqa.selenium.By;                             // Lokatorių paieškai naudojamas klasė
import org.openqa.selenium.Keys;                           // Leidžia simuliuoti klaviatūros paspaudimus (pvz., ENTER)
import org.openqa.selenium.WebDriver;                     // WebDriver sąsaja; pagrindinė naršyklės kontrolė
import org.openqa.selenium.WebElement;                    // Atstovauja HTML elementą
import org.openqa.selenium.chrome.ChromeDriver;          // ChromeDriver klasė; naudojama naršyklės atidarymui
import org.openqa.selenium.interactions.Actions;          // Leidžia simuliuoti klaviatūros ir pelės įvykius
import org.openqa.selenium.support.ui.ExpectedCondition;  // Naudojama kurti individualias laukimo sąlygas
import org.openqa.selenium.support.ui.ExpectedConditions; // Numatyti laukimo kriterijai (pvz., elemento matomumas)
import org.openqa.selenium.support.ui.WebDriverWait;        // Leidžia laukti, kol įvyksta tam tikra sąlyga
import org.testng.annotations.Test;                        // TestNG anotacija testams žymėti
import org.testng.Assert;                                  // TestNG asercijos, skirtos patikrinti rezultatą
import java.time.Duration;                                 // Leidžia nustatyti laiko intervalus
import java.util.Arrays;                                   // Naudojama lengvai sukurti sąrašus
import java.util.List;                                     // List sąsaja

// Public TestNG klasė, skirta testuoti Plot klasės funkcionalumą ir naršyklės sąveiką su Selenium.
public class PlotTests {

    // Helper metodas, kuris laukia 4 sekundes naudojant WebDriverWait.
    public void waitForFourSeconds(WebDriver driver) {
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

    // Metodas, ieškantis pagal label tekstus dropdown elementų susaistymą ir po 4 sekundžių laukimo simuliuojantis ENTER paspaudimą.
    public void selectLocationFields(WebDriver driver) {
        // Sukuriame WebDriverWait objektą, kad lauktume iki 10 sekundžių, kol elementas taps interaktyvus.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Sukuriame Actions objektą, skirtą simuliuoti klaviatūros įvykius.
        Actions actions = new Actions(driver);

        // 1. Pasirinkti "Savivaldybė"
        // Ieškome elemento, kurio label tekstas turi "Savivaldybė", ir surandame jo tolimesnį elementą (dropdown).
        WebElement savivaldybeElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[contains(text(),'Savivaldybė')]/following-sibling::*")
        ));
        savivaldybeElement.click(); // Paspaudžiame elementą, kad išskleistume savivaldybės pasirinkimus.
        waitForFourSeconds(driver); // Laukiame fiksuotą 4 sekundžių intervalą.
        actions.sendKeys(Keys.RETURN).perform(); // Simuliuojame ENTER paspaudimą, patvirtinant pasirinkimą.

        // 2. Pasirinkti "Gyvenvietė"
        WebElement gyvenvieteElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[contains(text(),'Gyvenvietė')]/following-sibling::*")
        ));
        gyvenvieteElement.click(); // Paspaudžiame dropdown'ą, kad išskleistume gyvenvietės pasirinkimus.
        waitForFourSeconds(driver); // Laukiame 4 sekundes.
        actions.sendKeys(Keys.RETURN).perform(); // Simuliuojame ENTER paspaudimą.

        // 3. Pasirinkti "Mikrorajonas"
        WebElement mikrorajonasElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[contains(text(),'Mikrorajonas')]/following-sibling::*")
        ));
        mikrorajonasElement.click(); // Paspaudžiame mikrorajono dropdown'ą.
        waitForFourSeconds(driver); // Laukiame 4 sekundes.
        actions.sendKeys(Keys.RETURN).perform(); // Simuliuojame ENTER paspaudimą.

        // 4. Pasirinkti "Gatvė"
        WebElement gatveElement = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//label[contains(text(),'Gatvė')]/following-sibling::*")
        ));
        gatveElement.click(); // Paspaudžiame gatvės dropdown'ą.
        waitForFourSeconds(driver); // Laukiame 4 sekundes.
        actions.sendKeys(Keys.RETURN).perform(); // Simuliuojame ENTER paspaudimą.
    }

    // Testas, patikrinantis, ar Plot objektas yra teisingai inicializuotas ir visi laukai turi tikėtiną reikšmę.
    @Test
    public void testPlotCreation() {
        // Sukuriame paskirties variantų sąrašą.
        List<String> purposes = Arrays.asList(
                "property", "manufacturingland", "farm", "garden", "forest",
                "factory", "storage", "comm", "recr", "none"
        );
        // Sukuriame specialiųjų ypatybių sąrašą.
        List<Integer> specialFeatures = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 11, 501);
        // Aprašymo tekstai įvairiomis kalbomis:
        String notesLt = "Sklypas mane tenkina ir norėčiau daugiau galimybių rinktis apmokėjimo būdui";
        String notesEn = "The plot meets my requirements and I would like more options for selecting the payment method";
        String notesRu = "Участок мне подходит, и я хотел бы иметь больше возможностей для выбора способа оплаты.";
        // Media nuorodos:
        String video = "https://www.youtube.com/embed/sklypaiExample";
        String tour3d = "https://www.example.com/3d-tour/sklypas";
        // Kaina, telefono numeris, el. paštas ir kontaktavimo nustatymai:
        int price = 35000;
        String phone = "+37060000000";
        String email = "example@example.com";
        boolean dontShowInAds = false;
        boolean dontWantChat = true;
        int accountType = 1;         // Vartotojo tipas: 1 = Privatus asmuo.
        boolean agreeToRules = true; // Vartotojas sutinka su taisyklėmis.

        // RC numeris, į kurį įtrauktos nereikalingos raidės, kurias išfiltruoja sanitarizacijos metodas.
        String unsanitizedRc = "1234-5678-ABC9012";
        String expectedRc = "123456789012";

        // Inicijuojame Plot objektą naudojant visus šiuos duomenis.
        Plot plot = new Plot(
                461, "Vilnius",
                1, "Vilniaus m.",
                2, "Balsiai",
                21862, "A. Jakšto g.",
                5, true,
                unsanitizedRc, true,
                200.0, purposes,
                true, specialFeatures,
                true, false,
                notesLt, notesEn, notesRu,
                video, tour3d,
                price, phone, email,
                dontShowInAds, dontWantChat,
                accountType, agreeToRules
        );

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
        Assert.assertTrue(plot.checkboxSelected);                               // Užtikrina, kad namo numerio rodymo flagas yra teisingas.
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
        Assert.assertEquals(plot.dontShowInAds, dontShowInAds);                   // Tikrina, kad kontaktavimas el. paštu neišjungtas.
        Assert.assertTrue(plot.dontWantChat);                                     // Tikrina, kad chat funkcija išjungta.
        Assert.assertEquals(plot.accountType, accountType);                       // Tikrina vartotojo tipą.
        Assert.assertTrue(plot.agreeToRules);                                     // Tikrina sutikimą su taisyklėmis.
    }

    // Testas statiniam metodui, kuris iš RC numerio pašalina netinkamus simbolius.
    @Test
    public void testRcNumberSanitizationStaticMethod() {
        String unsanitized = "12ab34CD56";  // Įvestas RC numeris su raidėmis ir kitais simboliais.
        String expected = "123456";         // Tikėtina reikšmė tik skaitmenimis.
        Assert.assertEquals(expected, Plot.sanitizeRcNumber(unsanitized));
    }



    // Selenium testas, kuris atidaro aruodas.lt skelbimų puslapį, laukiant kol pasirodys slapukų mygtukas,
    // vėliau laukia pagal label surandamas dropdown laukus (savivaldybė, gyvenvietė, mikrorajonas, gatvė)
    // ir simuliuoja ENTER paspaudimus su 4 sekundžių laukimo intervalais.
    @Test
    public void testAruodasSkelbimas() {

        WebDriver driver = new ChromeDriver(); // Atidarome Chrome naršyklę.
        driver.get("https://www.aruodas.lt/ideti-skelbima/?obj=11&offer_type=1"); // Atidarome aruodas.lt skelbimų puslapį.
        driver.manage().window().maximize(); // Išdidiname naršyklės langą.

        // Sukuriame explicit wait objektą su 10 sekundžių limitu.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Laukiame, kol bus pasiekiamas slapukų priėmimo mygtukas ir jį paspaudžiame.
        wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-reject-all-handler"))).click();
        // Laukiame, kol URL patvirtins, kad esame teisingame puslapyje.
        wait.until(ExpectedConditions.urlContains("aruodas.lt/ideti-skelbima"));

        // Iškviečiame metodą, kuris, remdamasis label tekstais, suranda dropdown elementus
        // (savivaldybė, gyvenvietė, mikrorajonas, gatvė), laukia 4 sekundes tarp pasirinkimų
        // ir simuliuoja ENTER paspaudimą.
        selectLocationFields(driver);

        // Patikriname, ar URL vis dar atitinka lūkesčius.
        Assert.assertTrue(driver.getCurrentUrl().contains("aruodas.lt/ideti-skelbima"),
                "URL turėtų turėti 'aruodas.lt/ideti-skelbima'");

    }

    // Selenium testas, tikrinantis toString() metodą ir ar jis teisingai atvaizduoja svarbias informaciją.
    @Test
    public void testPlotToString() {
        List<String> purposes = Arrays.asList(
                "property", "manufacturingland", "farm", "garden", "forest",
                "factory", "storage", "comm", "recr", "none"
        );
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

        Plot plot = new Plot(
                461, "Vilnius", 1, "Vilniaus m.", 2, "Balsiai",
                21862, "A. Jakšto g.", 5, true,
                "1234-5678-9012", true,
                200.0, purposes,
                true, specialFeatures,
                true, false,
                notesLt, notesEn, notesRu,
                video, tour3d,
                price, phone, email,
                dontShowInAds, dontWantChat,
                accountType, agreeToRules
        );
        String plotString = plot.toString();

        // Patikriname, ar toString() išvestis turi visas svarbias informaciją.
        Assert.assertTrue(plotString.contains("Vilnius"));                   // Tikrina regiono pavadinimą.
        Assert.assertTrue(plotString.contains("Vilniaus m."));                  // Tikrina gyvenvietės pavadinimą.
        Assert.assertTrue(plotString.contains("Balsiai"));                      // Tikrina mikrorajono pavadinimą.
        Assert.assertTrue(plotString.contains("A. Jakšto g."));                 // Tikrina gatvės pavadinimą.
        Assert.assertTrue(plotString.contains("5"));                            // Tikrina namo numerį.
        Assert.assertTrue(plotString.contains("1234-5678-9012") ||
                plotString.contains(Plot.sanitizeRcNumber("1234-5678-9012"))); // Tikrina RC numerį.
        Assert.assertTrue(plotString.contains("200.0"));                        // Tikrina sklypo plotą.
        Assert.assertTrue(plotString.contains("property"));                     // Tikrina bent vieną paskirties reikšmę.
        Assert.assertTrue(plotString.contains("showAttributes=true"));          // Tikrina flagą "Žymėti ypatumus".
        Assert.assertTrue(plotString.contains("interestedChange=true"));        // Tikrina flagą "Domina keitimas".
        Assert.assertTrue(plotString.contains("auction=false"));              // Tikrina aukciono flagą.
        Assert.assertTrue(plotString.contains(notesLt));                        // Tikrina lietuvišką aprašymą.
        Assert.assertTrue(plotString.contains(notesEn));                        // Tikrina anglų aprašymą.
        Assert.assertTrue(plotString.contains(notesRu));                        // Tikrina rusišką aprašymą.
        Assert.assertTrue(plotString.contains(video));                          // Tikrina video nuorodą.
        Assert.assertTrue(plotString.contains(tour3d));                         // Tikrina 3D turo nuorodą.
        Assert.assertTrue(plotString.contains(String.valueOf(price)));            // Tikrina sklypo kainą.
        Assert.assertTrue(plotString.contains(phone));                          // Tikrina telefono numerį.
        Assert.assertTrue(plotString.contains(email));                          // Tikrina el. pašto adresą.
        Assert.assertTrue(plotString.contains("dontShowInAds=false"));           // Tikrina email kontaktavimo flagą.
        Assert.assertTrue(plotString.contains("dontWantChat=true"));             // Tikrina chat flagą.
        Assert.assertTrue(plotString.contains("accountType=" + accountType));      // Tikrina vartotojo tipą.
        Assert.assertTrue(plotString.contains("agreeToRules=true"));             // Tikrina sutikimą su taisyklėmis.
    }
}
