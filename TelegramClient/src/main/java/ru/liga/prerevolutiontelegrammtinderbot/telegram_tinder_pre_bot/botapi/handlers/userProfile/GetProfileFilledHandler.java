package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers.userProfile;

import lombok.Data;
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
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.FormKeyboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.InlineKeyBoardSelector;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.MainMenuKeyboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.Communication;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.UpdateHandler;

import java.io.File;

@Data
@Component
public class GetProfileFilledHandler implements InputMessageHandler {
    public static final String NEED_REGISTRATION = "Вам нужно зарегистрироваться";
    public static final String CHANGE_FORM = "Изменяем анкету";
    public static final String BACK_TO_MAIN_MENU = "Возращаемся в главное меню";
    private final DataCache dataCache;
    private final Communication communication;

    @Autowired
    public GetProfileFilledHandler(DataCache dataCache, Communication communication) {
        this.dataCache = dataCache;
        this.communication = communication;
    }

    @Override
    public PartialBotApiMethod<?> handleUpdate(Update update) {
        long userID = UpdateHandler.getId(update);
        String chatID = UpdateHandler.getChatId(update);
        String text = UpdateHandler.getText(update);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setReplyMarkup(FormKeyboard.getFormKeyboard());
        sendMessage.setChatId(chatID);

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatID);
        sendPhoto.setReplyMarkup(FormKeyboard.getFormKeyboard());

        User user = communication.getUser(userID);
        if (user == null) {
            dataCache.setUsersCurrentBotState(userID, BotState.ASK_GENDER);
            sendMessage.setText(NEED_REGISTRATION);
            return sendMessage;
        }
        if (text.equals(FormKeyboard.CHANGE_FORM)) {
            dataCache.setUsersCurrentBotState(userID, BotState.ASK_GENDER);
            sendMessage.setText(CHANGE_FORM);
            sendMessage.setReplyMarkup(InlineKeyBoardSelector.getInlineKeyboardMarkup(BotState.START_STATE));
            return sendMessage;
        } else if (text.equals(FormKeyboard.BACK)) {
            sendMessage.setText(BACK_TO_MAIN_MENU);
            sendMessage.setReplyMarkup(MainMenuKeyboard.getMainMenuKeyboard());
            return sendMessage;
        }
        File textImageMaker = communication.getTextImageMaker(user.getId());
        sendPhoto.setPhoto(new InputFile(textImageMaker));
        sendPhoto.setCaption(user.getSex().getName() + " " + user.getName());
        return sendPhoto;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.SHOW_USER_PROFILE;
    }
}
