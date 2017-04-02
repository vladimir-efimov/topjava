package ru.javawebinar.topjava.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.UserUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Vova on 02.04.2017.
 */
public class InMemoryUserRepositoryImpl implements UserRepository{

    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    {
        UserUtil.USERS.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        //todo: try use stream
        if(repository.containsKey(id))
        {
            LOG.info("delete " + id);
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);
        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public Collection<User> getAll() {
        LOG.info("getAll");
        return repository.values();
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        //todo: use lambda expression
        //return repository.values().stream().findFirst().filter(user -> email==email);
        for(User user:repository.values()) {
            if(user.getEmail() == email) return user;
        }
        return null;
    }
}
