package com.womencancode.rbac.validator;

import com.womencancode.rbac.exception.ServiceException;
import com.womencancode.rbac.model.Role;
import com.womencancode.rbac.repository.RoleRepository;

public class RoleValidator extends ModelValidator<Role> {
    public RoleValidator(RoleRepository repository) {
        super(repository);
    }

    @Override
    public void customInsertValidation(Role model) throws ServiceException {
        // Do nothing
    }
}
