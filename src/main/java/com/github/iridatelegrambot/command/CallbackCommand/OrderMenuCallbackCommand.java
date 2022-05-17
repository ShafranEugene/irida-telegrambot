package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.command.AddInvoiceCommand;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.senders.CommandCallbackSenderService;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
@Component
public class OrderMenuCallbackCommand implements CallbackCommand {
    private final OrderService orderService;
    private final CommandCallbackSenderService sendMessageService;
    private final CallbackCommandName commandName = CallbackCommandName.ORDER_MENU;
    private final AddInvoiceCommand addInvoiceCommand;

    @Autowired
    public OrderMenuCallbackCommand(OrderService orderService, CommandCallbackSenderService sendMessageService,AddInvoiceCommand addInvoiceCommand) {
        this.orderService = orderService;
        this.sendMessageService = sendMessageService;
        this.addInvoiceCommand = addInvoiceCommand;
    }

    @Override
    public void execute(CallbackQuery callbackQuery) {
        String[] data = callbackQuery.getData().split(":");
        String subtype = data[1];
        int idOrder = Integer.parseInt(data[3]);
        Long chatId = callbackQuery.getMessage().getChatId();

        Optional<Order> orderOptional = orderService.getOrderById(idOrder);
        if(orderOptional.isEmpty()){
            sendMessageService.sendMessage(chatId.toString(),"Заказ не найден");
            return;
        }
        Order order = orderOptional.get();

        if(subtype.equals("addinvoice")){
            WaitDocument.INVOICE.setIdOrderToInvoice(chatId,idOrder);
            Update update = new Update();
            Message message = callbackQuery.getMessage();
            message.setText("/add_invoice");
            update.setMessage(message);
            addInvoiceCommand.execute(update);
        } else if (subtype.equals("delete")){
            orderService.delete(idOrder);
            sendMessageService.sendMessage(chatId.toString(),"Заказ с номером \"" + order.getNumber() +
                    "\" был удален.");
        }
    }

    @Override
    public String getNameCommand() {
        return commandName.getName();
    }
}
