package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity;

import lombok.Getter;

@Getter
public enum SexType {
    MALE("Сударь"), FEMALE("Сударыня"), ALL("Все");

    private final String name;

    SexType(String name) {
        this.name = name;
    }

    public static SexType getSexType(String string) {
        return switch (string) {
            case "Сударь" -> MALE;
            case "Сударыня" -> FEMALE;
            case "Все" -> ALL;
            default -> throw new IllegalStateException("Unexpected value: " + string);
        };
    }
}
