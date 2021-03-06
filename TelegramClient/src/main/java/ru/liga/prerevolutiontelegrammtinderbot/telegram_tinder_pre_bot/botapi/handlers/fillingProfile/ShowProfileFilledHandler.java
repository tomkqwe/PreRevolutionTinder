package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers.fillingProfile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.InputMessageHandler;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache.DataCache;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.SexType;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.InlineKeyBoardSelector;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.MainMenuKeyboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.Communication;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.UpdateHandler;

import java.io.File;

/**
 * В это класс нужно забульбенить картинку с текстом для просмотра своей анкеты на картинке
 */

@Component
public class ShowProfileFilledHandler implements InputMessageHandler {
    public static final String ANKET_COMPLITE = "Анкета заполнена!";
    public static final String GO_PRESS_BUTTON = "Кого ищем?\nНажмите на кнопку!";
    private final DataCache dataCache;
    private final Communication communication;

    @Autowired
    public ShowProfileFilledHandler(DataCache dataCache, Communication communication) {
        this.dataCache = dataCache;
        this.communication = communication;
    }

    @Override
    public PartialBotApiMethod<?> handleUpdate(Update update) {
        long userID = UpdateHandler.getId(update);
        String chatId = UpdateHandler.getChatId(update);

        //получаем callbackquery с partnerGender, сэтим его в юзера,
        //если пользователь ввел ручками partnerGender, то получаем текст
        //и просим пользователя нажать на кнопку для получения callbackquery
        //когда юзер заполнен, наконец-то заполнение анкеты завершено
        //переходим в главное меню

        User userProfileData = dataCache.getUserProfileData(userID);
        SendMessage sendMessage = null;
        if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            userProfileData.setPartnerSex(SexType.getSexType(data));
            sendMessage = new SendMessage(chatId, ANKET_COMPLITE);
            dataCache.saveUserProfileData(userID, dataCache.getUserProfileData(userID));

        } else {
            sendMessage = new SendMessage(chatId, GO_PRESS_BUTTON);
            sendMessage.setReplyMarkup(InlineKeyBoardSelector.getInlineKeyboardMarkup(BotState.ASK_PARTNER_GENDER));
            dataCache.setUsersCurrentBotState(userID, getHandlerName());
            return sendMessage;
        }

        SendMessage smWithMenu = new SendMessage(chatId, String.format("%s%n -------------------%nИмя: %s%nВозраст: %d%n Пол: %s%nОписание: %s%n" +
                        "Кого ищем: %s%n", "Данные по вашей анкете", userProfileData.getName(), userProfileData.getAge(), userProfileData.getSex().getName(), userProfileData.getDescription(),
                userProfileData.getPartnerSex()));
        smWithMenu.setReplyMarkup(MainMenuKeyboard.getMainMenuKeyboard());
        userProfileData.setId(userID);
        communication.saveUser(userProfileData);
        File textImageMaker = communication.getTextImageMaker(userProfileData.getId());
        SendPhoto sendPhoto = new SendPhoto(chatId, new InputFile(textImageMaker));
        sendPhoto.setCaption(userProfileData.getSex().getName() + " " + userProfileData.getName());
        sendPhoto.setReplyMarkup(MainMenuKeyboard.getMainMenuKeyboard());
        //communication.saveUser(userProfileData);
        return sendPhoto;

//        return smWithMenu;
    }


    @Override
    public BotState getHandlerName() {
        return BotState.PROFILE_FILLED;
    }
}
