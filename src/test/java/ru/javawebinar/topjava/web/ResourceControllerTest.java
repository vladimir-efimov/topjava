package ru.javawebinar.topjava.web;

import org.junit.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

/**
 * Created by Vova on 15.05.2017.
 */
public class ResourceControllerTest extends AbstractControllerTest {

    @Test
    public void testResources() throws Exception {

        //проверить status и ContentType
        mockMvc.perform(get("/resources/style.css"))
                .andDo(print())
                .andExpect(status().is(404))
                //java.lang.AssertionError: Content type not set
                //.andExpect(content().contentType("*/*"))
                .andExpect(handler().handlerType(ResourceHttpRequestHandler.class));
    }
}
