package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> filteredMeal = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);

        System.out.println("User meals with exceeded:");
        for(UserMealWithExceed meal : filteredMeal) {
            System.out.println(meal.getDateTime().toLocalDate() +
                    " : " + meal.getDescription() + " : " + meal.getExceed());
        }
    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        if(mealList == null) return null;
        List<UserMealWithExceed> filteredMeals = new ArrayList();
        Map<LocalDate,Integer> caloriesByDays = new HashMap();

        for(UserMeal meal : mealList) {
            LocalDate date = meal.getDateTime().toLocalDate();
            Integer calories = 0;
            if(caloriesByDays.containsKey(date)) calories = caloriesByDays.get(date);
            calories += meal.getCalories();
            caloriesByDays.put(date, calories);
        }

        for(UserMeal meal : mealList) {
            LocalTime mealTime =  meal.getDateTime().toLocalTime();
            if(mealTime.isBefore(startTime) || mealTime.isAfter(endTime)) continue;
            boolean exceed = caloriesByDays.get(meal.getDateTime().toLocalDate()) > caloriesPerDay;
            UserMealWithExceed exceedMeal = new UserMealWithExceed(meal.getDateTime(),
                    meal.getDescription(), meal.getCalories(),exceed);
            filteredMeals.add(exceedMeal);
        }
        return filteredMeals;
    }
}
