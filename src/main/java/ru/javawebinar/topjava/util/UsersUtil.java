package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {
    public static final List<User> users = Arrays.asList(
            new User(null, "Ivan Ivanov", "ivan.ivanov@mail.ru", "12345", Role.USER, Role.ADMIN),
            new User(null, "Petr Petrov", "petr.petrov@mail.ru", "12345", Role.USER)
    );
}
