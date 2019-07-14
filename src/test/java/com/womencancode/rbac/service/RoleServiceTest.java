package com.womencancode.rbac.service;

import com.womencancode.rbac.exception.EntityNotFoundException;
import com.womencancode.rbac.exception.InvalidFieldException;
import com.womencancode.rbac.mock.RoleData;
import com.womencancode.rbac.model.Role;
import com.womencancode.rbac.repository.RoleRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RoleServiceTest {

    @Mock
    private RoleRepository repository;

    @InjectMocks
    private RoleService service;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void givenAnyRole_whenInsertingRole_thenRoleIsInsertedAndReturned() throws Exception {

        // given
        Role role = RoleData.getRoleMock();
        when(repository.insert(eq(role))).thenReturn(role);

        // when
        Role returnedRole = service.insertRole(role);

        // then
        assertNotNull(returnedRole);
        assertEquals(role, returnedRole);
        verify(repository, times(1)).insert(eq(role));
    }

    @Test(expected = InvalidFieldException.class)
    public void givenARoleWithIdSet_whenInsertingRole_thenExceptionIsThrown() throws Exception {

        // given
        Role role = RoleData.getRoleMock("id");

        // when
        service.insertRole(role);

        // then
        expectedException.expectMessage("Id is an invalid parameter for the insert action");
        expectedException.expect(InvalidFieldException.class);
        verify(repository, times(0)).insert(eq(role));
    }

    @Test
    public void whenFindingAllRoles_thenAllRolesAreReturned() throws Exception {
        List<Role> roles = RoleData.getRolesMock();
        when(repository.findAll()).thenReturn(roles);

        // when
        List<Role> returnedRoles = service.findAll();

        // then
        assertNotNull(returnedRoles);
        assertEquals(roles, returnedRoles);
        verify(repository, times(1)).findAll();
    }

    @Test
    public void givenAnRoleId_whenFindingARoleWithId_thenARoleIsReturned() throws Exception {

        // given
        String id = "id";
        Role role = RoleData.getRoleMock(id);
        when(repository.findById(eq(id))).thenReturn(Optional.of(role));

        // when
        Role returnedRole = service.findById(id);

        // then
        assertNotNull(returnedRole);
        assertEquals(role, returnedRole);
        verify(repository, times(1)).findById(eq(id));
    }

    @Test(expected = EntityNotFoundException.class)
    public void givenAnInexistentRoleId_whenFindingARoleWithId_thenExceptionIsThrown() throws Exception {
        // given
        String id = "non-existent";
        when(repository.findById(eq(id))).thenReturn(Optional.empty());

        // when
        service.findById(id);

        // then
        expectedException.expectMessage(String.format("Role %s not found.", id));
        expectedException.expect(EntityNotFoundException.class);
        verify(repository, times(1)).findById(eq(id));
    }
}