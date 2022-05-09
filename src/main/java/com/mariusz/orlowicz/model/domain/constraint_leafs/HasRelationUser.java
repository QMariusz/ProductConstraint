package com.mariusz.orlowicz.model.domain.constraint_leafs;

import com.mariusz.orlowicz.model.domain.Constraint;
import com.mariusz.orlowicz.model.domain.User;

public class HasRelationUser implements Constraint {

    @Override
    public boolean validate(User user) {
        return user.getUserConstraintsSet()
                .contains(User.UserConstraints.HAS_RELATIONS);
    }
}
