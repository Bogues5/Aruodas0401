/*
KODO LOGIKOS APRAŠYMAS

1. Klasės struktūra ir duomenų saugojimas:
   - Plot klasė centralizuotai saugo visą informaciją apie sklypą.
   - Laukai apima regiono, gyvenvietės, mikrorajono, gatvės ir namo numerio duomenis.
   - RC numeris yra specialiai „sanitizuojamas“ – naudojamas statinis metodas sanitizeRcNumber,
     kuris pašalina visus ne-skaitmenis, užtikrindamas, kad į RC lauką pateikia tik skaitmenis.
   - Kiti laukai apima sklypo plotą, paskirties variantus, specialių ypatybių sąrašą,
     papildomus nustatymus (pvz., "Domina keitimas", "Varžytynės/aukcionas"),
     aprašymus įvairiomis kalbomis (LT, EN, RU) bei media nuorodas (YouTube, 3D turas).
   - Taip pat yra kontaktinė informacija – sklypo kaina, telefono numeris, el. paštas,
     ir nustatymai, ar išjungti el. pašto kontaktavimą bei „chat“ funkciją.
   - Galutinis laukas accountType nurodo, koks yra vartotojo tipas (pvz., 1 – Privatus asmuo).

2. Statinis metodas RC numerio sanitarizacijai:
   - Metodas sanitizeRcNumber(String rc) pašalina visus neatitinkančius skaitmenų simbolius.
   - Tai reiškia, kad jei unikaliame RC numeryje bus raidžių (ar kitų netinkamų simbolių),
     metodas grąžins tik skaitmenis, užtikrindamas duomenų vientisumą.

3. Konstruktorius:
   - Konstruktoriumi naudojame this. nuorodą, kad tiksliai priskirtume kiekvieną parametrą
     atitinkamam klasės laukui.
   - RC numeris yra paverčiamas panaudojant sanitizeRcNumber metodą, todėl galutiniame objekto
     lauke lieka tik skaitmeninė reikšmė.

4. Testavimo logika su TestNG ir Selenium:
   - TestNG naudojamas vienetiniams testams atlikti: tikrinamos reikšmės atitinka lūkesčius.
   - Assertions tikrina, kad kiekvienas laukas (pvz., regiono kodas, RC numerio sanitarizacija ir kt.)
     yra teisingai inicializuotas.
   - Selenium naudojamas sukurti naršyklės pagrindu vykdomus testus, pavyzdžiui:
       • Vienas testas atidaro Google ir tikrina, ar puslapio title turi "Google".
       • Kitas testas atidaro aruodas.lt nuorodą ir tikrina, ar URL atitinka lūkesčius.

5. Kodų organizacija:
   - Visi laukai yra vieši (public), todėl jie yra tiesiogiai pasiekiami.
   - Overridden toString() metodas leidžia vienoje eilutėje peržiūrėti visus objekto duomenis,
     kas padeda testavimo metu tikrinti, ar visi laukai tinkamai priskirti.
   - Testuose naudojami assert metodai (TestNG Assert) užtikrinti, kad objekto būsena atitinka
     numatytas reikšmes, įskaitant RC numerio sanitarizaciją.

Šis išsamus aprašymas turi padėti suprasti, kaip mūsų kodo logika buvo sukonstruota.
Galite šį komentarų bloką naudoti kaip dokumentaciją savo projekte IntelliJ IDEA aplinkoje.
*/




package org.example;
import java.util.List; // Import the List interface

// Public class holding all information about a plot. All fields are accessed via public getters in our tests.
public class Plot {
    public int regionCode;             // Region code (e.g., 461)
    public String regionName;          // Region name (e.g., "Vilnius")
    public int districtCode;           // District code (e.g., 1)
    public String districtName;        // District name (e.g., "Vilniaus m.")
    public int quartalCode;            // Quartal (micro-district) code (e.g., 2)
    public String quartalName;         // Quartal name (e.g., "Balsiai")
    public int streetCode;             // Street code (e.g., 21862)
    public String streetName;          // Street name (e.g., "A. Jakšto g.")
    public int houseNumber;            // House number (e.g., 5)
    public boolean checkboxSelected;   // Flag: whether the house number display checkbox is checked

    public String rcNumber;            // Unique RC number (stored only as digits)
    public boolean rcCheckboxSelected; // Flag: whether the RC checkbox is selected

    public double area;                // Plot area in ares (e.g., 200.0)
    public List<String> purposes;      // List of purpose codes (e.g., "property", "manufacturingland", etc.)
    public boolean showAttributes;     // Flag: whether the "Show Attributes" button is pressed
    public List<Integer> specialFeatures; // List of special feature codes (e.g., [1,2,3,4,5,6,7,11,501])

    public boolean interestedChange;   // Flag for "Domina keitimas"
    public boolean auction;            // Flag for "Varžytynės/aukcionas"

    public String notesLt;             // Description in Lithuanian
    public String notesEn;             // Description in English
    public String notesRu;             // Description in Russian

    public String video;               // YouTube link or embed code
    public String tour3d;              // 3D tour link

    public int price;                  // Plot price in euros (e.g., 35000)
    public String phone;               // Phone number (e.g., "+37060000000")
    public String email;               // Email address (e.g., "example@example.com")
    public boolean dontShowInAds;      // Flag: disable contact via email in the ad
    public boolean dontWantChat;       // Flag: disable the chat functionality

    public int accountType;            // Account type (1 = Privatus asmuo, 2 = Tarpininkas, 3 = Vystytojas/statytojas, 4 = Kitas verslo subjektas)
    public boolean agreeToRules;       // Flag: whether the user agrees with the portal rules (must be true)

