package ru.liga.oldrussiantinderbot.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity

public class User {
    @Id
    private Long id;
    private String name;
    //потом заменим на энамы, яхз как их в пост запросе передать
    private String sex;
    private Integer age;
    private String description;

    private String partnerSex;

    //сэты из видео, таблички он там делает в ручную
    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = {@JoinColumn(name = "from_user")},
            inverseJoinColumns = {@JoinColumn(name = "to_user")}
    )
    @JsonIgnore
    private Set<User> whoLikedMe = new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "user_likes",
            joinColumns = {@JoinColumn(name = "to_user")},
            inverseJoinColumns = {@JoinColumn(name = "from_user")}
    )
    @JsonIgnore
    private Set<User> weLike = new HashSet<>();
}
