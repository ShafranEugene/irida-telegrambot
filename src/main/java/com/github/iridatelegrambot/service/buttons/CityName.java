package com.github.iridatelegrambot.service.buttons;

public enum CityName {

    DNIPRO("Днепр", "/addCityDnipro"),
    KYIV("Киев", "/addCityKyiv"),
    LVIV("Львов", "/addCityLviv"),
    RIVNE("Ровно", "/addCityRivne"),
    IVANO_FRANKIVSK("Ивано-Франковск", "/addCityIvanoFrankivsk"),
    VINNITSA("Винница", "/addCityVinniisa"),
    ODESSA("Одесса", "/addCityOdessa"),
    ZAPOROZHYE("Запорожье", "/addCityZaporozhye"),
    KHARKIV("Харьков", "/addCityKharkiv"),
    KROPYVNYTSKYI("Кропивницкий", "/addCityKropyvnytskyi"),
    POLTAVA("Полтава", "/addCityPoltava"),
    NIKOLAEV("Николаев", "/addCityNikolaev"),
    CHERKASY("Черкассы", "/addCityCherkasy");

    private final String name;
    private final String callBack;

    CityName(String name, String callBack) {
        this.name = name;
        this.callBack = callBack;
    }

    public String getNameCity(){
        return name;
    }

    public String getCallBack(){
        return callBack;
    }
}
