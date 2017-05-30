package ru.javawebinar.topjava.to;

import org.springframework.format.annotation.DateTimeFormat;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

/**
 * Created by Vova on 30.05.2017.
 */
import java.time.LocalDateTime;

public class MealTo {

    private Integer id;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateTime;

    @NotBlank(message = "Описание не может быть пусто")
    private String description;

    @Range(min = 1, max = 3000, message = "Указано неверное число калорий (должно быть от 1 до 3000)")
    private Integer calories;

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

    public Integer getCalories() {
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
