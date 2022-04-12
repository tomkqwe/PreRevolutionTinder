package ru.liga.oldrussiantinderbot.model;

public enum SexType {
    MALE("Сударь"), FEMALE("Сударыня"), ALL("Все");

    private final String name;

    SexType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
