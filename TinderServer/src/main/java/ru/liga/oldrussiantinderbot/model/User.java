package ru.liga.oldrussiantinderbot.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor

public class User {
    @Id
    private Long id;
    private String name;
    private SexType sex;
    private Integer age;
    private String description;
    private SexType partnerSex;

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
