package ru.liga.oldrussiantinderbot.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public SexType getSex() {
        return this.sex;
    }

    public Integer getAge() {
        return this.age;
    }

    public String getDescription() {
        return this.description;
    }

    public SexType getPartnerSex() {
        return this.partnerSex;
    }

    public Set<User> getWhoLikedMe() {
        return this.whoLikedMe;
    }

    public Set<User> getWeLike() {
        return this.weLike;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(SexType sex) {
        this.sex = sex;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPartnerSex(SexType partnerSex) {
        this.partnerSex = partnerSex;
    }

    @JsonIgnore
    public void setWhoLikedMe(Set<User> whoLikedMe) {
        this.whoLikedMe = whoLikedMe;
    }

    @JsonIgnore
    public void setWeLike(Set<User> weLike) {
        this.weLike = weLike;
    }
}
