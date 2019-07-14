package com.womencancode.rbac.controller;

import com.womencancode.rbac.exception.ServiceException;
import com.womencancode.rbac.mock.RoleData;
import com.womencancode.rbac.model.Role;
import com.womencancode.rbac.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(RoleController.class)
@AutoConfigureMockMvc
public class RoleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RoleService service;

    @Test
    public void getAll() throws Exception {
        List<Role> roles = RoleData.getRolesMock();
        Mockito.when(service.findAll()).thenReturn(roles);

        mvc.perform(MockMvcRequestBuilders.get("/role")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(roles.get(0).getId())))
                .andExpect(jsonPath("$[0].name", is(roles.get(0).getName())))
                .andExpect(jsonPath("$[1].id", is(roles.get(1).getId())))
                .andExpect(jsonPath("$[1].name", is(roles.get(1).getName())))
                .andExpect(jsonPath("$[2].id", is(roles.get(2).getId())))
                .andExpect(jsonPath("$[2].name", is(roles.get(2).getName())))
                .andReturn();

    }

    @Test
    public void getById() throws Exception {
        String id = "id";
        Role role = RoleData.getRoleMock(id);
        Mockito.when(service.findById(eq(id))).thenReturn(role);

        mvc.perform(MockMvcRequestBuilders.get("/role/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(role.getId())))
                .andExpect(jsonPath("$.name", is(role.getName())))
                .andReturn();
    }
}