package ru.javawebinar.topjava.to;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import ru.javawebinar.topjava.util.UserUtil;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserTo extends BaseTo implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotBlank
    @SafeHtml
    private String name;

    @Email
    @NotBlank
    @SafeHtml
    private String email;

    @Length(min = 5, max = 32)
    @SafeHtml
    private String password;

    @Range(min = 10, max = 10000)
    @NotNull
    private Integer caloriesPerDay = UserUtil.DEFAULT_CALORIES_PER_DAY;

    public UserTo() {
    }

    public UserTo(Integer id, String name, String email, String password, int caloriesPerDay) {
        super(id);
        this.name = name;
        this.email = email;
        this.password = password;
        this.caloriesPerDay = caloriesPerDay;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCaloriesPerDay(Integer caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    public Integer getCaloriesPerDay() {
        return caloriesPerDay;
    }

    @Override
    public String toString() {
        return "UserTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", caloriesPerDay='" + caloriesPerDay + '\'' +
                '}';
    }
}
