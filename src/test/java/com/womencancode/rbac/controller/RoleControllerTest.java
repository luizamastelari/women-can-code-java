package com.womencancode.rbac.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.womencancode.rbac.exception.InvalidFieldException;
import com.womencancode.rbac.mock.RoleData;
import com.womencancode.rbac.model.Role;
import com.womencancode.rbac.service.RoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import static org.mockito.Mockito.when;
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
    public void insertRole() throws Exception {
        String id = "id";
        Role role = RoleData.getRoleMock();
        Role returnedRole = RoleData.getRoleMock(id);
        when(service.insertRole(eq(role))).thenReturn(returnedRole);

        mvc.perform(MockMvcRequestBuilders.post("/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(role)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(id)))
                .andExpect(jsonPath("$.name", is(returnedRole.getName())))
                .andReturn();

    }

    @Test
    public void insertRoleWithId() throws Exception {
        String id = "id";
        Role role = RoleData.getRoleMock(id);
        when(service.insertRole(eq(role))).thenThrow(InvalidFieldException.class);

        mvc.perform(MockMvcRequestBuilders.post("/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(role)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void getAll() throws Exception {
        List<Role> roles = RoleData.getRolesMock();
        when(service.findAll()).thenReturn(roles);

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
        when(service.findById(eq(id))).thenReturn(role);

        mvc.perform(MockMvcRequestBuilders.get("/role/" + id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(role.getId())))
                .andExpect(jsonPath("$.name", is(role.getName())))
                .andReturn();
    }
}