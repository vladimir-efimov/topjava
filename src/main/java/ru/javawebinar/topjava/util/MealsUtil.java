package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class MealsUtil {
    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500, 1),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000,1),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500,1),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000,1),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500,1),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510,1),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 9, 0), "Завтрак", 400,2),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1200,2),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510,2)
    );

    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static void main(String[] args) {
        List<MealWithExceed> mealsWithExceeded = getFilteredWithExceeded(MEALS, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000, 0);
        mealsWithExceeded.forEach(System.out::println);

        System.out.println(getFilteredWithExceededByCycle(MEALS, LocalTime.of(7, 0), LocalTime.of(12, 0), DEFAULT_CALORIES_PER_DAY));
    }

    public static List<MealWithExceed> getWithExceeded(Collection<Meal> meals, int caloriesPerDay) {
        return getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay, 0);
    }
    public static List<MealWithExceed> getWithExceeded(Collection<Meal> meals, int caloriesPerDay, int userId) {
        return getFilteredWithExceeded(meals, LocalTime.MIN, LocalTime.MAX, caloriesPerDay, userId);
    }

    public static List<MealWithExceed> getFilteredWithExceeded(Collection<Meal> meals, LocalTime startTime,
                                                               LocalTime endTime, int caloriesPerDay, int userId) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );
        if( userId == 0)
            return meals.stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime))
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());

        return meals.stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(), startTime, endTime) && meal.getUserId() == userId)
                .map(meal -> createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<MealWithExceed> getFilteredWithExceededByCycle(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumByDate = new HashMap<>();
        meals.forEach(meal -> caloriesSumByDate.merge(meal.getDate(), meal.getCalories(), Integer::sum));

        final List<MealWithExceed> mealsWithExceeded = new ArrayList<>();
        meals.forEach(meal -> {
            if (DateTimeUtil.isBetween(meal.getTime(), startTime, endTime)) {
                mealsWithExceeded.add(createWithExceed(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay));
            }
        });
        return mealsWithExceeded;
    }

    public static MealWithExceed createWithExceed(Meal meal, boolean exceeded) {
        return new MealWithExceed(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), exceeded);
    }
}