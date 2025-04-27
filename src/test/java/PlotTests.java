



package org.example;
import org.testng.annotations.Test; // TestNG annotation
import org.testng.Assert;           // TestNG assertions
import java.util.Arrays;             // For list creation
import java.util.List;               // List interface

// Selenium imports for the browser-driven tests.
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

// Public TestNG class for testing the Plot class.
public class PlotTests {

    // Test to verify the creation of a Plot instance and that all fields are set correctly.
    @Test
    public void testPlotCreation() {
        List<String> purposes = Arrays.asList(
                "property", "manufacturingland", "farm", "garden", "forest",
                "factory", "storage", "comm", "recr", "none"
        );
        List<Integer> specialFeatures = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 11, 501);
        String notesLt = "Sklypas mane tenkina ir norėčiau daugiau galimybių rinktis apmokėjimo būdui";
        String notesEn = "The plot meets my requirements and I would like more options for selecting the payment method";
        String notesRu = "Участок мне подходит, и я хотел бы иметь больше возможностей для выбора способа оплаты.";
        String video = "https://www.youtube.com/embed/sklypaiExample";
        String tour3d = "https://www.example.com/3d-tour/sklypas";
        int price = 35000;
        String phone = "+37060000000";
        String email = "example@example.com";
        boolean dontShowInAds = false;
        boolean dontWantChat = true;
        int accountType = 1;          // Privatus asmuo
        boolean agreeToRules = true;  // User must agree to rules

        // RC number deliberately includes letters; the static method should remove them.
        String unsanitizedRc = "1234-5678-ABC9012";
        String expectedRc = "123456789012";

        Plot plot = new Plot(
                461, "Vilnius", 1, "Vilniaus m.", 2, "Balsiai",
                21862, "A. Jakšto g.", 5, true,
                unsanitizedRc, true,
                200.0, purposes,
                true, specialFeatures,
                true, false,
                notesLt, notesEn, notesRu,
                video, tour3d,
                price, phone, email,
                dontShowInAds, dontWantChat,
                accountType, agreeToRules
        );

