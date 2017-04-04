package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collection;

public class MealRestController {
    private MealService service;

    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    public Meal get(int id) {
        LOG.info("get " + id);
        return service.get(id);
    }

    public Collection<Meal> getAll() {
        LOG.info("getAll");
        return service.getAll();
    }

    public Collection<Meal> getForUser(int userId) {
        LOG.info("getAll");
        return service.getForUser(userId);
    }

    public Meal create(Meal user) {
        LOG.info("create " + user);
        //checkNew(user);
        return service.save(user);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id);
    }

    public void update(Meal meal, int id) {
        LOG.info("update " + meal);
        //checkIdConsistent(user, id);
        service.update(meal);
    }

}