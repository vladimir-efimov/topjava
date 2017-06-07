package ru.javawebinar.topjava.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final UserService service;

    public AbstractUserController(UserService service) {
        this.service = service;
    }

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        try {
            return service.save(user);
        }
        catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("User with this email already present in application");
        }
        catch (Exception ex) {
            throw ex;
        }
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        checkIdConsistent(user, id);
        service.update(user);
    }

    public void update(UserTo userTo, int id) {
        log.info("update {} with id={}", userTo, id);
        checkIdConsistent(userTo, id);
        try {
            service.update(userTo);
        }
        catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException("User with this email already present in application");
        }
        catch (Exception ex) {
            throw ex;
        }
    }

    public User getByMail(String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }

    public void enable(int id, boolean enabled) {
        log.info((enabled ? "enable " : "disable ") + id);
        service.enable(id, enabled);
    }
}