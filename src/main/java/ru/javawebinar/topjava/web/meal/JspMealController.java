package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {

    private static final Logger log = LoggerFactory.getLogger(JspMealController.class);

    private final MealService service;

    public JspMealController(MealService service) {
        this.service = service;
    }

    @GetMapping("")
    public String getMeals(Model model) {
        int userId = SecurityUtil.authUserId();
        log.info("getMeals for user {}", userId);
        var meals = MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay());
        model.addAttribute("meals", meals);
        return "meals";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete meal {} for user {}", id, userId);
        service.delete(id, userId);
        return "redirect:/meals";
    }

    @GetMapping("/create")
    public String create(Model model) {
        int userId = SecurityUtil.authUserId();
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        model.addAttribute("action", "create");
        log.info("create {} for user {}", meal, userId);
        return "mealForm";
    }

    @GetMapping("/update")
    public String update(Model model, @RequestParam int id) {
        int userId = SecurityUtil.authUserId();
        Meal meal = service.get(id, userId);
        assureIdConsistent(meal, id);
        log.info("update {} for user {}", meal, userId);
        model.addAttribute("meal", meal);
        model.addAttribute("action", "edit");
        return "mealForm";
    }

    @PostMapping("/add")
    public String addMeal(HttpServletRequest request) {
        int userId = SecurityUtil.authUserId();
        log.info("addMeal for user {}", userId);

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.warn("addMeal: failed to set encoding UTF-8");
        }
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.hasLength(request.getParameter("id"))) {
            meal.setId(Integer.parseInt(request.getParameter("id")));
            service.update(meal, userId);
        } else {
            service.create(meal, userId);
        }
        return "redirect:/meals";
    }

    // below methods does not work properly

    /**
     * <ol>Filter separately
     * <li>by date</li>
     * <li>by time for every date</li>
     * </ol>
     */
    @GetMapping("/filter")
    public String getBetween(@Nullable LocalDate startDate, @Nullable LocalTime startTime,
                                   @Nullable LocalDate endDate, @Nullable LocalTime endTime) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);

        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        var meals = MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
        return "meals";
    }

}
