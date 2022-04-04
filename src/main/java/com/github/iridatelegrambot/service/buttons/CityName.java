package com.github.iridatelegrambot.service.buttons;

public enum CityName {

    DNIPRO("Днепр", "addCity:Dnipro"),
    KYIV("Киев", "addCity:Kyiv"),
    LVIV("Львов", "addCity:Lviv"),
    RIVNE("Ровно", "addCity:Rivne"),
    IVANO_FRANKIVSK("Ивано-Франковск", "addCity:IvanoFrankivsk"),
    VINNITSA("Винница", "addCity:Vinniisa"),
    ODESSA("Одесса", "addCity:Odessa"),
    ZAPOROZHYE("Запорожье", "addCity:Zaporozhye"),
    KHARKIV("Харьков", "addCity:Kharkiv"),
    KROPYVNYTSKYI("Кропивницкий", "addCity:Kropyvnytskyi"),
    POLTAVA("Полтава", "addCity:Poltava"),
    NIKOLAEV("Николаев", "addCity:Nikolaev"),
    CHERKASY("Черкассы", "addCity:Cherkasy");

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
