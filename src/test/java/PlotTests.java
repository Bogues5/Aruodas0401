import org.example.models.Plot;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import static org.testng.Assert.*;
import java.time.Duration;
import java.util.List;


    public class PlotTests {
        public WebDriver driver;
        public WebDriverWait wait;

        @BeforeClass
        public void setUp() {
            // Inicializuojame WebDriver (pvz., ChromeDriver) ir maksimalizuojame langą
            driver = new ChromeDriver();
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            driver.manage().window().maximize();

            // Atidarome skelbimų įkėlimo puslapį
            driver.get("https://www.aruodas.lt/ideti-skelbima/?obj=11&offer_type=1");

            // Priimame slapukų pranešimą, jei toks matomas
            try {
                WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("cookieAcceptButton")));
                cookieButton.click();
            } catch (Exception e) {
                // Jei mygtuko nėra – tęsiame
            }

            // Inicializuojame Plot modelį su testiniais duomenimis

        }

        @Test
        public void testFillRegion() {
            Plot plot = new Plot(
                    driver,
                    461, "Vilnius",               // Regiono duomenys
                    1, "Vilniaus m.",             // Gyvenvietės (district) duomenys
                    1, "Antakalnis",              // Mikrorajono (quartal) duomenys
                    21862, "A. Jakšto g.",         // Gatvės duomenys
                    5, true,                     // Namo numeris ir checkbox "Rodyti"
                    "1234-5678-9012", true,        // RC numeris ir jo checkbox
                    150.5,                      // Plotas
                    List.of("property", "farm"),   // Paskirties checkbox'ai
                    true, List.of(1, 2, 3),          // Ypatybių rodymas ir specialių ypatybių checkbox'ai
                    true, false,                // "Domina keitimas" ir "Varžytynės/aukcionas"
                    "Gražus butas", "Nice apartment", "Хорошая квартира",   // Aprašymo tekstai LT, EN, RU
                    "https://www.youtube.com/embed/test", "https://tour3d.example.com",  // Youtube nuoroda ir 3D turas
                    100000,                     // Kaina
                    "+37060000000", "test@example.com",  // Telefono numeris ir el. paštas
                    true, true,                // Išjungti kontaktavimo el. paštu ir pokalbių funkcijas
                    1,                         // Vartotojo tipas (1 = Privatus asmuo)
                    true);                     // Sutinkama su taisyklėmis
            plot.fill();
            WebElement regionElement = driver.findElement(By.className("dropdown-input-value-title"));


            // Validuojame telefono numerio lauką
            WebElement phoneInput = driver.findElement(By.id("phone"));
            String phoneNumber = phoneInput.getAttribute("value");

            try {
                PhoneValidator validator = new PhoneValidator();
                assertTrue(validator.validatePhoneNumber(phoneNumber), "Telefono numeris yra teisingas");
            } catch (IllegalArgumentException e) {
                fail("Telefono numeris neteisingas: " + e.getMessage());
            }
        }

        @Test
        public void testValidLithuanianPhoneNumber() {
            PhoneValidator validator = new PhoneValidator();
            assertTrue(validator.validatePhoneNumber("+37061234567"));
        }

        @Test
        public void testValidForeignPhoneNumber() {
            PhoneValidator validator = new PhoneValidator();
            assertTrue(validator.validatePhoneNumber("+16505551234"));
        }

        @Test(expectedExceptions = IllegalArgumentException.class,
                expectedExceptionsMessageRegExp = "Telefono numeris turi prasidėti šalies kodu \\(pvz\\., \\+370\\)")
        public void testPhoneStartingWithLetters() {
            PhoneValidator validator = new PhoneValidator();
            validator.validatePhoneNumber("abc61234567");
        }

        @Test(expectedExceptions = IllegalArgumentException.class,
                expectedExceptionsMessageRegExp = "Telefono numerio formatas neteisingas. Naudokite tik skaičius po šalies kodo")
        public void testPhoneWithSpecialCharacters() {
            PhoneValidator validator = new PhoneValidator();
            validator.validatePhoneNumber("+370-612-34567");
        }

        @Test(expectedExceptions = IllegalArgumentException.class,
                expectedExceptionsMessageRegExp = "Telefono numeris turi prasidėti šalies kodu \\(pvz\\., \\+370\\)")
        public void testPhoneWithoutCountryCode() {
            PhoneValidator validator = new PhoneValidator();
            validator.validatePhoneNumber("861234567");
        }

        @Test(expectedExceptions = IllegalArgumentException.class,
                expectedExceptionsMessageRegExp = "Telefono numerio formatas neteisingas. Naudokite tik skaičius po šalies kodo")
        public void testPhoneWithLettersAfterCountryCode() {
            PhoneValidator validator = new PhoneValidator();
            validator.validatePhoneNumber("+370abcdef");
        }
    }

    class PhoneValidator {
        public boolean validatePhoneNumber(String phoneNumber) {
            // Tikriname, ar numeris prasideda šalies kodu (+XXX)
            if (!phoneNumber.matches("^\\+\\d{1,4}.*")) {
                throw new IllegalArgumentException("Telefono numeris turi prasidėti šalies kodu (pvz., +370)");
            }

            // Tikriname, ar visas numeris yra teisingas
            if (!phoneNumber.matches("^\\+\\d{1,4}\\d{6,12}$")) {
                throw new IllegalArgumentException("Telefono numerio formatas neteisingas. Naudokite tik skaičius po šalies kodo");
            }

            return true;
        }
    }



        // Čia galima pridėti assert veiksmą patikrinti ar elementas teisingai užpildytas







