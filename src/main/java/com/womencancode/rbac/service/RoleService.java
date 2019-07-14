package com.womencancode.rbac.service;

import com.womencancode.rbac.exception.EntityNotFoundException;
import com.womencancode.rbac.exception.ServiceException;
import com.womencancode.rbac.model.Role;
import com.womencancode.rbac.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private RoleRepository repository;

    public RoleService(@Autowired RoleRepository repository) {
        this.repository = repository;
    }

    /**
     * Retrieve all roles available in the database.
     *
     * @return all roles
     */
    public List<Role> findAll() {
        return repository.findAll();
    }

    /**
     * Retrieve a {@link Role role} with the given {@code id}.<br/>
     * If the {@link Role role} was not found, a {@link ServiceException} is thrown.
     *
     * @param id id to be searched
     * @return {@link Role} with the given id
     * @throws ServiceException
     */
    public Role findById(String id) throws ServiceException {
        return repository.findById(id).orElseThrow(() -> {
            String message = String.format("Role %s not found.", id);
            return new EntityNotFoundException(message);
        });
    }
}
