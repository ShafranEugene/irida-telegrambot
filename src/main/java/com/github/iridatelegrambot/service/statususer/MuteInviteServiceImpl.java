package com.github.iridatelegrambot.service.statususer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MuteInviteServiceImpl implements MuteInviteService{
    private final Map<Long, Calendar> muteMap = new HashMap<>();
    private final static Logger logger = LoggerFactory.getLogger(MuteInviteServiceImpl.class);

    @Override
    public boolean checkStatus(Long chatId){
        if(muteMap.containsKey(chatId)){
            Calendar calendarNow = new GregorianCalendar();
            Calendar muteTimeUser = muteMap.get(chatId);
            logger.info("User - " + chatId + ",time now: " + calendarNow.getTime().toString() +
                    ", user is muted until: " + muteTimeUser.getTime().toString());
            return calendarNow.after(muteTimeUser);
        }
        return true;
    }

    @Override
    public void setMute(Long chatId){
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.HOUR,+1);
        logger.info("User - " + chatId + ", has been get mute to " + calendar.getTime().toString());
        muteMap.put(chatId,calendar);
    }

    @Override
    public void setMuteOfDay(Long chatId){
        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH,+1);
        logger.info("User - " + chatId + ", has been get mute to " + calendar.getTime().toString());
        muteMap.put(chatId,calendar);
    }
}
