package ru.liga.oldrussiantinderbot.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.oldrussiantinderbot.utils.TextImageMaker;

import java.io.File;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/users/image")
public class ImageController {

    @PostMapping("/")
    public File getTextImageMaker(@RequestBody String text){
        return TextImageMaker.getImageFile(text);
    }
}
