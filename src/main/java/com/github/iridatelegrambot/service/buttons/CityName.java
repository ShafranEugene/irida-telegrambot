package com.github.iridatelegrambot.service.buttons;

public enum CityName {

    DNIPRO("Днепр"),
    KYIV("Киев"),
    LVIV("Львов"),
    RIVNE("Ровно"),
    IVANO_FRANKIVSK("Ивано-Франковск"),
    VINNITSA("Винница"),
    ODESSA("Одесса"),
    ZAPOROZHYE("Запорожье"),
    KHARKIV("Харьков"),
    KROPYVNYTSKYI("Кропивницкий"),
    POLTAVA("Полтава"),
    NIKOLAEV("Николаев"),
    CHERKASY("Черкассы");

    private final String name;

    CityName(String name) {
        this.name = name;
    }

    public String getNameCity(){
        return name;
    }
}
