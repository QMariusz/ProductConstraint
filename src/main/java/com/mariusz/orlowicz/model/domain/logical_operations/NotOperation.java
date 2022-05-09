package com.mariusz.orlowicz.model.domain.logical_operations;

import com.mariusz.orlowicz.model.domain.Constraint;
import com.mariusz.orlowicz.model.domain.User;

public class NotOperation extends LogicalOperation implements Constraint {

    @Override
    public boolean validate(User user) {
        return constraintList.stream()
                .noneMatch(x -> x.validate(user));
    }
}
