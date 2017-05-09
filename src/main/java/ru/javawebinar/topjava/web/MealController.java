package ru.javawebinar.topjava.web;

import java.util.Objects;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MealController extends MealRestController{

    @Autowired
    public MealController(MealService service) {
        super(service);
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String doGet(Model model, HttpServletRequest request) {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                return deleteMeal(model, request);
            case "create":
            case "update":
                return editMeal(model, request);
            case "all":
            default:
                return getMeals(model);
        }
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String doPost(Model model, HttpServletRequest request) {
        try {
            request.setCharacterEncoding("UTF-8");
        }catch(Exception ex) {}
        String action = request.getParameter("action");
        if (action == null) {
            return updateMeal(model, request);
        } else if ("filter".equals(action)) {
            return filterMeals(model, request);
        }
        return "meals";
    }

    private String getMeals(Model model)
    {
        model.addAttribute("meals", getAll());
        return "meals";
    }

    private String deleteMeal(Model model, HttpServletRequest request) {
        int id = getId(request);
        delete(id);
        model.addAttribute("meals", getAll());
        return "meals";
    }

    private String editMeal(Model model, HttpServletRequest request) {
        String action = request.getParameter("action");
        final Meal meal = "create".equals(action) ?
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                get(getId(request));
        model.addAttribute("meal", meal);
        return "meal";
    }

    private String filterMeals(Model model, HttpServletRequest request) {
        LocalDate startDate = DateTimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = DateTimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = DateTimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = DateTimeUtil.parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    private String updateMeal(Model model, HttpServletRequest request) {
        final Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        if (request.getParameter("id").isEmpty()) {
            create(meal);
        } else {
            update(meal, getId(request));
        }
        return "redirect:meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

}
