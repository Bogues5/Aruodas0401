package org.example.tests;

import org.example.models.Plot;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import static org.testng.Assert.*;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class PlotTests {
    public WebDriver driver;
    public WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        // Inicializuojame WebDriver (pvz., ChromeDriver) ir maksimalizuojame langą
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Increased timeout to 20 seconds
        driver.manage().window().maximize();

        // Configure implicit wait for better element finding
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Atidarome skelbimų įkėlimo puslapį
        driver.get("https://www.aruodas.lt/ideti-skelbima/?obj=11&offer_type=1");

        // Priimame slapukų pranešimą, jei toks matomas
        try {
            // Try different selectors for cookie consent button
            try {
                WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("/html/body/div[8]/div[2]/div/div[1]/div/div[2]/div/button[1]")));
                cookieButton.click();
            } catch (Exception e1) {
                try {
                    WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(
                            By.cssSelector(".cookies-buttons-container .button-primary")));
                    cookieButton.click();
                } catch (Exception e2) {
                    WebElement cookieButton = wait.until(ExpectedConditions.elementToBeClickable(
                            By.xpath("//button[contains(text(), 'Sutinku') or contains(text(), 'Accept')]")));
                    cookieButton.click();
                }
            }
        } catch (Exception e) {
            System.out.println("Cookie banner not found or already accepted - continuing with test");
        }

        // Wait for page to fully load
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("objectFormContainer")));
        } catch (Exception e) {
            System.out.println("Form container not found with ID 'objectFormContainer'");
        }
    }

    @Test
    public void testFillRegion() throws InterruptedException {
        Plot plot = new Plot(
                driver,
                461, "Vilnius",               // Regiono duomenys
                1, "Vilniaus m.",             // Gyvenvietės (district) duomenys
                1, "Antakalnis",              // Mikrorajono (quartal) duomenys
                21862, "A. Jakšto g.",        // Gatvės duomenys
                5, true,                      // Namo numeris ir checkbox "Rodyti"
                "1234-5678-9012", true,       // RC numeris ir jo checkbox
                150.5,                        // Plotas
                Arrays.asList("property", "farm"), // Paskirties checkbox'ai
                true, Arrays.asList(1, 2, 3), // Ypatybių rodymas ir specialių ypatybių checkbox'ai
                true, false,                  // "Domina keitimas" ir "Varžytynės/aukcionas"
                "Gražus sklypas", "Nice plot", "Хороший участок", // Aprašymo tekstai LT, EN, RU
                "https://www.youtube.com/embed/test", "https://tour3d.example.com", // Youtube nuoroda ir 3D turas
                100000,                       // Kaina
                "+37060000000", "test@example.com", // Telefono numeris ir el. paštas
                true, true,                   // Išjungti kontaktavimo el. paštu ir pokalbių funkcijas
                1,                            // Vartotojo tipas (1 = Privatus asmuo)
                true,                         // Sutinku su taisyklėmis
                "C:\\Users\\Halo 5\\IdeaProjects\\Aruodas0401\\photos\\p1.jpeg"); // Nuotraukos kelias

        plot.fill();

        // Wait a bit to allow the form to update after filling

            driver.findElement(By.xpath("/html/body/div[1]/div[2]/form/ul/li[3]/span[1]/input[2]"));
            driver.wait(10);

            driver.findElement(By.xpath("/html/body/div[1]/div[2]/form/ul/li[4]/span[1]/input[2]"));
            driver.wait(10);

            driver.findElement(By.xpath("/html/body/div[1]/div[2]/form/ul/li[5]/span[1]/input[2]"));
            driver.wait(10);

            driver.findElement(By.xpath("/html/body/div[1]/div[2]/form/ul/li[6]/span[1]/input[2]"));
            driver.wait(10);



        // Tikriname, ar regionas buvo užpildytas teisingai - try different selectors
        try {
            WebElement regionElement = driver.findElement(By.cssSelector(".dropdown-input-value-title"));
            assertTrue(regionElement.getText().contains("Vilnius"), "Regionas turėtų būti 'Vilnius'");
        } catch (Exception e) {
            // Try alternative selector
            WebElement regionElement = driver.findElement(By.xpath("//div[contains(@class, 'dropdown-input-value')]"));
            assertTrue(regionElement.getText().contains("Vilnius"), "Regionas turėtų būti 'Vilnius'");
        }
    }



}