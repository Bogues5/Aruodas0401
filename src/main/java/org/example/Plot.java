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

*/

package org.example;

import java.util.List;      // Importuojame List sąsają, kad galėtume saugoti kolekcijas.
import java.util.Arrays;    // Importuojame Arrays klasę, kad lengvai sukurtume List'us.

/*
 * Plot klasė saugo visą informaciją apie sklypą, įskaitant regiono, adreso,
 * RC numerio (sanitizuojamo), ploto, paskirties, kontaktinės ir kitokios informacijos laukus.
 * Visi laukai yra public, todėl jie tiesiogiai prieinami.
 */
public class Plot {

    // Regiono ir adreso susiję laukai
    public int regionCode;             // Regiono kodas, pvz., 461
    public String regionName;          // Regiono pavadinimas, pvz., "Vilnius"
    public int districtCode;           // Gyvenvietės (miesto) kodas, pvz., 1
    public String districtName;        // Gyvenvietės pavadinimas, pvz., "Vilniaus m."
    public int quartalCode;            // Mikrorajono kodas, pvz., 2
    public String quartalName;         // Mikrorajono pavadinimas, pvz., "Balsiai"
    public int streetCode;             // Gatvės kodas, pvz., 21862
    public String streetName;          // Gatvės pavadinimas, pvz., "A. Jakšto g."
    public int houseNumber;            // Namo numeris, pvz., 5
    public boolean checkboxSelected;   // Flag, ar rodyti namo numerį (true, jei pažymėta)

    // RC numerio laukai (RC numeris bus išfiltruotas, kad liktų tik skaitmenys)
    public String rcNumber;            // Unikalus RC numeris (prieš priskyrimą vykdoma sanitizacija)
    public boolean rcCheckboxSelected; // Flag, ar rodyti RC numerį (true, jei pažymėta)

    // Ploto ir paskirties laukai
    public double area;                // Sklypo plotas arais (pvz., 200.0)
    public List<String> purposes;      // Paskirties variantų sąrašas (pvz., "property", "manufacturingland", ir pan.)
    public boolean showAttributes;     // Flag, ar paspaustas mygtukas "Žymėti ypatumus"
    public List<Integer> specialFeatures; // Specialių ypatybių sąrašas (pvz., [1, 2, 3, 4, 5, 6, 7, 11, 501])

    // Papildomi nustatymai
    public boolean interestedChange;   // Flag "Domina keitimas"
    public boolean auction;            // Flag "Varžytynės/aukcionas"

    // Aprašymo laukai įvairiomis kalbomis
    public String notesLt;             // Aprašymas lietuviškai
    public String notesEn;             // Aprašymas anglų kalba
    public String notesRu;             // Aprašymas rusiškai

    // Media nuorodos
    public String video;               // YouTube nuoroda arba embed kodas
    public String tour3d;              // 3D turo nuoroda

    // Kontaktinė informacija ir kaina
    public int price;                  // Sklypo kaina eurais, pvz., 35000
    public String phone;               // Telefono numeris, pvz., "+37060000000"
    public String email;               // El. pašto adresas

    // Papildomi kontaktų nustatymai
    public boolean dontShowInAds;      // Flag: ar išjungti kontaktavimą el. paštu
    public boolean dontWantChat;       // Flag: ar išjungti chat funkciją

    // Vartotojo tipo ir taisyklių sutikimo laukai
    public int accountType;            // Vartotojo tipas (1 = Privatus asmuo, 2 = Tarpininkas, 3 = Vystytojas/statytojas, 4 = Kitas verslo subjektas)
    public boolean agreeToRules;       // Flag: ar vartotojas sutinka su portalų taisyklėmis (privaloma būti true)

    /*
     * Statinis metodas sanitizeRcNumber pašalina visus simbolius, kurie nėra skaitmenys,
     * iš pateikto RC numerio. Tai užtikrina, kad RC numeris bus saugomas tik kaip skaitmeninė eilutė.
     */
    public static String sanitizeRcNumber(String rc) {
        if (rc == null) return "";                // Jei įvestis null, grąžiname tuščią eilutę.
        return rc.replaceAll("[^0-9]", "");        // Pašaliname visus simbolius, kurie nėra skaitmenys.
    }

