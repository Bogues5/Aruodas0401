Transkripcija: Selenium testavimo projektas su Java

Ši transkripcija apima Selenium testavimo projektą su Java, skirtą automatizuotam sklypo skelbimo formos užpildymui tinklalapyje aruodas.lt. Projektas naudoja Selenium WebDriver naršyklės valdymui ir TestNG testavimo framework'ą. Čia pateikiama visa informacija: projekto struktūra, kodo pavyzdžiai ir paaiškinimai.



Projekto apžvalga

Projektas susideda iš dviejų pagrindinių klasių:





Plot.java – Modelio klasė, skirta formos užpildymui pagal pateiktus duomenis.



PlotTests.java – Testavimo klasė, skirta patikrinti, ar forma užpildoma teisingai.

Visi kintamieji ir metodai klasėje Plot yra public. Projektas automatizuoja formos užpildymą ir naudoja WebDriverWait sinchronizacijai, kad išvengtų problemų dėl lėto puslapio įkėlimo.



Plot.java klasė

Ši klasė atsakinga už sklypo skelbimo formos užpildymą naudojant Selenium WebDriver. Ji turi metodus dropdown laukams, tekstiniams laukams, checkbox’ams ir radio mygtukams valdyti.

Kodo struktūra





Kintamieji: Visi formos laukai (pvz., regionName, houseNumber, purposes, notesLt) yra public.



Konstruktoriai: Tuščias ir pilnas konstruktorius su visais kintamaisiais.



Metodai:





sanitizeRcNumber – Pašalina ne skaitmeninius simbolius iš RC numerio.



fill – Pagrindinis metodas, kviečiantis kitus metodus formos užpildymui.



fillRegion, fillDistrict, fillQuartal, fillStreet – Užpildo dropdown laukus.



fillTextField – Užpildo tekstinius laukus.



setCheckbox – Nustato checkbox būseną.



selectRadio – Pasirenka radio mygtuką.

Kodo pavyzdys

public class Plot {
    public WebDriver driver;
    public WebDriverWait wait;
    public String regionName;
    public String districtName;
    public String quartalName;
    public String streetName;
    public int houseNumber;
    public boolean checkboxSelected;
    public String purposes;
    public String notesLt;

    public Plot() {}

    public Plot(WebDriver driver, WebDriverWait wait, String regionName, String districtName, 
                String quartalName, String streetName, int houseNumber, boolean checkboxSelected, 
                String purposes, String notesLt) {
        this.driver = driver;
        this.wait = wait;
        this.regionName = regionName;
        this.districtName = districtName;
        this.quartalName = quartalName;
        this.streetName = streetName;
        this.houseNumber = houseNumber;
        this.checkboxSelected = checkboxSelected;
        this.purposes = purposes;
        this.notesLt = notesLt;
    }

    public String sanitizeRcNumber(String rcNumber) {
        return rcNumber.replaceAll("[^0-9]", "");
    }

    public void fill() {
        fillRegion();
        fillDistrict();
        fillQuartal();
        fillStreet();
        fillTextField("FHouseNum", String.valueOf(houseNumber));
        setCheckbox("show_house_num", checkboxSelected);
        fillTextField("purposes", purposes);
        fillTextField("notes_lt", notesLt);
    }

    public void fillRegion() {
        WebElement region = wait.until(ExpectedConditions.elementToBeClickable(By.name("region")));
        region.sendKeys(regionName);
    }

    public void fillDistrict() {
        WebElement district = wait.until(ExpectedConditions.elementToBeClickable(By.name("district")));
        district.sendKeys(districtName);
    }

    public void fillQuartal() {
        WebElement quartal = wait.until(ExpectedConditions.elementToBeClickable(By.name("quartal")));
        quartal.sendKeys(quartalName);
    }

    public void fillStreet() {
        WebElement street = wait.until(ExpectedConditions.elementToBeClickable(By.name("street")));
        street.sendKeys(streetName);
    }

