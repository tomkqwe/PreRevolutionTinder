package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.handlers.search;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.BotState;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.InputMessageHandler;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.MainMenuKeyboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.keyboards.SearchKeyboard;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.Communication;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.UpdateHandler;

import java.io.File;
import java.util.List;

@Component
public class GetSearchingHandler implements InputMessageHandler {
    public static final String LIKE = "Лайк❤️";
    public static final String NEXT = "Следующий❌";
    public static final String BACK = "Назад";
    public static final String USE_MAIN_MENU = "Воспользуйтесь главным меню";
    public static final String NOBODY_HERE = "Тут никого нет\uD83D\uDE2D";
    private final Communication communication;
    private int index = 0;

    @Autowired
    public GetSearchingHandler(Communication communication) {
        this.communication = communication;
    }


    @Override
    public PartialBotApiMethod<?> handleUpdate(Update update) {
        long userID = UpdateHandler.getId(update);
        String chatID = UpdateHandler.getChatId(update);
        String text = UpdateHandler.getText(update);

        User currentUser = communication.getUser(userID);
        List<User> allUsersToSearch = communication.getUsersToSearch(userID);
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatID);
        sendPhoto.setReplyMarkup(SearchKeyboard.getSearchKeyboard());
        if (allUsersToSearch.isEmpty()) {
            return new SendMessage(chatID, NOBODY_HERE);
        }
        switch (text) {
            case LIKE:
                User target = allUsersToSearch.get(index);
                communication.likeRequest(currentUser.getId(), target.getId());
                index++;
                if (index == allUsersToSearch.size()) {
                    index = 0;
                }
                break;
            case NEXT:
                index++;
                if (index == allUsersToSearch.size()) {
                    index = 0;
                }
                break;
            case BACK:
                SendMessage sendMessage = new SendMessage(chatID, USE_MAIN_MENU);
                sendMessage.setReplyMarkup(MainMenuKeyboard.getMainMenuKeyboard());
                return sendMessage;
        }

        User user = allUsersToSearch.get(index);
        File textImageMaker = communication.getTextImageMaker(user.getId());
        sendPhoto.setPhoto(new InputFile(textImageMaker));
        sendPhoto.setCaption(user.getSex().getName() + " " + user.getName());
        return sendPhoto;
    }

    @Override
    public BotState getHandlerName() {
        return BotState.GET_SEARCHING;
    }

}
