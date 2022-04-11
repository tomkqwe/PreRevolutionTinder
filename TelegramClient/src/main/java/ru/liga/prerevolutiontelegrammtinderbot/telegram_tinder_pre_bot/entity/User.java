package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity;


import lombok.*;

import java.util.Objects;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
    private Long id;
    private String name;
    private SexType sex;
    private Integer age;
    private String description;
    private SexType partnerSex;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(getId(), user.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
