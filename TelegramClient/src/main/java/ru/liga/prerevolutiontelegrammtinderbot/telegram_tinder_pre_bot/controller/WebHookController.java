package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.botapi.TelegramTPB;


@RestController
public class WebHookController {
    @Autowired
    private TelegramTPB telegramTPB;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public PartialBotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return telegramTPB.onWebhookUpdateReceived(update);
    }

}
