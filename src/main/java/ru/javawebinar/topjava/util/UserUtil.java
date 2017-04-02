package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by Vova on 02.04.2017.
 */
public class UserUtil {

    //todo: find out how to pass not NULL roles
    // new User(1, "Vladimir", "vladimir@gmail.com", "12345", 2500,true,  Role.USER_ROLE)
    public static final List<User> USERS = Arrays.asList(
            new User(1, "Vladimir", "vladimir@gmail.com", "12345", 2500,true,  null)
    );
}
