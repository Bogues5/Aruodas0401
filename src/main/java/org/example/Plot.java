package org.example;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class Plot {
    public WebDriver driver;
    public String region, district, quartal, street, objNo, rcNo, plotSize, notes_lt, notes_en, notes_ru, video, tour3d, price, phone, email;
    public int[] intendances, specials;
    public boolean interestedChange, auction, dontShowInAds, cbdontWantChat, cbagreeToRules;
    public String[] photos;
    public int accountType;

    public Plot(WebDriver driver) {
        this.driver = driver;
    }

    public void fill() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String[] dropdowns = {region, district, quartal, street};
        for (int i = 0; i < dropdowns.length; i++) fillDropdown(i, dropdowns[i], wait);
        String[] fields = {objNo, rcNo, plotSize, String.valueOf(interestedChange), notes_lt, notes_en, notes_ru, video, tour3d, price, phone, email};
        String[] names = {"objNo", "rcNo", "plotSize", "interestedChange", "notes_lt", "notes_en", "notes_ru", "video", "tour3d", "price", "phone", "email"};
        for (int i = 0; i < fields.length; i++) fillTextField(names[i], fields[i]);
        boolean[] checks = {auction, cbagreeToRules, dontShowInAds, cbdontWantChat};
        String[] checkNames = {"auction", "agreeToRules", "dontShowInAds", "dontWantChat"};
        for (int i = 0; i < checks.length; i++) if (checks[i]) toggleCheckbox(checkNames[i], wait);
    }

    public void fillDropdown(int i, String val, WebDriverWait wait) {
        if (val != null && !val.isEmpty()) {
            List<WebElement> drops = driver.findElements(By.className("dropdown-input-value-title"));
            if (i < drops.size()) {
                drops.get(i).click();
                String xp = i == 3 ? "//*[@id=\"streets_1\"]/li[1]/input" : "//*[@id=\"input_" + (i + 1) + "\"]/li[1]/input";
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xp))).sendKeys(val + Keys.ENTER);
            }
        }
    }

    public void fillTextField(String name, String val) {
        if (val != null && !val.isEmpty()) driver.findElement(By.name(name)).sendKeys(val);
    }

    public void toggleCheckbox(String name, WebDriverWait wait) {
        WebElement cb = wait.until(ExpectedConditions.elementToBeClickable(By.name(name)));
        if (!cb.isSelected()) cb.click();
    }
}
