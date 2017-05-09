package ru.javawebinar.topjava.web;

import java.util.Objects;
import java.time.temporal.ChronoUnit;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.meal.MealRestController;
import javax.servlet.http.HttpServletRequest;

@Controller
public class MealController extends MealRestController{

    @Autowired
    public MealController(MealService service) {
        super(service);
    }

    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String meals(Model model, HttpServletRequest request) {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "delete":
                int id = getId(request);
                delete(id);
                //todo: double check if this way of updating of table with meals is OK
                model.addAttribute("meals", getAll());
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        get(getId(request));
                model.addAttribute("meal", meal);
                return "meal";
            case "all":
            default:
                model.addAttribute("meals", getAll());
                break;
        }
        return "meals";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String updateMeals(HttpServletRequest request) {
        //int userId = Integer.valueOf(request.getParameter("userId"));
        //AuthorizedUser.setId(userId);
        return "";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.valueOf(paramId);
    }

}
