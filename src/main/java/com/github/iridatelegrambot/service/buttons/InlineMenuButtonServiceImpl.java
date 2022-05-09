package com.github.iridatelegrambot.service.buttons;

import com.github.iridatelegrambot.service.statuswait.WaitDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.util.HashMap;
import java.util.Map;

import static com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandName.*;
import static com.github.iridatelegrambot.command.CallbackCommand.CallbackCommandName.STAT_DOCUMENT;
import static com.github.iridatelegrambot.service.statuswait.WaitTypeStatus.DELETE;
import static com.github.iridatelegrambot.service.statuswait.WaitTypeStatus.INFO;
@Service
public class InlineMenuButtonServiceImpl implements InlineMenuButtonService {
    private final InlineKeyboardService inlineKeyboardService;
    @Autowired
    public InlineMenuButtonServiceImpl(InlineKeyboardService inlineKeyboardService) {
        this.inlineKeyboardService = inlineKeyboardService;
    }

    @Override
    public InlineKeyboardMarkup showMenuOrder(Integer idOrder){
        Map<String,String> buttonsMap = new HashMap<>();
        buttonsMap.put("Добавить накладную на перемещение",ORDER_MENU.getNameForService() + "addinvoice:id:" + idOrder);
        buttonsMap.put("Удалить заказ",ORDER_MENU.getNameForService() + "delete:id:" + idOrder);
        ORDER_MENU.setSubCommands(new String[]{"addinvoice","delete"});
        return inlineKeyboardService.createMenu(buttonsMap);
    }

    @Override
    public InlineKeyboardMarkup showMenuStat(){
        Map<String,String> buttonsMap = new HashMap<>();
        buttonsMap.put("Информация о всех заказах", STAT_MENU.getNameForService() + "infoAllOrders");
        buttonsMap.put("Информация о всех накладных", STAT_MENU.getNameForService() + "infoAllInvoice");
        buttonsMap.put("Информация для администратора", STAT_MENU.getNameForService() + "mainAdminMenu");
        STAT_MENU.setSubCommands(new String[]{"infoAllOrders","infoAllInvoice","mainAdminMenu"});
        return inlineKeyboardService.createMenu(buttonsMap);
    }

    @Override
    public InlineKeyboardMarkup showMenuStatDetails(WaitDocument waitDocument){
        Map<String,String> buttonsMap = new HashMap<>();
        buttonsMap.put("Получить более детальную информацию по накладной",STAT_DOCUMENT.getNameForService() +
                waitDocument.getName() + ":" + INFO.getName());
        buttonsMap.put("Удалить накладную",STAT_DOCUMENT.getNameForService() +
                waitDocument.getName() + ":" + DELETE.getName());
        return inlineKeyboardService.createMenu(buttonsMap);
    }

    @Override
    public InlineKeyboardMarkup showMenuAdmin(){
        Map<String,String> buttonsMap = new HashMap<>();
        buttonsMap.put("Закрыть доступ пользователю",ADMIN_MENU.getNameForService() + "closeStatusUser");
        buttonsMap.put("Открыть доступ пользователю",ADMIN_MENU.getNameForService() + "openStatusUser");
        buttonsMap.put("Выдать права администратора пользователю",ADMIN_MENU.getNameForService() + "setAdmin");
        buttonsMap.put("Снять с меня правад администратора",ADMIN_MENU.getNameForService() + "pullOffAdmin");
        ADMIN_MENU.setSubCommands(new String[]{"closeStatusUser","openStatusUser","setAdmin"});
        return inlineKeyboardService.createMenu(buttonsMap);
    }
}
