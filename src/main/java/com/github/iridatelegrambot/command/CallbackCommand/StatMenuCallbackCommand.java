package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.entity.UserTelegram;
import com.github.iridatelegrambot.service.InvoiceService;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.senders.CommandCallbackSenderService;
import com.github.iridatelegrambot.service.UserTelegramService;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

import java.util.List;
import java.util.Optional;
@Component
public class StatMenuCallbackCommand implements CallbackCommand {

    private final CommandCallbackSenderService commandCallbackSenderService;
    private final OrderService orderService;
    private final InvoiceService invoiceService;
    private final UserTelegramService userTelegramService;
    private final CallbackCommandName commandName = CallbackCommandName.STAT_MENU;
    private Integer idMessage;

    @Autowired
    public StatMenuCallbackCommand(CommandCallbackSenderService commandCallbackSenderService, OrderService orderService,
                                   InvoiceService invoiceService, UserTelegramService userTelegramService) {
        this.commandCallbackSenderService = commandCallbackSenderService;
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
        commandCallbackSenderService.sendMenuStatDetails(chatId,info.toString(),idMessage, WaitDocument.ORDER);
    }

    private void sendInfoAllInvoice(Long chatId){
        List<Invoice> invoices = invoiceService.getAllInvoice();
        StringBuilder info = new StringBuilder("Список всех накладных на перемещение:");
        for (Invoice invoice : invoices){
            info.append("\n\t- Номер - ").append(invoice.getNumber()).append("; Город - ").append(invoice.getCity()).append("; Добавлен - ").append(invoice.getDate()).append(";");
        }
        commandCallbackSenderService.sendMenuStatDetails(chatId,info.toString(),idMessage,WaitDocument.INVOICE);
    }

    private void sendAdminMenu(Long chatId){
        Optional<UserTelegram> userOptional = userTelegramService.findByChatId(chatId);
        if(userOptional.isPresent()){
            if(userOptional.get().isAdmin()) {
                commandCallbackSenderService.sendAdminMenu(chatId, "Меню администратора:",idMessage);
            } else {
                commandCallbackSenderService.sendMessage(chatId.toString(),"У Вас нет прав администратора.");
            }
        } else {
            commandCallbackSenderService.sendMessage(chatId.toString(),"Не могу найти Вас в базе, попробуйте воспользоваться командой /start .");
        }
    }
}
