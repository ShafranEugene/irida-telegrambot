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
        ConditionBot conditionBot = findUserTelegram(idChat).getConditionBot();
        return conditionBot.isAnswerOrderStatus();
    }

    public boolean waitingNumberInvoice(Long idChat){
        ConditionBot conditionBot = findUserTelegram(idChat).getConditionBot();
        return conditionBot.isAnswerInvoiceStatus();
    }

    public void setStatusOrder(Long idChat,boolean status){
        UserTelegram userTelegram = userTelegramService.findByChatId(idChat).get();
        userTelegram.getConditionBot().setAnswerOrderStatus(status);
        userTelegramService.save(userTelegram);
    }
    public void  setStatusInvoice(Long idChat,boolean status){
        UserTelegram userTelegram = findUserTelegram(idChat);
        userTelegram.getConditionBot().setAnswerInvoiceStatus(status);
        userTelegramService.save(userTelegram);
    }

    private UserTelegram findUserTelegram(Long idChat){
        if(userTelegramService.findByChatId(idChat).isEmpty()){
                    UserTelegram user = new UserTelegram();
                    user.setActive(true);
                    user.setChatId(idChat);
                    ConditionBot conditionBot = new ConditionBot();
                    conditionBot.setUserTelegram(user);
                    user.setConditionBot(conditionBot);
                    userTelegramService.save(user);
                }
        return userTelegramService.findByChatId(idChat).get();
    }
}
