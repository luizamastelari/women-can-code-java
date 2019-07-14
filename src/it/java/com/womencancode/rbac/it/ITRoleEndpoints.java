package com.womencancode.rbac.it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.womencancode.rbac.group.Integration;
import com.womencancode.rbac.mock.RoleData;
import com.womencancode.rbac.model.Role;
import com.womencancode.rbac.repository.RoleRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Category(Integration.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.yaml")
public class ITRoleEndpoints {

    @Autowired
    MockMvc mvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RoleRepository roleRepository;

    @After
    @Before
    public void setup() {
        roleRepository.deleteAll();
    }

    @Test
    public void givenAnEmptyDB_whenGettingAllRoles_thenAEmptyListIsReturned() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/role")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)))
                .andReturn();
    }

    @Test
    public void givenANonEmptyDB_whenGettingAllRoles_thenAListIsReturned() throws Exception {
        // given
        List<Role> roles = RoleData.getRolesMock();
        roleRepository.insert(roles);

        mvc.perform(MockMvcRequestBuilders.get("/role")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(roles.size())))
                .andReturn();
    }
}
