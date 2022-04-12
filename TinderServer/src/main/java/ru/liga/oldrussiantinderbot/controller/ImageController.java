package ru.liga.oldrussiantinderbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.oldrussiantinderbot.service.UserService;
import ru.liga.oldrussiantinderbot.utils.TextImageMaker;

import java.io.File;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users/image")
public class ImageController {

    private UserService userService;

    @Autowired
    public ImageController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public File getTextImageMaker(@RequestBody Long userID) {

        return TextImageMaker.getImageFile(userService.getUser(userID));
    }
}