    /*
     * Konstruktorius inicializuoja visus laukus. Naudojame "this.", kad aiškiai atskirtume
     * klasės lauko pavadinimus nuo parametro pavadinimų. RC numeris yra praeina per sanitizeRcNumber.
     */
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
        this.regionCode = regionCode;                       // Priskiriame regiono kodą.
        this.regionName = regionName;                       // Priskiriame regiono pavadinimą.
        this.districtCode = districtCode;                   // Priskiriame gyvenvietės kodą.
        this.districtName = districtName;                   // Priskiriame gyvenvietės pavadinimą.
        this.quartalCode = quartalCode;                     // Priskiriame mikrorajono kodą.
        this.quartalName = quartalName;                     // Priskiriame mikrorajono pavadinimą.
        this.streetCode = streetCode;                       // Priskiriame gatvės kodą.
        this.streetName = streetName;                       // Priskiriame gatvės pavadinimą.
        this.houseNumber = houseNumber;                     // Priskiriame namo numerį.
        this.checkboxSelected = checkboxSelected;           // Priskiriame flagą, ar rodyti namo numerį.
        this.rcNumber = sanitizeRcNumber(rcNumber);         // Sanitarizuojame ir priskiriame RC numerį.
        this.rcCheckboxSelected = rcCheckboxSelected;       // Priskiriame flagą, ar rodyti RC numerį.
        this.area = area;                                   // Priskiriame sklypo plotą.
        this.purposes = purposes;                           // Priskiriame paskirties variantų sąrašą.
        this.showAttributes = showAttributes;               // Priskiriame flagą "Žymėti ypatumus".
        this.specialFeatures = specialFeatures;             // Priskiriame specialių ypatybių sąrašą.
        this.interestedChange = interestedChange;           // Priskiriame flagą "Domina keitimas".
        this.auction = auction;                             // Priskiriame flagą "Varžytynės/aukcionas".
        this.notesLt = notesLt;                             // Priskiriame lietuvišką aprašymą.
        this.notesEn = notesEn;                             // Priskiriame anglišką aprašymą.
        this.notesRu = notesRu;                             // Priskiriame rusišką aprašymą.
        this.video = video;                                 // Priskiriame YouTube nuorodą arba embed kodą.
        this.tour3d = tour3d;                               // Priskiriame 3D turo nuorodą.
        this.price = price;                                 // Priskiriame sklypo kainą.
        this.phone = phone;                                 // Priskiriame telefono numerį.
        this.email = email;                                 // Priskiriame el. pašto adresą.
        this.dontShowInAds = dontShowInAds;                 // Priskiriame flagą, ar išjungti kontaktavimą el. paštu.
        this.dontWantChat = dontWantChat;                   // Priskiriame flagą, ar išjungti chat funkciją.
        this.accountType = accountType;                     // Priskiriame vartotojo tipą.
        this.agreeToRules = agreeToRules;                   // Priskiriame flagą, kad vartotojas sutinka su taisyklėmis.
    }

    /*
     * Overridden toString() metodas grąžina visų objektų laukų informaciją kaip eilutę.
     * Tai padeda greitai peržiūrėti visą Plot objekto būseną.
     */
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

    /*
     * Main metodas, skirtas demonstracijai: sukuriamas Plot objektas su pavyzdiniais duomenimis,
     * o tada informacija išvedama į konsolę naudojant toString().
     */
    public static void main(String[] args) {
        // Sukuriame paskirties variantų sąrašą.
        List<String> purposes = Arrays.asList("property", "manufacturingland", "farm", "garden", "forest");
        // Sukuriame specialiųjų ypatybių sąrašą.
        List<Integer> specialFeatures = Arrays.asList(1, 2, 3, 4, 5);
        // Tekstai aprašymams įvairiomis kalbomis.
        String notesLt = "Sklypas puikiai tinka investicijoms.";
        String notesEn = "The plot is ideal for investments.";
        String notesRu = "Участок идеально подходит для инвестиций.";
        // Pavyzdinės media nuorodos.
        String video = "https://www.youtube.com/embed/exampleVideo";
        String tour3d = "https://www.example.com/3d-tour/examplePlot";
        // Kaina, kontaktinė informacija ir kiti nustatymai.
        int price = 35000;
        String phone = "+37060000000";
        String email = "example@example.com";
        boolean dontShowInAds = false;
        boolean dontWantChat = true;
        int accountType = 1;
        boolean agreeToRules = true;
        // RC numeris su nereikalingomis raidėmis – bus išfiltruotas.
        String unsanitizedRc = "1234-5678-ABC9012";

        // Sukuriame naują Plot objektą.
        Plot plot = new Plot(
                461, "Vilnius",
                1, "Vilniaus m.",
                2, "Balsiai",
                21862, "A. Jakšto g.",
                5, true,
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
        // Išvedame objekto informaciją į konsolę.
        System.out.println(plot.toString());
    }
}



