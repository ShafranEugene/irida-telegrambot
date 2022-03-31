package com.github.iridatelegrambot.buttons;

public enum CityName {

    DRIPRO("Днепр"),
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
}
