package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import java.util.Collection;

public interface MealService {

    MealWithExceed save(MealWithExceed meal);

    void delete(int id) throws NotFoundException;

    MealWithExceed get(int id) throws NotFoundException;

    Collection<MealWithExceed> getAll();

    Collection<MealWithExceed> getForUser(int userId);

    void update(MealWithExceed meal);

}