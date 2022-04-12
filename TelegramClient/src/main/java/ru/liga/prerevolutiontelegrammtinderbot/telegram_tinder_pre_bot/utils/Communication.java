package ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.utils;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.liga.prerevolutiontelegrammtinderbot.telegram_tinder_pre_bot.entity.User;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@Getter

@Component
public class Communication {
    private final RestTemplate restTemplate;
    @Value("${api.like}")
    public String LIKE;
    @Value("${api.welike}")
    public String WE_LIKE;
    @Value("${api.delimetr}")
    public String DELIMITER;
    @Value("${api.wholikedme}")
    public String WHO_LIKED_ME;
    @Value("${api.sympathy}")
    public String SYMPATHY;
    @Value("${api.image}")
    public String IMAGE;
    @Value("${api.startUrl}")
    private String URL;

    @Autowired
    public Communication(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<User> getAllUsers() {
//        log.info("get list of all users");
        return restTemplate.exchange(URL, HttpMethod.GET, null, new ParameterizedTypeReference<List<User>>() {
        }).getBody();
    }

    public User getUser(long id) {
        return restTemplate.getForObject(URL + "/" + id, User.class);
    }

    public void saveUser(User user) {
        restTemplate.postForEntity(URL, user, String.class);
    }

    public void updateUser(User user) {
        long id = user.getId();
        restTemplate.put(URL, user);
    }

    public List<User> getUsersToSearch(Long userID) {
        try {
            ResponseEntity<User[]> forEntity = restTemplate.getForEntity(URL + "filtredListToSearch/" + userID, User[].class);
            return Arrays.asList(forEntity.getBody());
        } catch (Exception e) {
            return null;
        }
    }

    public void likeRequest(long current, long target) {
        List<Long> longs = Arrays.asList(current, target);
        restTemplate.postForObject(URL + LIKE + current + DELIMITER + target, longs, ResponseEntity.class);
    }

    public List<User> getWeLike(Long userID) {
        ResponseEntity<User[]> responseEntity = restTemplate.getForEntity(URL + WE_LIKE + userID, User[].class);
        return Arrays.asList(responseEntity.getBody());
    }

    public List<User> getWhoLikedMe(Long userID) {
        ResponseEntity<User[]> forEntity = restTemplate.getForEntity(URL + WHO_LIKED_ME + userID, User[].class);
        return Arrays.asList(forEntity.getBody());
    }

    public List<User> getSympathy(Long userID) {
        ResponseEntity<User[]> forEntity = restTemplate.getForEntity(URL + SYMPATHY + userID, User[].class);
        return Arrays.asList(forEntity.getBody());
    }

    public File getTextImageMaker(Long userID) {
        HttpEntity<Long> stringHttpEntity = new HttpEntity<>(userID);
        return restTemplate.postForObject(URL + IMAGE, stringHttpEntity, File.class);
    }


    public void deleteUser(long id) {
        restTemplate.delete(URL + "/" + id);
    }


}
