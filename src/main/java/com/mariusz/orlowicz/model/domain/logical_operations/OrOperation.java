package com.mariusz.orlowicz.model.domain.logical_operations;

import com.mariusz.orlowicz.model.domain.Constraint;
import com.mariusz.orlowicz.model.domain.User;

public class OrOperation extends LogicalOperation implements Constraint {

    @Override
    public boolean validate(User user) {
        return constraintList.stream()
                .anyMatch(x -> x.validate(user));
    }
}
