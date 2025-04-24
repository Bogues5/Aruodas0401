package org.example.models;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class Plot {
    public WebDriver driver;
    public String region;
    public String district;
    public String quartal;
    public String street;
    public String FHouseNum;
    public String show_house_num;
    public String RCNumber;
    public String show_rc_number;
    public String FAreaOverAll;

    public String[] paskirtys = {
            "Namų valda",
            "Sklypas soduose",
            "Sandėliavimo",
            "Kita",
            "Daugiabučių statyba",
            "Miškų ūkio",
            "Komercinė",
            "Žemės ūkio",
            "Pramonės",
            "Rekreacinė"
    };


    public String[] ypatybes = {
            "Elektra",
            "Kraštinis sklypas",
            "Geodeziniai matavimai",
            "Dujos",
            "Greta miško",
            "Su pakrante",
            "Vanduo",
            "Be pastatų",
            "Asfaltuotas privažiavimas"};



    public String interestedChange;
    public String notes_lt;
    public String uploadPhotoBtn;}
    public String video;
    public String tour_3d;
    public String price;
    public String phone;
    public String email;
    public String cbdont_show_in_ads;
    public String dont_want_chat;
    public String[] account_type = {
