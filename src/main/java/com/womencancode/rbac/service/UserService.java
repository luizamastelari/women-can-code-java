package com.womencancode.rbac.service;

import com.womencancode.rbac.exception.DuplicatedKeyException;
import com.womencancode.rbac.exception.EntityNotFoundException;
import com.womencancode.rbac.exception.ServiceException;
import com.womencancode.rbac.model.User;
import com.womencancode.rbac.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    /**
     * Inserts a user in the database.</br>
     * If the username already exists in the database, this method will throw a {@link ServiceException}
     *
     * @param user user to be inserted
     * @return inserted user with the created id field
     * @throws ServiceException
     */
    public User insertUser(User user) throws ServiceException {

        if (repository.findByUsername(user.getUsername()).isPresent()) {
            String message = String.format("Username %s already exist", user.getUsername());
            log.error(message);
            throw new DuplicatedKeyException(message);
        }

        User savedUser = repository.insert(user);
        log.info(String.format("User inserted with id %s", user.getId()));

        return savedUser;
    }

    public User updateUser(User user) throws ServiceException {

        if (!repository.findById(user.getId()).isPresent()) {
            String message = String.format("User %s not found", user.getUsername());
            log.error(message);
            throw new EntityNotFoundException(message);
        }

        return repository.save(user);
    }
}
