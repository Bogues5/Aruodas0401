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
    public WebDriver driver;
    public WebDriverWait wait;
    driver.Plot

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
        } catch(Exception e) {
            // Jei mygtuko nėra – tęsiame
        }

        // Inicializuojame Plot modelį su testiniais duomenimis
        plot = new Plot(driver);
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
    }

    @Test(priority = 1)
    public void testFillRegion() {
        plot.fillRegion();
        WebElement regionElement = driver.findElement(By.className("dropdown-input-value-title"));

    }

    @Test(priority = 2)
    public void testFillDistrict() {
        plot.fillDistrict();
        WebElement districtElement = driver.findElement(By.id("districtTitle"));

    }

    @Test(priority = 3)
    public void testFillQuartal() {
        plot.fillQuartal();
        WebElement quartalElement = driver.findElement(By.id("quartalTitle"));

    }

    @Test(priority = 4)
    public void testFillStreet() {
        plot.fillStreet();
        WebElement streetElement = driver.findElement(By.id("streetTitle"));

    }

    @Test(priority = 5)
    public void testFillHouseNumber() {
        plot.fillHouseNumber();
        WebElement houseNumField = driver.findElement(By.name("FHouseNum"));

    }

    @Test(priority = 6)
    public void testToggleShowHouseNumberCheckbox() {
        plot.toggleShowHouseNumberCheckbox();
        WebElement checkbox = driver.findElement(By.id("cbshow_house_num"));

    }

    @Test(priority = 7)
    public void testFillRCNumber() {
        plot.fillRCNumber();
        WebElement rcField = driver.findElement(By.name("RCNumber"));

    }

    @Test(priority = 8)
    public void testToggleRCCheckbox() {
        plot.toggleRCCheckbox();
        WebElement checkbox = driver.findElement(By.id("cbshow_rc_number"));

    }

    @Test(priority = 9)
    public void testFillArea() {
        plot.fillArea();
        WebElement areaField = driver.findElement(By.name("FAreaOverAll"));

    }

    @Test(priority = 10)
    public void testSelectPurposes() {
        plot.selectPurposes();
        for (String purpose : plot.purposes) {
            String xpath = String.format("//input[@name='FIntendance[]' and @value='%s']", purpose);
            List<WebElement> checkboxes = driver.findElements(By.xpath(xpath));
            if (!checkboxes.isEmpty()) {

            }
        }
    }

    @Test(priority = 11)
    public void testExpandAttributesAndSelectSpecialFeatures() {
        plot.expandAttributes();
        WebElement attributesContainer = driver.findElement(By.xpath("//div[contains(@class,'input-style-checkbox') and contains(.,'Elektra')]"));

        plot.selectSpecialFeatures();
        for (Integer feature : plot.specialFeatures) {
            String xpath = String.format("//input[@name='Special[]' and @value='%d']", feature);
            List<WebElement> specialCheckboxes = driver.findElements(By.xpath(xpath));
            if (!specialCheckboxes.isEmpty()){

            }
        }
    }

    @Test(priority = 12)
    public void testToggleInterestedChange() {
        plot.toggleInterestedChange();
        WebElement checkbox = driver.findElement(By.id("cbInterestedChange"));

    }

    @Test(priority = 13)
    public void testToggleAuction() {
        plot.toggleAuction();
        WebElement checkbox = driver.findElement(By.id("cbAuction"));

    }

    @Test(priority = 14)
    public void testFillDescription() {
        plot.fillDescription();
        WebElement notesLtField = driver.findElement(By.name("notes_lt"));
        WebElement notesEnField = driver.findElement(By.name("notes_en"));
        WebElement notesRuField = driver.findElement(By.name("notes_ru"));

    }

    @Test(priority = 15)
    public void testFillVideoAndTour3D() {
        plot.fillVideoAndTour3D();
        WebElement videoField = driver.findElement(By.name("Video"));
        WebElement tour3dField = driver.findElement(By.name("tour_3d"));

    }

    @Test(priority = 16)
    public void testFillPrice() {
        plot.fillPrice();
        WebElement priceField = driver.findElement(By.id("priceField"));

    }

    @Test(priority = 17)
    public void testFillPhone() {
        plot.fillPhone();
        WebElement phoneField = driver.findElement(By.name("phone"));

    }

    @Test(priority = 18)
    public void testFillEmail() {
        plot.fillEmail();
        WebElement emailField = driver.findElement(By.name("email"));

    }

    @Test(priority = 19)
    public void testToggleDontShowInAds() {
        plot.toggleDontShowInAds();
        WebElement checkbox = driver.findElement(By.id("cbdont_show_in_ads"));

    }

    @Test(priority = 20)
    public void testToggleDontWantChat() {
        plot.toggleDontWantChat();
        WebElement checkbox = driver.findElement(By.id("cbdont_want_chat"));

    }

    @Test(priority = 21)
    public void testSelectAccountType() {
        plot.selectAccountType();
        WebElement hiddenInput = driver.findElement(By.name("account_type"));

    }

    @Test(priority = 22)
    public void testToggleAgreeToRules() {
        plot.toggleAgreeToRules();
        WebElement checkbox = driver.findElement(By.id("cbagree_to_rules"));

    }

    @Test(priority = 23)
    public void testUploadPhoto() {
        String filePath = "C:\\Users\\Halo 5\\IdeaProjects\\Aruodas0401\\photos\\p1.jpeg";
        plot.uploadPhoto(filePath);
        WebElement uploadedThumbnail = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("uploaded-photo-thumbnail")));
        assert uploadedThumbnail.isDisplayed() : "Uploaded photo thumbnail is not displayed";
    }

    @Test(priority = 24)
    public void testSubmitListing() {
        plot.submitListing();
        WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmationMessage")));

    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}