        // Assertions for each field.
        Assert.assertEquals(plot.regionCode, 461);
        Assert.assertEquals(plot.regionName, "Vilnius");
        Assert.assertEquals(plot.districtCode, 1);
        Assert.assertEquals(plot.districtName, "Vilniaus m.");
        Assert.assertEquals(plot.quartalCode, 2);
        Assert.assertEquals(plot.quartalName, "Balsiai");
        Assert.assertEquals(plot.streetCode, 21862);
        Assert.assertEquals(plot.streetName, "A. Jakšto g.");
        Assert.assertEquals(plot.houseNumber, 5);
        Assert.assertTrue(plot.checkboxSelected);
        Assert.assertEquals(plot.rcNumber, expectedRc); // Check RC number is sanitized.
        Assert.assertTrue(plot.rcCheckboxSelected);
        Assert.assertEquals(plot.area, 200.0, 0.001);
        Assert.assertEquals(plot.purposes, purposes);
        Assert.assertTrue(plot.showAttributes);
        Assert.assertEquals(plot.specialFeatures, specialFeatures);
        Assert.assertTrue(plot.interestedChange);
        Assert.assertFalse(plot.auction);
        Assert.assertEquals(plot.notesLt, notesLt);
        Assert.assertEquals(plot.notesEn, notesEn);
        Assert.assertEquals(plot.notesRu, notesRu);
        Assert.assertEquals(plot.video, video);
        Assert.assertEquals(plot.tour3d, tour3d);
        Assert.assertEquals(plot.price, price);
        Assert.assertEquals(plot.phone, phone);
        Assert.assertEquals(plot.email, email);
        Assert.assertEquals(plot.dontShowInAds, dontShowInAds);
        Assert.assertTrue(plot.dontWantChat);
        Assert.assertEquals(plot.accountType, accountType);
        Assert.assertTrue(plot.agreeToRules);
    }

    // Test the RC number sanitization static method separately.
    @Test
    public void testRcNumberSanitizationStaticMethod() {
        String unsanitized = "12ab34CD56";
        String expected = "123456";
        Assert.assertEquals(expected, Plot.sanitizeRcNumber(unsanitized));
    }

    // Selenium test to verify that Google opens correctly.
    @Test
    public void testSeleniumExample() {

        WebDriver driver = new ChromeDriver(); // Launch Chrome browser.
        driver.get("https://www.google.com");    // Navigate to Google.
        Assert.assertTrue(driver.getTitle().contains("Google"), "Title should contain 'Google'");

    }

    // Additional Selenium test that navigates to the Aruodas.lt posting URL.
    @Test
    public void testAruodasSkelbimas() {

        WebDriver driver = new ChromeDriver(); // Launch Chrome.
        driver.get("https://www.aruodas.lt/ideti-skelbima/?obj=11&offer_type=1"); // Navigate to the specified URL.
        // Assert that the current URL contains the expected domain and path.
        Assert.assertTrue(driver.getCurrentUrl().contains("aruodas.lt/ideti-skelbima"), "URL should contain 'aruodas.lt/ideti-skelbima'");

    }

    // Test the toString() method to ensure it outputs key fields.
    @Test
    public void testPlotToString() {
        List<String> purposes = Arrays.asList(
                "property", "manufacturingland", "farm", "garden", "forest",
                "factory", "storage", "comm", "recr", "none"
        );
        List<Integer> specialFeatures = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 11, 501);
        String notesLt = "Sklypas mane tenkina ir norėčiau daugiau galimybių rinktis apmokėjimo būdui";
        String notesEn = "The plot meets my requirements and I would like more options for selecting the payment method";
        String notesRu = "Участок мне подходит, и я хотел бы иметь больше возможностей для выбора способа оплаты.";
        String video = "https://www.youtube.com/embed/sklypaiExample";
        String tour3d = "https://www.example.com/3d-tour/sklypas";
        int price = 35000;
        String phone = "+37060000000";
        String email = "example@example.com";
        boolean dontShowInAds = false;
        boolean dontWantChat = true;
        int accountType = 1;
        boolean agreeToRules = true;

        Plot plot = new Plot(
                461, "Vilnius", 1, "Vilniaus m.", 2, "Balsiai",
                21862, "A. Jakšto g.", 5, true,
                "1234-5678-9012", true,
                200.0, purposes,
                true, specialFeatures,
                true, false,
                notesLt, notesEn, notesRu,
                video, tour3d,
                price, phone, email,
                dontShowInAds, dontWantChat,
                accountType, agreeToRules
        );
        String plotString = plot.toString();

        // Verify key parts of the toString() output.
        Assert.assertTrue(plotString.contains("Vilnius"));
        Assert.assertTrue(plotString.contains("Vilniaus m."));
        Assert.assertTrue(plotString.contains("Balsiai"));
        Assert.assertTrue(plotString.contains("A. Jakšto g."));
        Assert.assertTrue(plotString.contains("5"));
        Assert.assertTrue(plotString.contains("1234-5678-9012") ||
                plotString.contains(Plot.sanitizeRcNumber("1234-5678-9012")));
        Assert.assertTrue(plotString.contains("200.0"));
        Assert.assertTrue(plotString.contains("property"));
        Assert.assertTrue(plotString.contains("showAttributes=true"));
        Assert.assertTrue(plotString.contains("interestedChange=true"));
        Assert.assertTrue(plotString.contains("auction=false"));
        Assert.assertTrue(plotString.contains(notesLt));
        Assert.assertTrue(plotString.contains(notesEn));
        Assert.assertTrue(plotString.contains(notesRu));
        Assert.assertTrue(plotString.contains(video));
        Assert.assertTrue(plotString.contains(tour3d));
        Assert.assertTrue(plotString.contains(String.valueOf(price)));
        Assert.assertTrue(plotString.contains(phone));
        Assert.assertTrue(plotString.contains(email));
        Assert.assertTrue(plotString.contains("dontShowInAds=false"));
        Assert.assertTrue(plotString.contains("dontWantChat=true"));
        Assert.assertTrue(plotString.contains("accountType=" + accountType));
        Assert.assertTrue(plotString.contains("agreeToRules=true"));
    }
}
