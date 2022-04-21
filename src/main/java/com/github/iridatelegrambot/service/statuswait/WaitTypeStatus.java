package com.github.iridatelegrambot.service.statuswait;

import java.util.HashMap;


public enum WaitTypeStatus {
    DELETE("delete"),
    ADD("add"),
    INFO("info");

    private String name;
    private HashMap<Long,Boolean> waitNumber = new HashMap<>();

    WaitTypeStatus(String name) {
        this.name = name;
    }

    public boolean getStatus(Long chatId) {
        if(!waitNumber.containsKey(chatId)){
            return false;
        }
        return waitNumber.get(chatId);
    }

    public void setStatusTrue(Long chatId) {
        WaitTypeStatus[] waitTypeStatusList = WaitTypeStatus.values();
        for(WaitTypeStatus waitTypeStatus : waitTypeStatusList){
           waitTypeStatus.setStatus(chatId,false);
        }
        waitNumber.put(chatId,true);
    }

    public void setStatusFalse(Long chatId){
        waitNumber.put(chatId,false);
    }

    private void setStatus(Long chatId,Boolean status){
        waitNumber.put(chatId,status);
    }

    public String getName() {
        return name;
    }
}
