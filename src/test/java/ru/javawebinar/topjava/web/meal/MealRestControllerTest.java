package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.service.MealService;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

import org.junit.Test;
import org.springframework.http.MediaType;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by Vova on 16.05.2017.
 */
public class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL + '/';

    @Autowired
    private MealService mealService;

    @Test
    public void testGet() throws Exception {

        mockMvc.perform(get(REST_URL + MEAL1.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1.getId()))
                .andDo(print())
                .andExpect(status().isOk());
        List<Meal> mealsExpected = Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
        MATCHER.assertCollectionEquals(mealsExpected, mealService.getAll(USER_ID));
    }

    @Test
    public void testGetAll() throws Exception {
        //todo: probably need to compare with MealsWithExceed, need another matcher
        List<Meal> mealsExpected = Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON)))
                .andExpect(MATCHER.contentListMatcher(mealsExpected));
        //MATCHER.assertCollectionEquals(mealsExpected, mealService.getAll(USER_ID));
    }


    @Test
    public void testUpdate() throws Exception {
        Meal updated = new Meal(MEAL1.getId(),MEAL1.getDateTime(),MEAL1.getDescription(),MEAL1.getCalories());
        updated.setCalories(999);
        mockMvc.perform(put(REST_URL + MEAL1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, mealService.get(MEAL1.getId(), USER_ID));
    }

    @Test
    public void testGetBetween() throws Exception {
        List<Meal> mealsExpected = Arrays.asList(MEAL6, MEAL5, MEAL3, MEAL2);
        mockMvc.perform(get(REST_URL + "filter?startTime=11:00"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentListMatcher(mealsExpected));
    }


}
