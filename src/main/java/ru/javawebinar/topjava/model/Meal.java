package ru.javawebinar.topjava.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Meal extends AbstractNamedEntity {
    private final LocalDateTime dateTime;
    private final int calories;
    private final int userId;

    public Meal(int userId, LocalDateTime dateTime, String description, int calories) {
        this(null, userId, dateTime, description, calories);
    }

    public Meal(Integer id, int userId, LocalDateTime dateTime, String description, int calories) {
        super(id, description);
        this.userId = userId;
        this.dateTime = dateTime;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return name;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public int getUserId() { return userId; }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + name + '\'' +
                ", calories=" + calories +
                '}';
    }
}
