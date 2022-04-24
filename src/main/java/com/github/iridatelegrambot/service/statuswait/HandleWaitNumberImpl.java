package com.github.iridatelegrambot.service.statuswait;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.AnswerCatcherService;
import com.github.iridatelegrambot.service.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
public class HandleWaitNumberImpl implements HandleWaitNumber {

    private final SendMessageService sendMessageService;
    private final AnswerCatcherService answerCatcherService;
    private WaitDocument document;
    private WaitTypeStatus waitType;
    private Long chatId;
    private String answer;
    @Autowired
    public HandleWaitNumberImpl(SendMessageService sendMessageService, AnswerCatcherService answerCatcherService) {
        this.sendMessageService = sendMessageService;
        this.answerCatcherService = answerCatcherService;
    }
    @Override
    public void handle(Update update){
        chatId = update.getMessage().getChatId();
        findDocumentAndType();
        document.setWaitNumber(chatId,false,waitType);
        if(waitType == WaitTypeStatus.ADD){
            addDocument(update);
        } else if(waitType == WaitTypeStatus.DELETE){
            deleteDocument(update);
        } else if(waitType == WaitTypeStatus.INFO){
            infoDocument(update);
        }

        if (waitType != WaitTypeStatus.ADD){
            if(answer.startsWith("Повторите попытку.")){
                comeBackDocumentStatus();
            }
        }
    }

    private void findDocumentAndType(){
        WaitDocument[] waitDocuments = WaitDocument.values();

        for(WaitDocument document : waitDocuments){
            if(document.getWaitStatus(chatId)){
                this.document = document;
            }
        }

        Optional<WaitTypeStatus> typeStatusOptional = document.getWaitType(chatId);
        if(typeStatusOptional.isEmpty()){
            sendMessageService.sendMessage(chatId.toString(),"Ошибка. Не найден цель для вводимого номера.");
        }
        waitType = typeStatusOptional.get();
    }

    private void addDocument(Update update){
        if(document == WaitDocument.ORDER){
            Optional<Order> order = answerCatcherService.addOrder(update);
            if(order.isEmpty()){
                comeBackDocumentStatus();
            }
            sendMessageService.sendListCityForOrder(order,chatId);
        } else if(document == WaitDocument.INVOICE){
            Optional<Invoice> invoice = answerCatcherService.addInvoice(update);
            if(invoice.isEmpty()){
                comeBackDocumentStatus();
            }
            sendMessageService.sendListCityForInvoice(invoice,chatId);
        }
    }

    private void infoDocument(Update update){
        if(document == WaitDocument.ORDER){
            answer = answerCatcherService.infoOrder(update);
            sendMessageService.sendMessage(chatId.toString(),answer);
        } else if(document == WaitDocument.INVOICE){
            answer = answerCatcherService.infoInvoice(update);
            sendMessageService.sendMessage(chatId.toString(),answer);
        }
    }

    private void deleteDocument(Update update){
        if(document == WaitDocument.ORDER){
            answer = answerCatcherService.deleteOrder(update);
            sendMessageService.sendMessage(chatId.toString(),answer);
        } else if(document == WaitDocument.INVOICE){
            answer = answerCatcherService.deleteInvoice(update);
            sendMessageService.sendMessage(chatId.toString(),answer);
        }
    }

    private void comeBackDocumentStatus(){
        document.setWaitNumber(chatId,true,waitType);
    }
}
