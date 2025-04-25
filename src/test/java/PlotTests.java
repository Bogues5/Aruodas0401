package org.example.tests;

import org.example.models.Plot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.time.Duration;

@Test
public class PlotTests {
    public WebDriver driver;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.aruodas.lt/ideti-skelbima/?obj=11&offer_type=1");

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement acceptBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("onetrust-accept-btn-handler")));
            acceptBtn.click();
        } catch (Exception e) {
            System.out.println("Sutikimo mygtukas nerastas arba jau paspaustas.");
        }
    }

    public void testFillPlotForm() {
        Plot plot = new Plot(driver);

        plot.region = "Vilnius";
        plot.district = "Antakalnis";
        plot.quartal = "Naujamiestis";
        plot.street = "Gedimino pr.";
        plot.fHouseNum = "1";
        plot.rcNumber = "12345";
        plot.fAreaOverAll = "100";
        plot.paskirtys = new String[]{"Gyvenamoji"};
        plot.ypatybes = new String[]{"Elektra", "Vanduo"};
        plot.interestedChange = "Taip";
        plot.auction = true;
        plot.notesLt = "Parduodamas sklypas su elektra.";
        plot.video = "https://youtube.com/example";
        plot.tour3d = "https://3dturas.example.com";
        plot.price = "50000";
        plot.phone = "+37012345678";
        plot.email = "test@example.com";
        plot.agreeToRules = "Taip";
        plot.submitFormButton = "Pateikti";

        plot.fill();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement priceField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("price")));
        Assert.assertEquals(priceField.getAttribute("value"), "50000", "Kaina neteisingai įvesta");

        WebElement auctionCheckbox = driver.findElement(By.name("auction"));
        Assert.assertTrue(auctionCheckbox.isSelected(), "Aukciono checkbox'as turėtų būti pažymėtas");

        WebElement rcNumberField = driver.findElement(By.name("rcNumber"));
        Assert.assertEquals(rcNumberField.getAttribute("value"), "12345", "RC numeris neteisingai įvestas");

        WebElement regionField = driver.findElement(By.className("dropdown-input-value-title"));
        Assert.assertTrue(regionField.getText().contains("Vilnius"), "Regionas neteisingai pasirinktas");
    }
}