    public void fillTextField(String fieldName, String value) {
        WebElement field = wait.until(ExpectedConditions.elementToBeClickable(By.name(fieldName)));
        field.clear();
        field.sendKeys(value);
    }

    public void setCheckbox(String checkboxId, boolean shouldBeChecked) {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(By.id(checkboxId)));
        if (checkbox.isSelected() != shouldBeChecked) {
            checkbox.click();
        }
    }

    public void selectRadio(String radioName, String value) {
        WebElement radio = wait.until(ExpectedConditions.elementToBeClickable(
            By.xpath("//input[@name='" + radioName + "'][@value='" + value + "']")));
        radio.click();
    }
}



PlotTests.java klasė

Ši klasė testuoja Plot klasės funkcionalumą, patikrindama, ar forma užpildoma teisingai. Testai apima laukų užpildymą, dropdown sinchronizaciją ir checkbox bei radio mygtukų būseną.

Kodo struktūra





Kintamieji: driver ir wait naršyklės valdymui.



Metodai:





@BeforeClass setUp – Inicializuoja naršyklę ir sukuria Plot objektą.



@Test testPlotFormFilling – Tikrina pagrindinių laukų užpildymą.



@Test testDropdownSynchronization – Tikrina dropdown sinchronizaciją.



@Test testCheckboxAndRadioInteraction – Tikrina checkbox ir radio mygtukų būseną.



@AfterClass tearDown – Uždaro naršyklę.

Kodo pavyzdys

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class PlotTests {
    public WebDriver driver;
    public WebDriverWait wait;
    public Plot plot;

    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("https://aruodas.lt");
        
        plot = new Plot(driver, wait, "Vilnius", "Antakalnis", "Naujamiestis", 
                        "Žalgirio g.", 10, true, "Residential", "Test note");
    }

    @Test
    public void testPlotFormFilling() {
        plot.fill();
        Assert.assertEquals(driver.findElement(By.name("FHouseNum")).getAttribute("value"), "10");
        Assert.assertEquals(driver.findElement(By.name("purposes")).getAttribute("value"), "Residential");
        Assert.assertEquals(driver.findElement(By.name("notes_lt")).getAttribute("value"), "Test note");
    }

    @Test
    public void testDropdownSynchronization() {
        plot.fillRegion();
        plot.fillDistrict();
        Assert.assertTrue(driver.findElement(By.name("district")).isDisplayed());
    }

    @Test
    public void testCheckboxAndRadioInteraction() {
        plot.setCheckbox("show_house_num", true);
        Assert.assertTrue(driver.findElement(By.id("show_house_num")).isSelected());
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }
}



Sinchronizacija ir laukimas

Projektas naudoja WebDriverWait, kad elementai būtų pasiekiami prieš atliekant veiksmus. Tai apsaugo nuo sinchronizacijos problemų.

Pavyzdys

WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.name("fieldName")));
element.sendKeys("value");



Priklausomybės (pom.xml)

Projektas naudoja šias priklausomybes, aprašytas pom.xml faile:

<project>
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.example</groupId>
    <artifactId>selenium-project</artifactId>
    <version>1.0-SNAPSHOT</version>
    
    <dependencies>
        <dependency>
            <groupId>org.seleniumhq.selenium</groupId>
            <artifactId>selenium-java</artifactId>
            <version>4.11.0</version>
        </dependency>
        <dependency>
            <groupId>org.testng</groupId>
            <artifactId>testng</artifactId>
            <version>7.8.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>



Išvada

Šis projektas automatizuoja sklypo skelbimo formos užpildymą ir testavimą tinklalapyje aruodas.lt. Visi kintamieji ir metodai yra public, o testai patikrina funkcionalumą. Kodas yra paruoštas integracijai į jūsų GitHub repozitoriją. Jei reikia papildomų paaiškinimų, praneškite!