//    @Test(priority = 2)
//    public void testFillDistrict() {
//        plot.fillDistrict();
//        WebElement districtElement = driver.findElement(By.id("districtTitle"));
//        // Čia galima pridėti assert veiksmą patikrinti ar elementas teisingai užpildytas
//    }
//
//    @Test(priority = 3)
//    public void testFillQuartal() {
//        plot.fillQuartal();
//        WebElement quartalElement = driver.findElement(By.id("quartalTitle"));
//        // Čia galima pridėti assert veiksmą patikrinti ar elementas teisingai užpildytas
//    }
//
//    @Test(priority = 4)
//    public void testFillStreet() {
//        plot.fillStreet();
//        WebElement streetElement = driver.findElement(By.id("streetTitle"));
//        // Čia galima pridėti assert veiksmą patikrinti ar elementas teisingai užpildytas
//    }
//
//    @Test(priority = 5)
//    public void testFillHouseNumber() {
//        plot.fillHouseNumber();
//        WebElement houseNumField = driver.findElement(By.name("FHouseNum"));
//        // Čia galima pridėti assert veiksmą patikrinti ar elementas teisingai užpildytas
//    }
//
//    @Test(priority = 6)
//    public void testToggleShowHouseNumberCheckbox() {
//        plot.toggleShowHouseNumberCheckbox();
//        WebElement checkbox = driver.findElement(By.id("cbshow_house_num"));
//        // Čia galima pridėti assert veiksmą patikrinti ar elementas teisingai užpildytas
//    }
//
//    @Test(priority = 7)
//    public void testFillRCNumber() {
//        plot.fillRCNumber();
//        WebElement rcField = driver.findElement(By.name("RCNumber"));
//        // Čia galima pridėti assert veiksmą patikrinti ar elementas teisingai užpildytas
//    }
//
//    @Test(priority = 8)
//    public void testToggleRCCheckbox() {
//        plot.toggleRCCheckbox();
//        WebElement checkbox = driver.findElement(By.id("cbshow_rc_number"));
//        // Čia galima pridėti assert veiksmą patikrinti ar elementas teisingai užpildytas
//    }
//
//    @Test(priority = 9)
//    public void testFillArea() {
//        plot.fillArea();
//        WebElement areaField = driver.findElement(By.name("FAreaOverAll"));
//        // Čia galima pridėti assert veiksmą patikrinti ar elementas teisingai užpildytas
//    }
//
//    @Test(priority = 10)
//    public void testSelectPurposes() {
//        plot.selectPurposes();
//        for (String purpose : plot.purposes) {
//            String xpath = String.format("//input[@name='FIntendance[]' and @value='%s']", purpose);
//            List<WebElement> checkboxes = driver.findElements(By.xpath(xpath));
//            if (!checkboxes.isEmpty()) {
//                // Čia galima pridėti assert patikrinti ar elementas pažymėtas
//            }
//        }
//    }
//
//    @Test(priority = 11)
//    public void testExpandAttributesAndSelectSpecialFeatures() {
//        plot.expandAttributes();
//        WebElement attributesContainer = driver.findElement(By.xpath("//div[contains(@class,'input-style-checkbox') and contains(.,'Elektra')]"));
//        // Čia galima pridėti assert patikrinti ar atributų skyrius matomas
//
//        plot.selectSpecialFeatures();
//        for (Integer feature : plot.specialFeatures) {
//            String xpath = String.format("//input[@name='Special[]' and @value='%d']", feature);
//            List<WebElement> specialCheckboxes = driver.findElements(By.xpath(xpath));
//            if (!specialCheckboxes.isEmpty()){
//                // Čia galima pridėti assert patikrinti ar elementas pažymėtas
//            }
//        }
//    }
//
//    @Test(priority = 12)
//    public void testToggleInterestedChange() {
//        plot.toggleInterestedChange();
//        WebElement checkbox = driver.findElement(By.id("cbInterestedChange"));
//        // Čia galima pridėti assert patikrinti ar elementas pažymėtas
//    }
//
//    @Test(priority = 13)
//    public void testToggleAuction() {
//        plot.toggleAuction();
//        WebElement checkbox = driver.findElement(By.id("cbAuction"));
//        // Čia galima pridėti assert patikrinti ar elementas pažymėtas
//    }
//
//    @Test(priority = 14)
//    public void testFillDescription() {
//        plot.fillDescription();
//        WebElement notesLtField = driver.findElement(By.name("notes_lt"));
//        WebElement notesEnField = driver.findElement(By.name("notes_en"));
//        WebElement notesRuField = driver.findElement(By.name("notes_ru"));
//        // Čia galima pridėti assert patikrinti ar laukai teisingai užpildyti
//    }
//
//    @Test(priority = 15)
//    public void testFillVideoAndTour3D() {
//        plot.fillVideoAndTour3D();
//        WebElement videoField = driver.findElement(By.name("Video"));
//        WebElement tour3dField = driver.findElement(By.name("tour_3d"));
//        // Čia galima pridėti assert patikrinti ar laukai teisingai užpildyti
//    }
//
//    @Test(priority = 16)
//    public void testFillPrice() {
//        plot.fillPrice();
//        WebElement priceField = driver.findElement(By.id("priceField"));
//        // Čia galima pridėti assert patikrinti ar laukas teisingai užpildytas
//    }
//
//    @Test(priority = 17)
//    public void testFillPhone() {
//        plot.fillPhone();
//        WebElement phoneField = driver.findElement(By.name("phone"));
//        // Čia galima pridėti assert patikrinti ar laukas teisingai užpildytas
//    }
//
//    @Test(priority = 18)
//    public void testFillEmail() {
//        plot.fillEmail();
//        WebElement emailField = driver.findElement(By.name("email"));
//        // Čia galima pridėti assert patikrinti ar laukas teisingai užpildytas
//    }
//
//    @Test(priority = 19)
//    public void testToggleDontShowInAds() {
//        plot.toggleDontShowInAds();
//        WebElement checkbox = driver.findElement(By.id("cbdont_show_in_ads"));
//        // Čia galima pridėti assert patikrinti ar elementas pažymėtas
//    }
//
//    @Test(priority = 20)
//    public void testToggleDontWantChat() {
//        plot.toggleDontWantChat();
//        WebElement checkbox = driver.findElement(By.id("cbdont_want_chat"));
//        // Čia galima pridėti assert patikrinti ar elementas pažymėtas
//    }
//
//    @Test(priority = 21)
//    public void testSelectAccountType() {
//        plot.selectAccountType();
//        WebElement hiddenInput = driver.findElement(By.name("account_type"));
//        // Čia galima pridėti assert patikrinti ar teisingas tipas pasirinktas
//    }
//
//    @Test(priority = 22)
//    public void testToggleAgreeToRules() {
//        plot.toggleAgreeToRules();
//        WebElement checkbox = driver.findElement(By.id("cbagree_to_rules"));
//        // Čia galima pridėti assert patikrinti ar elementas pažymėtas
//    }
//
//    @Test(priority = 23)
//    public void testUploadPhoto() {
//        String filePath = "C:\\Users\\Halo 5\\IdeaProjects\\Aruodas0401\\photos\\p1.jpeg";
//        plot.uploadPhoto(filePath);
//        WebElement uploadedThumbnail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("uploaded-photo-thumbnail")));
//        assert uploadedThumbnail.isDisplayed() : "Uploaded photo thumbnail is not displayed";
//    }
//
//    @Test(priority = 24)
//    public void testSubmitListing() {
//        plot.submitListing();
//        WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmationMessage")));
//        // Čia galima pridėti assert patikrinti ar patvirtinimo žinutė matoma
//    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}

    public void main() {
    }