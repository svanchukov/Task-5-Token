package ru.svanchukov.Token_5_Task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.svanchukov.Token_5_Task.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
