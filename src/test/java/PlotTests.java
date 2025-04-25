
import org.example.models.Plot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;

public class PlotTests {
    public static WebDriver driver;

    @BeforeClass
    public static void setUp() {


        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.aruodas.lt/ideti-skelbima/?obj=11&offer_type=1");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(7));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[8]/div[2]/div/div[1]/div/div[2]/div/button[1]")));
    }

    @Test
    public void testFillPlotForm() {
        Plot plot = new Plot();
        plot.driver = driver;
        plot.fHouseNum = "123";
        plot.rcNumber = "1234-5678-9012";
        plot.paskirtys = new String[]{"Namų valda",""};
        plot.ypatybes= new String[]{"Elektra", "Vanduo","","","","",""};
        plot.notesLt = "Parduodamas sklypas su elektra.";
        plot.video = "https://youtube.com/example";
        plot.tour3d = "https://3dturas.example.com";
        plot.price = "50000";
        plot.phone = "+37012345678";
        plot.email = "test@example.com";

        // Iškviečiame fill() metodą
        plot.fill();

//        Select savivaldybe = new Select(driver.findElement(By.name("region")));
//        Assert.assertEquals(savivaldybe.getFirstSelectedOption().getText(), "Vilniaus m. sav.", "Savivaldybė pasirinkta neteisingai");
//
//        Select gyvenviete = new Select(driver.findElement(By.name("district")));
//        Assert.assertEquals(gyvenviete.getFirstSelectedOption().getText(), "Vilnius", "Gyvenvietė pasirinkta neteisingai");
//
//        Select mikrorajonas = new Select(driver.findElement(By.name("quartal")));
//        Assert.assertEquals(mikrorajonas.getFirstSelectedOption().getText(), "Antakalnis", "Mikrorajonas pasirinktas neteisingai");
//
//        Select gatve = new Select(driver.findElement(By.name("street")));
//        Assert.assertEquals(gatve.getFirstSelectedOption().getText(), "Antakalnio g.", "Gatvė pasirinkta neteisingai");
//
//        String actualHouseNum = driver.findElement(By.name("fHouseNum")).getAttribute("value");
//        Assert.assertEquals(actualHouseNum, "123", "Namo numeris užpildytas neteisingai");
//
//        String actualRcNumber = driver.findElement(By.name("rcNumber")).getAttribute("value");
//        Assert.assertEquals(actualRcNumber, "1234-5678-9012", "RC numeris užpildytas neteisingai");
//
//        String actualArea = driver.findElement(By.name("fAreaOverAll")).getAttribute("value");
//        Assert.assertEquals(actualArea, "100", "Plotas užpildytas neteisingai");
//
//        String actualNotes = driver.findElement(By.name("notes_lt")).getAttribute("value");
//        Assert.assertEquals(actualNotes, "Parduodamas sklypas su elektra.", "Pastabos užpildytos neteisingai");
//
//        String actualVideo = driver.findElement(By.name("video")).getAttribute("value");
//        Assert.assertEquals(actualVideo, "https://youtube.com/example", "Video nuoroda užpildyta neteisingai");
//
//        String actualTour3d = driver.findElement(By.name("tour_3d")).getAttribute("value");
//        Assert.assertEquals(actualTour3d, "https://3dturas.example.com", "3D turo nuoroda užpildyta neteisingai");
//
//        String actualPrice = driver.findElement(By.name("price")).getAttribute("value");
//        Assert.assertEquals(actualPrice, "50000", "Kaina užpildyta neteisingai");
//
//        String actualPhone = driver.findElement(By.name("phone")).getAttribute("value");
//        Assert.assertEquals(actualPhone, "+37012345678", "Telefono numeris užpildytas neteisingai");
//
//        String actualEmail = driver.findElement(By.name("email")).getAttribute("value");
//        Assert.assertEquals(actualEmail, "test@example.com", "El. paštas užpildytas neteisingai");
//
//        boolean isIntendanceChecked = driver.findElement(By.id("cb_FIntendance_property")).isSelected();
//        Assert.assertTrue(isIntendanceChecked, "Checkbox 'cb_FIntendance_property' nepažymėtas");
//
//        for (String ypatybe : plot.ypatybes) {
//            boolean isYpatybeChecked = driver.findElement(By.xpath("//input[@data-title='" + ypatybe + "']")).isSelected();
//            Assert.assertTrue(isYpatybeChecked, "Ypatybė '" + ypatybe + "' nepažymėta");
//        }
//
//        boolean isPrivatePersonChecked = driver.findElement(By.xpath("//input[@data-title='Privatus asmuo']")).isSelected();
//        Assert.assertTrue(isPrivatePersonChecked, "Checkbox 'Privatus asmuo' nepažymėtas");
//
//        boolean isRulesAgreed = driver.findElement(By.name("agree_to_rules")).isSelected();
//        Assert.assertTrue(isRulesAgreed, "Checkbox 'agree_to_rules' nepažymėtas");
    }

    @AfterClass
    public static void tearDown() {

        }
    }
