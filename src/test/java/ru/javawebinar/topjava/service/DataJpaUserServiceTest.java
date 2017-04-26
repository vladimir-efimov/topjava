package ru.javawebinar.topjava.service;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

/**
 * Created by Vova on 26.04.2017.
 */

@ActiveProfiles({Profiles.ACTIVE_DB,"datajpa"})
public class DataJpaUserServiceTest extends UserServiceTest {
}
