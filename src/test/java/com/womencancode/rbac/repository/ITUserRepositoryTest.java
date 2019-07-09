package com.womencancode.rbac.repository;

import com.womencancode.rbac.model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
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
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ITUserRepositoryTest {

    private static final String USER_1 = "User 1";
    private static final String USERNAME_1 = "user1";
    private static final String EMAIL_1 = "email@test.com";
    
    private static final String NEW_USER = "New User";

    @Autowired
    MongoOperations mongoOperations;

    @Autowired
    UserRepository repository;

    private List<String> idsToDelete = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        if (!mongoOperations.collectionExists(User.class)) {
            mongoOperations.createCollection(User.class);
        }
    }

    @After
    public void tearDown() {
        mongoOperations.remove(Query.query(Criteria.where("id").in(idsToDelete)), User.class);
        idsToDelete.clear();
    }

    @Test
    public void whenInsertingUser_thenUserIsInserted() {
        //given
        User user = createUser(USER_1, USERNAME_1, EMAIL_1);

        // when
        String id = repository.save(user).getId();
        idsToDelete.add(id);

        //then
        User expectedUser = mongoOperations.findById(id, User.class);
        assertThat(user, is(expectedUser));
    }

    @Test
    public void givenUserExists_whenSavingUser_thenUserIsUpdated() {
        //given
        User user = createUser(USER_1, USERNAME_1, EMAIL_1);
        String id = mongoOperations.insert(user).getId();
        idsToDelete.add(id);

        // when
        user = repository.findByNameIgnoreCase("user 1").get();
        user.setName(NEW_USER);
        repository.save(user);

        //then
        User savedUser = mongoOperations.findById(id, User.class);
        assertThat(NEW_USER, is(savedUser.getName()));
    }

    @Test
    public void givenUserExists_whenDeletingUser_thenUserIsRemoved() {
        //given
        User user = createUser(USER_1, USERNAME_1, EMAIL_1);
        String id = mongoOperations.insert(user).getId();

        // when
        repository.deleteById(id);

        //then
        User dbUser = mongoOperations.findById(id, User.class);
        assertNull(dbUser);
    }

    private User createUser(String name, String username, String email) {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setEmail(email);
        return user;
    }
}