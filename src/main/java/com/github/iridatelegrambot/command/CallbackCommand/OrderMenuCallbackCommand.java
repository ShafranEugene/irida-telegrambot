package com.github.iridatelegrambot.command.CallbackCommand;

import com.github.iridatelegrambot.command.AddInvoiceCommand;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.OrderService;
import com.github.iridatelegrambot.service.send.SendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Optional;
@Component
public class OrderMenuCallbackCommand implements CallbackCommand {
    private final OrderService orderService;
    private final SendMessageService sendMessageService;
    private final CallbackCommandName commandName = CallbackCommandName.ORDER_MENU;

    @Autowired
    public OrderMenuCallbackCommand(OrderService orderService, SendMessageService sendMessageService) {
        this.orderService = orderService;
        this.sendMessageService = sendMessageService;
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
            Update update = new Update();
            Message message = callbackQuery.getMessage();
            message.setText("/add_invoice");
            update.setMessage(message);
            AddInvoiceCommand addInvoiceCommand = new AddInvoiceCommand(sendMessageService);
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
