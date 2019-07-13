package com.womencancode.rbac.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.womencancode.rbac.exception.DuplicatedKeyException;
import com.womencancode.rbac.exception.EntityNotFoundException;
import com.womencancode.rbac.mock.UserData;
import com.womencancode.rbac.model.User;
import com.womencancode.rbac.service.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    private UserService service;

    @Test
    public void insertUser() throws Exception {
        String userId = "Id";
        User user = UserData.getUserMock();
        Mockito.when(service.insertUser(eq(user))).thenReturn(user.withId(userId));

        mvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userId)))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andReturn();
    }

    @Test
    public void insertExistentUser() throws Exception {
        User user = UserData.getUserMock();
        Mockito.when(service.insertUser(eq(user))).thenThrow(DuplicatedKeyException.class);

        mvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isConflict())
                .andReturn();
    }

    @Test
    public void insertUsers() throws Exception {
        String userId = "Id";
        List<User> users = UserData.getUserListMock();
        List<User> returnedUsers = users.stream().map(user -> user.withId(userId)).collect(Collectors.toList());
        Mockito.when(service.insertUser(eq(users))).thenReturn(returnedUsers);

        mvc.perform(MockMvcRequestBuilders.post("/user/bulk")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(users)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(returnedUsers.get(0).getId())))
                .andExpect(jsonPath("$[0].name", is(returnedUsers.get(0).getName())))
                .andExpect(jsonPath("$[0].lastName", is(returnedUsers.get(0).getLastName())))
                .andExpect(jsonPath("$[0].email", is(returnedUsers.get(0).getEmail())))
                .andExpect(jsonPath("$[0].username", is(returnedUsers.get(0).getUsername())))
                .andExpect(jsonPath("$[1].id", is(returnedUsers.get(1).getId())))
                .andExpect(jsonPath("$[1].name", is(returnedUsers.get(1).getName())))
                .andExpect(jsonPath("$[1].lastName", is(returnedUsers.get(1).getLastName())))
                .andExpect(jsonPath("$[1].email", is(returnedUsers.get(1).getEmail())))
                .andExpect(jsonPath("$[1].username", is(returnedUsers.get(1).getUsername())))
                .andExpect(jsonPath("$[2].id", is(returnedUsers.get(2).getId())))
                .andExpect(jsonPath("$[2].name", is(returnedUsers.get(2).getName())))
                .andExpect(jsonPath("$[2].lastName", is(returnedUsers.get(2).getLastName())))
                .andExpect(jsonPath("$[2].email", is(returnedUsers.get(2).getEmail())))
                .andExpect(jsonPath("$[2].username", is(returnedUsers.get(2).getUsername())))
                .andReturn();
    }

    @Test
    public void updateUser() throws Exception {
        String userId = "Id";
        User user = UserData.getUserMock().withId(userId);
        Mockito.when(service.updateUser(eq(user))).thenReturn(user);

        mvc.perform(MockMvcRequestBuilders.put("/user/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userId)))
                .andExpect(jsonPath("$.name", is(user.getName())))
                .andExpect(jsonPath("$.lastName", is(user.getLastName())))
                .andExpect(jsonPath("$.email", is(user.getEmail())))
                .andExpect(jsonPath("$.username", is(user.getUsername())))
                .andReturn();
    }

    @Test
    public void updateNonExistentUser() throws Exception {
        String userId = "Id";
        User user = UserData.getUserMock().withId(userId);
        Mockito.when(service.updateUser(eq(user))).thenThrow(EntityNotFoundException.class);

        mvc.perform(MockMvcRequestBuilders.put("/user/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    public void updateThrowsRuntimeException() throws Exception {
        String userId = "Id";
        User user = UserData.getUserMock().withId(userId);
        Mockito.when(service.updateUser(eq(user))).thenThrow(RuntimeException.class);

        mvc.perform(MockMvcRequestBuilders.put("/user/" + userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isInternalServerError())
                .andReturn();
    }

    @Test
    public void getUsersWithDefaultPaging() throws Exception {
        List<User> users = UserData.getUserListMock("id");
        Page page = new PageImpl(new ArrayList(), PageRequest.of(0, 1), 0);

        Mockito.when(service.findAll(any())).thenReturn(page);

        mvc.perform(MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(users)))
                .andExpect(status().isOk())
                .andReturn();

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        Mockito.verify(service, times(1)).findAll(pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();
        Assert.assertThat(pageable.getPageSize(), is(20));
        Assert.assertThat(pageable.getPageNumber(), is(0));
    }

    @Test
    public void getUsersWithCustomPaging() throws Exception {
        List<User> users = UserData.getUserListMock("id");
        Page page = new PageImpl(new ArrayList(), PageRequest.of(1, 2), 0);

        Mockito.when(service.findAll(any())).thenReturn(page);

        mvc.perform(MockMvcRequestBuilders.get("/user?page=1&size=2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(users)))
                .andExpect(status().isOk())
                .andReturn();

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        Mockito.verify(service, times(1)).findAll(pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();
        Assert.assertThat(pageable.getPageSize(), is(2));
        Assert.assertThat(pageable.getPageNumber(), is(1));
    }
}