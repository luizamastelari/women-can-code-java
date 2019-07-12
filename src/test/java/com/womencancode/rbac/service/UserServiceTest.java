package com.womencancode.rbac.service;

import com.womencancode.rbac.exception.DuplicatedKeyException;
import com.womencancode.rbac.exception.EntityNotFoundException;
import com.womencancode.rbac.mock.UserData;
import com.womencancode.rbac.model.User;
import com.womencancode.rbac.repository.UserRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService service;

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
    public void givenAnyUser_whenUpdatingUser_thenUserIsUpdatedAndReturned() throws Exception {

        // given
        String id = "Id";
        User user = UserData.getUserMock().withId(id);
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
        expectedException.expectMessage(String.format("User %s not found", user.getUsername()));
        verify(repository, times(1)).findById(eq(user.getId()));
        verify(repository, times(0)).save(eq(user));
    }
}