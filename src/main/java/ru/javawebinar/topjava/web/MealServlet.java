package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    protected List<MealTo> mealsTo;

    public MealServlet() {
        super();
        mealsTo = MealsUtil.composeMealsTo(MealsUtil.initMeals(), 2000);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("doGet()");
        log.debug(mealsTo.size() + " meals");
        request.setAttribute("meals", mealsTo);
        request.getRequestDispatcher("meals.jsp").forward(request, response);
    }

}
