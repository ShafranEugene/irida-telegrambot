package com.github.iridatelegrambot.service.statuswait;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public enum WaitDocument {
    ORDER("order"),
    INVOICE("invoice");

    private String name;
    private HashMap<Long,Boolean> waitNumber = new HashMap<>();
    private List<WaitTypeStatus> waitTypeStatusList = Arrays.asList(WaitTypeStatus.values());
    private HashMap<Long,Integer> idOrderForInvoice = new HashMap<>();

    WaitDocument(String name) {
        this.name = name;
    }

    public static Boolean getWaitAllStatus(Long chatId){
        WaitDocument[] waitDocumentList = WaitDocument.values();
        for(WaitDocument document : waitDocumentList){
            if(document.getWaitStatus(chatId)){
                return true;
            }
        }
        return false;
    }

    public void setWaitNumber(Long chatId,Boolean statusDocument, WaitTypeStatus waitTypeStatus){
        List<WaitDocument> waitDocumentList = Arrays.asList(WaitDocument.values());
        for(WaitDocument document : waitDocumentList){
            document.waitNumber.put(chatId, false);
        }
        waitNumber.put(chatId,statusDocument);
        if(statusDocument){
            waitTypeStatus.setStatusTrue(chatId);
        } else {
            waitTypeStatus.setStatusFalse(chatId);
        }
    }

    public Boolean getWaitStatus(Long chatId){
        boolean allTypeStatus = false;
        for(WaitTypeStatus type : waitTypeStatusList){
            if(type.getStatus(chatId)){
                allTypeStatus = true;
            }
        }
        if(!allTypeStatus){
            return false;
        }
        if(!waitNumber.containsKey(chatId)){
            return false;
        }
        return waitNumber.get(chatId);
    }

    public Optional<WaitTypeStatus> getWaitType(Long chatId){
        if(!getWaitStatus(chatId)){
            return Optional.empty();
        }
        for(WaitTypeStatus type : waitTypeStatusList){
            if(type.getStatus(chatId)){
                return Optional.of(type);
            }
        }
        return Optional.empty();
    }

    public String getName() {
        return name;
    }

    public void addWaitInvoiceForOrder(Long chatId, Integer idOrder){
        if(this == INVOICE){
            INVOICE.setWaitNumber(chatId,true,WaitTypeStatus.ADD);
            INVOICE.idOrderForInvoice.put(chatId,idOrder);
        }
    }

    public Boolean invoiceHaveIdOrder(Long chatId){
        return idOrderForInvoice.containsKey(chatId);
    }

    public Integer getIdOrderForInvoice(Long chatId){
        Integer id = idOrderForInvoice.get(chatId);
        idOrderForInvoice.remove(chatId);
        return id;
    }
}
