package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserCaloriesPerDay;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal get(int id) {
        int userId = authUserId();
        log.info("user {} get {}", userId, id);
        return service.get(id, userId);
    }

    public List<Meal> getAll() {
        int userId = authUserId();
        log.info("user {} getAll {}", userId);
        return service.findByUserId(userId);
    }

    public List<MealTo> getAllWithExceeded() {
        int userId = authUserId();
        log.info("user {} getAllWithExceeded {}", userId);
        return MealsUtil.getTos(service.findByUserId(userId), authUserCaloriesPerDay());
    }

    public Meal create(Meal meal) {
        int userId = authUserId();
        log.info("user {} create {}", userId, meal);
        return service.create(meal, userId);
    }

    public void delete(int id) {
        int userId = authUserId();
        log.info("user {} delete {}", userId, id);
        service.delete(id, userId);
    }

    public void update(Meal meal) {
        int userId = authUserId();
        log.info("user {} update {}", userId, meal);
        service.update(meal, userId);
    }

}