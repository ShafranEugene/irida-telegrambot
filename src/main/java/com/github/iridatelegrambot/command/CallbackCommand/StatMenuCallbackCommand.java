package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.InvoiceService;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.send.SendMessageAdminMenuService;
import com.github.iridatelegrambot.service.UserTelegramService;
import com.github.iridatelegrambot.service.send.SendMessageStatMenuService;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;
import java.util.Optional;
@Component
public class StatMenuCallbackCommand implements CallbackCommand {

    private final SendMessageStatMenuService sendMessageService;
    private final SendMessageAdminMenuService sendAdminMenu;
    private final OrderService orderService;
    private final InvoiceService invoiceService;
    private final UserTelegramService userTelegramService;
    private final CallbackCommandName commandName = CallbackCommandName.STAT_MENU;
    private Integer idMessage;

    @Autowired
    public StatMenuCallbackCommand(SendMessageStatMenuService sendMessageService, OrderService orderService,
                                   InvoiceService invoiceService, UserTelegramService userTelegramService,
                                   SendMessageAdminMenuService sendAdminMenu) {
        this.sendMessageService = sendMessageService;
        this.sendAdminMenu = sendAdminMenu;
        this.orderService = orderService;
        this.invoiceService = invoiceService;
        this.userTelegramService = userTelegramService;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        idMessage = callbackQuery.getMessage().getMessageId();
        Long chatId = callbackQuery.getMessage().getChatId();
        String[] data = callbackQuery.getData().split(":");
        String text = data[1];

        if(text.equals("infoAllOrders")){
            sendInfoAllOrders(chatId);
        } else if(text.equals("infoAllInvoice")){
            sendInfoAllInvoice(chatId);
        } else if(text.equals("mainAdminMenu")){
            sendAdminMenu(chatId);
        }
    }

    @Override
    public String getNameCommand() {
        return commandName.getName();
    }

    private void sendInfoAllOrders(Long chatId){
        List<Order> orderList = orderService.getAllOrders();
        StringBuilder info = new StringBuilder("Список всех заказов:");
        for(Order order : orderList){
            info.append("\n\t- Номер - ").append(order.getNumber()).append("; Город - ").append(order.getCity()).append("; Добавлен - ").append(order.getDate()).append(";");
        }
        sendMessageService.sendMenuStatDetails(chatId,info.toString(),idMessage, WaitDocument.ORDER);
    }

    private void sendInfoAllInvoice(Long chatId){
        List<Invoice> invoices = invoiceService.getAllInvoice();
        StringBuilder info = new StringBuilder("Список всех накладных на перемещение:");
        for (Invoice invoice : invoices){
            info.append("\n\t- Номер - ").append(invoice.getNumber()).append("; Город - ").append(invoice.getCity()).append("; Добавлен - ").append(invoice.getDate()).append(";");
        }
        sendMessageService.sendMenuStatDetails(chatId,info.toString(),idMessage,WaitDocument.INVOICE);
    }

    private void sendAdminMenu(Long chatId){
        Optional<UserTelegram> userOptional = userTelegramService.findByChatId(chatId);
        if(userOptional.isPresent()){
            if(userOptional.get().isAdmin()) {
                sendAdminMenu.sendAdminMenu(chatId, "Меню администратора:",idMessage);
            } else {
                sendMessageService.sendMessage(chatId.toString(),"У Вас нет прав администратора.");
            }
        } else {
         sendMessageService.sendMessage(chatId.toString(),"Не могу найти Вас в базе, попробуйте воспользоваться командой /start .");
        }
    }
}
