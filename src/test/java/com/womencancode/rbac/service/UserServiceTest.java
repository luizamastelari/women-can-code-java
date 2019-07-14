package com.womencancode.rbac.service;

import com.womencancode.rbac.exception.DuplicatedKeyException;
import com.womencancode.rbac.exception.EntityNotFoundException;
import com.womencancode.rbac.exception.InvalidFieldException;
import com.womencancode.rbac.mock.UserData;
import com.womencancode.rbac.model.User;
import com.womencancode.rbac.repository.UserRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    private UserService service;

    @Before
    public void setup() {
        service = new UserService(repository);
    }

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void givenAnyUser_whenSavingUser_thenUserIsSavedAndReturned() throws Exception {

        // given
        User user = UserData.getUserMock();
        when(repository.findByUsername(eq(user.getUsername()))).thenReturn(Optional.empty());
        when(repository.insert(eq(user))).thenReturn(user);

        // when
        User returnedUser = service.insertUser(user);

        // then
        assertNotNull(returnedUser);
        assertEquals(user, returnedUser);
        verify(repository, times(1)).findByUsername(eq(user.getUsername()));
        verify(repository, times(1)).insert(eq(user));
    }

    @Test(expected = DuplicatedKeyException.class)
    public void givenAUserWithAnExistentUsername_whenInsertingUser_thenExceptionIsThrown() throws Exception {
        // given
        User user = UserData.getUserMock();
        when(repository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // when
        service.insertUser(user);

        // then
        expectedException.expect(DuplicatedKeyException.class);
        expectedException.expectMessage(String.format("Username %s already exist", user.getUsername()));
        verify(repository, times(1)).findByUsername(eq(user.getUsername()));
        verify(repository, times(0)).insert(eq(user));
    }

    @Test
    public void givenAUserList_whenSavingUsers_thenUsersAreSavedAndReturned() throws Exception {

        // given
        List<User> users = UserData.getUserListMock();
        when(repository.findByUsername(eq(users.get(0).getUsername()))).thenReturn(Optional.empty());
        when(repository.insert(eq(users))).thenReturn(users);

        // when
        List<User> returnedUsers = service.insertUser(users);

        // then
        assertNotNull(returnedUsers);
        assertEquals(users, returnedUsers);
        verify(repository, times(3)).findByUsername(eq(users.get(0).getUsername()));
        verify(repository, times(1)).insert(eq(users));
    }

    @Test(expected = InvalidFieldException.class)
    public void givenAUserWithAnIdSet_whenInsertingUser_thenExceptionIsThrown() throws Exception {
        // given
        User user = UserData.getUserMock();
        user.setId("test");

        // when
        service.insertUser(user);

        // then
        expectedException.expect(InvalidFieldException.class);
        expectedException.expectMessage("Id is an invalid parameter for the insert action");
        verify(repository, times(0)).findByUsername(eq(user.getUsername()));
        verify(repository, times(0)).insert(eq(user));
    }

    @Test
    public void givenAnyUser_whenUpdatingUser_thenUserIsUpdatedAndReturned() throws Exception {

        // given
        String id = "Id";
        User user = UserData.getUserMock();
        user.setId(id);

        when(repository.findById(eq(user.getId()))).thenReturn(Optional.of(user));
        when(repository.save(eq(user))).thenReturn(user);

        // when
        User returnedUser = service.updateUser(user);

        // then
        assertNotNull(returnedUser);
        assertEquals(user, returnedUser);
        verify(repository, times(1)).findById(eq(user.getId()));
        verify(repository, times(1)).save(eq(user));
    }

    @Test(expected = EntityNotFoundException.class)
    public void givenANonExistentUser_whenSavingUser_thenExceptionIsThrown() throws Exception {
        // given
        User user = UserData.getUserMock();

        // when
        service.updateUser(user);

        // then
        expectedException.expect(EntityNotFoundException.class);
        expectedException.expectMessage(String.format("User %s not found", user.getId()));
        verify(repository, times(1)).findById(eq(user.getId()));
        verify(repository, times(0)).save(eq(user));
    }

    @Test
    public void givenAUserId_whenSearchingUserById_thenReturnFoundUser() throws Exception {
        String id = "id";

        // given
        User user = UserData.getUserMock();
        user.setId(id);
        when(repository.findById(eq(id))).thenReturn(Optional.of(user));

        // when
        User returnedUser = service.findById(id);

        // then
        verify(repository, times(1)).findById(id);
        assertEquals(returnedUser, user);
    }

    @Test(expected = EntityNotFoundException.class)
    public void givenAnInexistentUserId_whenSearchingUserById_thenThrowAndException() throws Exception {
        String id = "id";

        // given
        when(repository.findById(eq(id))).thenReturn(Optional.empty());

        // when
        service.findById(id);

        // then
        verify(repository, times(1)).findById(id);
        expectedException.expect(EntityNotFoundException.class);
        expectedException.expectMessage(String.format("User %s not found", id));
    }

    @Test
    public void givenAnUserId_whenDeletingUserWithId_thenUserIsDeleted() throws Exception {
        String id = "id";

        // given
        User user = UserData.getUserMock();
        user.setId(id);
        when(repository.findById(eq(id))).thenReturn(Optional.of(user));
        doNothing().when(repository).deleteById(eq(id));

        // when
        service.delete(id);

        // then
        verify(repository, times(1)).findById(eq(id));
        verify(repository, times(1)).deleteById(eq(id));
    }

    @Test(expected = EntityNotFoundException.class)
    public void givenAnInvalidUserId_whenDeletingUserWithId_thenExceptionIsThrown() throws Exception {
        String id = "id";

        // given
        when(repository.findById(eq(id))).thenReturn(Optional.empty());

        // when
        service.delete(id);

        // then
        verify(repository, times(1)).findById(eq(id));
        verify(repository, times(0)).deleteById(eq(id));

        expectedException.expect(EntityNotFoundException.class);
        expectedException.expectMessage(String.format("User %s not found", id));
    }
}