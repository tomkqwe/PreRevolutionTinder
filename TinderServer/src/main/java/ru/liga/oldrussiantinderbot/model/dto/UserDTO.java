package ru.liga.oldrussiantinderbot.model.dto;

import lombok.Data;
import ru.liga.oldrussiantinderbot.model.SexType;
@Data
public class UserDTO {
    private Long id;
    private String name;
    private SexType sex;
    private Integer age;
    private String description;
    private SexType partnerSex;
}
