package com.github.iridatelegrambot.bot;

import com.github.iridatelegrambot.entity.ConditionBot;
import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckUpdateOnPost {

    private final UserTelegramService userTelegramService;
    @Autowired
    public CheckUpdateOnPost(UserTelegramService userTelegramService) {
        this.userTelegramService = userTelegramService;
    }

    public boolean waitingNumberOrder(Long idChat){
        if(userTelegramService.findByChatId(idChat).isEmpty()){
            return false;
        }
        ConditionBot conditionBot = userTelegramService.findByChatId(idChat).get().getConditionBot();
        return conditionBot.isAnswerOrderStatus();
    }

    public boolean waitingNumberInvoice(Long idChat){
        if(userTelegramService.findByChatId(idChat).isEmpty()){
            return false;
        }
        ConditionBot conditionBot = userTelegramService.findByChatId(idChat).get().getConditionBot();
        return conditionBot.isAnswerInvoiceStatus();
    }

    public void setStatusOrder(Long idChat,boolean status){
        try {
            UserTelegram userTelegram = userTelegramService.findByChatId(idChat).get();
            userTelegram.getConditionBot().setAnswerOrderStatus(status);
            userTelegramService.save(userTelegram);
        } catch (Exception e){
            System.out.println("Cannot find User Telegram with Id: " + idChat);
        }
    }

    public void  setStatusInvoice(Long idChat,boolean status){
        try {
            UserTelegram userTelegram = userTelegramService.findByChatId(idChat).get();
            userTelegram.getConditionBot().setAnswerInvoiceStatus(status);
            userTelegramService.save(userTelegram);
        } catch (Exception e){
            System.out.println("Cannot find User Telegram with Id: " + idChat);
        }
    }
}
