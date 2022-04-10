package ru.liga.oldrussiantinderbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.oldrussiantinderbot.model.User;


public interface UserRepository extends JpaRepository<User, Long> {
}
