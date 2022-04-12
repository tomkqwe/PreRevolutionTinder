package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramWebhookBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils.PropertiesUtil;

@Data
@Component
@Slf4j
public class TelegramTPB extends TelegramWebhookBot {
    private final String webHookPath = "bot.webHookPath";
    private final String botUserName = "bot.name";
    private final String botToken = "bot.token";

    private final MyTelegramFacade telegramFacade;


    @Autowired
    public TelegramTPB(MyTelegramFacade telegramFacade) {
        this.telegramFacade = telegramFacade;
    }

    @Override
    public String getBotUsername() {
        return PropertiesUtil.get(botUserName);
    }

    @Override
    public String getBotToken() {
        return PropertiesUtil.get(botToken);
    }

    @Override
    public BotApiMethod<?> onWebhookUpdateReceived(Update update) {

        PartialBotApiMethod<?> botApiMethod = telegramFacade.handleUpdate(update);

        if (botApiMethod instanceof SendPhoto) {
            try {
                execute((SendPhoto) botApiMethod);
                return null;
            } catch (TelegramApiException e) {
                log.info(e.getMessage());
            }
        }
        return (BotApiMethod<?>) botApiMethod;
    }

    @Override
    public String getBotPath() {
        return PropertiesUtil.get(webHookPath);
    }

}
