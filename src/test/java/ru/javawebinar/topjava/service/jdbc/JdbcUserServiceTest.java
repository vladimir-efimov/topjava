package ru.javawebinar.topjava.service.jdbc;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import static ru.javawebinar.topjava.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {

    @Override
    @Ignore
    @Test
    public void createWithException() throws Exception {
        //todo: do not override base test when use of Bean Validation API will be implemented in JDBC repository
    }
}