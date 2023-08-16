package com.twoday.wms.warehouse.unit.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twoday.wms.dto.UserDto;
import com.twoday.wms.warehouse.unit.configs.TestAuthConfig;
import com.twoday.wms.warehouse.user.UserController;
import com.twoday.wms.warehouse.user.interfaces.UserService;

import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;

@WebMvcTest(UserController.class)
@Import(TestAuthConfig.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    private ListAppender<ILoggingEvent> listAppender;

    @BeforeEach
    public void setUp() {
        Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);
        listAppender = new ListAppender<>();
        listAppender.start();
        logger.addAppender(listAppender);
    }

    @AfterEach
    public void tearDown() {
        listAppender.stop();
        listAppender.list.clear();
    }

    @Test
    public void testRegister() throws Exception {
        UserDto userDto = new UserDto(1L, "Sample Username", "Sample Password");
        Mockito.when(userService.register(Mockito.any(UserDto.class))).thenReturn(userDto);
        userDto.setPassword("encodedPassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userDto)))
                .andExpect(status().isCreated())
                .andExpect(content().json(objectMapper.writeValueAsString(userDto)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.username", is("Sample Username")))
                .andExpect(jsonPath("$.password", is("encodedPassword")));
        List<ILoggingEvent> logsList = listAppender.list;
        assertEquals(2, logsList.size());
        assertEquals("Attempting to register user with username: Sample Username",
                logsList.get(0).getFormattedMessage());
        assertEquals("Successfully registered user with username: Sample Username",
                logsList.get(1).getFormattedMessage());
        Mockito.verify(userService, Mockito.times(1)).register(Mockito.any(UserDto.class));
    }

}