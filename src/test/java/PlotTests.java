import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.List;

public class PlotTests {
    private WebDriver driver;
    private WebDriverWait wait;
    private Plot plot;

    @BeforeClass
    public void setUp() {
        // Inicializuojame WebDriver (pvz., ChromeDriver) ir maksimalizuojame langą.
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();

        // Atidarome skelbimų įkėlimo puslapį
        driver.get("https://www.aruodas.lt/ideti-skelbima/?obj=11&offer_type=1");

        // Priimame slapukų pranešimą, jei toks matomas (pakeiskite ID, jei reikia)
        try {
            WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("cookieAcceptButton")));
            cookieButton.click();
        } catch (Exception e) {
            // Jei nėra – tęsiame.
        }

        // Inicializuojame Plot modelį su testiniais duomenimis
        plot = new Plot(driver,
                461, "Vilnius",              // Regiono duomenys.
                1, "Vilniaus m.",            // Gyvenvietės (district) duomenys.
                1, "Antakalnis",             // Mikrorajono (quartal) duomenys.
                21862, "A. Jakšto g.",        // Gatvės duomenys.
                5, true,                     // Namo numeris ir checkbox "Rodyti"
                "1234-5678-9012", true,       // RC numeris ir jo checkbox.
                150.5,                     // Plotas (a).
                List.of("property", "farm"), // Paskirties checkbox'ai.
                true, List.of(1, 2, 3),      // Ypatybių rodyti nustatymas ir specialių ypatybių checkbox'ai.
                true, false,               // "Domina keitimas" ir "Varžytynės/aukcionas" checkbox'ai.
                "Gražus butas", "Nice apartment", "Хорошая квартира",  // Aprašymo tekstai LT, EN, RU.
                "https://www.youtube.com/embed/test", "https://tour3d.example.com", // Youtube nuoroda ir 3D turas.
                100000,                    // Sklypo kaina.
                "+37060000000", "test@example.com", // Telefono numeris ir el. paštas.
                true, true,                // Išjungti kontaktavimo el. paštu ir pokalbių funkcijas.
                1,                         // Vartotojo tipas (1 = Privatus asmuo).
                true);                     // Sutinkama su taisyklėmis.
    }

    @Test(priority = 1)
    public void testFillRegion() {
        plot.fillRegion();
        WebElement regionElement = driver.findElement(By.className("dropdown-input-value-title"));
        assert regionElement.getText().contains(plot.regionName);
    }

    @Test(priority = 2)
    public void testFillDistrict() {
        plot.fillDistrict();
        WebElement districtElement = driver.findElement(By.id("districtTitle"));
        // Patikriname, ar įvestas tekstas nėra tuščias – (detalų tikrinimas gali priklausyti nuo implementacijos)
        assert !districtElement.getAttribute("value").isEmpty();
    }

    @Test(priority = 3)
    public void testFillQuartal() {
        plot.fillQuartal();
        WebElement quartalElement = driver.findElement(By.id("quartalTitle"));
        assert !quartalElement.getAttribute("value").isEmpty();
    }

    @Test(priority = 4)
    public void testFillStreet() {
        plot.fillStreet();
        WebElement streetElement = driver.findElement(By.id("streetTitle"));
        assert streetElement.getText().contains(plot.streetName);
    }

    @Test(priority = 5)
    public void testFillHouseNumber() {
        plot.fillHouseNumber();
        WebElement houseNumField = driver.findElement(By.name("FHouseNum"));
        assert houseNumField.getAttribute("value").equals(String.valueOf(plot.houseNumber));
    }

    @Test(priority = 6)
    public void testToggleShowHouseNumberCheckbox() {
        plot.toggleShowHouseNumberCheckbox();
        WebElement checkbox = driver.findElement(By.id("cbshow_house_num"));
        assert checkbox.isSelected();
    }

    @Test(priority = 7)
    public void testFillRCNumber() {
        plot.fillRCNumber();
        WebElement rcField = driver.findElement(By.name("RCNumber"));
        assert rcField.getAttribute("value").equals(plot.rcNumber);
    }

    @Test(priority = 8)
    public void testToggleRCCheckbox() {
        plot.toggleRCCheckbox();
        WebElement checkbox = driver.findElement(By.id("cbshow_rc_number"));
        assert checkbox.isSelected();
    }

    @Test(priority = 9)
    public void testFillArea() {
        plot.fillArea();
        WebElement areaField = driver.findElement(By.name("FAreaOverAll"));
        assert areaField.getAttribute("value").equals(String.valueOf(plot.area));
    }

    @Test(priority = 10)
    public void testSelectPurposes() {
        plot.selectPurposes();
        for (String purpose : plot.purposes) {
            String xpath = String.format("//input[@name='FIntendance[]' and @value='%s']", purpose);
            List<WebElement> checkboxes = driver.findElements(By.xpath(xpath));
            if (!checkboxes.isEmpty()) {
                assert checkboxes.get(0).isSelected();
            }
        }
    }

    @Test(priority = 11)
    public void testExpandAttributesAndSelectSpecialFeatures() {
        plot.expandAttributes();
        // Patikriname, ar rodoma bent viena ypatybė, pvz., turinti tekstą "Elektra"
        WebElement attributesContainer = driver.findElement(By.xpath("//div[contains(@class,'input-style-checkbox') and contains(.,'Elektra')]"));
        assert attributesContainer.isDisplayed();
        plot.selectSpecialFeatures();
        for (Integer feature : plot.specialFeatures) {
            String xpath = String.format("//input[@name='Special[]' and @value='%d']", feature);
            List<WebElement> specialCheckboxes = driver.findElements(By.xpath(xpath));
            if (!specialCheckboxes.isEmpty()) {
                assert specialCheckboxes.get(0).isSelected();
            }
        }
    }

    @Test(priority = 12)
    public void testToggleInterestedChange() {
        plot.toggleInterestedChange();
        WebElement checkbox = driver.findElement(By.id("cbInterestedChange"));
        assert checkbox.isSelected();
    }

    @Test(priority = 13)
    public void testToggleAuction() {
        plot.toggleAuction();
        WebElement checkbox = driver.findElement(By.id("cbAuction"));
        assert checkbox.isSelected();
    }

    @Test(priority = 14)
    public void testFillDescription() {
        plot.fillDescription();
        WebElement notesLtField = driver.findElement(By.name("notes_lt"));
        WebElement notesEnField = driver.findElement(By.name("notes_en"));
        WebElement notesRuField = driver.findElement(By.name("notes_ru"));
        assert notesLtField.getAttribute("value").equals(plot.notesLt);
        assert notesEnField.getAttribute("value").equals(plot.notesEn);
        assert notesRuField.getAttribute("value").equals(plot.notesRu);
    }

    @Test(priority = 15)
    public void testFillVideoAndTour3D() {
        plot.fillVideoAndTour3D();
        WebElement videoField = driver.findElement(By.name("Video"));
        WebElement tour3dField = driver.findElement(By.name("tour_3d"));
        assert videoField.getAttribute("value").equals(plot.video);
        assert tour3dField.getAttribute("value").equals(plot.tour3d);
    }

    @Test(priority = 16)
    public void testFillPrice() {
        plot.fillPrice();
        WebElement priceField = driver.findElement(By.id("priceField"));
        assert priceField.getAttribute("value").equals(String.valueOf(plot.price));
    }

    @Test(priority = 17)
    public void testFillPhone() {
        plot.fillPhone();
        WebElement phoneField = driver.findElement(By.name("phone"));
        assert phoneField.getAttribute("value").equals(plot.phone);
    }

    @Test(priority = 18)
    public void testFillEmail() {
        plot.fillEmail();
        WebElement emailField = driver.findElement(By.name("email"));
        assert emailField.getAttribute("value").equals(plot.email);
        {
        }

        @Test(priority = 19)
        public void testToggleDontShowInAds () {
            plot.toggleDontShowInAds();
            WebElement checkbox = driver.findElement(By.id("cbdont_show_in_ads"));
            assert checkbox.isSelected();
        }

        @Test(priority = 20)
        public void testToggleDontWantChat () {
            plot.toggleDontWantChat();
            WebElement checkbox = driver.findElement(By.id("cbdont_want_chat"));
            assert checkbox.isSelected();
        }

        @Test(priority = 21)
        public void testSelectAccountType () {
            plot.selectAccountType();
            // Patikriname, ar paslėptas input su name "account_type" atitinka
            WebElement hiddenInput = driver.findElement(By.name("account_type"));
            assert hiddenInput.getAttribute("value").equals(String.valueOf(plot.accountType));
        }

        @Test(priority = 22)
        public void testToggleAgreeToRules () {
            plot.toggleAgreeToRules();
            WebElement checkbox = driver.findElement(By.id("cbagree_to_rules"));
            assert checkbox.isSelected();
        }

        @Test(priority = 23)
        public void testUploadPhoto () {
            // Nuotraukos įkėlimas – nurodykite tikrą failo kelią savo sistemoje.
            String filePath = "C:\\path\\to\\your\\testimage.jpg";
            plot.uploadPhoto(filePath);
            WebElement uploadedThumbnail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("uploaded-photo-thumbnail")));
            assert uploadedThumbnail.isDisplayed();
        }

        @Test(priority = 24)
        public void testSubmitListing () {
            plot.submitListing();
            // Po skelbimo pateikimo galima patikrinti ar rodoma patvirtinimo žinutė (locator pritaikykite pagal savo puslapį)
            WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmationMessage")));
            assert confirmationMessage.isDisplayed();
        }

        @AfterClass
        public void tearDown () {
            driver.quit();
        }
    }
}
