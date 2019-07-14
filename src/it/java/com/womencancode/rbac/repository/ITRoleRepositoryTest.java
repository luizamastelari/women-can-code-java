package com.womencancode.rbac.repository;

import com.womencancode.rbac.group.Integration;
import com.womencancode.rbac.model.Role;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Category(Integration.class)
public class ITRoleRepositoryTest {

    private static final String ROLE_1 = "Role 1";
    private static final String NEW_ROLE = "New Role";

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    RoleRepository repository;

    private List<String> idsToDelete = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        if (!mongoOperations.collectionExists(Role.class)) {
            mongoOperations.createCollection(Role.class);
        }
    }

    @After
    public void tearDown() {
        mongoOperations.remove(Query.query(Criteria.where("id").in(idsToDelete)), Role.class);
        idsToDelete.clear();
    }

    @Test
    public void whenInsertingRole_thenRoleIsInserted() {
        //given
        Role role = createRole(ROLE_1);

        // when
        String id = repository.save(role).getId();
        idsToDelete.add(id);

        //then
        Role expectedRole = mongoOperations.findById(id, Role.class);
        assertEquals(expectedRole.getName(), role.getName());
        assertEquals(expectedRole.getId(), role.getId());
    }

    @Test
    public void givenRoleExists_whenSavingRole_thenRoleIsUpdated() {
        //given
        Role role = createRole(ROLE_1);
        String id = mongoOperations.insert(role).getId();
        idsToDelete.add(id);

        // when
        role = repository.findByNameIgnoreCase("role 1").get();
        role.setName(NEW_ROLE);
        repository.save(role);

        //then
        Role savedRole = mongoOperations.findById(id, Role.class);
        assertThat(NEW_ROLE, is(savedRole.getName()));
    }

    @Test
    public void givenRoleExists_whenDeletingRole_thenRoleIsRemoved() {
        //given
        Role role = createRole(ROLE_1);
        String id = mongoOperations.insert(role).getId();

        // when
        repository.deleteById(id);

        //then
        Role dbRole = mongoOperations.findById(id, Role.class);
        assertNull(dbRole);
    }

    private Role createRole(String name) {
        Role role = Role.builder()
                .name(name)
                .build();
        return role;
    }
}