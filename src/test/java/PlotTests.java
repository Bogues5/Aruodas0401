// C:\Users\Halo 5\.cache\selenium\chromedriver\win64\135.0.7049.114
//chrome driver kelias pasirašiau sau
package org.example.tests;
import org.example.Plot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

public class PlotTests {
    private WebDriver driver;

    @BeforeClass
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.findElement(By.xpath("//*[@id=\"onetrust-accept-btn-handler\"]")).click();
        driver.get("https://www.aruodas.lt/ideti-skelbima/?obj=11&offer_type=1");






    }



    @Test
    public void testFillFormMethod() {
        Plot plot = new Plot(driver);
        plot.region = "Kaunas";
        plot.district = "Centras";
        plot.quartal = "Senamiestis";
        plot.street = "Laisvės al.";
        plot.objNo = "10";
        plot.rcNo = "67890";
        plot.plotSize = "200";
        plot.auction = true;
        plot.price = "75000";
        plot.fill();
        Assert.assertEquals(driver.findElement(By.name("price")).getAttribute("value"), "75000");
        Assert.assertTrue(driver.findElement(By.name("auction")).isSelected());
        Assert.assertEquals(driver.findElement(By.name("rcNo")).getAttribute("value"), "67890");
    }

    @AfterClass
    public void tearDown() {
   //     if (driver != null) driver.quit();
    }
}
