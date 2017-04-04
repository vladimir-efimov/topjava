package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

public class MealServiceImpl implements MealService {

    private MealRepository repository;

    public Meal save(Meal meal) {
        return repository.save(meal);
    }

    public void delete(int id) throws NotFoundException {
        if(!repository.delete(id)) {
            throw new NotFoundException("Meal with id " + id + " is not found");
        }
    }

    public Meal get(int id) throws NotFoundException {
        Meal meal = repository.get(id);
        if(meal == null) {
            throw new NotFoundException("Meal with id " + id + " is not found");
        }
        return meal;
    }

    public Collection<Meal> getAll() {
        return repository.getAll();
    }

    public Collection<Meal> getForUser(int userId) {
        return repository.getForUser(userId);
    }

    public void update(Meal meal) {
        repository.save(meal);
    }

}