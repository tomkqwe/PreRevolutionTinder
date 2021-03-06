package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers.getFavorites;

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
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.FavoritesKeyboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.MainMenuKeyboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.WeLikeKeayboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.Communication;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.UpdateHandler;

import java.io.File;
import java.util.List;

@Component
public class GetFavoritesHandler implements InputMessageHandler {
    public static final String USE_MAIN_MENU = "Воспользуйтесь главным меню";
    public static final String MENU_FAVORITES = "Меню Любимцы";
    public static final String BACK = "Назад";
    public static final String YOURCHOOSE = "Нравятся вам";
    public static final String WHO_LIKED_ME = "Выбрали вас";
    public static final String SYMPATHY = "Взаимный выбор";
    private final int index = 0;
    private final DataCache dataCache;
    private final Communication communication;

    @Autowired
    public GetFavoritesHandler(DataCache dataCache, Communication communication) {
        this.dataCache = dataCache;
        this.communication = communication;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public PartialBotApiMethod<?> handleUpdate(Update update) {
        String chatId = UpdateHandler.getChatId(update);
        String text = UpdateHandler.getText(update);
        long id = UpdateHandler.getId(update);
        List<User> weLike = communication.getWeLike(id);
        List<User> whoLikedMe = communication.getWhoLikedMe(id);
        List<User> sympathy = communication.getSympathy(id);

        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setReplyMarkup(WeLikeKeayboard.getWeLikeKeayboard());

        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setReplyMarkup(WeLikeKeayboard.getWeLikeKeayboard());

        switch (text) {
            case YOURCHOOSE: {
                if (weLike.isEmpty()) {
                    sendMessage.setText(CheckWhoLikedMeListHandler.VOID_HERE);
                    sendMessage.setReplyMarkup(FavoritesKeyboard.getFavoritesKeyboard());
                    return sendMessage;
                }
                User user = weLike.get(index);
                File textImageMaker = communication.getTextImageMaker(user.getId());
                sendPhoto.setPhoto(new InputFile(textImageMaker));
                sendPhoto.setCaption(user.getSex().getName() + " " + user.getName());
                dataCache.setUsersCurrentBotState(id, BotState.CHECK_WE_LIKE_LIST);
                return sendPhoto;

            }
            case WHO_LIKED_ME: {
                if (whoLikedMe.isEmpty()) {
                    sendMessage.setText(CheckWhoLikedMeListHandler.VOID_HERE);
                    sendMessage.setReplyMarkup(FavoritesKeyboard.getFavoritesKeyboard());
                    return sendMessage;
                }
                User user = whoLikedMe.get(index);
                File textImageMaker = communication.getTextImageMaker(user.getId());
                sendPhoto.setPhoto(new InputFile(textImageMaker));
                sendPhoto.setCaption(user.getSex().getName() + " " + user.getName());
                dataCache.setUsersCurrentBotState(id, BotState.CHECK_WHO_LIKED_ME_LIST);
                return sendPhoto;

            }
            case SYMPATHY: {
                if (sympathy.isEmpty()) {
                    sendMessage.setText(CheckWhoLikedMeListHandler.VOID_HERE);
                    sendMessage.setReplyMarkup(FavoritesKeyboard.getFavoritesKeyboard());
                    return sendMessage;
                }
                User user = sympathy.get(index);
                File textImageMaker = communication.getTextImageMaker(user.getId());
                sendPhoto.setPhoto(new InputFile(textImageMaker));
                sendPhoto.setCaption(user.getSex().getName() + " " + user.getName());
                dataCache.setUsersCurrentBotState(id, BotState.CHECK_SYMPATHY_LIST);
                return sendPhoto;
            }
            case BACK: {
                sendMessage.setText(USE_MAIN_MENU);
                sendMessage.setReplyMarkup(MainMenuKeyboard.getMainMenuKeyboard());
                return sendMessage;
            }
        }
        sendMessage.setText(MENU_FAVORITES);
        sendMessage.setReplyMarkup(FavoritesKeyboard.getFavoritesKeyboard());
        return sendMessage;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.GET_FAVORITES;
    }
}
