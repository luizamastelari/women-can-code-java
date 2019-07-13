package com.womencancode.rbac.service;

import com.womencancode.rbac.exception.DuplicatedKeyException;
import com.womencancode.rbac.exception.EntityNotFoundException;
import com.womencancode.rbac.exception.InvalidFieldException;
import com.womencancode.rbac.exception.ServiceException;
import com.womencancode.rbac.model.User;
import com.womencancode.rbac.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    /**
     * Inserts a {@link User user} in the database.</br>
     * If the username already exists in the database, this method will throw a {@link ServiceException}
     *
     * @param user user to be inserted
     * @return inserted user with the created id field
     * @throws ServiceException
     */
    public User insertUser(User user) throws ServiceException {
        validateInsert(user);
        return repository.insert(user);
    }

    /**
     * Inserts a list of {@link User users} in the database.</br>
     * If the username already exists in the database, this method will throw a {@link ServiceException}
     *
     * @param users users to be inserted
     * @return inserted users with the created id field
     * @throws ServiceException
     */
    public List<User> insertUser(List<User> users) throws ServiceException {
        validateInsert(users);
        return repository.insert(users);
    }

    /**
     * Updates an existent user in the database. If the user doesn't exist, it throws a {@link ServiceException}
     *
     * @param user user to be updated
     * @return updated user
     * @throws ServiceException
     */
    public User updateUser(User user) throws ServiceException {
        validateUpdate(user);
        return repository.save(user);
    }

    /**
     * Retrieve all users in the given page.
     *
     * @param pageable page properties
     * @return page with user for this page
     */
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public User findById(String id) throws ServiceException {
        String message = String.format("User %s not found", id);
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(message));
    }

    private void validateInsert(List<User> users) throws ServiceException {
        for (User user : users) {
            validateInsert(user);
        }
    }

    private void validateInsert(User user) throws ServiceException {
        if (StringUtils.hasLength(user.getId()))
            throw new InvalidFieldException("Id is an invalid parameter for the insert action");

        if (repository.findByUsername(user.getUsername()).isPresent())
            throw new DuplicatedKeyException(String.format("Username %s already exist", user.getUsername()));
    }

    private void validateUpdate(User user) throws EntityNotFoundException {
        if (!repository.findById(user.getId()).isPresent())
            throw new EntityNotFoundException(String.format("User %s not found", user.getUsername()));
    }
}
