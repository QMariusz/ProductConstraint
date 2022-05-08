package com.mariusz.orlowicz.domain;

import com.mariusz.orlowicz.model.User;

public class RelationLeaf implements Constraint {

    @Override
    public boolean validate(User user) {
        return user.getUserConstraintsSet()
                .contains(User.UserConstraints.RELATION);
    }
}
