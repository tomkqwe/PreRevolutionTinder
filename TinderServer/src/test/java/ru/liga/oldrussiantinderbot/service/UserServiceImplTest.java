package ru.liga.oldrussiantinderbot.service;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.liga.oldrussiantinderbot.model.SexType;
import ru.liga.oldrussiantinderbot.model.User;
import ru.liga.oldrussiantinderbot.repository.UserRepository;
import ru.liga.oldrussiantinderbot.utils.Translator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {
    private UserRepository userRepository = mock(UserRepository.class);
    private Translator translator = mock(Translator.class);
    private UserServiceImpl service = new UserServiceImpl(userRepository,translator);
    private User user = new User();

    @Test
    void check_Get_All_Users() {
       when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        List<User> allUsers = service.getAllUsers();
        Assertions.assertEquals(1,allUsers.size());
    }
    @Test
    void check_Get_All_Users_Not_Null() {
       when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        List<User> allUsers = service.getAllUsers();
        Assertions.assertNotNull(allUsers);
    }

    @Test
    void checkUser_artem() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        List<User> allUsers = service.getAllUsers();
        user.setName("Artem");
        Assert.assertEquals("Artem",allUsers.get(0).getName());
    }
    @Test
    void checkUser_age() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        List<User> allUsers = service.getAllUsers();
        user.setAge(25);
        Assertions.assertEquals(25,allUsers.get(0).getAge());
    }
    @Test
    void checkUser_description() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        List<User> allUsers = service.getAllUsers();
        user.setDescription("bla-bla-bla");
        Assertions.assertEquals("bla-bla-bla",allUsers.get(0).getDescription());
    }
    @Test
    void checkUser_sex() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        List<User> allUsers = service.getAllUsers();
        user.setSex(SexType.MALE);
        SexType sex = allUsers.get(0).getSex();
        Assertions.assertEquals("Сударь", sex.getName());
    }
}