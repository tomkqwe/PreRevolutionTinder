package ru.liga.oldrussiantinderbot.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.liga.oldrussiantinderbot.model.User;
import ru.liga.oldrussiantinderbot.repository.UserRepository;
import ru.liga.oldrussiantinderbot.utils.Translator;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    @Autowired
    private Translator translator;

    public UserServiceImpl(UserRepository userRepository, Translator translator) {
        this.userRepository = userRepository;
        this.translator = translator;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(Long id) {
        User user = null;
        Optional<User> byId = userRepository.findById(id);
        if (byId.isPresent()) {
            user = byId.get();
        }
        return user;
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        user.setName(translator.translateInOldLanguage(user.getName()));
        user.setDescription(translator.translateInOldLanguage(user.getDescription()));
        user.setSex(translator.translateInOldLanguage(user.getSex()));
        saveUser(user);
        return user;
    }

    @Override
    public void putFromCurrentToTargetLike(String ids) {
        String[] split = ids.split("_");
        long current = Long.parseLong(split[0]);
        long target = Long.parseLong(split[1]);
        User currentUser = getUser(current);
        User targetUser = getUser(target);
        currentUser.getWeLike().add(targetUser);
        saveUser(currentUser);
    }

    @Override
    public List<User> exportWeLikeList(Long id) {
        User user = getUser(id);
        Set<User> whoLikedMe = user.getWhoLikedMe();
        Set<User> weLike = user.getWeLike();
        return weLike
                .stream()
                .filter(user1 -> !whoLikedMe.contains(user1))
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());

    }

    @Override
    public List<User> exportWhoLikedMeList(Long id) {
        User user = getUser(id);
        Set<User> weLike = user.getWeLike();
        Set<User> whoLikedMe = user.getWhoLikedMe();
        return whoLikedMe
                .stream()
                .filter(user1 -> !weLike.contains(user1))
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());

    }

    @Override
    public List<User> exportSympathyList(Long id) {
        ArrayList<User> export = new ArrayList<>();
        User user = getUser(id);
        Set<User> weLike = user.getWeLike();
        Set<User> whoLikedMe = user.getWhoLikedMe();
        for (User us : weLike) {
            if (whoLikedMe.contains(us)) {
                export.add(us);
            }
        }
        return export;
    }

    @Override
    public List<User> getAllUsersToSearchList(Long id) {
        User user = getUser(id);
        Set<User> whoLikedMe = user.getWhoLikedMe();
        Set<User> weLike = user.getWeLike();
        return getAllUsers()
                .stream()
                .filter(user1 -> !weLike.contains(user1))
                .filter(user1 -> !user1.getId().equals(user.getId()))
                .filter(user1 -> (user1.getPartnerSex().equals("Все") ||
                        user1.getPartnerSex().equals(user.getSex())) &&
                        (user.getPartnerSex().equals("Все")||
                                user.getPartnerSex().equals(user1.getSex())))
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());

    }
}
