package com.github.iridatelegrambot.service.send;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.iridatelegrambot.bot.IridaBot;
import com.github.iridatelegrambot.entity.BondOrderToInvoice;
import com.github.iridatelegrambot.entity.Invoice;
import com.github.iridatelegrambot.entity.Order;
import com.github.iridatelegrambot.service.buttons.InlineKeyboardService;
import com.github.iridatelegrambot.service.buttons.MenuButtonsService;
import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Optional;

import static com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandName.ADD_ORDER_TO_INVOICE;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class SendMessageServiceImpl implements SendMessageCitiesService,SendMessageMainMenuService, SendMessageWithOrderService,
                                            SendMessageOrderMenuService,SendMessageStatMenuService,SendMessageInviteForAdminService,
                                            SendMessageAdminMenuService{

    private final IridaBot iridaBot;
    private final InlineKeyboardService inlineKeyboardService;
    private final MenuButtonsService menuButtonsService;

    @Autowired
    public SendMessageServiceImpl(@Lazy IridaBot iridaBot, InlineKeyboardService inlineKeyboardService,
                                  MenuButtonsService menuButtonsService) {
        this.iridaBot = iridaBot;
        this.inlineKeyboardService = inlineKeyboardService;
        this.menuButtonsService = menuButtonsService;
    }



    @Override
    public void sendMessage(String chatId, String message) {
        if(isBlank(message)) return;

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);


        try {
            iridaBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String chatId, String message, ReplyKeyboard replyKeyboard) {
        if(isBlank(message)) return;

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(replyKeyboard);
        sendMessage.enableHtml(true);

        try {
            iridaBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editMessage(Long chatId,Integer messageId, String message){
        EditMessageText editMessageText = new EditMessageText();
        editMessageText.setChatId(chatId.toString());
        editMessageText.setMessageId(messageId);
        editMessageText.setText(message);

        try {
            iridaBot.execute(editMessageText);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void editButtons(Long chatId, Integer messageId, InlineKeyboardMarkup markup){
        EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
        editMessageReplyMarkup.setChatId(chatId.toString());
        editMessageReplyMarkup.setReplyMarkup(markup);
        editMessageReplyMarkup.setMessageId(messageId);
        try {
            iridaBot.execute(editMessageReplyMarkup);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMessage(Long chatId,Integer messageId){
        DeleteMessage deleteMessage = new DeleteMessage();
        deleteMessage.setChatId(chatId.toString());
        deleteMessage.setMessageId(messageId);
        try {
            iridaBot.execute(deleteMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendListCityForOrder(Optional<Order> orderOptional, Long chatId){
        if(orderOptional.isEmpty()){
            sendMessage(chatId.toString(),
                    "Повторите попытку.\nСообщение не содержит номера или такой номер уже есть.");
            return;
        }

        Order order = orderOptional.get();
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.cityButtons(order);
        sendMessage(chatId.toString(),"Введите город который сделал заказ:",inlineKeyboardMarkup);
    }
    @Override
    public void sendListCityForInvoice(Optional<Invoice> invoiceOptional, Long chatId){
        if (invoiceOptional.isEmpty()){
            sendMessage(chatId.toString(),
                    "Повторите попытку.\nСообщение не содержит номера или такой номер уже есть.");
            return;
        }

        Invoice invoice = invoiceOptional.get();
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.cityButtons(invoice);
        sendMessage(chatId.toString(),"Введите город из которого была накладная:",inlineKeyboardMarkup);
    }

    @Override
    public void sendMainMenu(Long chatId, String message){
        ReplyKeyboardMarkup markup = menuButtonsService.mainMenu();
        sendMessage(chatId.toString(),message,markup);
    }

    @Override
    public void sendActiveOrdersForInvoice(Long chatId, String message,Integer messageId, Invoice invoice){
        if(WaitDocument.INVOICE.invoiceHaveIdOrder(chatId)){
            sendMessageCloseOrderIfInvoiceHaveOrder(chatId,invoice,messageId);
            return;
        }
        InlineKeyboardMarkup inlineKeyboardMarkup = inlineKeyboardService.markupActiveOrdersForInvoice(invoice);
        sendMessage(chatId.toString(),message,inlineKeyboardMarkup);
    }

    private void sendMessageCloseOrderIfInvoiceHaveOrder(Long chatId, Invoice invoice, Integer messageId){
        BondOrderToInvoice bond = new BondOrderToInvoice();
        bond.setIdInvoice(invoice.getId());
        bond.setIdOrder(WaitDocument.INVOICE.getIdOrderForInvoice(chatId));
        ObjectMapper objectMapper = new ObjectMapper();
        CallbackQuery callbackQuery = new CallbackQuery();
        try {
            String data = ADD_ORDER_TO_INVOICE.getNameForService() + objectMapper.writeValueAsString(bond);
            callbackQuery.setData(data);
            Message messageToData = new Message();
            Chat chat = new Chat();
            chat.setId(chatId);
            messageToData.setChat(chat);
            messageToData.setMessageId(messageId);
            callbackQuery.setMessage(messageToData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Update update = new Update();
        update.setCallbackQuery(callbackQuery);
        iridaBot.onUpdateReceived(update);
    }

    @Override
    public void sendMessageCloseOrder(Long chatId, Integer messageId, String message, Order order){
        editMessage(chatId,messageId,message);
        editButtons(chatId,messageId,inlineKeyboardService.closeOrder(order));
    }

    @Override
    public void sendActiveOrders(Long chatId, String message){
        sendMessage(chatId.toString(),message,inlineKeyboardService.showActiveOrders());
    }

    @Override
    public void sendMenuOrder(Long chatId,Order order){
        InlineKeyboardMarkup markup = inlineKeyboardService.showMenuOrder(order);
        sendMessage(chatId.toString(),order.toStringForUsers(),markup);
    }

    @Override
    public void sendMenuStat(Long chatId, String message){
        InlineKeyboardMarkup markup = inlineKeyboardService.showMenuStat();
        sendMessage(chatId.toString(),message,markup);
    }

    @Override
    public void sendMenuStatDetails(Long chatId, String message, WaitDocument waitDocument){
        InlineKeyboardMarkup markup = inlineKeyboardService.showMenuStatDetails(waitDocument);
        sendMessage(chatId.toString(),message,markup);
    }

    @Override
    public void sendInviteToAdmin(Long chatIdAdmin,Long chatIdUser, String message){
        sendMessage(chatIdAdmin.toString(),message, inlineKeyboardService.inviteForAdmin(chatIdUser));
    }

    @Override
    public void sendAdminMenu(Long chatId, String message, Integer idMessage){
        editMessage(chatId,idMessage,message);
        editButtons(chatId,idMessage,inlineKeyboardService.showMenuAdmin());
    }

    @Override
    public void sendAdminSetStatus(Long chatId, boolean status, String message, Integer messageId){
        editMessage(chatId,messageId,message);
        editButtons(chatId,messageId,inlineKeyboardService.showAllUsersForSetStatus(status));
    }

    @Override
    public void sendUsersForAdmin(Long chatId, String message, Integer messageId){
        editMessage(chatId,messageId,message);
        editButtons(chatId,messageId,inlineKeyboardService.showAllUsersForSetAdmin());
    }
}
