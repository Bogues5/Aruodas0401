package org.example.models;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class Plot {
    public WebDriver driver;
    public String region;
    public String district;
    public String quartal;
    public String street;
    public String fHouseNum;
    public String show_house_num;
    public String rcNumber;
    public String showRcNumber;
    public String fAreaOverAll;
    public String[] paskirtys;
    public String[] ypatybes;
    public String interestedChange;
    public String notesLt;
    public String uploadPhoto;
    public String video;
    public String tour3d;
    public String price;
    public String phone;
    public String email;
    public String cbDontShowInAds;
    public String dontWantChat;
    public String[] datatitle;
    public String agreeToRules;
    public String submitFormButton;

    public Plot() {
    }

    public void fill() {
        Select savivaldybe = new Select(driver.findElement(By.name("region")));
        savivaldybe.selectByIndex(1);

        Select gyvenviete = new Select(driver.findElement(By.name("district")));
        gyvenviete.selectByIndex(1);

        Select mikrorajonas = new Select(driver.findElement(By.name("quartal")));
        mikrorajonas.selectByIndex(1);

        Select gatve = new Select(driver.findElement(By.name("street")));
        gatve.selectByIndex(1);

        driver.findElement(By.name("fHouseNum")).sendKeys(this.fHouseNum);
        driver.findElement(By.name("rcNumber")).sendKeys(this.rcNumber);
        driver.findElement(By.name("fAreaOverAll")).sendKeys("100");


        driver.findElement(By.xpath("//label[@for='cb_FIntendance_property']")).click();

        for (int i = 0; i < this.paskirtys.length; i++) {
            WebElement vienaPaskirtis = driver.findElement(By.xpath("//input[@data-title='" + this.paskirtys[i] + "']/following-sibling::label"));
            vienaPaskirtis.click();
        }
        for (int i = 0; i < this.ypatybes.length; i++) {
            WebElement vienaYpatybe = driver.findElement(By.xpath("//input[@data-title='" + this.ypatybes[i] + "']/following-sibling::label"));
            vienaYpatybe.click();
        }

        driver.findElement(By.name("notes_lt")).sendKeys("Parduodamas sklypas su elektra.");
        driver.findElement(By.name("video")).sendKeys("https://youtube.com/example");
        driver.findElement(By.name("tour_3d")).sendKeys("https://3dturas.example.com");
        driver.findElement(By.name("price")).sendKeys("50000");
        driver.findElement(By.name("phone")).sendKeys("+37012345678");
        driver.findElement(By.name("email")).sendKeys("test@example.com");


        driver.findElement(By.xpath("//input[@data-title='Privatus asmuo']")).click();

        driver.findElement(By.name("agree_to_rules")).click();
        driver.findElement(By.name("submitFormButton")).click();
    }
}

