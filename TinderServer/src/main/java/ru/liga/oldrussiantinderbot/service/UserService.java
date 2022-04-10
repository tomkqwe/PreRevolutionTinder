package ru.liga.oldrussiantinderbot.service;

import ru.liga.oldrussiantinderbot.model.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();

    User getUser(Long id);

    void saveUser(User user);

    void deleteUser(Long id);

    User updateUser(User user);

    void putFromCurrentToTargetLike(String ids);

    List<User> exportWeLikeList(Long id);

    List<User> exportWhoLikedMeList(Long id);

    List<User> exportSympathyList(Long id);

    List<User> getAllUsersToSearchList(Long id);


}
