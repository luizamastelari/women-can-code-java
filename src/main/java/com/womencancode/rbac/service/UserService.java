package com.womencancode.rbac.service;

import com.womencancode.rbac.exception.EntityNotFoundException;
import com.womencancode.rbac.exception.ServiceException;
import com.womencancode.rbac.model.User;
import com.womencancode.rbac.repository.UserRepository;
import com.womencancode.rbac.validator.ModelValidator;
import com.womencancode.rbac.validator.ValidatorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository repository;
    private ModelValidator validator;

    public UserService(@Autowired UserRepository repository) {
        this.repository = repository;
        this.validator = ValidatorFactory.getValidator(User.class, repository);
    }

    /**
     * Inserts a {@link User user} in the database.</br>
     * If the username already exists in the database, this method will throw a {@link ServiceException}
     *
     * @param user user to be inserted
     * @return inserted user with the created id field
     * @throws ServiceException
     */
    public User insertUser(User user) throws ServiceException {
        validator.validateInsert(user);
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
        validator.validateInsert(users);
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
        validator.validateId(user.getId());
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

    public void delete(String id) throws ServiceException {
        validator.validateId(id);
        repository.deleteById(id);
    }
}
