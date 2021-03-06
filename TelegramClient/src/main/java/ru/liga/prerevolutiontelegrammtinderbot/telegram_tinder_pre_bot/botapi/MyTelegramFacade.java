package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.cache.DataCache;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.UpdateHandler;

@Data
@Component
@Slf4j
public class MyTelegramFacade {

    public static final String START = "/start";
    public static final String LETS_GO = "Поехали";
    public static final String FORM = "Анкета";
    public static final String FAVORITES = "Любимцы";
    public static final String SEARCH = "Поиск";
    private BotStateContext botStateContext;
    private DataCache userDataCache;

    @Autowired
    public MyTelegramFacade(BotStateContext botStateContext, DataCache userDataCache) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
    }

    public PartialBotApiMethod<?> handleUpdate(Update update) {
        Message message = update.getMessage();
        if (message != null && message.hasText()) {
            log.info("New message from User:{}, userId: {}, chatId: {},  with text: {}",
                    message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText());
        }
        if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info("New callbackQuery from User: {}, userId: {}, with data: {}", update.getCallbackQuery().getFrom().getUserName(),
                    callbackQuery.getFrom().getId(), update.getCallbackQuery().getData());
        }
        return handleInputMessage(update);
    }

    private PartialBotApiMethod<?> handleInputMessage(Update update) {
        long id = UpdateHandler.getId(update);
        String text = UpdateHandler.getText(update);
        BotState botState;
        switch (text) {
            case START:
                botState = BotState.START_STATE;
                break;
            case LETS_GO:
                botState = BotState.ASK_GENDER;
                break;
            case FORM:
                botState = BotState.SHOW_USER_PROFILE;
                break;
            case FAVORITES:
                botState = BotState.GET_FAVORITES;
                break;
            case SEARCH:
                botState = BotState.GET_SEARCHING;
                break;
            default:
                botState = userDataCache.getUsersCurrentBotState(id);
        }
        userDataCache.setUsersCurrentBotState(id, botState);

        return botStateContext.processInputMessage(botState, update);
    }


}