    // Static method to sanitize the RC number field (keeps only digits).
    public static String sanitizeRcNumber(String rc) {
        if (rc == null) return "";                // Return an empty string if input is null.
        return rc.replaceAll("[^0-9]", "");        // Remove any character that is not a digit.
    }

    // Public constructor initializing all fields using "this." for clarity.
    public Plot(
            int regionCode, String regionName,
            int districtCode, String districtName,
            int quartalCode, String quartalName,
            int streetCode, String streetName,
            int houseNumber, boolean checkboxSelected,
            String rcNumber, boolean rcCheckboxSelected,
            double area, List<String> purposes,
            boolean showAttributes, List<Integer> specialFeatures,
            boolean interestedChange, boolean auction,
            String notesLt, String notesEn, String notesRu,
            String video, String tour3d,
            int price, String phone, String email,
            boolean dontShowInAds, boolean dontWantChat,
            int accountType, boolean agreeToRules
    ) {
        this.regionCode = regionCode;                    // Set region code.
        this.regionName = regionName;                    // Set region name.
        this.districtCode = districtCode;                // Set district code.
        this.districtName = districtName;                // Set district name.
        this.quartalCode = quartalCode;                  // Set quartal code.
        this.quartalName = quartalName;                  // Set quartal name.
        this.streetCode = streetCode;                    // Set street code.
        this.streetName = streetName;                    // Set street name.
        this.houseNumber = houseNumber;                  // Set house number.
        this.checkboxSelected = checkboxSelected;        // Set house number checkbox flag.
        this.rcNumber = sanitizeRcNumber(rcNumber);      // Sanitize and set RC number.
        this.rcCheckboxSelected = rcCheckboxSelected;      // Set RC checkbox flag.
        this.area = area;                                // Set plot area.
        this.purposes = purposes;                        // Set purposes list.
        this.showAttributes = showAttributes;            // Set "show attributes" flag.
        this.specialFeatures = specialFeatures;          // Set special features list.
        this.interestedChange = interestedChange;        // Set "domina keitimas" flag.
        this.auction = auction;                          // Set auction flag.
        this.notesLt = notesLt;                          // Set Lithuanian description.
        this.notesEn = notesEn;                          // Set English description.
        this.notesRu = notesRu;                          // Set Russian description.
        this.video = video;                              // Set YouTube link/embed.
        this.tour3d = tour3d;                            // Set 3D tour link.
        this.price = price;                              // Set plot price.
        this.phone = phone;                              // Set phone number.
        this.email = email;                              // Set email address.
        this.dontShowInAds = dontShowInAds;              // Set flag for email contact.
        this.dontWantChat = dontWantChat;                // Set flag for chat functionality.
        this.accountType = accountType;                  // Set account type.
        this.agreeToRules = agreeToRules;                // Set agreement with rules flag.
    }

    // Public getter methods for all fields.
    public int getRegionCode() { return regionCode; }
    public String getRegionName() { return regionName; }
    public int getDistrictCode() { return districtCode; }
    public String getDistrictName() { return districtName; }
    public int getQuartalCode() { return quartalCode; }
    public String getQuartalName() { return quartalName; }
    public int getStreetCode() { return streetCode; }
    public String getStreetName() { return streetName; }
    public int getHouseNumber() { return houseNumber; }
    public boolean isCheckboxSelected() { return checkboxSelected; }
    public String getRcNumber() { return rcNumber; }
    public boolean isRcCheckboxSelected() { return rcCheckboxSelected; }
    public double getArea() { return area; }
    public List<String> getPurposes() { return purposes; }
    public boolean isShowAttributes() { return showAttributes; }
    public List<Integer> getSpecialFeatures() { return specialFeatures; }
    public boolean isInterestedChange() { return interestedChange; }
    public boolean isAuction() { return auction; }
    public String getNotesLt() { return notesLt; }
    public String getNotesEn() { return notesEn; }
    public String getNotesRu() { return notesRu; }
    public String getVideo() { return video; }
    public String getTour3d() { return tour3d; }
    public int getPrice() { return price; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public boolean isDontShowInAds() { return dontShowInAds; }
    public boolean isDontWantChat() { return dontWantChat; }
    public int getAccountType() { return accountType; }
    public boolean isAgreeToRules() { return agreeToRules; }

    // Overridden toString() method providing a full string representation of the plot instance.
    @Override
    public String toString() {
        return "Plot{" +
                "regionCode=" + regionCode +
                ", regionName='" + regionName + '\'' +
                ", districtCode=" + districtCode +
                ", districtName='" + districtName + '\'' +
                ", quartalCode=" + quartalCode +
                ", quartalName='" + quartalName + '\'' +
                ", streetCode=" + streetCode +
                ", streetName='" + streetName + '\'' +
                ", houseNumber=" + houseNumber +
                ", checkboxSelected=" + checkboxSelected +
                ", rcNumber='" + rcNumber + '\'' +
                ", rcCheckboxSelected=" + rcCheckboxSelected +
                ", area=" + area +
                ", purposes=" + purposes +
                ", showAttributes=" + showAttributes +
                ", specialFeatures=" + specialFeatures +
                ", interestedChange=" + interestedChange +
                ", auction=" + auction +
                ", notesLt='" + notesLt + '\'' +
                ", notesEn='" + notesEn + '\'' +
                ", notesRu='" + notesRu + '\'' +
                ", video='" + video + '\'' +
                ", tour3d='" + tour3d + '\'' +
                ", price=" + price +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", dontShowInAds=" + dontShowInAds +
                ", dontWantChat=" + dontWantChat +
                ", accountType=" + accountType +
                ", agreeToRules=" + agreeToRules +
                '}';
    }
}
