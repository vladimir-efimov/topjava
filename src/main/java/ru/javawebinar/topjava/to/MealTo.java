package ru.javawebinar.topjava.to;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * Created by Vova on 30.05.2017.
 */
import java.time.LocalDateTime;

public class MealTo {

    private Integer id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;

    private String description;

    private int calories;

    public MealTo() {
    }

    public MealTo( Integer id, LocalDateTime dateTime,
                   String description, int calories
                   ) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) { this.id = id;}


    public LocalDateTime getDateTime() {
        return dateTime;
    }
    public void setDateTime(LocalDateTime dateTime) {this.dateTime = dateTime;}

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {this.description = description;}

    public int getCalories() {
        return calories;
    }
    public void setCalories(Integer calories) {this.calories = calories;}

    @Override
    public String toString() {
        return "MealTo{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }

}
