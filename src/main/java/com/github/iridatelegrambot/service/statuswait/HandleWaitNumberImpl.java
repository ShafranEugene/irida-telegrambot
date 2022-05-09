package com.github.iridatelegrambot.service.statuswait;

import com.github.iridatelegrambot.service.AnswerCatcherService;
import com.github.iridatelegrambot.service.senders.CommandCallbackSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;

@Service
public class HandleWaitNumberImpl implements HandleWaitNumber {

    private final CommandCallbackSenderService commandCallbackSenderService;
    private final AnswerCatcherService answerCatcherService;
    private WaitDocument document;
    private WaitTypeStatus waitType;
    private Long chatId;
    private String answer;
    @Autowired
    public HandleWaitNumberImpl(AnswerCatcherService answerCatcherService, CommandCallbackSenderService commandCallbackSenderService) {
        this.answerCatcherService = answerCatcherService;
        this.commandCallbackSenderService = commandCallbackSenderService;
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
            commandCallbackSenderService.sendMessage(chatId.toString(),"Ошибка. Не найден цель для вводимого номера.");
        }
        waitType = typeStatusOptional.get();
    }

    private void addDocument(Update update){
        if(document == WaitDocument.ORDER){
            if(answerCatcherService.addOrder(update).isEmpty()){
                comeBackDocumentStatus();
            }
            commandCallbackSenderService.sendListCityForOrder(answerCatcherService.addOrder(update),chatId);
        } else if(document == WaitDocument.INVOICE){
            if(answerCatcherService.addInvoice(update).isEmpty()){
                comeBackDocumentStatus();
            }
            commandCallbackSenderService.sendListCityForInvoice(answerCatcherService.addInvoice(update),chatId);
        }
    }

    private void infoDocument(Update update){
        if(document == WaitDocument.ORDER){
            answer = answerCatcherService.infoOrder(update);
            commandCallbackSenderService.sendMessage(chatId.toString(),answer);
        } else if(document == WaitDocument.INVOICE){
            answer = answerCatcherService.infoInvoice(update);
            commandCallbackSenderService.sendMessage(chatId.toString(),answer);
        }
    }

    private void deleteDocument(Update update){
        if(document == WaitDocument.ORDER){
            answer = answerCatcherService.deleteOrder(update);
            commandCallbackSenderService.sendMessage(chatId.toString(),answer);
        } else if(document == WaitDocument.INVOICE){
            answer = answerCatcherService.deleteInvoice(update);
            commandCallbackSenderService.sendMessage(chatId.toString(),answer);
        }
    }

    private void comeBackDocumentStatus(){
        document.setWaitNumber(chatId,true,waitType);
    }
}
