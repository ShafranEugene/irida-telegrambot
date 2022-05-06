package com.github.iridatelegrambot.service.senders;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommandCallbackSenderServiceImpl implements CommandCallbackSenderService{
    private final SendMessageService sendMessageService;
    private final SendWithOrderService sendMessageWithOrderService;
    private final SendMainMenuService sendMessageMainMenuService;
    private final SendAdminMenuService sendMessageAdminMenuService;
    private final SendOrderMenuService sendMessageOrderMenuService;
    private final SendStatMenuService sendMessageStatMenuService;
    private final SendCitiesService sendCitiesService;

    @Autowired
    public CommandCallbackSenderServiceImpl(SendMessageService sendMessageService, SendWithOrderService sendMessageWithOrderService, SendMainMenuService sendMessageMainMenuService,
                                            SendAdminMenuService sendMessageAdminMenuService, SendOrderMenuService sendMessageOrderMenuService,
                                            SendStatMenuService sendMessageStatMenuService, SendCitiesService sendCitiesService) {
        this.sendMessageService = sendMessageService;
        this.sendMessageWithOrderService = sendMessageWithOrderService;
        this.sendMessageMainMenuService = sendMessageMainMenuService;
        this.sendMessageAdminMenuService = sendMessageAdminMenuService;
        this.sendMessageOrderMenuService = sendMessageOrderMenuService;
        this.sendMessageStatMenuService = sendMessageStatMenuService;
        this.sendCitiesService = sendCitiesService;
    }

    @Override
    public void sendMessage(String chatId, String message){
        sendMessageService.sendMessage(chatId,message);
    }

    @Override
    public void sendActiveOrdersForInvoice(Long chatId, String message, Integer messageId, Invoice invoice){
        sendMessageWithOrderService.sendActiveOrdersForInvoice(chatId,message,messageId,invoice);
    }

    @Override
    public void deleteMessage(Long chatId, Integer messageId){
        sendMessageService.deleteMessage(chatId,messageId);
    }

    @Override
    public void sendMainMenu(Long chatId, String message){
        sendMessageMainMenuService.sendMainMenu(chatId,message);
    }

    @Override
    public void sendMessageCloseOrder(Long chatId, Integer messageId, String message, Order order){
        sendMessageWithOrderService.sendMessageCloseOrder(chatId,messageId,message,order);
    }

    @Override
    public void sendAdminSetStatus(Long chatId, boolean status, String message, Integer messageId){
        sendMessageAdminMenuService.sendAdminSetStatus(chatId,status,message,messageId);
    }

    @Override
    public void sendUsersForAdmin(Long chatId, String message, Integer messageId){
        sendMessageAdminMenuService.sendUsersForAdmin(chatId,message, messageId);
    }

    @Override
    public void sendMenuOrder(Long chatId, Order order){
        sendMessageOrderMenuService.sendMenuOrder(chatId,order);
    }

    @Override
    public void sendMenuStatDetails(Long chatId, String message, Integer messageId, WaitDocument waitDocument){
        sendMessageStatMenuService.sendMenuStatDetails(chatId,message,messageId,waitDocument);
    }

    @Override
    public void sendAdminMenu(Long chatId, String message, Integer messageId){
        sendMessageAdminMenuService.sendAdminMenu(chatId,message,messageId);
    }

    @Override
    public void sendListCityForOrder(Optional<Order> orderOptional, Long chatId){
        sendCitiesService.sendListCityForOrder(orderOptional,chatId);
    }

    @Override
    public void sendListCityForInvoice(Optional<Invoice> invoiceOptional, Long chatId){
        sendCitiesService.sendListCityForInvoice(invoiceOptional,chatId);
    }
}
