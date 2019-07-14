package com.womencancode.rbac.validator;

import com.womencancode.rbac.exception.DuplicatedKeyException;
import com.womencancode.rbac.exception.ServiceException;
import com.womencancode.rbac.model.User;
import com.womencancode.rbac.repository.UserRepository;

public class UserValidator extends ModelValidator<User> {

    public UserValidator(UserRepository repository) {
        super(repository);
    }

    @Override
    public void customInsertValidation(User model) throws ServiceException {
        UserRepository repository = (UserRepository) getRepository();
        if (repository.findByUsername(model.getUsername()).isPresent())
            throw new DuplicatedKeyException(String.format("Username %s already exist", model.getUsername()));
    }
}
