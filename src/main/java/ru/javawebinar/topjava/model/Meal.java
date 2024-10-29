package ru.javawebinar.topjava.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@NamedQueries({
        @NamedQuery(name = Meal.ALL_SORTED, query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user WHERE m.user.id=:userId ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.BETWEEN_HALF_OPEN, query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user WHERE m.user.id=:userId " +
                "and m.dateTime >= :startDateTime and m.dateTime < :endDateTime ORDER BY m.dateTime DESC"),
        @NamedQuery(name = Meal.BY_ID, query = "SELECT m FROM Meal m LEFT JOIN FETCH m.user WHERE m.id=:id and m.user.id=:userId"),
        @NamedQuery(name = Meal.DELETE, query = "DELETE FROM Meal m WHERE m.id=:id and m.user.id=:userId"),
        @NamedQuery(name = Meal.UPDATE, query =
                "UPDATE Meal m SET m.description=:description, m.calories=:calories, m.dateTime=:date_time WHERE m.id=:id and m.user.id=:userId"),
})
@Entity
@Table(name = "meal", uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "date_time"}, name = "id_date_time")})
public class Meal extends AbstractBaseEntity {

    public static final String ALL_SORTED = "Meal.getAllSorted";
    public static final String BETWEEN_HALF_OPEN = "Meal.getBetweenHalfOpen";
    public static final String BY_ID = "Meal.getById";
    public static final String DELETE = "Meal.delete";
    public static final String UPDATE = "Meal.update";

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotBlank
    @Size(max = 128)
    private String description;

    @Column(name = "calories", nullable = false)
    @Range(min = 0, max = 10000)
    private int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Meal() {
    }

    public Meal(LocalDateTime dateTime, String description, int calories) {
        this(null, dateTime, description, calories);
    }

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
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

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
