import org.example.models.Plot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
        WebElement acceptBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));

        acceptBtn.click();
    }

    @Test
    public void testFillPlotForm() {
        Plot plot = new Plot();
        plot.driver = PlotTests.driver;
        plot.fHouseNum = "123";
        plot.rcNumber = "1234-5678-9012";
        plot.paskirtys = new String[]{"Namų valda","Sklypas soduose","Sandėliavimo","Kita","Daugiabučių statyba","Miškų ūkio","Komercinė","Žemės ūkio","Pramonės","Rekreacinė"};
        plot.ypatybes= new String[]{"Elektra", "Vanduo","Kraštinis sklypas","Geodeziniai matavimai","Dujos","Greta miško","Su pakrante","Vanduo","Be pastatų","Asfaltuotas privažiavimas"};
        plot.interestedChange = "interestedChange";
        plot.auction = false;
        plot.notesLt = "Parduodamas sklypas su elektra.";
        plot.video = "https://youtube.com/example";
        plot.tour3d = "https://3dturas.example.com";
        plot.price = "50000";
        plot.phone = "+37012345678";
        plot.email = "test@example.com";

        // Iškviečiame fill() metodą
        plot.fill();












   //    Select savivaldybe = new Select(driver.findElement(By.name("region")));
  //     Assert.assertEquals(savivaldybe.getFirstSelectedOption().getText(), "Vilniaus m. sav.", "Savivaldybė pasirinkta neteisingai");


    }

    @AfterClass
    public static void tearDown() {

        }
    }
