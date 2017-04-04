package ru.javawebinar.topjava.web.meal;

import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.util.UserUtil;
import java.util.Collection;

public class MealRestController {

    private MealService service = new MealServiceImpl();
    private User AuthorizedUser = UserUtil.USERS.get(0);
    protected final Logger LOG = LoggerFactory.getLogger(getClass());

    public MealWithExceed get(int id) {
        LOG.info("get " + id);
        return service.get(id);
    }

    public Collection<MealWithExceed> getAll() {
        LOG.info("getAll");
        return service.getAll();
    }

    public Collection<MealWithExceed> getForUser() {
        LOG.info("getAll");
        return service.getForUser(AuthorizedUser.getId());
    }

    public MealWithExceed create(MealWithExceed meal) {
        LOG.info("create " + meal);
        //checkNew(user);
        return service.save(meal);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id);
    }

    public void update(MealWithExceed meal, int id) {
        LOG.info("update " + meal);
        //checkIdConsistent(user, id);
        service.update(meal);
    }

}