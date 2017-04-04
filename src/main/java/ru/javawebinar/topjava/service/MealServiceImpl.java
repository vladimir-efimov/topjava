package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.InMemoryMealRepositoryImpl;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;

public class MealServiceImpl implements MealService {

    private MealRepository repository = new InMemoryMealRepositoryImpl();
    private UserRepository userRepository = new InMemoryUserRepositoryImpl();
    private Collection<MealWithExceed> meals = null;
    private boolean firstCall = true;

    public MealWithExceed save(MealWithExceed meal) {
        int mealId = meal.getId();
        Meal newMeal = new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories(), mealId);
        repository.save(newMeal);

        //return meal with updated property of exceeded
        //todo: there should be bug with passing default calories per day
        //maybe need to redesign system (user can have different allowed calories per day)
        meals = MealsUtil.getWithExceeded(repository.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY, 0);
        return meals.stream().filter(m -> m.getId() == mealId).findFirst().get();
    }

    public void delete(int id) throws NotFoundException {
        if(!repository.delete(id)) {
            throw new NotFoundException("Meal with id " + id + " is not found");
        }
        meals = MealsUtil.getWithExceeded(repository.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY, 0);
    }

    public MealWithExceed get(int id) throws NotFoundException {
        MealWithExceed meal = meals.stream().filter(m -> m.getId() == id).findFirst().get();
        if(meal == null) {
            throw new NotFoundException("Meal with id " + id + " is not found");
        }
        return meal;
    }

    public Collection<MealWithExceed> getAll() {
        if(firstCall) {
            meals = MealsUtil.getWithExceeded(repository.getAll(), MealsUtil.DEFAULT_CALORIES_PER_DAY, 0);
            firstCall = false;
        }
        return meals;
    }

    public Collection<MealWithExceed> getForUser(int userId) {
        User user = userRepository.get(userId);
        return MealsUtil.getWithExceeded(repository.getForUser(userId),user.getCaloriesPerDay(),userId);
    }

    public void update(MealWithExceed meal) {
        save(meal);
    }

}