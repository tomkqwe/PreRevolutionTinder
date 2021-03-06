package ru.liga.oldrussiantinderbot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.liga.oldrussiantinderbot.model.User;
import ru.liga.oldrussiantinderbot.model.dto.UserDTO;
import ru.liga.oldrussiantinderbot.model.dto.UserMapper;
import ru.liga.oldrussiantinderbot.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRESTController {

    private final UserService userService;


    @Autowired
    public UserRESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        return UserMapper.INSTANCE.fromUserToUserDTO(user);
    }

    @PostMapping("/users")
    public UserDTO addNewUser(@RequestBody User user) {
        userService.saveUser(user);
        return UserMapper.INSTANCE.fromUserToUserDTO(user);
    }

    @PutMapping("/users")
    public UserDTO updateUser(@RequestBody User user) {
        userService.updateUser(user);
        return UserMapper.INSTANCE.fromUserToUserDTO(user);
    }

    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userService.getUser(id);
        userService.deleteUser(user.getId());
        return "User with ID = " + id + " was deleted";
    }

    @PostMapping("/users/like/{ids}")
    public void likeUser(@PathVariable String ids) {
        userService.putFromCurrentToTargetLike(ids);
    }

    @GetMapping("/users/weLike/{id}")
    public List<User> exportWeLike(@PathVariable Long id) {
        return userService.exportWeLikeList(id);
    }

    @GetMapping("/users/whoLikedMe/{id}")
    public List<User> exportWhoLikedMe(@PathVariable Long id) {
        return userService.exportWhoLikedMeList(id);
    }

    @GetMapping("users/sympathy/{id}")
    public List<User> exportSympathy(@PathVariable Long id) {
        return userService.exportSympathyList(id);
    }

    @GetMapping("/users/filtredListToSearch/{id}")
    public List<User> getAllUsersToSearch(@PathVariable Long id) {
        return userService.getAllUsersToSearchList(id);
    }


}
