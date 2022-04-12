package ru.liga.oldrussiantinderbot.model.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.liga.oldrussiantinderbot.model.User;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO fromUserToUserDTO(User user);

    User fromUserDTOToUser(UserDTO userDTO);
}
