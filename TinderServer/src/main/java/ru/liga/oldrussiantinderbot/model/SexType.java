package ru.liga.oldrussiantinderbot.model;

import lombok.Getter;

@Getter
public enum SexType {
    MALE("Сударь"), FEMALE("Сударыня"), ALL("Все");

    private final String name;

    SexType(String name) {
        this.name = name;
    }
}
