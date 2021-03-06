package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TelegramTinderPreBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(TelegramTinderPreBotApplication.class, args);
    }

}
