package com.github.iridatelegrambot.bot;

import com.github.iridatelegrambot.entity.BondOrderToInvoice;
import com.github.iridatelegrambot.entity.ConditionBot;
import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.UserTelegramService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CheckUpdateOnPost {

    private final UserTelegramService userTelegramService;
    private final ArrayList<BondOrderToInvoice> bondWaitInvoice;

    @Autowired
    public CheckUpdateOnPost(UserTelegramService userTelegramService) {
        this.userTelegramService = userTelegramService;
        bondWaitInvoice = new ArrayList<>();
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
            if(userTelegramService.findByChatId(idChat).isEmpty()) return;
            UserTelegram userTelegram = userTelegramService.findByChatId(idChat).get();
            userTelegram.getConditionBot().setAnswerOrderStatus(status);
            userTelegramService.save(userTelegram);
    }

    public void  setStatusInvoice(Long idChat,boolean status){
            if(userTelegramService.findByChatId(idChat).isEmpty()) return;
            UserTelegram userTelegram = userTelegramService.findByChatId(idChat).get();
            userTelegram.getConditionBot().setAnswerInvoiceStatus(status);
            userTelegramService.save(userTelegram);
    }

    public void setOrderToBond(Long chatId,Integer idOrder){
        BondOrderToInvoice bond = new BondOrderToInvoice();
        bond.setIdOrder(idOrder);
        bond.setChatId(chatId);
        bond.setWaitInvoice(true);
        bondWaitInvoice.add(bond);
    }

    public boolean checkWaitToInvoice(Long chatId){
        for(BondOrderToInvoice bond : bondWaitInvoice){
            if(bond.isWaitInvoice() && bond.getChatId().equals(chatId)){
                return true;
            }
        }
        return false;
    }

    public Optional<BondOrderToInvoice> getBond(Long chatId){
        for(BondOrderToInvoice bond : bondWaitInvoice){
            if(bond.getChatId().equals(chatId)){
                bondWaitInvoice.remove(bond);
                return Optional.of(bond);
            }
        }
        return Optional.empty();
    }
}
