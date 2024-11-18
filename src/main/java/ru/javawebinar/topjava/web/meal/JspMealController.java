package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {

    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping("")
    public String getMeals(Model model, HttpServletRequest request) {

        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        List<MealTo> meals;
        if (startDate != null || startTime != null || endDate != null || endTime != null) {
            meals = getBetween(startDate, startTime, endDate, endTime);
        } else {
            meals = getAll();
        }
        model.addAttribute("meals", meals);
        return "meals";
    }

    @GetMapping("/delete")
    public String deleteMeal(@RequestParam int id) {
        super.delete(id);
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
        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.warn("add: failed to set encoding UTF-8");
        }
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.hasLength(request.getParameter("id"))) {
            int id = Integer.parseInt(request.getParameter("id"));
            meal.setId(id);
            this.update(meal, id);
        } else {
            this.create(meal);
        }
        return "redirect:/meals";
    }
}
