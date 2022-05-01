package com.github.iridatelegrambot.service.statususer;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MuteInviteServiceImpl implements MuteInviteService{
    private Map<Long, Calendar> muteMap = new HashMap<>();

    @Override
    public boolean checkStatus(Long chatId){
        if(muteMap.containsKey(chatId)){
            Calendar calendarNow = new GregorianCalendar();
            return calendarNow.after(muteMap.get(chatId));
        }
        return true;
    }

    @Override
    public void setMute(Long chatId){
        Calendar calendar = new GregorianCalendar();
        calendar.roll(Calendar.HOUR,+1);
        muteMap.put(chatId,calendar);
    }

    @Override
    public void setMuteOfDay(Long chatId){
        Calendar calendar = new GregorianCalendar();
        calendar.roll(Calendar.DAY_OF_MONTH,+1);
        muteMap.put(chatId,calendar);
    }
}
